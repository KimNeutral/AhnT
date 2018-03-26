package dgsw.hs.kr.ahnt.Helper;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import dgsw.hs.kr.ahnt.school.SchoolMenu;

/**
 * Created by neutral on 23/03/2018.
 */

public class MealHelper {

    public static final String MEAL_CODE_PREFIX = "M";

    public static SchoolMenu getSchoolMenuByCalendar(Calendar calendar, Map<String, List<SchoolMenu>> schoolMeals){
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String mealCode = createMealCode(calendar);

        List<SchoolMenu> list = schoolMeals.get(mealCode);
        if(list == null){
            return null;
        }

        return list.get(day - 1);
    }

    public static String getMealDayStatus(Calendar cal){
        Calendar tcal = CalendarHelper.CreateCalendar();
        int year = tcal.get(Calendar.YEAR);
        int month = tcal.get(Calendar.MONTH);
        int day = tcal.get(Calendar.DAY_OF_MONTH);

        String meal = "breakfast";

        if (year == cal.get(Calendar.YEAR) && month == cal.get(Calendar.MONTH) && day == cal.get(Calendar.DAY_OF_MONTH)) {
            int hour = tcal.get(Calendar.HOUR_OF_DAY);
            int min = tcal.get(Calendar.MINUTE);


            if (hour <= 7) {
                meal = "breakfast";
                if (hour == 7 && min >= 40) {
                    meal = "lunch";
                }
            } else if (hour <= 12) {
                meal = "lunch";
                if (min >= 50) {
                    meal = "dinner";
                }
            } else if (hour <= 16) {
                meal = "dinner";
                if (min >= 50) {
                    meal = "next";
                }
            } else {
                meal = "next";
            }
        }

        return meal;
    }

    public static String createMealCode(Calendar day){
        String code = MEAL_CODE_PREFIX + day.get(Calendar.YEAR) + "" + (day.get(Calendar.MONTH) + 1);

        return code;
    }

    public static boolean validateMealCode(String code){
        String prefix = code.substring(0, 1);
        if(!prefix.equals(MEAL_CODE_PREFIX)){
            return false;
        }
        return true;
    }
}
