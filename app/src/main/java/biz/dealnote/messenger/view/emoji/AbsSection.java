package biz.dealnote.messenger.view.emoji;

public class AbsSection {

    public static final int TYPE_EMOJI = 0;
    public static final int TYPE_STICKER = 1;
    public static final int TYPE_PHOTO_ALBUM = 3;

    public int type;
    public boolean active;

    public AbsSection(int type) {
        this.type = type;
    }
}
