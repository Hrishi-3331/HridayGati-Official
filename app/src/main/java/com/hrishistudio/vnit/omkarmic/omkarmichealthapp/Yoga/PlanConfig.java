package com.hrishistudio.vnit.omkarmic.omkarmichealthapp.Yoga;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.R;

public class PlanConfig extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_config);
    }

    public void next(View view){
        Intent intent = new Intent(PlanConfig.this, YogaPlanBenefit.class);
        startActivity(intent);
        finish();
    }
}
