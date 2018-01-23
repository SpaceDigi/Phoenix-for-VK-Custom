package biz.dealnote.messenger.db.impl;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import biz.dealnote.messenger.db.DatabaseIdRange;
import biz.dealnote.messenger.db.MessengerContentProvider;
import biz.dealnote.messenger.db.column.NotificationColumns;
import biz.dealnote.messenger.db.interfaces.IFeedbackStore;
import biz.dealnote.messenger.db.model.entity.OwnerEntities;
import biz.dealnote.messenger.db.model.entity.feedback.CopyEntity;
import biz.dealnote.messenger.db.model.entity.feedback.FeedbackEntity;
import biz.dealnote.messenger.db.model.entity.feedback.LikeCommentEntity;
import biz.dealnote.messenger.db.model.entity.feedback.LikeEntity;
import biz.dealnote.messenger.db.model.entity.feedback.MentionCommentEntity;
import biz.dealnote.messenger.db.model.entity.feedback.MentionEntity;
import biz.dealnote.messenger.db.model.entity.feedback.NewCommentEntity;
import biz.dealnote.messenger.db.model.entity.feedback.PostFeedbackEntity;
import biz.dealnote.messenger.db.model.entity.feedback.ReplyCommentEntity;
import biz.dealnote.messenger.db.model.entity.feedback.UsersEntity;
import biz.dealnote.messenger.model.criteria.NotificationsCriteria;
import io.reactivex.Single;

import static biz.dealnote.messenger.util.Objects.isNull;
import static biz.dealnote.messenger.util.Objects.nonNull;
import static biz.dealnote.messenger.util.Utils.safeCountOf;

/**
 * Created by ruslan.kolbasa on 13-Jun-16.
 * phoenix
 */
class FeedbackStore extends AbsStore implements IFeedbackStore {

    FeedbackStore(@NonNull AppStores context) {
        super(context);
    }

    @Override
    public Single<int[]> insert(int accountId, List<FeedbackEntity> dbos, OwnerEntities owners, boolean clearBefore) {
        return Single.create(emitter -> {
            final Uri uri = MessengerContentProvider.getNotificationsContentUriFor(accountId);
            final ArrayList<ContentProviderOperation> operations = new ArrayList<>();

            if (clearBefore) {
                operations.add(ContentProviderOperation
                        .newDelete(uri)
                        .build());
            }

            int[] indexes = new int[dbos.size()];

            for (int i = 0; i < dbos.size(); i++) {
                FeedbackEntity dbo = dbos.get(i);

                ContentValues cv = new ContentValues();
                cv.put(NotificationColumns.DATE, dbo.getDate());
                cv.put(NotificationColumns.TYPE, typeForClass(dbo.getClass()));
                cv.put(NotificationColumns.DATA, GSON.toJson(dbo));

                int index = addToListAndReturnIndex(operations, ContentProviderOperation
                        .newInsert(uri)
                        .withValues(cv)
                        .build());

                indexes[i] = index;
            }

            OwnersRepositiry.appendOwnersInsertOperations(operations, accountId, owners);

            ContentProviderResult[] results = getContentResolver().applyBatch(MessengerContentProvider.AUTHORITY, operations);

            final int[] ids = new int[dbos.size()];

            for (int i = 0; i < indexes.length; i++) {
                int index = indexes[i];
                ContentProviderResult result = results[index];
                ids[i] = extractId(result);
            }

            emitter.onSuccess(ids);
        });
    }

    @Override
    public Single<List<FeedbackEntity>> findByCriteria(@NonNull NotificationsCriteria criteria) {
        return Single.create(e -> {
            DatabaseIdRange range = criteria.getRange();
            Uri uri = MessengerContentProvider.getNotificationsContentUriFor(criteria.getAccountId());

            Cursor cursor;
            if (range != null) {
                String where = NotificationColumns._ID + " >= ? AND " + NotificationColumns._ID + " <= ?";
                String[] args = new String[]{String.valueOf(range.getFirst()), String.valueOf(range.getLast())};
                cursor = getContext().getContentResolver().query(uri, null, where, args, NotificationColumns.DATE + " DESC");
            } else {
                cursor = getContext().getContentResolver().query(uri, null, null, null, NotificationColumns.DATE + " DESC");
            }

            List<FeedbackEntity> dtos = new ArrayList<>(safeCountOf(cursor));
            if (nonNull(cursor)) {
                while (cursor.moveToNext()) {
                    if (e.isDisposed()) {
                        break;
                    }

                    FeedbackEntity dto = mapDto(cursor);
                    dtos.add(dto);
                }

                cursor.close();
            }

            e.onSuccess(dtos);
        });
    }

    private static final int LIKE = 1;
    private static final int LIKE_COMMENT = 2;
    private static final int COPY = 3;
    private static final int MENTION = 4;
    private static final int MENTION_COMMENT = 5;
    private static final int WALL_PUBLISH = 6;
    private static final int NEW_COMMENT = 7;
    private static final int REPLY_COMMENT = 8;
    private static final int USERS = 9;

    private static final Map<Class, Integer> TYPES = new HashMap<>(8);

    static {
        TYPES.put(LikeEntity.class, LIKE);
        TYPES.put(LikeCommentEntity.class, LIKE_COMMENT);
        TYPES.put(CopyEntity.class, COPY);
        TYPES.put(MentionEntity.class, MENTION);
        TYPES.put(MentionCommentEntity.class, MENTION_COMMENT);
        TYPES.put(PostFeedbackEntity.class, WALL_PUBLISH);
        TYPES.put(NewCommentEntity.class, NEW_COMMENT);
        TYPES.put(ReplyCommentEntity.class, REPLY_COMMENT);
        TYPES.put(UsersEntity.class, USERS);
    }

    private static int typeForClass(Class<? extends FeedbackEntity> c) {
        Integer internalType = TYPES.get(c);

        if(isNull(internalType)){
            throw new UnsupportedOperationException("Unsupported type: " + c);
        }

        return internalType;
    }

    private static Class<? extends FeedbackEntity> classForType(int dbtype) {
        switch (dbtype) {
            case LIKE:
                return LikeEntity.class;
            case LIKE_COMMENT:
                return LikeCommentEntity.class;
            case COPY:
                return CopyEntity.class;
            case MENTION:
                return MentionEntity.class;
            case MENTION_COMMENT:
                return MentionCommentEntity.class;
            case WALL_PUBLISH:
                return PostFeedbackEntity.class;
            case NEW_COMMENT:
                return NewCommentEntity.class;
            case REPLY_COMMENT:
                return ReplyCommentEntity.class;
            case USERS:
                return UsersEntity.class;
        }

        throw new UnsupportedOperationException("Unsupported type: " + dbtype);
    }

    private FeedbackEntity mapDto(Cursor cursor) {
        int dbtype = cursor.getInt(cursor.getColumnIndex(NotificationColumns.TYPE));
        String data = cursor.getString(cursor.getColumnIndex(NotificationColumns.DATA));

        Class<? extends FeedbackEntity> feedbackClass = classForType(dbtype);
        return GSON.fromJson(data, feedbackClass);
    }
}