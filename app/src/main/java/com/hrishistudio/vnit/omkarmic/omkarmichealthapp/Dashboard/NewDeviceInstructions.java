package com.hrishistudio.vnit.omkarmic.omkarmichealthapp.Dashboard;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.R;

public class NewDeviceInstructions extends AppCompatActivity {

    private String device_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_device_instructions);
        Intent intent = getIntent();
        device_id = intent.getStringExtra("device_model_id");
    }

    public void addNew(View view){
        Intent intent = new Intent(NewDeviceInstructions.this, NewDeviceConfigurationActivity.class);
        intent.putExtra("device_model_id", device_id);
        startActivity(intent);
        finish();
    }

}
