package biz.dealnote.messenger.db.model.entity;

/**
 * Created by admin on 03.09.2017.
 * phoenix
 */
public class PhotoEntity extends Entity {

    private final int id;

    private final int ownerId;

    private int albumId;

    private int width;

    private int height;

    private String text;

    private long date;

    private boolean userLikes;

    private int likesCount;

    private boolean canComment;

    private int commentsCount;

    private int tagsCount;

    private String accessKey;

    private int postId;

    private boolean deleted;

    private PhotoSizeEntity sizes;

    public PhotoEntity(int id, int ownerId) {
        this.id = id;
        this.ownerId = ownerId;
    }

    public PhotoEntity setSizes(PhotoSizeEntity sizes) {
        this.sizes = sizes;
        return this;
    }

    public PhotoSizeEntity getSizes() {
        return sizes;
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

    public PhotoEntity setAlbumId(int albumId) {
        this.albumId = albumId;
        return this;
    }

    public PhotoEntity setCanComment(boolean canComment) {
        this.canComment = canComment;
        return this;
    }

    public boolean isCanComment() {
        return canComment;
    }

    public int getWidth() {
        return width;
    }

    public PhotoEntity setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public PhotoEntity setHeight(int height) {
        this.height = height;
        return this;
    }

    public String getText() {
        return text;
    }

    public PhotoEntity setText(String text) {
        this.text = text;
        return this;
    }

    public long getDate() {
        return date;
    }

    public PhotoEntity setDate(long date) {
        this.date = date;
        return this;
    }

    public boolean isUserLikes() {
        return userLikes;
    }

    public PhotoEntity setUserLikes(boolean userLikes) {
        this.userLikes = userLikes;
        return this;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public PhotoEntity setLikesCount(int likesCount) {
        this.likesCount = likesCount;
        return this;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public PhotoEntity setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
        return this;
    }

    public int getTagsCount() {
        return tagsCount;
    }

    public PhotoEntity setTagsCount(int tagsCount) {
        this.tagsCount = tagsCount;
        return this;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public PhotoEntity setAccessKey(String accessKey) {
        this.accessKey = accessKey;
        return this;
    }

    public int getPostId() {
        return postId;
    }

    public PhotoEntity setPostId(int postId) {
        this.postId = postId;
        return this;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public PhotoEntity setDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }
}