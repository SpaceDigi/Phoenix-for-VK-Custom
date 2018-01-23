package biz.dealnote.messenger.model;

import android.os.Parcel;
import android.os.Parcelable;

import biz.dealnote.messenger.api.model.Identificable;

public final class Account implements Parcelable, Identificable {

    private int id;
    private Owner owner;

    public Account(int id, Owner owner) {
        this.id = id;
        this.owner = owner;
    }

    private Account(Parcel in) {
        id = in.readInt();

        ParcelableOwnerWrapper wrapper = in.readParcelable(ParcelableOwnerWrapper.class.getClassLoader());
        this.owner = wrapper.get();
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeParcelable(new ParcelableOwnerWrapper(owner), flags);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account that = (Account) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public String getDisplayName(){
        return owner == null ? String.valueOf(id) : owner.getFullName();
    }

    public Owner getOwner() {
        return owner;
    }

    @Override
    public int getId() {
        return id;
    }
}