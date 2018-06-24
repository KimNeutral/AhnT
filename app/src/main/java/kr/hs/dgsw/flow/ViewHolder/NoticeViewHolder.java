package kr.hs.dgsw.flow.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kr.hs.dgsw.flow.R;

public class NoticeViewHolder extends RecyclerView.ViewHolder {
    public TextView textView;
    public NoticeViewHolder(View v) {
        super(v);
        textView = (TextView) itemView.findViewById(R.id.tvTitle);
    }
}
