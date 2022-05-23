package com.humiture;

import static android.os.Build.VERSION.SDK_INT;
import static com.humiture.entity.TcpClient.sharedCenter;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.humiture.entity.TcpClient;

import java.util.ArrayList;

public class introductoryActivity extends AppCompatActivity {
    int i, j, k;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductry);

        findViewById(R.id.History).setOnClickListener(v -> {
            Intent intent = new Intent(introductoryActivity.this, HistoryActivity.class);
            startActivity(intent);
        });

        TcpClient.sharedCenter().connect();

        LineChart CD_lineChart = findViewById(R.id.CD_Line_chart);
        LineChart RH_lineChart = findViewById(R.id.RH_Line_chart);

        YAxis CD_AxisLift = CD_lineChart.getAxisLeft();
        YAxis RH_AxisLift = RH_lineChart.getAxisLeft();

        Description CD_Description = CD_lineChart.getDescription();
        Description RH_Description = RH_lineChart.getDescription();

        CD_lineChart.getXAxis().setEnabled(false);
        CD_lineChart.getAxisRight().setEnabled(false);
        CD_lineChart.getLegend().setEnabled(false);
        CD_AxisLift.setAxisLineWidth(2);
        CD_Description.setTextSize(25);

        RH_lineChart.getXAxis().setEnabled(false);
        RH_lineChart.getAxisRight().setEnabled(false);
        RH_lineChart.getLegend().setEnabled(false);
        RH_AxisLift.setAxisLineWidth(2);
        RH_Description.setTextSize(25);

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


        sharedCenter().setReceiveCallbackBlock(receivedMessage -> {

            float CD_value = receivedMessage[0];

            float RH_value = receivedMessage[1];

            CD_lineData.add(new Entry(i, CD_value));
            RH_lineData.add(new Entry(i, RH_value));

            if (i >= 10) {
                CD_lineData.remove(0);
                CD_lineColors.remove(0);
                CD_CircleColors.remove(0);

                RH_lineData.remove(0);
                RH_lineColors.remove(0);
                RH_CircleColors.remove(0);
            }

            LineDataSet CD_lineDataSet = new LineDataSet(CD_lineData, "Celsius Degree");
            CD_lineDataSet.setValueTextSize(12);
            CD_lineDataSet.setLineWidth(2);
            CD_lineDataSet.setDrawCircleHole(true);
            CD_lineDataSet.setCircleHoleRadius(5);
            CD_lineDataSet.setCircleRadius(8);
            CD_lineDataSet.setValueFormatter(new LargeValueFormatter(" ℃"));
            CD_lineChart.setData(new LineData(CD_lineDataSet));
            CD_lineChart.setAutoScaleMinMaxEnabled(true);
            CD_lineChart.setScaleEnabled(false);
            CD_Description.setText("温度: " + CD_value + " ℃");

            LineDataSet RH_lineDataSet = new LineDataSet(RH_lineData, "Relative Humidity");
            RH_lineDataSet.setValueTextSize(12);
            RH_lineDataSet.setLineWidth(2);
            RH_lineDataSet.setDrawCircleHole(true);
            RH_lineDataSet.setCircleHoleRadius(5);
            RH_lineDataSet.setCircleRadius(8);
            RH_lineDataSet.setValueFormatter(new LargeValueFormatter(" %"));
            RH_lineChart.setData(new LineData(RH_lineDataSet));
            RH_lineChart.setAutoScaleMinMaxEnabled(true);
            RH_lineChart.setScaleEnabled(false);
            RH_Description.setText("湿度: " + RH_value + " %");


            if ((getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == 32) {
                j = CD_value < 0 ? darkColor1 : ((CD_value < 10) ? darkColor2 : ((CD_value < 20) ? darkColor3 : ((CD_value < 30) ? darkColor4 : darkColor5)));

                k = RH_value < 20 ? darkColor1 : ((RH_value < 40) ? darkColor2 : ((RH_value < 60) ? darkColor3 : ((RH_value < 80) ? darkColor4 : darkColor5)));

                CD_lineDataSet.setCircleHoleColor(Color.BLACK);
                CD_AxisLift.setTextColor(Color.WHITE);
                CD_AxisLift.setAxisLineColor(Color.WHITE);

                RH_lineDataSet.setCircleHoleColor(Color.BLACK);
                RH_AxisLift.setTextColor(Color.WHITE);
                RH_AxisLift.setAxisLineColor(Color.WHITE);
            } else {
                j = CD_value < 0 ? lightColor1 : CD_value < 10 ? lightColor2 : CD_value < 20 ? lightColor3 : CD_value < 30 ? lightColor4 : lightColor5;

                k = RH_value < 20 ? lightColor1 : RH_value < 40 ? lightColor2 : RH_value < 60 ? lightColor3 : RH_value < 80 ? lightColor4 : lightColor5;

                CD_lineDataSet.setCircleHoleColor(Color.WHITE);
                CD_AxisLift.setTextColor(Color.BLACK);
                CD_AxisLift.setAxisLineColor(Color.BLACK);

                RH_lineDataSet.setCircleHoleColor(Color.WHITE);
                RH_AxisLift.setTextColor(Color.BLACK);
                RH_AxisLift.setAxisLineColor(Color.BLACK);
            }
            CD_lineColors.add(j);
            CD_CircleColors.add(j);
            CD_Description.setTextColor(j);

            RH_lineColors.add(k);
            RH_CircleColors.add(k);
            RH_Description.setTextColor(k);

            CD_lineDataSet.setValueTextColors(CD_CircleColors);
            CD_lineDataSet.setCircleColors(CD_CircleColors);

            RH_lineDataSet.setValueTextColors(RH_CircleColors);
            RH_lineDataSet.setCircleColors(RH_CircleColors);

            if (i != 0) {
                CD_lineDataSet.setColors(CD_lineColors);
                RH_lineDataSet.setColors(RH_lineColors);
            } else {
                CD_lineColors.remove(0);
                RH_lineColors.remove(0);
            }

            if (SDK_INT >= 30) {
                CD_lineChart.setHardwareAccelerationEnabled(true);
                RH_lineChart.setHardwareAccelerationEnabled(true);
            }

            CD_lineChart.invalidate();

            RH_lineChart.invalidate();

            i++;
        });
    }
}