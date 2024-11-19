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

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class EventListFragment extends Fragment {

    private TextView tvEvents;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_list, container, false);

        tvEvents = view.findViewById(R.id.tvEvents);
        Button btnPickDate = view.findViewById(R.id.btnPickDate);
        Button btnToday = view.findViewById(R.id.btnToday);

        btnPickDate.setOnClickListener(v -> openDatePicker());
        btnToday.setOnClickListener(v -> fetchTodayEvents());

        return view;
    }

    private void openDatePicker() {
        DatePickerFragment datePickerFragment = new DatePickerFragment();

        datePickerFragment.setOnDateSetListener(this::fetchEventsForDate);

        datePickerFragment.show(getParentFragmentManager(), "datePicker");
    }
    private void fetchTodayEvents() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String todayDate = sdf.format(Calendar.getInstance().getTime());

        fetchEventsForDate(todayDate);
    }
    private void fetchEventsForDate(String date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = date;

        try {
            Date parsedDate = sdf.parse(date);
            formattedDate = sdf.format(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String finalFormattedDate = formattedDate;
        ((MainActivity) requireActivity()).getAllEvents(events -> displayEvents(events, finalFormattedDate));
    }

    @SuppressLint("SetTextI18n")
    private void displayEvents(List<Event> events, String selectedDate) {
        StringBuilder eventsText = new StringBuilder();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        for (Event event : events) {
            String eventDate = dateFormat.format(event.getDate());
            if (eventDate.equals(selectedDate)) {
                String eventTime = timeFormat.format(event.getDate());
                eventsText.append(eventTime)  // Display event time
                        .append(": ").append(event.getDescription())
                        .append("\n");
            }
        }

        if (eventsText.length() == 0) {
            tvEvents.setText("Nenhum evento encontrado para a data " + selectedDate);
        } else {
            tvEvents.setText(eventsText.toString());
        }
    }

}