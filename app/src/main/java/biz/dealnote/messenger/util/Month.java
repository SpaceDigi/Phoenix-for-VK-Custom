package biz.dealnote.messenger.util;

import android.support.annotation.StringRes;

import java.util.Calendar;

import biz.dealnote.messenger.R;

/**
 * Created by admin on 17.06.2017.
 * phoenix
 */
public class Month {

    @StringRes
    public static int getMonthTitle(int num){
        switch (num){
            case Calendar.JANUARY:
                return R.string.january;
            case Calendar.FEBRUARY:
                return R.string.february;
            case Calendar.MARCH:
                return R.string.march;
            case Calendar.APRIL:
                return R.string.april;
            case Calendar.MAY:
                return R.string.may;
            case Calendar.JUNE:
                return R.string.june;
            case Calendar.JULY:
                return R.string.july;
            case Calendar.AUGUST:
                return R.string.august;
            case Calendar.SEPTEMBER:
                return R.string.september;
            case Calendar.OCTOBER:
                return R.string.october;
            case Calendar.NOVEMBER:
                return R.string.november;
            case Calendar.DECEMBER:
                return R.string.december;
        }

        throw new IllegalArgumentException();
    }

}
