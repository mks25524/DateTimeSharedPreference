package com.example.mks.datetimesharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

/**
 * Created by mks on 7/18/2016.
 */
public class Preference {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public Preference(Context context) {
        sharedPreferences = context.getSharedPreferences("Exam", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveDate(String examName, String examDate, String examTime) {
        editor.putString("name", examName);
        editor.putString("date", examDate);
        editor.putString("time", examTime);
        editor.commit();
    }

    public ArrayList<String> retrieveData() {
        ArrayList<String> exam = new ArrayList<>();
        exam.add(sharedPreferences.getString("name", "No data found"));
        exam.add(sharedPreferences.getString("date", "No data found"));
        exam.add(sharedPreferences.getString("time", "No data found"));

        return exam;
    }
}
