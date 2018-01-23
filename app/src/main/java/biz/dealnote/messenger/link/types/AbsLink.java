package biz.dealnote.messenger.link.types;

public abstract class AbsLink {

    public static final int PHOTO = 0;
    public static final int PHOTO_ALBUM = 1;
    public static final int PROFILE = 2;
    public static final int GROUP = 3;
    public static final int TOPIC = 4;
    public static final int DOMAIN = 5;
    public static final int WALL_POST = 6;
    public static final int PAGE = 7;
    public static final int ALBUMS = 8;
    public static final int DIALOG = 9;
    public static final int EXTERNAL_LINK = 10;
    public static final int WALL = 11;
    public static final int DIALOGS = 12;
    public static final int VIDEO = 13;
    public static final int DOC = 14;
    public static final int AUDIOS = 15;
    public static final int FAVE = 16;
    public static final int WALL_COMMENT = 17;
    public static final int BOARD = 18;
    public static final int FEED_SEARCH = 19;

    public int type;

    public AbsLink(int vkLinkType) {
        this.type = vkLinkType;
    }

    public boolean isValid(){
        return true;
    }
}