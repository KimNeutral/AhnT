package dgsw.hs.kr.ahnt.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import dgsw.hs.kr.ahnt.Adapter.MealPagerAdapter;
import dgsw.hs.kr.ahnt.Helper.CalendarHelper;
import dgsw.hs.kr.ahnt.Helper.MealHelper;
import dgsw.hs.kr.ahnt.Interface.IPassValue;
import dgsw.hs.kr.ahnt.Interface.IProgressBarControl;
import dgsw.hs.kr.ahnt.Network.MealGetAsyncTask;
import dgsw.hs.kr.ahnt.R;
import dgsw.hs.kr.ahnt.school.SchoolMonthlyMenu;

public class MealFragment extends BaseFragment implements IProgressBarControl, IPassValue<SchoolMonthlyMenu> {

    @BindView(R.id.progressbar) ProgressBar progressBar;
    @BindView(R.id.clProgress) ConstraintLayout clProgress;
    @BindView(R.id.viewPager) ViewPager vp;
    MealPagerAdapter adapter;

    private static final String TITLE = "급식";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal, container, false);

        ButterKnife.bind(this, view);

        adapter = new MealPagerAdapter(getFragmentManager());

        vp.setOffscreenPageLimit(3);

        new MealGetAsyncTask(this).execute(CalendarHelper.CreateCalendar());

        return view;
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

    @Override
    protected String getTitle() {
        return TITLE;
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
