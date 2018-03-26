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
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);

        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal;
    }

    public static Calendar CreateCalendar(int year, int month, int day, int hour, int min, int sec) {
        Calendar cal = CreateCalendar();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);

        cal.set(Calendar.HOUR, hour);
        cal.set(Calendar.MINUTE, min);
        cal.set(Calendar.SECOND, sec);
        return cal;
    }
}
