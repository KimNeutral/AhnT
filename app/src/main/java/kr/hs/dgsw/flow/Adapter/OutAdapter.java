package kr.hs.dgsw.flow.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.List;

import kr.hs.dgsw.flow.Model.GoOut;
import kr.hs.dgsw.flow.R;
import kr.hs.dgsw.flow.ViewHolder.OutViewHolder;

public class OutAdapter extends RecyclerView.Adapter<OutViewHolder> {

    private List<GoOut> items;

    public OutAdapter(List<GoOut> items) {
        this.items = items;
    }

    @Override
    public OutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_out, parent, false);

        return new OutViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OutViewHolder holder, int position) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        GoOut data = items.get(position);
        holder.tvDate.setText(formatter.format(data.getStartTime()) + " ~ " + formatter.format(data.getEndTime()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
