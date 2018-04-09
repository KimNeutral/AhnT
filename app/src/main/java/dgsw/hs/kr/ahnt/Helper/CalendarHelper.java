package dgsw.hs.kr.ahnt.Helper;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by neutral on 26/03/2018.
 */

public class CalendarHelper {
    public static Calendar CreateCalendar() {
        return Calendar.getInstance(TimeZoneHelper.getLocalTimeZone());
    }

    public static Calendar CreateCalendar(int year, int month, int day) {
        Calendar cal = CreateCalendar();
        cal.set(year, month, day, 0, 0, 0);
        return cal;
    }

    public static Calendar CreateCalendar(int year, int month, int day, int hour, int min, int sec) {
        Calendar cal = CreateCalendar();
        cal.set(year, month, day, hour, min, sec);
        return cal;
    }
}
