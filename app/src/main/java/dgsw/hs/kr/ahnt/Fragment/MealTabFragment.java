package dgsw.hs.kr.ahnt.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import dgsw.hs.kr.ahnt.Activity.MealActivity;
import dgsw.hs.kr.ahnt.R;
import dgsw.hs.kr.ahnt.school.SchoolMenu;


/**
 * A simple {@link Fragment} subclass.
 */
public class MealTabFragment extends Fragment {

    SchoolMenu menu;

    public MealTabFragment() {
        // Required empty public constructor
    }

    public static MealTabFragment NewInstance(SchoolMenu menu){
        MealTabFragment fragment = new MealTabFragment();
        fragment.menu = menu;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal_tab, container, false);
        TextView tv = (TextView) view.findViewById(R.id.tvMeal);
        tv.setText(menu.toString());

        TextView tvCal = (TextView) view.findViewById(R.id.tvCalendar);
        SimpleDateFormat df = new SimpleDateFormat("yyyy년 MM월 dd일");
        tvCal.setText(df.format(menu.date.getTime()));

        tvCal.setOnClickListener((v) -> {
            MealActivity.DatePicker timePicker = new MealActivity.DatePicker();
            Bundle bundle = new Bundle();
            bundle.putInt("year", menu.date.get(Calendar.YEAR));
            bundle.putInt("month", menu.date.get(Calendar.MONTH));
            bundle.putInt("day", menu.date.get(Calendar.DAY_OF_MONTH));

            timePicker.setArguments(bundle);
            timePicker.show(getFragmentManager(), "날짜를 선택하세요");
        });

        // Inflate the layout for this fragment
        return view;
    }

}
