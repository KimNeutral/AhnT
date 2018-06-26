package kr.hs.dgsw.flow.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import kr.hs.dgsw.flow.Activity.MainActivity;
import kr.hs.dgsw.flow.Adapter.OutSleepAdapter;
import kr.hs.dgsw.flow.Model.GoOut;
import kr.hs.dgsw.flow.Model.SleepOut;
import kr.hs.dgsw.flow.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SleepOutListFragment extends BaseFragment {

    @BindView(R.id.recyclerView) public RecyclerView recyclerView;

    private OutSleepAdapter outSleepAdapter;

    private static final String TITLE = "외박";

    public SleepOutListFragment() {
        // Required empty public constructor
    }

    public static SleepOutListFragment newInstance() {
        SleepOutListFragment fragment = new SleepOutListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view  = inflater.inflate(R.layout.fragment_sleep_out_list, container, false);
        ButterKnife.bind(this, view);

        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        RealmResults<SleepOut> result = realm.where(SleepOut.class).findAll();
        List<SleepOut> list =  Arrays.asList(result.toArray(new SleepOut[0]));
        outSleepAdapter = new OutSleepAdapter(list);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(outSleepAdapter);

        realm.commitTransaction();
        return view;
    }

    @OnClick(R.id.fbtnAdd)
    public void addButtonClicked() {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.addFragment(SleepOutFragment.newInstance(outSleepAdapter));
    }

    @Override
    public String getTitle() {
        return TITLE;
    }
}
