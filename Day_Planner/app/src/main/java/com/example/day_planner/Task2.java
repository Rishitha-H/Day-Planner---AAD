package com.example.day_planner;


import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.HashSet;

public class Task2 extends AppCompatActivity implements View.OnClickListener{
    int notetID;
    Button save;
    ImageButton bdate, btime;
    TextView txtDate, txtTime;
    private int year, mnth, day, hour, min;
    Button bs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task2);
        bdate= findViewById(R.id.idate);
        btime= findViewById(R.id.itime);
        txtDate= findViewById(R.id.Date);
        txtTime= findViewById(R.id.Time);

        bdate.setOnClickListener(this);
        btime.setOnClickListener(this);
        EditText editText = (EditText)findViewById(R.id.taskdes);

        Intent intentx = getIntent();
        notetID = intentx.getIntExtra("notetID", -1);
        if(notetID != -1)
        {
            editText.setText(task_display.tasks.get(notetID));

        }

        else
        {
            task_display.tasks.add("");                // as initially, the note is empty
            notetID = task_display.tasks.size() - 1;
            task_display.arrayAdapter.notifyDataSetChanged();
        }

        editText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                task_display.tasks.set(notetID, String.valueOf(s));
               task_display.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.day_planner", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<>(task_display.tasks);
                sharedPreferences.edit().putStringSet("tasks", set).apply();
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });


    }

    public void onClick(View v) {

        if (v == bdate) {

            final java.util.Calendar c = java.util.Calendar.getInstance();
            year = c.get(java.util.Calendar.YEAR);
            mnth = c.get(java.util.Calendar.MONTH);
            day = c.get(java.util.Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + " : " + (monthOfYear + 1) + " : " + year);

                        }
                    }, year, mnth, day);
            datePickerDialog.show();
        }
        if (v == btime) {

            final java.util.Calendar c = java.util.Calendar.getInstance();
            hour = c.get(java.util.Calendar.HOUR_OF_DAY);
            min = c.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, hour, min, true);
            timePickerDialog.show();
        }


    }
}