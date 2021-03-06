package kr.hs.dgsw.flow.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.hs.dgsw.flow.Activity.MainActivity;
import kr.hs.dgsw.flow.Helper.MealHelper;
import kr.hs.dgsw.flow.Helper.TimeZoneHelper;
import kr.hs.dgsw.flow.Network.MealGetAsyncTask;
import kr.hs.dgsw.flow.R;
import kr.hs.dgsw.flow.school.SchoolMenu;


/**
 * A simple {@link Fragment} subclass.
 */
public class MealTabFragment extends Fragment {

    Calendar cal;
    SchoolMenu menu;

    @BindView(R.id.tvMeal) TextView tvMeal;
    @BindView(R.id.tvCalendar) TextView tvCal;
    @BindView(R.id.btnDownload) Button btnDownload;
    @BindView(R.id.navigation) BottomNavigationView navigationView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (menu == null) {
                return false;
            }
            switch (item.getItemId()) {
                case R.id.navigation_breakfast:
                    tvMeal.setText(menu.breakfast);
                    return true;
                case R.id.navigation_lunch:
                    tvMeal.setText(menu.lunch);
                    return true;
                case R.id.navigation_dinner:
                    tvMeal.setText(menu.dinner);
                    return true;
            }
            return false;
        }
    };

    public MealTabFragment() {
        // Required empty public constructor
    }

    public static MealTabFragment NewInstance(Calendar cal, SchoolMenu menu){
        MealTabFragment fragment = new MealTabFragment();
        fragment.cal = cal;
        fragment.menu = menu;
        return fragment;
    }

    public void updateUI(Calendar cal, SchoolMenu menu){
        this.cal = cal;
        this.menu = menu;

        if (navigationView != null) {
            navigationView.setSelectedItemId(R.id.navigation_breakfast);
        }

        InitMealText();

        SimpleDateFormat df = new SimpleDateFormat("yyyy년 MM월 dd일");
        df.setTimeZone(TimeZoneHelper.getLocalTimeZone());
        if (cal != null) {
            if (tvCal != null) {
                tvCal.setText(df.format(cal.getTime()));
            }
        }
    }

    public void InitMealText(){
        if (tvMeal == null || navigationView == null) {
            return;
        }

        if (menu == null) {
            showDownloadButton();
            return;
        }

        String meal = MealHelper.getMealDayStatus(cal);

        switch (meal) {
            case "breakfast":
                tvMeal.setText(menu.breakfast);
                navigationView.setSelectedItemId(R.id.navigation_breakfast);
                break;
            case "lunch":
                tvMeal.setText(menu.lunch);
                navigationView.setSelectedItemId(R.id.navigation_lunch);
                break;
            case "dinner":
            case "next":
                tvMeal.setText(menu.dinner);
                navigationView.setSelectedItemId(R.id.navigation_dinner);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal_tab, container, false);
        ButterKnife.bind(this, view);

        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        btnDownload.setOnClickListener(e -> {
            MainActivity activity = (MainActivity) getActivity();
            if(activity != null){
                MealFragment fragment = (MealFragment) activity.getSupportFragmentManager().findFragmentById(R.id.content_frame);
                if(fragment != null) new MealGetAsyncTask(fragment).execute(cal);
            }
        });

        updateUI(cal, menu);

        tvCal.setOnClickListener((v) -> {
            MealFragment.DatePicker timePicker = new MealFragment.DatePicker();
            Bundle bundle = new Bundle();
            bundle.putInt("year", cal.get(Calendar.YEAR));
            bundle.putInt("month", cal.get(Calendar.MONTH));
            bundle.putInt("day", cal.get(Calendar.DAY_OF_MONTH));

            timePicker.setArguments(bundle);
            timePicker.show(getFragmentManager(), "날짜를 선택하세요");
        });

        // Inflate the layout for this fragment
        return view;
    }

    public void showDownloadButton() {
        btnDownload.setVisibility(View.VISIBLE);
        navigationView.setVisibility(View.INVISIBLE);
    }

    public void hideDownloadButton() {
        btnDownload.setVisibility(View.INVISIBLE);
        navigationView.setVisibility(View.VISIBLE);
    }


    public Calendar getCal() {
        return cal;
    }

    public SchoolMenu getMenu() {
        return menu;
    }

}
