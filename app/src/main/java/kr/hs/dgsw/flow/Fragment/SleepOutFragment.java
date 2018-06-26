package kr.hs.dgsw.flow.Fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import kr.hs.dgsw.flow.Activity.MainActivity;
import kr.hs.dgsw.flow.Adapter.OutSleepAdapter;
import kr.hs.dgsw.flow.Helper.CalendarHelper;
import kr.hs.dgsw.flow.Helper.SharedPreferencesHelper;
import kr.hs.dgsw.flow.Interface.IPassValue;
import kr.hs.dgsw.flow.Model.SleepOut;
import kr.hs.dgsw.flow.Network.NetworkManager;
import kr.hs.dgsw.flow.Network.Response.OutData;
import kr.hs.dgsw.flow.Network.Response.OutSleepData;
import kr.hs.dgsw.flow.Network.Response.ResponseFormat;
import kr.hs.dgsw.flow.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SleepOutFragment extends BaseFragment implements IPassValue<ResponseFormat<OutSleepData>> {
    @BindView(R.id.startTime) EditText startTime;
    @BindView(R.id.endTime) EditText endTime;
    @BindView(R.id.reason) EditText reason;

    OutSleepAdapter adapter;

    private static final String TITLE = "외박 신청";

    public SleepOutFragment() {}

    public static SleepOutFragment newInstance(OutSleepAdapter adapter) {
        SleepOutFragment fragment = new SleepOutFragment();
        fragment.adapter = adapter;
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
        View view = inflater.inflate(R.layout.fragment_sleep_out, container, false);
        ButterKnife.bind(this, view);
        startTime.setOnClickListener(v -> {
            showTimePicker((TextView)v);
            showDatePicker((TextView)v);
        });
        startTime.setInputType(0);
        endTime.setOnClickListener(v -> {
            showTimePicker((TextView)v);
            showDatePicker((TextView)v);
        });
        endTime.setInputType(0);
        return view;
    }

    @Override
    public String getTitle() {
        return TITLE;
    }

    @OnClick(R.id.btnApply)
    public void ApplyOutGo() {
        String startStr = startTime.getText().toString();
        String endStr = endTime.getText().toString();
        String reasonText = reason.getText().toString();

        boolean isError = false;

        if (TextUtils.isEmpty(startStr)) {
            startTime.setError("시작을 입력해주세요!");
            isError = true;
        } else if (TextUtils.isEmpty(endStr)) {
            endTime.setError("끝을 입력해주세요!");
            isError = true;
        } else if (TextUtils.isEmpty(reasonText)) {
            reason.setError("사유를 입력해주세요!");
            isError = true;
        }

        if(!isError) {
            NetworkManager.applyOutSleep(this, SharedPreferencesHelper.getPreference("token"), startStr, endStr, reasonText);
        }
    }

    private void showTimePicker(TextView v){
        TimePicker picker = new TimePicker();
        picker.setTextView(v);
        picker.show(getFragmentManager(), "시간을 선택해주세요.");
    }

    private void showDatePicker(TextView v){
        DatePicker picker = new DatePicker();
        picker.setTextView(v);
        picker.show(getFragmentManager(), "날짜을 선택해주세요.");
    }

    @Override
    public void passValue(ResponseFormat<OutSleepData> value) {
        Resources r = getResources();
        Toast.makeText(getContext(), value.getMessage(), Toast.LENGTH_SHORT).show();

        if(value.getStatus() == r.getInteger(R.integer.status_success)) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            realm.copyToRealm(value.getData().getSleepOut());
            realm.commitTransaction();

            adapter.add(value.getData().getSleepOut());

            MainActivity activity = (MainActivity) getActivity();
            activity.getFragmentManager().popBackStack();
        }
    }

    public static class DatePicker extends android.support.v4.app.DialogFragment implements DatePickerDialog.OnDateSetListener {
        TextView view;

        public DatePicker() {}

        public void setTextView(TextView view) {
            this.view = view;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {
            if(view == null) return;
            view.setText(String.format("%04d", year) + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", day));
        }
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
            String str = view.getText().toString();
            view.setText(str + " " + String.format("%02d", hour) + ":" + String.format("%02d", minute));
        }
    }
}
