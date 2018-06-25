package kr.hs.dgsw.flow.Fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import kr.hs.dgsw.flow.Adapter.NoticeAdapter;
import kr.hs.dgsw.flow.Adapter.OutAdapter;
import kr.hs.dgsw.flow.Model.GoOut;
import kr.hs.dgsw.flow.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OutListFragment extends BaseFragment {

    @BindView(R.id.recyclerView) public RecyclerView recyclerView;
    @BindView(R.id.fbtnAdd) public FloatingActionButton fbtnAdd;

    private OutAdapter outAdapter;

    private static final String TITLE = "외출";

    public OutListFragment() {
        // Required empty public constructor
    }

    public static OutListFragment newInstance() {
        OutListFragment fragment = new OutListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_out_list, container, false);
        ButterKnife.bind(this, view);

        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        RealmResults<GoOut> result = realm.where(GoOut.class).findAll();
        List<GoOut> list = Arrays.asList(result.toArray(new GoOut[0]));

        outAdapter = new OutAdapter(list);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(outAdapter);
        realm.commitTransaction();

        return view;
    }

    @OnClick(R.id.fbtnAdd)
    public void addButtonClicked() {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.addFragment(OutFragment.newInstance());
    }

    @Override
    public String getTitle() {
        return TITLE;
    }
}
