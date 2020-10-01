package com.hrishistudio.vnit.omkarmic.omkarmichealthapp.Yoga;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.R;

public class YogaSplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_yoga_splash);

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(YogaSplash.this, YogaHome.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}
