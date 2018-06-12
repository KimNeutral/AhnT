package kr.hs.dgsw.flow.Fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.hs.dgsw.flow.R;

public class OutFragment extends BaseFragment {
    @BindView(R.id.startTime) EditText startTime;
    @BindView(R.id.endTime) EditText endTime;

    private static final String TITLE = "외출";

    public OutFragment() {}

    public static OutFragment newInstance() {
        OutFragment fragment = new OutFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_out, container, false);
        ButterKnife.bind(this, view);
        startTime.setOnClickListener(v -> showTimePicker((TextView)v));
        startTime.setInputType(0);
        endTime.setOnClickListener(v -> showTimePicker((TextView)v));
        endTime.setInputType(0);
        return view;
    }

    @Override
    public String getTitle() {
        return TITLE;
    }

    private void showTimePicker(TextView v){
        TimePicker picker = new TimePicker();
        picker.setTextView(v);
        picker.show(getFragmentManager(), "시간을 선택해주세요.");
    }

    public static class TimePicker extends android.support.v4.app.DialogFragment implements TimePickerDialog.OnTimeSetListener {
        TextView view;

        public TimePicker() {}

        public void setTextView(TextView view) {
            this.view = view;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onTimeSet(android.widget.TimePicker timePicker, int hour, int minute) {
            if(view == null) return;
            view.setText(hour + ":" + minute);
        }
    }
}
