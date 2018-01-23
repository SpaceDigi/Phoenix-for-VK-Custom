package biz.dealnote.messenger.db.model.entity;

/**
 * Created by Ruslan Kolbasa on 04.09.2017.
 * phoenix
 */
public class VideoEntity extends Entity {

    private final int id;

    private final int ownerId;

    private int albumId;

    private String title;

    private String description;

    private String link;

    private long date;

    private long addingDate;

    private int views;

    private String player;

    private String photo130;

    private String photo320;

    private String photo800;

    private String accessKey;

    private int commentsCount;

    private boolean userLikes;

    private int likesCount;

    private String mp4link240;

    private String mp4link360;

    private String mp4link480;

    private String mp4link720;

    private String mp4link1080;

    private String externalLink;

    private String platform;

    private boolean repeat;

    private int duration;

    private PrivacyEntity privacyView;

    private PrivacyEntity privacyComment;

    private boolean canEdit;

    private boolean canAdd;

    private boolean canComment;

    private boolean canRepost;

    public VideoEntity(int id, int ownerId) {
        this.id = id;
        this.ownerId = ownerId;
    }

    public int getId() {
        return id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public VideoEntity setAlbumId(int albumId) {
        this.albumId = albumId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public VideoEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public VideoEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getLink() {
        return link;
    }

    public VideoEntity setLink(String link) {
        this.link = link;
        return this;
    }

    public long getDate() {
        return date;
    }

    public VideoEntity setDate(long date) {
        this.date = date;
        return this;
    }

    public long getAddingDate() {
        return addingDate;
    }

    public VideoEntity setAddingDate(long addingDate) {
        this.addingDate = addingDate;
        return this;
    }

    public int getViews() {
        return views;
    }

    public VideoEntity setViews(int views) {
        this.views = views;
        return this;
    }

    public String getPlayer() {
        return player;
    }

    public VideoEntity setPlayer(String player) {
        this.player = player;
        return this;
    }

    public String getPhoto130() {
        return photo130;
    }

    public VideoEntity setPhoto130(String photo130) {
        this.photo130 = photo130;
        return this;
    }

    public String getPhoto320() {
        return photo320;
    }

    public VideoEntity setPhoto320(String photo320) {
        this.photo320 = photo320;
        return this;
    }

    public String getPhoto800() {
        return photo800;
    }

    public VideoEntity setPhoto800(String photo800) {
        this.photo800 = photo800;
        return this;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public VideoEntity setAccessKey(String accessKey) {
        this.accessKey = accessKey;
        return this;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public VideoEntity setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
        return this;
    }

    public boolean isUserLikes() {
        return userLikes;
    }

    public VideoEntity setUserLikes(boolean userLikes) {
        this.userLikes = userLikes;
        return this;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public VideoEntity setLikesCount(int likesCount) {
        this.likesCount = likesCount;
        return this;
    }

    public String getMp4link240() {
        return mp4link240;
    }

    public VideoEntity setMp4link240(String mp4link240) {
        this.mp4link240 = mp4link240;
        return this;
    }

    public String getMp4link360() {
        return mp4link360;
    }

    public VideoEntity setMp4link360(String mp4link360) {
        this.mp4link360 = mp4link360;
        return this;
    }

    public String getMp4link480() {
        return mp4link480;
    }

    public VideoEntity setMp4link480(String mp4link480) {
        this.mp4link480 = mp4link480;
        return this;
    }

    public String getMp4link720() {
        return mp4link720;
    }

    public VideoEntity setMp4link720(String mp4link720) {
        this.mp4link720 = mp4link720;
        return this;
    }

    public String getMp4link1080() {
        return mp4link1080;
    }

    public VideoEntity setMp4link1080(String mp4link1080) {
        this.mp4link1080 = mp4link1080;
        return this;
    }

    public String getExternalLink() {
        return externalLink;
    }

    public VideoEntity setExternalLink(String externalLink) {
        this.externalLink = externalLink;
        return this;
    }

    public String getPlatform() {
        return platform;
    }

    public VideoEntity setPlatform(String platform) {
        this.platform = platform;
        return this;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public VideoEntity setRepeat(boolean repeat) {
        this.repeat = repeat;
        return this;
    }

    public int getDuration() {
        return duration;
    }

    public VideoEntity setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public PrivacyEntity getPrivacyView() {
        return privacyView;
    }

    public VideoEntity setPrivacyView(PrivacyEntity privacyView) {
        this.privacyView = privacyView;
        return this;
    }

    public PrivacyEntity getPrivacyComment() {
        return privacyComment;
    }

    public VideoEntity setPrivacyComment(PrivacyEntity privacyComment) {
        this.privacyComment = privacyComment;
        return this;
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public VideoEntity setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
        return this;
    }

    public boolean isCanAdd() {
        return canAdd;
    }

    public VideoEntity setCanAdd(boolean canAdd) {
        this.canAdd = canAdd;
        return this;
    }

    public boolean isCanComment() {
        return canComment;
    }

    public VideoEntity setCanComment(boolean canComment) {
        this.canComment = canComment;
        return this;
    }

    public boolean isCanRepost() {
        return canRepost;
    }

    public VideoEntity setCanRepost(boolean canRepost) {
        this.canRepost = canRepost;
        return this;
    }
}