package com.hrishistudio.vnit.omkarmic.omkarmichealthapp.Yoga;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.R;

public class YogaPlanBenefit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoga_plan_benefit);
    }

    public void next(View view){
        Intent intent = new Intent(YogaPlanBenefit.this, YogaPlanSelection.class);
        startActivity(intent);
        finish();
    }
}
