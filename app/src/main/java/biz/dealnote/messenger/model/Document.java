package biz.dealnote.messenger.model;

import android.os.Parcel;
import android.os.Parcelable;

import biz.dealnote.messenger.util.Objects;

import static biz.dealnote.messenger.util.Objects.nonNull;
import static biz.dealnote.messenger.util.Utils.safeIsEmpty;

/**
 * Created by admin on 04.12.2016.
 * phoenix
 */
public class Document extends AbsModel implements Parcelable {

    private final int id;

    private final int ownerId;

    private String title;

    private long size;

    private String ext;

    private String url;

    private long date;

    @DocType
    private int type;

    private String accessKey;

    private PhotoSizes photoPreview;

    private VideoPreview videoPreview;

    private Graffiti graffiti;

    public Document(int id, int ownerId) {
        this.id = id;
        this.ownerId = ownerId;
    }

    protected Document(Parcel in) {
        super(in);
        id = in.readInt();
        ownerId = in.readInt();
        title = in.readString();
        size = in.readLong();
        ext = in.readString();
        url = in.readString();
        date = in.readLong();
        //noinspection ResourceType
        type = in.readInt();
        accessKey = in.readString();
        photoPreview = in.readParcelable(PhotoSizes.class.getClassLoader());
        videoPreview = in.readParcelable(VideoPreview.class.getClassLoader());
        graffiti = in.readParcelable(Graffiti.class.getClassLoader());
    }

    public String getPreviewWithSize(@PhotoSize int size, boolean excludeNonAspectRatio){
        return Objects.isNull(photoPreview) ? null : photoPreview.getUrlForSize(size, excludeNonAspectRatio);
    }

    public PhotoSizes getPhotoPreview() {
        return photoPreview;
    }

    public Document setPhotoPreview(PhotoSizes photoPreview) {
        this.photoPreview = photoPreview;
        return this;
    }

    public VideoPreview getVideoPreview() {
        return videoPreview;
    }

    public Document setVideoPreview(VideoPreview videoPreview) {
        this.videoPreview = videoPreview;
        return this;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(id);
        dest.writeInt(ownerId);
        dest.writeString(title);
        dest.writeLong(size);
        dest.writeString(ext);
        dest.writeString(url);
        dest.writeLong(date);
        dest.writeInt(type);
        dest.writeString(accessKey);
        dest.writeParcelable(photoPreview, flags);
        dest.writeParcelable(videoPreview, flags);
        dest.writeParcelable(graffiti, flags);
    }

    public static final Creator<Document> CREATOR = new Creator<Document>() {
        @Override
        public Document createFromParcel(Parcel in) {
            return new Document(in);
        }

        @Override
        public Document[] newArray(int size) {
            return new Document[size];
        }
    };

    public Graffiti getGraffiti() {
        return graffiti;
    }

    public Document setGraffiti(Graffiti graffiti) {
        this.graffiti = graffiti;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getId() {
        return id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public String getTitle() {
        return title;
    }

    public Document setTitle(String title) {
        this.title = title;
        return this;
    }

    public long getSize() {
        return size;
    }

    public Document setSize(long size) {
        this.size = size;
        return this;
    }

    public String getExt() {
        return ext;
    }

    public Document setExt(String ext) {
        this.ext = ext;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Document setUrl(String url) {
        this.url = url;
        return this;
    }

    public long getDate() {
        return date;
    }

    public Document setDate(long date) {
        this.date = date;
        return this;
    }

    public int getType() {
        return type;
    }

    public Document setType(int type) {
        this.type = type;
        return this;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public Document setAccessKey(String accessKey) {
        this.accessKey = accessKey;
        return this;
    }

    public String generateWebLink() {
        return String.format("vk.com/doc%s_%s", ownerId, id);
    }

    public boolean isGif() {
        return "gif".equals(ext);
    }

    public boolean hasValidGifVideoLink() {
        return nonNull(videoPreview) && !safeIsEmpty(videoPreview.src);
    }

    public static class Graffiti extends AbsModel implements Parcelable {

        private String src;

        private int width;

        private int height;

        public Graffiti(){

        }

        protected Graffiti(Parcel in) {
            super(in);
            src = in.readString();
            width = in.readInt();
            height = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeString(src);
            dest.writeInt(width);
            dest.writeInt(height);
        }

        public static final Creator<Graffiti> CREATOR = new Creator<Graffiti>() {
            @Override
            public Graffiti createFromParcel(Parcel in) {
                return new Graffiti(in);
            }

            @Override
            public Graffiti[] newArray(int size) {
                return new Graffiti[size];
            }
        };

        public String getSrc() {
            return src;
        }

        public Graffiti setSrc(String src) {
            this.src = src;
            return this;
        }

        public int getWidth() {
            return width;
        }

        public Graffiti setWidth(int width) {
            this.width = width;
            return this;
        }

        public int getHeight() {
            return height;
        }

        public Graffiti setHeight(int height) {
            this.height = height;
            return this;
        }

        @Override
        public int describeContents() {
            return 0;
        }
    }

    public static class VideoPreview extends AbsModel implements Parcelable {

        private String src;

        private int width;

        private int height;

        private long fileSize;

        public VideoPreview(){

        }

        protected VideoPreview(Parcel in) {
            super(in);
            src = in.readString();
            width = in.readInt();
            height = in.readInt();
            fileSize = in.readLong();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeString(src);
            dest.writeInt(width);
            dest.writeInt(height);
            dest.writeLong(fileSize);
        }

        public static final Creator<VideoPreview> CREATOR = new Creator<VideoPreview>() {
            @Override
            public VideoPreview createFromParcel(Parcel in) {
                return new VideoPreview(in);
            }

            @Override
            public VideoPreview[] newArray(int size) {
                return new VideoPreview[size];
            }
        };

        public String getSrc() {
            return src;
        }

        public VideoPreview setSrc(String src) {
            this.src = src;
            return this;
        }

        public VideoPreview setFileSize(long fileSize) {
            this.fileSize = fileSize;
            return this;
        }

        public long getFileSize() {
            return fileSize;
        }

        public int getWidth() {
            return width;
        }

        public VideoPreview setWidth(int width) {
            this.width = width;
            return this;
        }

        public int getHeight() {
            return height;
        }

        public VideoPreview setHeight(int height) {
            this.height = height;
            return this;
        }

        @Override
        public int describeContents() {
            return 0;
        }
    }
}
