package com.humiture;

import static android.os.Build.VERSION.SDK_INT;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.humiture.Entity.ClickSpinner;
import com.humiture.Entity.DataBase;
import com.humiture.Entity.DataBase_;

import java.util.ArrayList;

import io.objectbox.query.Query;

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
        Query<DataBase> list = DataBase.queryData(year, month, day, hour, minute);

        float[] second = list.property(DataBase_.Second).findFloats();
        float[] CD = list.property(DataBase_.CD).findFloats();
        float[] RH = list.property(DataBase_.RH).findFloats();

        LineChart CD_lineChart = findViewById(R.id.CD_Line_chart);
        LineChart RH_lineChart = findViewById(R.id.RH_Line_chart);

        YAxis CD_AxisLift = CD_lineChart.getAxisLeft();
        XAxis CD_AxisBottom = CD_lineChart.getXAxis();

        YAxis RH_AxisLift = RH_lineChart.getAxisLeft();
        XAxis RH_AxisBottom = RH_lineChart.getXAxis();

        CD_lineChart.getAxisRight().setEnabled(false);
        CD_lineChart.getLegend().setEnabled(false);

        CD_AxisLift.setAxisLineWidth(2);
        CD_AxisBottom.setAxisLineWidth(2);
        CD_AxisBottom.setPosition(XAxis.XAxisPosition.BOTTOM);

        RH_lineChart.getAxisRight().setEnabled(false);
        RH_lineChart.getLegend().setEnabled(false);

        RH_AxisLift.setAxisLineWidth(2);
        RH_AxisBottom.setAxisLineWidth(2);
        RH_AxisBottom.setPosition(XAxis.XAxisPosition.BOTTOM);

        ArrayList<Entry> CD_lineData = new ArrayList<>();
        ArrayList<Entry> RH_lineData = new ArrayList<>();

        ArrayList<Integer> CD_lineColors = new ArrayList<>();
        ArrayList<Integer> CD_CircleColors = new ArrayList<>();

        ArrayList<Integer> RH_lineColors = new ArrayList<>();
        ArrayList<Integer> RH_CircleColors = new ArrayList<>();

        int darkColor1 = Color.parseColor("#a1cfd4");
        int darkColor2 = Color.parseColor("#94ccff");
        int darkColor3 = Color.parseColor("#90d789");
        int darkColor4 = Color.parseColor("#f3be47");
        int darkColor5 = Color.parseColor("#f9b4a9");

        int lightColor1 = Color.parseColor("#38656a");
        int lightColor2 = Color.parseColor("#1f639c");
        int lightColor3 = Color.parseColor("#286b2a");
        int lightColor4 = Color.parseColor("#7d5700");
        int lightColor5 = Color.parseColor("#bb1b1b");

        int j;

        for (int i = 0; i < second.length; i++) {
            CD_lineData.add(new Entry(second[i], CD[i]));
            RH_lineData.add(new Entry(second[i], RH[i]));

            LineDataSet CD_lineDataSet = new LineDataSet(CD_lineData, "Celsius Degree");
            CD_lineDataSet.setValueTextSize(12);
            CD_lineDataSet.setLineWidth(2);
            CD_lineDataSet.setDrawCircleHole(true);
            CD_lineDataSet.setCircleHoleRadius(5);
            CD_lineDataSet.setCircleRadius(8);
            CD_lineDataSet.setValueFormatter(new LargeValueFormatter(" ℃"));
            CD_lineChart.setData(new LineData(CD_lineDataSet));
            CD_lineChart.setVisibleXRange(0, 10);

            LineDataSet RH_lineDataSet = new LineDataSet(RH_lineData, "Relative Humidity");
            RH_lineDataSet.setValueTextSize(12);
            RH_lineDataSet.setLineWidth(2);
            RH_lineDataSet.setDrawCircleHole(true);
            RH_lineDataSet.setCircleHoleRadius(5);
            RH_lineDataSet.setCircleRadius(8);
            RH_lineDataSet.setValueFormatter(new LargeValueFormatter(" %"));
            RH_lineChart.setData(new LineData(RH_lineDataSet));
            RH_lineChart.setVisibleXRange(0, 10);

            if ((getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == 32) {
                j = CD[i] < 0 ? darkColor1 : ((CD[i] < 10) ? darkColor2 : ((CD[i] < 20) ? darkColor3 : ((CD[i] < 30) ? darkColor4 : darkColor5)));
                CD_lineColors.add(j);
                CD_CircleColors.add(j);

                j = RH[i] < 20 ? darkColor1 : ((RH[i] < 40) ? darkColor2 : ((RH[i] < 60) ? darkColor3 : ((RH[i] < 80) ? darkColor4 : darkColor5)));
                RH_lineColors.add(j);
                RH_CircleColors.add(j);

                CD_lineDataSet.setCircleHoleColor(Color.BLACK);
                CD_AxisLift.setTextColor(Color.WHITE);
                CD_AxisLift.setAxisLineColor(Color.WHITE);
                CD_AxisBottom.setTextColor(Color.WHITE);
                CD_AxisBottom.setAxisLineColor(Color.WHITE);

                RH_lineDataSet.setCircleHoleColor(Color.BLACK);
                RH_AxisLift.setTextColor(Color.WHITE);
                RH_AxisLift.setAxisLineColor(Color.WHITE);
                RH_AxisBottom.setTextColor(Color.WHITE);
                RH_AxisBottom.setAxisLineColor(Color.WHITE);
            } else {
                j = CD[i] < 0 ? lightColor1 : CD[i] < 10 ? lightColor2 : CD[i] < 20 ? lightColor3 : CD[i] < 30 ? lightColor4 : lightColor5;
                CD_lineColors.add(j);
                CD_CircleColors.add(j);

                j = RH[i] < 20 ? lightColor1 : RH[i] < 40 ? lightColor2 : RH[i] < 60 ? lightColor3 : RH[i] < 80 ? lightColor4 : lightColor5;
                RH_lineColors.add(j);
                RH_CircleColors.add(j);

                CD_lineDataSet.setCircleHoleColor(Color.WHITE);
                CD_AxisLift.setTextColor(Color.BLACK);
                CD_AxisLift.setAxisLineColor(Color.BLACK);
                CD_AxisBottom.setTextColor(Color.BLACK);
                CD_AxisBottom.setAxisLineColor(Color.BLACK);

                RH_lineDataSet.setCircleHoleColor(Color.WHITE);
                RH_AxisLift.setTextColor(Color.BLACK);
                RH_AxisLift.setAxisLineColor(Color.BLACK);
                RH_AxisBottom.setTextColor(Color.BLACK);
                RH_AxisBottom.setAxisLineColor(Color.BLACK);
            }

            if (i != 0) {
                CD_lineDataSet.setColors(CD_lineColors);
                RH_lineDataSet.setColors(RH_lineColors);
            } else {
                CD_lineColors.remove(0);
                RH_lineColors.remove(0);
            }

            CD_lineDataSet.setValueTextColors(CD_CircleColors);
            CD_lineDataSet.setCircleColors(CD_CircleColors);

            RH_lineDataSet.setValueTextColors(RH_CircleColors);
            RH_lineDataSet.setCircleColors(RH_CircleColors);
        }

        if (SDK_INT >= 30) {
            CD_lineChart.setHardwareAccelerationEnabled(true);
            RH_lineChart.setHardwareAccelerationEnabled(true);
        }

        CD_lineChart.notifyDataSetChanged();
        CD_lineChart.invalidate();

        RH_lineChart.notifyDataSetChanged();
        RH_lineChart.invalidate();
    }
}

