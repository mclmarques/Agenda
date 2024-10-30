package com.android.puc.agenda;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.puc.agenda.data.Event;
import java.util.List;

public class EventListFragment extends Fragment {

    private TextView tvEvents;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_list, container, false);

        tvEvents = view.findViewById(R.id.tvEvents);
        Button btnPickDate = view.findViewById(R.id.btnPickDate);

        btnPickDate.setOnClickListener(v -> openDatePicker());

        return view;
    }

    private void openDatePicker() {
        DatePickerFragment datePickerFragment = new DatePickerFragment();

        datePickerFragment.setOnDateSetListener(this::fetchEventsForDate);


        datePickerFragment.show(getParentFragmentManager(), "datePicker");
    }

    private void fetchEventsForDate(String date) {
        ((MainActivity) requireActivity()).getAllEvents(events -> displayEvents(events, date));
    }

    @SuppressLint("SetTextI18n")
    private void displayEvents(List<Event> events, String selectedDate) {
        StringBuilder eventsText = new StringBuilder();

        for (Event event : events) {
            if (event.getDate().toString().equals(selectedDate)) {
                eventsText.append(event.getTitle()).append(": ").append(event.getDescription()).append("\n");
            }
        }

        if (eventsText.length() == 0) {
            tvEvents.setText("Nenhum evento encontrado para a data " + selectedDate);
        } else {
            tvEvents.setText(eventsText.toString());
        }
    }
}
