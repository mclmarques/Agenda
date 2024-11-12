package com.android.puc.agenda;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddEventFragment extends Fragment {

    private TextView selectedDateTextView;
    private TextView tvSelectedTime;
    private EditText eventDescriptionEt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_event, container, false);

        selectedDateTextView = view.findViewById(R.id.selectedDateTv);
        tvSelectedTime = view.findViewById(R.id.selectedTimeTv);
        eventDescriptionEt = view.findViewById(R.id.descricaoEditText);

        Button btnData = view.findViewById(R.id.btnData);
        btnData.setOnClickListener(v -> openDatePicker());

        Button btnHora = view.findViewById(R.id.btnHora);
        btnHora.setOnClickListener(v -> {
            TimePickerFragment timePickerFragment = new TimePickerFragment();
            timePickerFragment.setOnTimeSetListener(time -> tvSelectedTime.setText(time));
            timePickerFragment.show(getParentFragmentManager(), "timePicker");
        });

        Button btnOk = view.findViewById(R.id.btnOk); // OK Button to save the event
        btnOk.setOnClickListener(v -> addEvent());

        return view;
    }

    private void openDatePicker() {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setOnDateSetListener(date -> selectedDateTextView.setText(date));
        datePickerFragment.show(getParentFragmentManager(), "datePicker");
    }

    private void addEvent() {
        String date = selectedDateTextView.getText().toString();
        String time = tvSelectedTime.getText().toString();
        String description = eventDescriptionEt.getText().toString();

        if (date.isEmpty() || time.isEmpty() || description.isEmpty()) {
            Toast.makeText(getContext(), "Please select date, time, and enter a description.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Combine date and time
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date eventDate;
        try {
            eventDate = dateTimeFormat.parse(date + " " + time);
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Invalid date/time format.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Pass the event to MainActivity
        ((MainActivity) requireActivity()).addNewEvent("Event Title", eventDate, description);

        // Show confirmation and clear fields
        Toast.makeText(getContext(), "Event added successfully!", Toast.LENGTH_SHORT).show();
        selectedDateTextView.setText("");
        tvSelectedTime.setText("");
        eventDescriptionEt.setText("");
    }
}
