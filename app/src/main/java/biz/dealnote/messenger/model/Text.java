package biz.dealnote.messenger.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import biz.dealnote.messenger.util.Objects;
import biz.dealnote.messenger.util.ParcelUtils;

/**
 * Created by ruslan.kolbasa on 14.12.2016.
 * phoenix
 */
public final class Text implements Parcelable {

    @StringRes
    private Integer res;

    private String text;

    public Text(Integer res) {
        this.res = res;
    }

    public Text(String text) {
        this.text = text;
    }

    protected Text(Parcel in) {
        res = ParcelUtils.readObjectInteger(in);
        text = in.readString();
    }

    public static final Creator<Text> CREATOR = new Creator<Text>() {
        @Override
        public Text createFromParcel(Parcel in) {
            return new Text(in);
        }

        @Override
        public Text[] newArray(int size) {
            return new Text[size];
        }
    };

    public String getText(@NonNull Context context){
        return Objects.isNull(res) ? text : context.getString(res);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        ParcelUtils.writeObjectInteger(parcel, res);
        parcel.writeString(text);
    }
}
