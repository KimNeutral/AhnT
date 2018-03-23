package dgsw.hs.kr.ahnt.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dgsw.hs.kr.ahnt.Fragment.MealTabFragment;
import dgsw.hs.kr.ahnt.school.SchoolMenu;

/**
 * Created by neutral on 19/03/2018.
 */

public class MealPagerAdapter extends FragmentPagerAdapter {

    public static final int TOTAL_PAGE = 100000;
    public static final int BASE = TOTAL_PAGE / 2;
    private final Calendar base_cal = Calendar.getInstance();
    private final Calendar cal = Calendar.getInstance();

    private final Map<String, List<SchoolMenu>> schoolMeals = new HashMap<>();

    public static final String MEAL_CODE_PREFIX = "M";

    public MealPagerAdapter(FragmentManager fm) {
        super(fm);
        base_cal.set(Calendar.HOUR, 0);
        base_cal.set(Calendar.MINUTE, 0);
        base_cal.set(Calendar.SECOND, 0);
    }

    public MealPagerAdapter(FragmentManager fm, String code, List<SchoolMenu> list) {
        super(fm);
        schoolMeals.put(code, list);
    }

    @Override
    public Fragment getItem(int position) {
        Calendar menuDate = getCalendarByPosition(position);
        SchoolMenu menu = getSchoolMenuByCalendar(menuDate);
        return MealTabFragment.NewInstance(menuDate, menu);
    }

    @Override
    public int getCount() {
        return TOTAL_PAGE;
    }

    public SchoolMenu getSchoolMenuByCalendar(Calendar calendar){
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

    public Calendar getCalendarByPosition(int position) {
        Calendar temp = (Calendar) base_cal.clone();
        int offset = position - BASE;
        temp.add(Calendar.DAY_OF_MONTH, offset);
        return temp;
    }

    public int getPositionByCalendar(Calendar calPosition) {
        int offset = getOffsetFromBaseCalendar(calPosition);
        return BASE + offset;
    }

    public int getOffsetFromBaseCalendar(Calendar target) {
        Date d1 = base_cal.getTime();

        Date today = target.getTime();

        long diff = today.getTime() - d1.getTime();
        int offset = (int)(diff / (1000 * 60 * 60 * 24));

        return offset;
    }

    private String createMealCode(Calendar day){
        String code = MEAL_CODE_PREFIX + day.get(Calendar.YEAR) + "" + (day.get(Calendar.MONTH) + 1);

        return code;
    }

    private boolean validateMealCode(String code){
        String prefix = code.substring(0, 1);
        if(!prefix.equals(MEAL_CODE_PREFIX)){
            return false;
        }
        return true;
    }

    public void putMonthlyMeal(String code, List<SchoolMenu> list){
        if(!validateMealCode(code)){
            return;
        }

        schoolMeals.put(code, list);
    }

    public Calendar getCalendar() {
        return (Calendar) cal.clone();
    }

}
