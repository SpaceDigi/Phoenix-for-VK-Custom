package biz.dealnote.messenger.api.model;

/**
 * Created by admin on 03.01.2017.
 * phoenix
 */
public class AttachmentToken implements IAttachmentToken {

    public final String type;

    public final int id;

    public final int ownerId;

    public final String accessKey;

    public AttachmentToken(String type, int id, int ownerId) {
        this.type = type;
        this.id = id;
        this.ownerId = ownerId;
        this.accessKey = null;
    }

    public AttachmentToken(String type, int id, int ownerId, String accessKey) {
        this.type = type;
        this.id = id;
        this.ownerId = ownerId;
        this.accessKey = accessKey;
    }

    @Override
    public String format() {
        return type + ownerId + "_" + id + (accessKey == null || accessKey.length() == 0 ? "" : ("_" + accessKey));
    }
}