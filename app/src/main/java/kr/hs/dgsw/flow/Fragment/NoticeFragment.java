package kr.hs.dgsw.flow.Fragment;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.hs.dgsw.flow.Adapter.NoticeAdapter;
import kr.hs.dgsw.flow.Helper.SharedPreferencesHelper;
import kr.hs.dgsw.flow.Interface.IPassValue;
import kr.hs.dgsw.flow.Model.Notice;
import kr.hs.dgsw.flow.Network.NetworkManager;
import kr.hs.dgsw.flow.Network.Response.AllNoticeData;
import kr.hs.dgsw.flow.Network.Response.ResponseFormat;
import kr.hs.dgsw.flow.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoticeFragment extends BaseFragment implements IPassValue<ResponseFormat<AllNoticeData>> {

    @BindView(R.id.recyclerView)
    public RecyclerView recyclerView;

    private NoticeAdapter noticeAdapter;

    private static final String TITLE = "공지";

    public NoticeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notice, container, false);
        ButterKnife.bind(this, view);

        noticeAdapter = new NoticeAdapter(new ArrayList<>());

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(noticeAdapter);

        NetworkManager.getAllNotice(this, SharedPreferencesHelper.getPreference("token"));

        return view;
    }

    @Override
    public String getTitle() {
        return TITLE;
    }

    public static NoticeFragment newInstance() {
        NoticeFragment fragment = new NoticeFragment();
        return fragment;
    }

    @Override
    public void passValue(ResponseFormat<AllNoticeData> value) {
        Resources r = getResources();
        if(value.getStatus() == r.getInteger(R.integer.status_success)) {
            for(Notice notice : value.getData().getList()) noticeAdapter.add(notice);
            noticeAdapter.notifyDataSetChanged();
        }
    }
}
