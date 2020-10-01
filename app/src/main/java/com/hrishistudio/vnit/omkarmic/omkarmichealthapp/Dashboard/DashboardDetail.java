package com.hrishistudio.vnit.omkarmic.omkarmichealthapp.Dashboard;

import android.graphics.Color;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.R;

import java.util.ArrayList;
import java.util.Random;

import pl.pawelkleczkowski.customgauge.CustomGauge;

public class DashboardDetail extends AppCompatActivity {

    private LineChart monthly_chart;
    private BarChart daily_chart;
    private CustomGauge main_gauge;
    private CustomGauge mon_gauge;
    private CustomGauge tue_gauge;
    private CustomGauge wed_gauge;
    private CustomGauge thurs_gauge;
    private CustomGauge fri_gauge;
    private CustomGauge sat_gauge;
    private CustomGauge sun_gauge;
    private CustomGauge[] week_gauges;

    private TextView main_val;
    private TextView mon_val;
    private TextView tue_val;
    private TextView wed_val;
    private TextView thu_val;
    private TextView fri_val;
    private TextView sat_val;
    private TextView sun_val;
    private TextView[] week_vals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_detail);

        main_gauge = (CustomGauge)findViewById(R.id.dd_gauge);
        mon_gauge = (CustomGauge)findViewById(R.id.dd_gauge_monday);
        tue_gauge = (CustomGauge)findViewById(R.id.dd_gauge_tuesday);
        wed_gauge = (CustomGauge)findViewById(R.id.dd_gauge_wednesday);
        thurs_gauge = (CustomGauge)findViewById(R.id.dd_gauge_thursday);
        fri_gauge = (CustomGauge)findViewById(R.id.dd_gauge_friday);
        sat_gauge = (CustomGauge)findViewById(R.id.dd_gauge_saturday);
        sun_gauge = (CustomGauge)findViewById(R.id.dd_gauge_sunday);

        week_gauges = new CustomGauge[]{mon_gauge, tue_gauge, wed_gauge, thurs_gauge, fri_gauge, sat_gauge, sun_gauge};

        main_val = (TextView)findViewById(R.id.dd_val_present);
        mon_val = (TextView)findViewById(R.id.dd_val_mon);
        tue_val = (TextView)findViewById(R.id.dd_val_tue);
        wed_val = (TextView)findViewById(R.id.dd_val_wed);
        thu_val = (TextView)findViewById(R.id.dd_val_thu);
        fri_val = (TextView)findViewById(R.id.dd_val_fri);
        sat_val = (TextView)findViewById(R.id.dd_val_sat);
        sun_val = (TextView)findViewById(R.id.dd_val_sun);

        week_vals = new TextView[]{mon_val, tue_val, wed_val, thu_val, fri_val, sat_val, sun_val};

        monthly_chart = (LineChart)findViewById(R.id.dd_monthly);
        daily_chart = (BarChart)findViewById(R.id.dd_daily);
        setDummy();
    }

    private void setDummy(){
        ArrayList<BarEntry> list = new ArrayList<>();
        for(int i = 0; i < 12; i++){
            BarEntry entry = new BarEntry((float)80 + new Random().nextInt(10), i);
            list.add(entry);
        }
        ArrayList<String> time = new ArrayList<>();
        for(int i = 0; i < 12; i++){
            time.add("time " + i);
        }
        BarDataSet dataSet = new BarDataSet(list, "Heart rate");
        dataSet.setColor(ContextCompat.getColor(DashboardDetail.this, R.color.colorPrimary));
        BarData data = new BarData(time, dataSet);
        daily_chart.setData(data);

        ArrayList<Entry> list1 = new ArrayList<>();
        for(int i = 0; i < 12; i++){
            Entry entry = new Entry((float)80 + new Random().nextInt(8), i);
            list1.add(entry);
        }
        LineDataSet dataSet1 = new LineDataSet(list1, "Heart Rate");
        dataSet1.setCircleColorHole(ContextCompat.getColor(DashboardDetail.this, R.color.colorPrimary));
        dataSet1.setCircleColor(ContextCompat.getColor(DashboardDetail.this, R.color.colorPrimaryDark));
        dataSet1.setColor(Color.GRAY);
        LineData data1 = new LineData(time, dataSet1);
        monthly_chart.setData(data1);

        for (int i = 0; i < 7; i++){
            int val = 80 + new Random().nextInt(20);
            week_gauges[i].setStartValue(70);
            week_gauges[i].setEndValue(110);
            try {
                week_vals[i].setText(val+"");
            }
            catch (Exception e){
                System.out.println("Error in " + i);
            }
            for(int p = 70; p <= val; p++) week_gauges[i].setValue(p);
        }

        int val = 80 + new Random().nextInt(20);
        main_gauge.setStartValue(70);
        main_gauge.setEndValue(110);
        main_val.setText(val+ "");
        for(int p = 70; p <= val; p++) main_gauge.setValue(p);
    }

    public void goBack(View view){finish();}
}
