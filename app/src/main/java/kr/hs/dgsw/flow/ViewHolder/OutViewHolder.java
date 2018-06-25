package kr.hs.dgsw.flow.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import kr.hs.dgsw.flow.R;

public class OutViewHolder extends RecyclerView.ViewHolder {
    public TextView tvDate;

    public OutViewHolder(View v) {
        super(v);
        tvDate = v.findViewById(R.id.tvDate);
    }
}
