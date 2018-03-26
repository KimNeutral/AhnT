package dgsw.hs.kr.ahnt.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import dgsw.hs.kr.ahnt.Activity.MealActivity;
import dgsw.hs.kr.ahnt.Network.MealGetAsyncTask;
import dgsw.hs.kr.ahnt.R;
import dgsw.hs.kr.ahnt.school.SchoolMenu;


/**
 * A simple {@link Fragment} subclass.
 */
public class MealTabFragment extends Fragment {

    Calendar cal;
    SchoolMenu menu;

    TextView tvMeal;
    TextView tvCal;

    Button btnDownload;

    BottomNavigationView navigationView;

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

        if (menu != null) {
            if (tvMeal != null) {
                tvMeal.setText(menu.breakfast);
            }
        } else {
            if (tvMeal != null) {
                showDownloadButton();
            }
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy년 MM월 dd일");
        if (cal != null) {
            if (tvCal != null) {
                tvCal.setText(df.format(cal.getTime()));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal_tab, container, false);
        tvMeal = (TextView) view.findViewById(R.id.tvMeal);
        tvCal = (TextView) view.findViewById(R.id.tvCalendar);
        btnDownload = (Button) view.findViewById(R.id.btnDownload);
        navigationView = (BottomNavigationView) view.findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        btnDownload.setOnClickListener(e -> {
            if (!(getActivity() instanceof MealActivity)) {
                return;
            }
            MealActivity activity = (MealActivity)getActivity();
            new MealGetAsyncTask(activity).execute(cal);
        });

        updateUI(cal, menu);

        tvCal.setOnClickListener((v) -> {
            MealActivity.DatePicker timePicker = new MealActivity.DatePicker();
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
