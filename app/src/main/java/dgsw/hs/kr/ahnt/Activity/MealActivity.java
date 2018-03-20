package dgsw.hs.kr.ahnt.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import java.util.Calendar;
import java.util.List;

import dgsw.hs.kr.ahnt.Adapter.MealPagerAdapter;
import dgsw.hs.kr.ahnt.Fragment.MealTabFragment;
import dgsw.hs.kr.ahnt.R;
import dgsw.hs.kr.ahnt.school.School;
import dgsw.hs.kr.ahnt.school.SchoolException;
import dgsw.hs.kr.ahnt.school.SchoolMenu;

public class MealActivity extends AppCompatActivity {

    public static final String MEAL_CODE_PREFIX = "M";

    ProgressBar progressBar;
    ConstraintLayout clProgress;

    ViewPager vp;
    MealPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        clProgress = (ConstraintLayout) findViewById(R.id.clProgress);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        vp = (ViewPager) findViewById(R.id.viewPager);
        adapter = new MealPagerAdapter(getSupportFragmentManager());

        MealTabFragment[] tabs = new MealTabFragment[3];
        tabs[0] = MealTabFragment.NewInstance(null);
        tabs[1] = MealTabFragment.NewInstance(null);
        tabs[2] = MealTabFragment.NewInstance(null);

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {

                }
            }
        });

        new MealGetOperation().execute(Calendar.getInstance());
    }

    private String CreateMealCode(Calendar day){
        String code = MEAL_CODE_PREFIX + day.get(Calendar.YEAR) + "" + (day.get(Calendar.MONTH) + 1);

        return code;
    }

    /**
     * 급식전용 DatePicker
     */
    public static class DatePicker extends android.support.v4.app.DialogFragment implements DatePickerDialog.OnDateSetListener {

        int year;
        int month;
        int day;

        public DatePicker() {}

        @Override
        public void setArguments(Bundle args) {
            super.setArguments(args);

            year = args.getInt("year");
            month = args.getInt("month");
            day = args.getInt("day");
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {

        }

    }

    /**
     * 원하는 년도와 월을 넣은 Calendar를 인자로 받아
     * 그 달의 급식 메뉴의 리스트를 반환한다.
     */
    private class MealGetOperation extends AsyncTask<Calendar, Integer, List<SchoolMenu>> {

        Calendar cur;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            clProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<SchoolMenu> doInBackground(Calendar... calendars) {
            School api = new School(School.Type.HIGH, School.Region.DAEGU, "D100000282");

            if(calendars.length <= 0){
                return null;
            }

            cur = calendars[0];

            try {
                List<SchoolMenu> menu = api.getMonthlyMenu(cur.get(Calendar.YEAR), cur.get(Calendar.MONTH) + 1);

                return menu;
            } catch (SchoolException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<SchoolMenu> list) {
            super.onPostExecute(list);

            if (cur != null) {
                String mealCode = CreateMealCode(cur);

                clProgress.setVisibility(View.GONE);

                adapter.putMonthlyMeal(mealCode, list);
                vp.setAdapter(adapter);
                vp.setCurrentItem(cur.get(Calendar.DAY_OF_MONTH) - 1);
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }
    }
}
