package dgsw.hs.kr.ahnt.Helper;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by neutral on 26/03/2018.
 */

public class CalendarHelper {
    public static Calendar CreateCalendar() {
        return Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"));
    }

    public static Calendar CreateCalendar(int year, int month, int day) {
        Calendar cal = CreateCalendar();
        set(cal, year, month, day, 0, 0, 0);
        return cal;
    }

    public static Calendar CreateCalendar(int year, int month, int day, int hour, int min, int sec) {
        Calendar cal = CreateCalendar();
        set(cal, year, month, day, hour, min, sec);

        return cal;
    }

    public static Calendar set(Calendar cal, int year, int month, int day) {
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);

        return cal;
    }

    public static Calendar set(Calendar cal, int year, int month, int day, int hour, int min, int sec) {
        set(cal, year, month, day);
        setTime(cal, hour, min, sec);

        return cal;
    }

    public static Calendar setTime(Calendar cal, int hour, int min, int sec) {
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, min);
        cal.set(Calendar.SECOND, sec);

        return cal;
    }
}
