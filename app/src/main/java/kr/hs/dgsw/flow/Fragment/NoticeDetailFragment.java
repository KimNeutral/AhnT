package kr.hs.dgsw.flow.Fragment;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.hs.dgsw.flow.Helper.FileHelper;
import kr.hs.dgsw.flow.Helper.SharedPreferencesHelper;
import kr.hs.dgsw.flow.Interface.IPassValue;
import kr.hs.dgsw.flow.Model.GoOut;
import kr.hs.dgsw.flow.Model.Notice;
import kr.hs.dgsw.flow.Model.NoticeFile;
import kr.hs.dgsw.flow.Network.NetworkManager;
import kr.hs.dgsw.flow.Network.Response.ResponseFormat;
import kr.hs.dgsw.flow.R;

import static kr.hs.dgsw.flow.Network.NetworkManager.SERVER_URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoticeDetailFragment extends BaseFragment implements IPassValue<ResponseFormat<Notice>> {

    @BindView(R.id.tvContent) public TextView tvContent;
    @BindView(R.id.tvWriter) public TextView tvWriter;
    @BindView(R.id.tvFileTitle) public TextView tvFileTitle;
    @BindView(R.id.btnFileDownload) public TextView btnFileDownload;
    @BindView(R.id.clFile) public ConstraintLayout clFile;

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

    public static NoticeDetailFragment newInstance(int idx) {
        NoticeDetailFragment fragment = new NoticeDetailFragment();
        NetworkManager.getNotice(fragment, SharedPreferencesHelper.getPreference("token"), idx);
        return fragment;
    }

    private void setNotice(Notice notice) {
        this.notice = notice;
    }

    private void bindView() {
        if(notice == null) return;

        tvContent.setText(notice.getContent());
        tvWriter.setText(notice.getWriter());

        if(notice.getNoticeFiles() != null && notice.getNoticeFiles().length != 0) {
            clFile.setVisibility(View.VISIBLE);
            NoticeFile file = notice.getNoticeFiles()[0];
            tvFileTitle.setText(file.getUploadName());
        }
    }

    @OnClick(R.id.btnFileDownload)
    public void btnFileDownload_OnClick(View v) {
        String dir = FileHelper.getRootDirPath(getActivity().getApplicationContext());
        NoticeFile file = notice.getNoticeFiles()[0];
        PRDownloader.download(SERVER_URL + file.getUploadDir(), dir, file.getUploadName())
                .build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {
                        Toast.makeText(getActivity().getApplicationContext(), "다운로드중...", Toast.LENGTH_SHORT);
                    }
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        Toast.makeText(getActivity().getApplicationContext(), "파일을 다운로드 받았습니다.", Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onError(Error error) {
                        Toast.makeText(getActivity().getApplicationContext(), "파일 다운로드중 에러가 발생하였습니다.", Toast.LENGTH_SHORT);
                    }
                });
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

    @Override
    public void passValue(ResponseFormat<Notice> value) {
        Resources r = getResources();
        if(value.getStatus() == r.getInteger(R.integer.status_success)) {
            setNotice(value.getData());
            bindView();
        }
    }
}
