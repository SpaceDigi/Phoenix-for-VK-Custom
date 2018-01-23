package biz.dealnote.messenger.upload.task;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import biz.dealnote.messenger.Injection;
import biz.dealnote.messenger.api.Apis;
import biz.dealnote.messenger.api.WeakPercentageListener;
import biz.dealnote.messenger.api.model.VKApiPhoto;
import biz.dealnote.messenger.api.model.server.UploadServer;
import biz.dealnote.messenger.api.model.upload.UploadPhotoToWallDto;
import biz.dealnote.messenger.db.AttachToType;
import biz.dealnote.messenger.domain.IAttachmentsRepository;
import biz.dealnote.messenger.domain.mappers.Dto2Model;
import biz.dealnote.messenger.model.Photo;
import biz.dealnote.messenger.upload.BaseUploadResponse;
import biz.dealnote.messenger.upload.Method;
import biz.dealnote.messenger.upload.UploadCallback;
import biz.dealnote.messenger.upload.UploadDestination;
import biz.dealnote.messenger.upload.UploadObject;
import biz.dealnote.messenger.util.IOUtils;
import biz.dealnote.messenger.util.Objects;
import retrofit2.Call;

import static biz.dealnote.messenger.util.Utils.safeCountOf;

public class PhotoWallUploadTask extends AbstractUploadTask<PhotoWallUploadTask.Response> {

    private Integer userId;
    private Integer groupId;

    public PhotoWallUploadTask(@NonNull Context context, @NonNull UploadCallback callback,
                               @NonNull UploadObject uploadObject, @Nullable UploadServer server) {
        super(context, callback, uploadObject, server);
        int subjectOwnerId = uploadObject.getDestination().getOwnerId();
        userId = subjectOwnerId > 0 ? subjectOwnerId : null;
        groupId = subjectOwnerId < 0 ? Math.abs(subjectOwnerId) : null;
    }

    @Override
    protected Response doUpload(@Nullable UploadServer server, @NonNull UploadObject uploadObject) throws CancelException {
        int accountId = uploadObject.getAccountId();

        Response result = new Response();

        InputStream is = null;
        try {
            is = openStream(getContext(), uploadObject.getFileUri(), uploadObject.getSize());

            if (server == null) {
                server = Apis.get()
                        .vkDefault(accountId)
                        .photos()
                        .getWallUploadServer(groupId)
                        .blockingGet();

                result.setServer(server);
            }

            assertCancel(this);

            String serverUrl = server.getUrl();

            Call<UploadPhotoToWallDto> call = Apis.get()
                    .uploads()
                    .uploadPhotoToWall(serverUrl, is, new WeakPercentageListener(this));

            registerCall(call);

            UploadPhotoToWallDto entity;

            try {
                entity = call.execute().body();
            } catch (Exception e) {
                result.setError(e);
                return result;
            } finally {
                unregisterCall(call);
            }

            assertCancel(this);

            List<VKApiPhoto> photos = Apis.get()
                    .vkDefault(accountId)
                    .photos()
                    .saveWallPhoto(userId, groupId, entity.photo, entity.server, entity.hash, null, null, null)
                    .blockingGet();

            assertCancel(this);

            if (safeCountOf(photos) == 1) {
                VKApiPhoto dto = photos.get(0);

                if(uploadObject.isAutoCommit()){
                    attachIntoDatabase(dto);
                }

                result.photo = Dto2Model.transform(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(e);
        } finally {
            IOUtils.closeStreamQuietly(is);
        }

        return result;
    }

    private void attachIntoDatabase(@NonNull VKApiPhoto dto) {
        Photo photo = Dto2Model.transform(dto);

        int aid = uploadObject.getAccountId();
        UploadDestination dest = uploadObject.getDestination();

        IAttachmentsRepository attachmentsRepository = Injection.provideAttachmentsRepository();

        switch (dest.getMethod()) {
            case Method.PHOTO_TO_COMMENT:
                attachmentsRepository
                        .attach(aid, AttachToType.COMMENT, dest.getId(), Collections.singletonList(photo))
                        .blockingAwait();
                break;

            case Method.PHOTO_TO_WALL:
                attachmentsRepository
                        .attach(aid, AttachToType.POST, dest.getId(), Collections.singletonList(photo))
                        .blockingAwait();
                break;
        }
    }

    public static class Response extends BaseUploadResponse {

        public Photo photo;

        @Override
        public boolean isSuccess() {
            return Objects.nonNull(photo);
        }
    }
}