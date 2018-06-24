package kr.hs.dgsw.flow.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import kr.hs.dgsw.flow.Model.Notice;
import kr.hs.dgsw.flow.R;
import kr.hs.dgsw.flow.ViewHolder.NoticeViewHolder;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeViewHolder> {
    private List<Notice> items;

    public NoticeAdapter(List<Notice> items) {
        this.items = items;
    }

    @Override
    public NoticeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notice, parent, false);

        return new NoticeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NoticeViewHolder holder, int position) {
        Notice notice = items.get(position);
        holder.textView.setText(notice.getContent());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
