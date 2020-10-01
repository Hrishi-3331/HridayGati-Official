package com.hrishistudio.vnit.omkarmic.omkarmichealthapp.Dashboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.Meta.Constants;
import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import pl.pawelkleczkowski.customgauge.CustomGauge;

public class DashboardHome extends AppCompatActivity {

    private ProgressDialog dialog;
    private RecyclerView dashboard_view;
    private LinearLayout noDataNotice;
    private LinearLayout noDeviceNotice;
    private LinearLayout healthDashboard;
    private ArrayList<String> parameters;
    private ArrayList<ArrayList<Double>> values;
    private ArrayList<String> time;
    private DashboardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_home);
        dashboard_view = (RecyclerView)findViewById(R.id.dashboard_view);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(DashboardHome.this, LinearLayoutManager.VERTICAL, false);
        dashboard_view.setLayoutManager(manager);

        noDataNotice = (LinearLayout)findViewById(R.id.no_data_notice);
        noDeviceNotice = (LinearLayout)findViewById(R.id.no_device_notice);
        healthDashboard = (LinearLayout)findViewById(R.id.health_dashboard);

        dialog = new ProgressDialog(DashboardHome.this);
        dialog.setTitle("Loading Dashboard");
        dialog.setMessage("Please wait..");
        dialog.setCanceledOnTouchOutside(false);
        parameters = new ArrayList<>();
        time = new ArrayList<>();
        values = new ArrayList<ArrayList<Double>> ();
        loadDevices();
        loadDashboard();
        setDashboardValues();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter = new DashboardAdapter(parameters, values, time, DashboardHome.this);
        dashboard_view.setAdapter(adapter);
    }

    public void loadDevices(){
        dialog.show();
        SharedPreferences preferences = getSharedPreferences(Constants.APP_DATABASE_KEY, MODE_PRIVATE);
        int devices_configured = preferences.getInt(Constants.MY_DEVICES_COUNT, 0);
        if (devices_configured == 0){
            noDeviceNotice.setVisibility(View.VISIBLE);
            noDataNotice.setVisibility(View.VISIBLE);
            healthDashboard.setVisibility(View.GONE);
        }
        else {
            try {
                InputStream inputStream = getApplicationContext().openFileInput(Constants.META_DATA_FILE_NAME);
                if ( inputStream != null ) {
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader reader = new BufferedReader(inputStreamReader);
                    String[] data = reader.readLine().split("OM");
                    String name = data[0].split("DEVICE ")[1];
                    String id = data[1].split(" ")[1];
                    noDeviceNotice.setVisibility(View.GONE);
                    findViewById(R.id.device1_box).setVisibility(View.VISIBLE);
                    TextView nameView = (TextView)findViewById(R.id.device1_name);
                    nameView.setText(name);
                    TextView idView = (TextView)findViewById(R.id.device1_id);
                    idView.setText(id);
                    String parameter_data = reader.readLine();
                    data = parameter_data.split(" ");
                    int count = Integer.parseInt(data[1]);
                    for(int i = 2; i < 2 + count; i++){
                        parameters.add(data[i]);
                        values.add(new ArrayList<Double>());
                    }
                }
            }
            catch (Exception e){
                Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                noDeviceNotice.setVisibility(View.VISIBLE);
                noDataNotice.setVisibility(View.VISIBLE);
                healthDashboard.setVisibility(View.GONE);
            }
        }
    }

    private void loadDashboard(){
        try {
            InputStream inputStream = getApplicationContext().openFileInput(Constants.DATABASE_FILE);
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                for (int i = 0; i < 10; i++){
                    String data_string = reader.readLine();
                    String[] data = data_string.split(" ");
                    time.add(data[0]);
                    for (int p = 1; p <= parameters.size(); p++){
                        values.get(p-1).add(Double.valueOf(data[p]));
                    }
                }
                noDataNotice.setVisibility(View.GONE);
            }
        }
        catch (Exception e){
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            noDataNotice.setVisibility(View.VISIBLE);
            healthDashboard.setVisibility(View.GONE);
        }
        dialog.dismiss();
    }

    public void addNewDevice(View view){
        Intent intent = new Intent(DashboardHome.this, SelectNewDeviceActivity.class);
        startActivity(intent);
    }

    public void setDashboardValues(){
        CustomGauge gauge1 = (CustomGauge) findViewById(R.id.home_gauge1);
        CustomGauge gauge2 = (CustomGauge) findViewById(R.id.home_gauge2);
        CustomGauge gauge3 = (CustomGauge) findViewById(R.id.home_gauge3);
        CustomGauge gauge4 = (CustomGauge) findViewById(R.id.home_gauge4);
        CustomGauge gauge5 = (CustomGauge) findViewById(R.id.home_gauge5);

        TextView gauage_label1 = (TextView) findViewById(R.id.home_param_value1);
        TextView gauage_label2 = (TextView) findViewById(R.id.home_param_value2);
        TextView gauage_label3 = (TextView) findViewById(R.id.home_param_value3);
        TextView gauage_label4 = (TextView) findViewById(R.id.home_param_value4);
        TextView gauage_label5 = (TextView) findViewById(R.id.home_param_value5);

        CustomGauge[] dashboard_gauges = new CustomGauge[]{gauge1, gauge2, gauge3, gauge4, gauge5};
        TextView[] textViews = new TextView[]{gauage_label1, gauage_label2, gauage_label3, gauage_label4, gauage_label5};

        for (int i = 0; i < 5; i++){
            dashboard_gauges[i].setStartValue(70);
            dashboard_gauges[i].setEndValue(100);
            int val = 80 + new Random().nextInt(10);
            dashboard_gauges[i].setValue(val);
            textViews[i].setText(val+"");
        }
    }

    public void goBackView(View view){
        finish();
    }
}
