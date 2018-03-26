package dgsw.hs.kr.ahnt.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dgsw.hs.kr.ahnt.Fragment.MealTabFragment;
import dgsw.hs.kr.ahnt.Helper.CalendarHelper;
import dgsw.hs.kr.ahnt.Helper.MealHelper;
import dgsw.hs.kr.ahnt.school.SchoolMenu;
import dgsw.hs.kr.ahnt.school.SchoolMonthlyMenu;

/**
 * Created by neutral on 19/03/2018.
 */

public class MealPagerAdapter extends FragmentStatePagerAdapter {

    public static final int TOTAL_PAGE = 100000;
    public static final int BASE = TOTAL_PAGE / 2;
    private final Calendar base_cal = CalendarHelper.CreateCalendar();
    private final Calendar cal = CalendarHelper.CreateCalendar();

    private Map<String, List<SchoolMenu>> schoolMeals = new HashMap<>();

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
    public int getItemPosition(Object object) {
        MealTabFragment f = (MealTabFragment) object;
        if (f != null) {
            f.updateUI(f.getCal(), MealHelper.getSchoolMenuByCalendar(f.getCal(), schoolMeals));
            Log.d("", f.getCal().get(Calendar.YEAR) + "-" + f.getCal().get(Calendar.MONTH));
        }
        return super.getItemPosition(object);
    }

    @Override
    public Fragment getItem(int position) {
        Calendar menuDate = getCalendarByPosition(position);
        SchoolMenu menu = MealHelper.getSchoolMenuByCalendar(menuDate, schoolMeals);
        return MealTabFragment.NewInstance(menuDate, menu);
    }

    @Override
    public int getCount() {
        return TOTAL_PAGE;
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

    public void putMonthlyMeal(String code, List<SchoolMenu> list) {
        if (!MealHelper.validateMealCode(code)) {
            return;
        }

        schoolMeals.put(code, list);
    }

    public void putMonthlyMeal(Calendar date, List<SchoolMenu> list) {
        putMonthlyMeal(MealHelper.createMealCode(date), list);
    }

    public void putMonthlyMeal(SchoolMonthlyMenu monthlyMenu) {
        putMonthlyMeal(monthlyMenu.getDate(), monthlyMenu.getMenus());
    }

    public Calendar getCalendar() {
        return (Calendar) cal.clone();
    }

    public Map<String, List<SchoolMenu>> getSchoolMeals() {
        return schoolMeals;
    }
}
