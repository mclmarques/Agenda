package com.android.puc.agenda;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment {

    public interface OnTimeSetListener {
        void onTimeSelected(String time);
    }

    private OnTimeSetListener listener;

    public void setOnTimeSetListener(OnTimeSetListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Define a hora atual como padrão
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), (TimePicker view, int hourOfDay, int minute1) -> {
            @SuppressLint("DefaultLocale") String time = String.format("%02d:%02d", hourOfDay, minute1);
            if (listener != null) {
                listener.onTimeSelected(time);
            }
        }, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }
}
