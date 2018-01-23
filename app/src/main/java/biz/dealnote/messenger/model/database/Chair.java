package biz.dealnote.messenger.model.database;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ruslan Kolbasa on 20.09.2017.
 * phoenix
 */
public final class Chair implements Parcelable {

    private final int id;

    private final String title;

    public Chair(int id, String title) {
        this.id = id;
        this.title = title;
    }

    private Chair(Parcel in) {
        id = in.readInt();
        title = in.readString();
    }

    public static final Creator<Chair> CREATOR = new Creator<Chair>() {
        @Override
        public Chair createFromParcel(Parcel in) {
            return new Chair(in);
        }

        @Override
        public Chair[] newArray(int size) {
            return new Chair[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
    }
}