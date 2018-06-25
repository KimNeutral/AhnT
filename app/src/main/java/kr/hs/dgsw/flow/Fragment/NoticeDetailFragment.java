package kr.hs.dgsw.flow.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.hs.dgsw.flow.Model.Notice;
import kr.hs.dgsw.flow.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoticeDetailFragment extends BaseFragment {

    @BindView(R.id.tvContent) public TextView tvContent;
    @BindView(R.id.tvWriter) public TextView tvWriter;

    private Notice notice;

    private static final String TITLE = "공지";

    public NoticeDetailFragment() {
        // Required empty public constructor
    }

    public static NoticeDetailFragment newInstance(Notice notice) {
        NoticeDetailFragment fragment = new NoticeDetailFragment();
        fragment.setNotice(notice);

        return fragment;
    }

    private void setNotice(Notice notice) {
        this.notice = notice;
    }

    private void bindView() {
        tvContent.setText(notice.getContent());
        tvWriter.setText(notice.getWriter());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notice_detail, container, false);
        ButterKnife.bind(this, view);

        bindView();
        return view;
    }

    @Override
    public String getTitle() {
        return TITLE;
    }
}
