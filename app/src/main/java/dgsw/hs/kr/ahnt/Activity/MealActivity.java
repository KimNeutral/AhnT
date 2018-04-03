package dgsw.hs.kr.ahnt.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import dgsw.hs.kr.ahnt.Adapter.MealPagerAdapter;
import dgsw.hs.kr.ahnt.Helper.CalendarHelper;
import dgsw.hs.kr.ahnt.Helper.MealHelper;
import dgsw.hs.kr.ahnt.Interface.IPassValue;
import dgsw.hs.kr.ahnt.Interface.IProgressBarControl;
import dgsw.hs.kr.ahnt.Network.MealGetAsyncTask;
import dgsw.hs.kr.ahnt.R;
import dgsw.hs.kr.ahnt.school.SchoolMonthlyMenu;

public class MealActivity extends AppCompatActivity implements IProgressBarControl, IPassValue<SchoolMonthlyMenu> {

    @BindView(R.id.progressbar) ProgressBar progressBar;
    @BindView(R.id.clProgress) ConstraintLayout clProgress;
    @BindView(R.id.viewPager) ViewPager vp;
    MealPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        adapter = new MealPagerAdapter(getSupportFragmentManager());

        vp.setOffscreenPageLimit(3);

        new MealGetAsyncTask(this).execute(CalendarHelper.CreateCalendar());
    }

    @Override
    public void showProgressBar() {
        clProgress.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        clProgress.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setProgressBarValue(int value) {
        progressBar.setProgress(value);
    }

    @Override
    public void passValue(SchoolMonthlyMenu value) {
        if (value == null) {
            return;
        }

        adapter.putMonthlyMeal(value);
        int bPos = vp.getCurrentItem();
        vp.setAdapter(adapter);

        if (bPos == 0) {
            bPos = MealPagerAdapter.BASE;
            if (MealHelper.getMealDayStatus(CalendarHelper.CreateCalendar()).equals("next")) {
                bPos++;
            }
        }

        vp.setCurrentItem(bPos);
        adapter.notifyDataSetChanged();
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
            ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.viewPager);
            MealPagerAdapter mpa = (MealPagerAdapter)viewPager.getAdapter();

            Calendar cal = CalendarHelper.CreateCalendar(year, month, day);

            int pos = mpa.getPositionByCalendar(cal);

            viewPager.setCurrentItem(pos);
        }

    }

}
