package biz.dealnote.messenger.model;

import android.os.Parcel;
import android.os.Parcelable;

import biz.dealnote.messenger.api.model.Identificable;

/**
 * Created by ruslan.kolbasa on 29.11.2016.
 * phoenix
 */
public class PhotoAlbum extends AbsModel implements Parcelable, Identificable, ISomeones {

    private final int id;

    private final int ownerId;

    private int size;

    private String title;

    private String description;

    private boolean canUpload;

    private long updatedTime;

    private long createdTime;

    private PhotoSizes sizes;

    private boolean uploadByAdminsOnly;

    private boolean commentsDisabled;

    private SimplePrivacy privacyView;

    private SimplePrivacy privacyComment;

    public PhotoAlbum(int id, int ownerId) {
        this.id = id;
        this.ownerId = ownerId;
    }

    protected PhotoAlbum(Parcel in) {
        super(in);
        id = in.readInt();
        ownerId = in.readInt();
        size = in.readInt();
        title = in.readString();
        description = in.readString();
        canUpload = in.readByte() != 0;
        updatedTime = in.readLong();
        createdTime = in.readLong();
        sizes = in.readParcelable(PhotoSizes.class.getClassLoader());
        uploadByAdminsOnly = in.readByte() != 0;
        commentsDisabled = in.readByte() != 0;
        privacyView = in.readParcelable(SimplePrivacy.class.getClassLoader());
        privacyComment = in.readParcelable(SimplePrivacy.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(id);
        dest.writeInt(ownerId);
        dest.writeInt(size);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeByte((byte) (canUpload ? 1 : 0));
        dest.writeLong(updatedTime);
        dest.writeLong(createdTime);
        dest.writeParcelable(sizes, flags);
        dest.writeByte((byte) (uploadByAdminsOnly ? 1 : 0));
        dest.writeByte((byte) (commentsDisabled ? 1 : 0));
        dest.writeParcelable(privacyView, flags);
        dest.writeParcelable(privacyComment, flags);
    }

    public static final Creator<PhotoAlbum> CREATOR = new Creator<PhotoAlbum>() {
        @Override
        public PhotoAlbum createFromParcel(Parcel in) {
            return new PhotoAlbum(in);
        }

        @Override
        public PhotoAlbum[] newArray(int size) {
            return new PhotoAlbum[size];
        }
    };

    public int getId() {
        return id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public int getSize() {
        return size;
    }

    public PhotoAlbum setSize(int size) {
        this.size = size;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public PhotoAlbum setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public PhotoAlbum setDescription(String description) {
        this.description = description;
        return this;
    }

    public boolean isCanUpload() {
        return canUpload;
    }

    public PhotoAlbum setCanUpload(boolean canUpload) {
        this.canUpload = canUpload;
        return this;
    }

    public long getUpdatedTime() {
        return updatedTime;
    }

    public PhotoAlbum setUpdatedTime(long updatedTime) {
        this.updatedTime = updatedTime;
        return this;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public PhotoAlbum setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public PhotoSizes getSizes() {
        return sizes;
    }

    public PhotoAlbum setSizes(PhotoSizes sizes) {
        this.sizes = sizes;
        return this;
    }

    public boolean isUploadByAdminsOnly() {
        return uploadByAdminsOnly;
    }

    public PhotoAlbum setUploadByAdminsOnly(boolean uploadByAdminsOnly) {
        this.uploadByAdminsOnly = uploadByAdminsOnly;
        return this;
    }

    public boolean isCommentsDisabled() {
        return commentsDisabled;
    }

    public PhotoAlbum setCommentsDisabled(boolean commentsDisabled) {
        this.commentsDisabled = commentsDisabled;
        return this;
    }

    public PhotoAlbum setPrivacyComment(SimplePrivacy privacyComment) {
        this.privacyComment = privacyComment;
        return this;
    }

    public SimplePrivacy getPrivacyView() {
        return privacyView;
    }

    public SimplePrivacy getPrivacyComment() {
        return privacyComment;
    }

    public PhotoAlbum setPrivacyView(SimplePrivacy privacyView) {
        this.privacyView = privacyView;
        return this;
    }

    public boolean isSystem() {
        return id < 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
