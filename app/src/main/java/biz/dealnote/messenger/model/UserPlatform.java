package biz.dealnote.messenger.model;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by admin on 08.10.2016.
 * phoenix
 */
@IntDef({UserPlatform.MOBILE, UserPlatform.IPHONE, UserPlatform.IPAD, UserPlatform.ANDROID, UserPlatform.WPHONE, UserPlatform.WINDOWS, UserPlatform.WEB, UserPlatform.UNKNOWN})
@Retention(RetentionPolicy.SOURCE)
public @interface UserPlatform {
    int UNKNOWN = 0;
    int MOBILE = 1;
    int IPHONE = 2;
    int IPAD = 3;
    int ANDROID = 4;
    int WPHONE = 5;
    int WINDOWS = 6;
    int WEB = 7;
}
