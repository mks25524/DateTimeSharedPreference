package com.example.mks.datetimesharedpreference;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.prefs.Preferences;

public class MainActivity extends AppCompatActivity {
    private EditText etExamName;
    private static TextView tvExamDate;
    private static TextView tvExamTime;
    private Preference preferences;
    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // use the current date as the default date in the picker
            final Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            // create new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String date = "";

            // format the date for one digit number
            if (dayOfMonth < 10 && monthOfYear < 10)
                date = "0" + dayOfMonth + "-0" + monthOfYear + "-" + year;
            else if (dayOfMonth < 10 && monthOfYear >= 10)
                date = "0" + dayOfMonth + "-" + monthOfYear + "-" + year;
            else if (dayOfMonth >= 10 && monthOfYear < 10)
                date = dayOfMonth + "-0" + monthOfYear + "-" + year;
            else
                date = dayOfMonth + "-" + monthOfYear + "-" + year;

            tvExamDate.setText(date);
        }
    }

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // use the current time as the default values for the picker
            final Calendar cal = Calendar.getInstance();
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            int minute = cal.get(Calendar.MINUTE);

            // create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String time = "";
            String format = "";

            if(hourOfDay == 0) {
                hourOfDay += 12;
                format = "AM";
            } else if (hourOfDay == 12) {
                format = "PM";
            } else if (hourOfDay > 12) {
                hourOfDay -= 12;
                format = "PM";
            } else {
                format = "AM";
            }

            // format the time for one digit number
            if (hourOfDay < 10 && minute < 10)
                time = "0" + hourOfDay + " : 0" + minute + " " + format;
            else if (hourOfDay < 10 && minute >= 10)
                time = "0" + hourOfDay + " : " + minute + " " + format;
            else if (hourOfDay >= 10 && minute < 10)
                time = hourOfDay + " : 0" + minute + " " + format;
            else
                time = hourOfDay + " : " + minute + " " + format;

            tvExamTime.setText(time);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etExamName = (EditText) findViewById(R.id.getExamName);
        tvExamDate = (TextView) findViewById(R.id.getExamDate);
        tvExamTime = (TextView) findViewById(R.id.getExamTime);
        preferences = new Preference(this);
    }
    public void openDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void openTimePicker(View view) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void saveData(View view) {
        String examName = etExamName.getText().toString();
        String examDate = tvExamDate.getText().toString();
        String examTime = tvExamTime.getText().toString();
        preferences.saveDate(examName, examDate, examTime);
        etExamName.setText("");
        tvExamDate.setText("");
        tvExamTime.setText("");
    }

    public void retrieveData(View view) {
        ArrayList<String> examList = preferences.retrieveData();
        etExamName.setText(examList.get(0));
        tvExamDate.setText(examList.get(1));
        tvExamTime.setText(examList.get(2));
    }
}
