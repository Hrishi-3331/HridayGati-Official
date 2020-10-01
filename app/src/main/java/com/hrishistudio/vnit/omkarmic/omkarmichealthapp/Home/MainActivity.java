package com.hrishistudio.vnit.omkarmic.omkarmichealthapp.Home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hrishistudio.devstudio3331.carouselview.CarouselView;
import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.Dashboard.DashboardHome;
import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.Meta.Constants;
import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.Models.Post;
import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.Nutrition.NutritionSplash;
import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.R;
import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.Spirituality.SpiritualSplash;
import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.ViewHolders.PostViewHolder;
import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.Yoga.YogaSplash;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import pl.pawelkleczkowski.customgauge.CustomGauge;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private NavigationView mNavigation;
    private ActionBarDrawerToggle mToggle;
    private RecyclerView postView;
    private DatabaseReference mRef;
    private RecyclerView.LayoutManager manager;
    private CarouselView promotionalCarousel;
    private LinearLayout noDevicesNotice;
    private int j;
    private LinearLayout dashboardLayout;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        mDrawer = (DrawerLayout) findViewById(R.id.mDrawer);
        mNavigation = (NavigationView) findViewById(R.id.mNavigation);
        mToggle = new ActionBarDrawerToggle(MainActivity.this, mDrawer, toolbar, R.string.open, R.string.close);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
        DrawerArrowDrawable drawable = mToggle.getDrawerArrowDrawable();
        drawable.setColor(ContextCompat.getColor(this,R.color.colorAccent));
        mToggle.setDrawerArrowDrawable(drawable);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        noDevicesNotice = (LinearLayout)findViewById(R.id.no_device_notice);
        dashboardLayout = (LinearLayout)findViewById(R.id.dashboard);

        mDialog = new ProgressDialog(MainActivity.this);
        mDialog.setTitle("Loading Data");
        mDialog.setMessage("Please wait");
        mDialog.setCanceledOnTouchOutside(false);

        promotionalCarousel = (CarouselView)findViewById(R.id.home_promotions);
        promotionalCarousel.setLength(4);
        promotionalCarousel.setColorActive(getResources().getColor(R.color.colorAccent));
        promotionalCarousel.setColorInActive(getResources().getColor(android.R.color.white));
        promotionalCarousel.enableDots();

        mNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.drawer_profile:
                        //startActivity(new Intent(MainActivity.this, BAIMain.class));
                        break;

                    case R.id.drawer_store:
                        //startActivity(new Intent(MainActivity.this, SleepTime.class));
                        break;

                    case R.id.drawer_subscriptions:
                        //tartActivity(new Intent(MainActivity.this, HospitalReviews.class));
                        break;

                    case R.id.drawer_contact:
                        //mAuth.signOut();
                        //startActivity(new Intent(MainActivity.this, Authentication.class));
                        //finish();
                        break;

                    case R.id.drawer_logout:
                        //tartActivity(new Intent(MainActivity.this, HospitalReviews.class));
                        break;

                    default:
                        break;
                }
                mDrawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });
        mNavigation.setItemIconTintList(null);

        postView = (RecyclerView)findViewById(R.id.posts_view);
        mRef = FirebaseDatabase.getInstance().getReference().child("posts");
        manager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        postView.setLayoutManager(manager);
        mDialog.show();
    }

    private void fetchPromotions(){
        FirebaseDatabase.getInstance().getReference().child("promotions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null){
                    for(DataSnapshot key :  dataSnapshot.getChildren()){
                        promotionalCarousel.addPromotion(key.getValue().toString());
                    }
                    promotionalCarousel.showCarousel(getSupportFragmentManager());
                    mDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void pillarOpen(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.pillar_1:
                intent = new Intent(MainActivity.this, YogaSplash.class);
                startActivity(intent);
                break;

            case R.id.pillar_2:
                intent = new Intent(MainActivity.this, NutritionSplash.class);
                startActivity(intent);
                break;

            case R.id.pillar_3:
                intent = new Intent(MainActivity.this, SpiritualSplash.class);
                startActivity(intent);
                break;

            case R.id.pillar_4:
                intent = new Intent(MainActivity.this, NutritionSplash.class);
                startActivity(intent);
                break;
        }
    }


    private void loadDashboard(){
        SharedPreferences preferences = getSharedPreferences(Constants.APP_DATABASE_KEY, MODE_PRIVATE);
        int devices_configured = preferences.getInt(Constants.MY_DEVICES_COUNT, 0);
        if (devices_configured == 0){
            noDevicesNotice.setVisibility(View.VISIBLE);
            dashboardLayout.setVisibility(View.GONE);
        }
        else {
            try {
                ArrayList<ArrayList<Double>> values = new ArrayList<>();
                ArrayList<String> parameters = new ArrayList<>();
                ArrayList<String> time = new ArrayList<>();
                InputStream inputStream = getApplicationContext().openFileInput(Constants.META_DATA_FILE_NAME);
                if ( inputStream != null ) {
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader reader = new BufferedReader(inputStreamReader);
                    reader.readLine();
                    dashboardLayout.setVisibility(View.VISIBLE);
                    noDevicesNotice.setVisibility(View.GONE);
                    String parameter_data = reader.readLine();
                    String[] data = parameter_data.split(" ");
                    int count = Integer.parseInt(data[1]);
                    for(int i = 2; i < 2 + count; i++){
                        parameters.add(data[i]);
                        values.add(new ArrayList<Double>());
                    }
                }

                InputStream inputStream2 = getApplicationContext().openFileInput(Constants.DATABASE_FILE);
                if (inputStream2 != null) {
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream2);
                    BufferedReader reader = new BufferedReader(inputStreamReader);
                    for (int i = 0; i < 10; i++){
                        String data_string = reader.readLine();
                        String[] data = data_string.split(" ");
                        time.add(data[0]);
                        for (int p = 1; p < data.length; p++){
                            values.get(p-1).add(Double.valueOf(data[p]));
                        }
                    }

                    for (int i = 0; i < parameters.size(); i++) {
                        String parameter = parameters.get(i);
                        if (parameter.trim().equals("BODY_TEMPERATURE")) {
                            TextView view = (TextView) findViewById(R.id.param_value1);
                            view.setText(String.valueOf((int)Math.round(values.get(i).get(0))));

                            final CustomGauge gauge = (CustomGauge) findViewById(R.id.gauge2);
                            gauge.setStartValue(90);
                            gauge.setEndValue(110);
                            final int val = values.get(i).get(0).intValue();
                            new Thread() {
                                public void run() {
                                    for (j = 90; j < val; j++) {
                                        try {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    gauge.setValue(j);
                                                }
                                            });
                                            Thread.sleep(50);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }.start();

                        } else if (parameter.trim().equals("BLOOD_PRESSURE")) {
                            TextView view = (TextView) findViewById(R.id.param_value1);
                            view.setText(String.valueOf((int)Math.round(values.get(i).get(0))));
                            final CustomGauge gauge = (CustomGauge) findViewById(R.id.gauge1);
                            gauge.setStartValue(50);
                            gauge.setEndValue(160);
                            int val = values.get(i).get(0).intValue();
                            for (int j = 50; j < val; j++) gauge.setValue(j);
                        }
                        else if(parameter.trim().equals("OXYGEN_LEVEL")){
                            BarChart barChart = (BarChart)findViewById(R.id.home_dashboard_chart2);
                            ArrayList<BarEntry> valueSet1 = new ArrayList<>();
                            for (int p = 0; p < values.get(0).size(); p++) {
                                BarEntry entry = new BarEntry(values.get(i).get(p).floatValue(), p);
                                valueSet1.add(entry);
                            }
                            BarDataSet barDataSet = new BarDataSet(valueSet1, "Oxygen Level");
                            barDataSet.setColor(Color.parseColor("#00c853"));
                            BarData data = new BarData(time, barDataSet);
                            barChart.setData(data);

                        }
                        else {
                            LineChart lineChart = (LineChart)findViewById(R.id.home_dashboard_chart1);
                            ArrayList<Entry> valueSet1 = new ArrayList<>();
                            for (int p = 0; p < values.get(0).size(); p++) {
                                Entry entry = new Entry(values.get(i).get(p).floatValue(), p);
                                valueSet1.add(entry);
                            }
                            LineDataSet lineDataSet = new LineDataSet(valueSet1, "Heart Rate");
                            lineDataSet.setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
                            lineDataSet.setLineWidth(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                            lineDataSet.setLineWidth(2f);
                            LineData data = new LineData(time, lineDataSet);
                            lineChart.setData(data);
                        }
                    }
                }
            }
            catch (Exception e){
                Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                dashboardLayout.setVisibility(View.GONE);
                noDevicesNotice.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu, menu);
        Drawable drawable = menu.findItem(R.id.appbar_settings).getIcon();
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, ContextCompat.getColor(this,R.color.colorAccent));
        menu.findItem(R.id.appbar_settings).setIcon(drawable);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        fetchPromotions();
        FirebaseRecyclerAdapter<Post, PostViewHolder> adapter = new FirebaseRecyclerAdapter<Post, PostViewHolder>(Post.class, R.layout.post_layout, PostViewHolder.class, mRef) {
            @Override
            protected void populateViewHolder(PostViewHolder viewHolder, Post model, int position) {
                viewHolder.setPostView(model.getPost_title(), model.getPoster_detail(), model.getPost_text());
                viewHolder.setImages(model.getPost_image(), model.getPoster_image());
                viewHolder.setListners(MainActivity.this, getRef(position).getKey());
            }
        };
        postView.setAdapter(adapter);
        loadDashboard();
    }

    public void openDashboard(View view){
        Intent intent = new Intent(MainActivity.this, DashboardHome.class);
        startActivity(intent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
