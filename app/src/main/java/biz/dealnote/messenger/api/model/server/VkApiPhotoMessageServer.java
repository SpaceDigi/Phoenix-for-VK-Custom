package biz.dealnote.messenger.api.model.server;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Атрибуты сервера для аплоада фотографий в личное сообщение
 */
public class VkApiPhotoMessageServer implements Parcelable, UploadServer {

    /**
     * Адрес сервера
     */
    @SerializedName("upload_url")
    public String upload_url;

    /**
     * id альбома
     */
    @SerializedName("album_id")
    public int album_id;

    /**
     * id текущего пользователя
     */
    @SerializedName("user_id")
    public int user_id;

    public VkApiPhotoMessageServer() {}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(upload_url);
        dest.writeInt(album_id);
        dest.writeInt(user_id);
    }

    public VkApiPhotoMessageServer(Parcel in) {
        this.upload_url = in.readString();
        this.album_id = in.readInt();
        this.user_id = in.readInt();
    }

    public static final Creator<VkApiPhotoMessageServer> CREATOR = new Creator<VkApiPhotoMessageServer>() {
        @Override
        public VkApiPhotoMessageServer createFromParcel(Parcel in) {
            return new VkApiPhotoMessageServer(in);
        }

        @Override
        public VkApiPhotoMessageServer[] newArray(int size) {
            return new VkApiPhotoMessageServer[size];
        }
    };

    @Override
    public String toString() {
        return "VkApiPhotoMessageServer{" +
                "upload_url='" + upload_url + '\'' +
                ", album_id=" + album_id +
                ", user_id=" + user_id +
                '}';
    }

    @Override
    public String getUrl() {
        return upload_url;
    }
}
