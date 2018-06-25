package kr.hs.dgsw.flow.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kr.hs.dgsw.flow.Model.Notice;
import kr.hs.dgsw.flow.R;

public class NoticeViewHolder extends RecyclerView.ViewHolder {
    public TextView tvTitle;
    public TextView tvWriter;

    public View view;

    private Notice notice;

    public NoticeViewHolder(View v) {
        super(v);
        view = v;
        tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        tvWriter = (TextView) itemView.findViewById(R.id.tvWriter);
    }

    private void setNotice(Notice notice) {
        this.notice = notice;
        bindView();
    }

    private void bindView() {
        tvTitle.setText(notice.getContent());
        tvWriter.setText(notice.getWriter());
    }
}
