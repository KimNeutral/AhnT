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
public class SleepOutFragment extends Fragment {


    public SleepOutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sleep_out, container, false);
    }

}
