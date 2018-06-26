package kr.hs.dgsw.flow.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import kr.hs.dgsw.flow.Model.SleepOut;
import kr.hs.dgsw.flow.R;
import kr.hs.dgsw.flow.ViewHolder.OutSleepViewHolder;

public class OutSleepAdapter extends RecyclerView.Adapter<OutSleepViewHolder> {

    private List<SleepOut> items;

    public OutSleepAdapter(List<SleepOut> items) {
        this.items = new ArrayList<>(items);
    }


    public void add(SleepOut sleepOut) {
        try {
            items.add(sleepOut);
            notifyDataSetChanged();
        }
        catch (Exception e) {
            Log.d("", e.toString());
        }
    }

    @Override
    public OutSleepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_out_sleep, parent, false);

        return new OutSleepViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OutSleepViewHolder holder, int position) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        SleepOut data = items.get(position);
        holder.tvDate.setText(formatter.format(data.getStartTime()) + " ~ " + formatter.format(data.getEndTime()));
        if(data.getAccept() == 1) {
            holder.img.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
