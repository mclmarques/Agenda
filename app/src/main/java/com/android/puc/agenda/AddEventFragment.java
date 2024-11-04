package com.android.puc.agenda;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AddEventFragment extends Fragment {

    private TextView selectedDateTextView;
    private TextView tvSelectedTime;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_event, container, false);

        selectedDateTextView = view.findViewById(R.id.selectedDateTv);
        tvSelectedTime = view.findViewById(R.id.selectedTimeTv);

        Button btnData = view.findViewById(R.id.btnData);
        btnData.setOnClickListener(v -> openDatePicker());

        Button btnHora = view.findViewById(R.id.btnHora);
        btnHora.setOnClickListener(v -> {
            TimePickerFragment timePickerFragment = new TimePickerFragment();
            timePickerFragment.setOnTimeSetListener(time -> tvSelectedTime.setText(time));
            timePickerFragment.show(getParentFragmentManager(), "timePicker");
        });

        return view;
    }

    private void openDatePicker() {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setOnDateSetListener(date -> selectedDateTextView.setText(date));
        datePickerFragment.show(getParentFragmentManager(), "datePicker");
    }
}
