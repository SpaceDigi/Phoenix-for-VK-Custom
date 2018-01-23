package biz.dealnote.messenger.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 24.09.2016.
 * phoenix
 */
public class VideoSize implements Parcelable {

    private int width;
    private int height;

    public VideoSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    protected VideoSize(Parcel in) {
        width = in.readInt();
        height = in.readInt();
    }

    public static final Creator<VideoSize> CREATOR = new Creator<VideoSize>() {
        @Override
        public VideoSize createFromParcel(Parcel in) {
            return new VideoSize(in);
        }

        @Override
        public VideoSize[] newArray(int size) {
            return new VideoSize[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public int getWidth() {
        return width;
    }

    public VideoSize setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public VideoSize setHeight(int height) {
        this.height = height;
        return this;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(width);
        parcel.writeInt(height);
    }

    @Override
    public String toString() {
        return "[" + width + "*" + height + "]";
    }
}