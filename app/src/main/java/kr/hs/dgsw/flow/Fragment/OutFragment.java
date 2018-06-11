package kr.hs.dgsw.flow.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.hs.dgsw.flow.R;

public class OutFragment extends BaseFragment {
    private static final String TITLE = "외출";

    public OutFragment() {
        // Required empty public constructor
    }

    public static OutFragment newInstance() {
        OutFragment fragment = new OutFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_out, container, false);
    }

    @Override
    public String getTitle() {
        return TITLE;
    }
}
