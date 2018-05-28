package kr.hs.dgsw.flow.Helper;

import android.util.Pair;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import kr.hs.dgsw.flow.school.SchoolMenu;

/**
 * Created by neutral on 23/03/2018.
 */

public class MealHelper {

    public static final String MEAL_CODE_PREFIX = "M";

    /**
     * Calendar 객체를 이용해 Map에서 그 날짜의 SchoolMenu를 가져옵니다.
     * @param calendar 원하는 날짜
     * @param schoolMeals 급식 맵
     * @return 그 날의 SchoolMenu
     */
    public static SchoolMenu getSchoolMenuByCalendar(Calendar calendar, Map<String, List<SchoolMenu>> schoolMeals) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String mealCode = createMealCode(calendar);

        List<SchoolMenu> list = schoolMeals.get(mealCode);
        if (list == null) {
            return null;
        }

        return list.get(day - 1);
    }

    public static String getMealDayStatus(Calendar cal) {
        Calendar tcal = CalendarHelper.CreateCalendar();
        int year = tcal.get(Calendar.YEAR);
        int month = tcal.get(Calendar.MONTH);
        int day = tcal.get(Calendar.DAY_OF_MONTH);

        String meal = "breakfast";

        if (year == cal.get(Calendar.YEAR) && month == cal.get(Calendar.MONTH) && day == cal.get(Calendar.DAY_OF_MONTH)) {
            MealTimeTable mealTime = GetTodayMealTimeTable();

            int hour = tcal.get(Calendar.HOUR_OF_DAY);
            int min = tcal.get(Calendar.MINUTE);


            if (hour <= mealTime.breakfast.first) {
                meal = "breakfast";
                if (hour == mealTime.breakfast.first && min >= mealTime.breakfast.second) {
                    meal = "lunch";
                }
            } else if (hour <= mealTime.lunch.first) {
                meal = "lunch";
                if (hour == mealTime.lunch.first && min >= mealTime.lunch.second) {
                    meal = "dinner";
                }
            } else if (hour <= mealTime.dinner.first) {
                meal = "dinner";
                if (hour == mealTime.dinner.first && min >= mealTime.dinner.second) {
                    meal = "next";
                }
            } else {
                meal = "next";
            }
        }

        return meal;
    }

    public static String createMealCode(Calendar day) {
        String code = MEAL_CODE_PREFIX + day.get(Calendar.YEAR) + "" + (day.get(Calendar.MONTH) + 1);

        return code;
    }

    public static boolean validateMealCode(String code) {
        String prefix = code.substring(0, 1);
        if (!prefix.equals(MEAL_CODE_PREFIX)) {
            return false;
        }
        return true;
    }

    public static MealTimeTable GetTodayMealTimeTable() {
        Calendar cal = CalendarHelper.CreateCalendar();
        int day = cal.get(Calendar.DAY_OF_WEEK);

        if (day == Calendar.SATURDAY || day == Calendar.SUNDAY) {
            return MealTimeTable.WEEKEND_MEALTIME;
        }
        return MealTimeTable.WEEKDAY_MEALTIME;
    }

    public static class MealTimeTable {
        static MealTimeTable WEEKDAY_MEALTIME = new MealTimeTable(Pair.create(7, 30), Pair.create(12, 50), Pair.create(18, 50));
        static MealTimeTable WEEKEND_MEALTIME = new MealTimeTable(Pair.create(8, 30), Pair.create(12, 00), Pair.create(18, 50));

        Pair<Integer, Integer> breakfast;
        Pair<Integer, Integer> lunch;
        Pair<Integer, Integer> dinner;

        private MealTimeTable(Pair<Integer, Integer> breakfast, Pair<Integer, Integer> lunch, Pair<Integer, Integer> dinner) {
            this.breakfast = breakfast;
            this.lunch = lunch;
            this.dinner = dinner;
        }
    }

}
