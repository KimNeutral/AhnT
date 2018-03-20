package dgsw.hs.kr.ahnt.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import dgsw.hs.kr.ahnt.Fragment.MealTabFragment;
import dgsw.hs.kr.ahnt.school.SchoolMenu;

/**
 * Created by neutral on 19/03/2018.
 */

public class MealPagerAdapter extends FragmentPagerAdapter {

    int num_tab;
    Calendar cal = Calendar.getInstance();
    Map<String, List<SchoolMenu>> schoolMeals = new HashMap<>();

    public static final String MEAL_CODE_PREFIX = "M";

    public MealPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public MealPagerAdapter(FragmentManager fm, String code, List<SchoolMenu> list) {
        super(fm);
        schoolMeals.put(code, list);
    }

    @Override
    public Fragment getItem(int position) {
        List<SchoolMenu> list = schoolMeals.get(createMealCode(cal));
        return MealTabFragment.NewInstance(list.get(position));
    }

    @Override
    public int getCount() {
        int sum = 0;

        Iterator<String> itr = schoolMeals.keySet().iterator();
        while (itr.hasNext()) {
            String key = itr.next();
            sum += schoolMeals.get(key).size();
        }
        return sum;
    }

    private String createMealCode(Calendar day){
        String code = MEAL_CODE_PREFIX + day.get(Calendar.YEAR) + "" + (day.get(Calendar.MONTH) + 1);

        return code;
    }

    private boolean validateMealCode(String code){
        String prefix = code.substring(1);
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

    public Calendar getCalendar(){
        return cal;
    }
}
