package biz.dealnote.messenger.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Collections;
import java.util.List;

/**
 * Created by Ruslan Kolbasa on 15.06.2017.
 * phoenix
 */
public final class GroupSettings implements Parcelable {

    private String title;

    private String description;

    private String address;

    private IdOption category;

    private IdOption subcategory;

    private List<IdOption> availableCategories;

    private String website;

    private Day dateCreated;

    private boolean feedbackCommentsEnabled;

    private boolean obsceneFilterEnabled;

    private boolean obsceneStopwordsEnabled;

    private String obsceneWords;

    public GroupSettings(){
        this.availableCategories = Collections.emptyList();
    }

    protected GroupSettings(Parcel in) {
        title = in.readString();
        description = in.readString();
        address = in.readString();
        category = in.readParcelable(IdOption.class.getClassLoader());
        subcategory = in.readParcelable(IdOption.class.getClassLoader());
        availableCategories = in.createTypedArrayList(IdOption.CREATOR);
        website = in.readString();
        feedbackCommentsEnabled = in.readByte() != 0;
        obsceneFilterEnabled = in.readByte() != 0;
        obsceneStopwordsEnabled = in.readByte() != 0;
        obsceneWords = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(address);
        dest.writeParcelable(category, flags);
        dest.writeParcelable(subcategory, flags);
        dest.writeTypedList(availableCategories);
        dest.writeString(website);
        dest.writeByte((byte) (feedbackCommentsEnabled ? 1 : 0));
        dest.writeByte((byte) (obsceneFilterEnabled ? 1 : 0));
        dest.writeByte((byte) (obsceneStopwordsEnabled ? 1 : 0));
        dest.writeString(obsceneWords);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GroupSettings> CREATOR = new Creator<GroupSettings>() {
        @Override
        public GroupSettings createFromParcel(Parcel in) {
            return new GroupSettings(in);
        }

        @Override
        public GroupSettings[] newArray(int size) {
            return new GroupSettings[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }



    public static final class SectionState {

        public static final int DISABLED = 0;
        public static final int OPEN = 1;
        public static final int LIMITED = 2;
        public static final int CLOSED = 3;

    }

    public String getAddress() {
        return address;
    }

    public GroupSettings setDescription(String description) {
        this.description = description;
        return this;
    }

    public GroupSettings setCategory(IdOption category) {
        this.category = category;
        return this;
    }

    public IdOption getCategory() {
        return category;
    }

    public GroupSettings setAddress(String address) {
        this.address = address;
        return this;
    }

    public GroupSettings setTitle(String title) {
        this.title = title;
        return this;
    }

    public GroupSettings setAvailableCategories(List<IdOption> availableCategories) {
        this.availableCategories = availableCategories;
        return this;
    }

    public List<IdOption> getAvailableCategories() {
        return availableCategories;
    }

    public GroupSettings setSubcategory(IdOption subcategory) {
        this.subcategory = subcategory;
        return this;
    }

    public IdOption getSubcategory() {
        return subcategory;
    }

    public GroupSettings setWebsite(String website) {
        this.website = website;
        return this;
    }

    public String getWebsite() {
        return website;
    }

    public GroupSettings setDateCreated(Day dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public GroupSettings setFeedbackCommentsEnabled(boolean feedbackCommentsEnabled) {
        this.feedbackCommentsEnabled = feedbackCommentsEnabled;
        return this;
    }

    public boolean isFeedbackCommentsEnabled() {
        return feedbackCommentsEnabled;
    }

    public Day getDateCreated() {
        return dateCreated;
    }

    public boolean isObsceneFilterEnabled() {
        return obsceneFilterEnabled;
    }

    public boolean isObsceneStopwordsEnabled() {
        return obsceneStopwordsEnabled;
    }

    public GroupSettings setObsceneFilterEnabled(boolean obsceneFilterEnabled) {
        this.obsceneFilterEnabled = obsceneFilterEnabled;
        return this;
    }

    public GroupSettings setObsceneStopwordsEnabled(boolean obsceneStopwordsEnabled) {
        this.obsceneStopwordsEnabled = obsceneStopwordsEnabled;
        return this;
    }

    public String getObsceneWords() {
        return obsceneWords;
    }

    public GroupSettings setObsceneWords(String obsceneWords) {
        this.obsceneWords = obsceneWords;
        return this;
    }
}