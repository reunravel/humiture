package com.humiture;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.humiture.Entity.ClickSpinner;
import com.humiture.Entity.DataBase;

public class HistoryActivity extends AppCompatActivity {
    String year;
    String month;
    String day;
    String hour;
    String minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ClickSpinner yearClickSpinner = findViewById(R.id.Year);
        ClickSpinner monthClickSpinner = findViewById(R.id.Month);
        ClickSpinner dayClickSpinner = findViewById(R.id.Day);
        ClickSpinner hourClickSpinner = findViewById(R.id.Hour);
        ClickSpinner minuteClickSpinner = findViewById(R.id.Minute);

        yearClickSpinner.setOnClickMyListener(() -> {
            ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, DataBase.listYearData());
            yearClickSpinner.setAdapter(yearAdapter);
            yearClickSpinner.performClick();
            yearClickSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    year = yearAdapter.getItem(position);
                    monthClickSpinner.setAdapter(null);
                    dayClickSpinner.setAdapter(null);
                    hourClickSpinner.setAdapter(null);
                    minuteClickSpinner.setAdapter(null);
                    month = null;
                    day = null;
                    hour = null;
                    minute = null;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        });

        monthClickSpinner.setOnClickMyListener(() -> {
            if (year != null) {
                ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, DataBase.listMonthData(year));
                monthClickSpinner.setAdapter(monthAdapter);
                monthClickSpinner.performClick();
                monthClickSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        month = monthAdapter.getItem(position);
                        dayClickSpinner.setAdapter(null);
                        hourClickSpinner.setAdapter(null);
                        minuteClickSpinner.setAdapter(null);
                        day = null;
                        hour = null;
                        minute = null;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
        });

        dayClickSpinner.setOnClickMyListener(() -> {
            if (month != null) {
                ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, DataBase.listDayData(year, month));
                dayClickSpinner.setAdapter(dayAdapter);
                dayClickSpinner.performClick();
                dayClickSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        day = dayAdapter.getItem(position);
                        hourClickSpinner.setAdapter(null);
                        minuteClickSpinner.setAdapter(null);
                        hour = null;
                        minute = null;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
        });

        hourClickSpinner.setOnClickMyListener(() -> {
            if (day != null) {
                ArrayAdapter<String> hourAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, DataBase.listHourData(year, month, day));
                hourClickSpinner.setAdapter(hourAdapter);
                hourClickSpinner.performClick();
                hourClickSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        hour = hourAdapter.getItem(position);
                        minuteClickSpinner.setAdapter(null);
                        minute = null;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
        });

        minuteClickSpinner.setOnClickMyListener(() -> {
            if (hour != null) {
                ArrayAdapter<String> minuteAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, DataBase.listMinuteData(year, month, day, hour));
                minuteClickSpinner.setAdapter(minuteAdapter);
                minuteClickSpinner.performClick();
                minuteClickSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        minute = minuteAdapter.getItem(position);
                        draw();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
        });
    }

    public void draw() {
    }
}

