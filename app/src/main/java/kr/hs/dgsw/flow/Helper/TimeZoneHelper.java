package kr.hs.dgsw.flow.Helper;

import java.util.TimeZone;

/**
 * Created by neutral on 28/03/2018.
 */

public class TimeZoneHelper {
    public static TimeZone getLocalTimeZone() {
        return TimeZone.getTimeZone("Asia/Seoul");
    }
}
