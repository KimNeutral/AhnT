package kr.hs.dgsw.flow.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.hs.dgsw.flow.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SleepOutFragment extends BaseFragment {

    private static final String TITLE = "외박";

    public SleepOutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sleep_out, container, false);
    }

    @Override
    public String getTitle() {
        return TITLE;
    }

    public static SleepOutFragment newInstance() {
        SleepOutFragment fragment = new SleepOutFragment();

        return fragment;
    }
}
