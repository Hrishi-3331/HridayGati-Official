package com.hrishistudio.vnit.omkarmic.omkarmichealthapp.Home;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.Authentication.SignIn;
import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.R;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if(FirebaseAuth.getInstance().getCurrentUser() == null){
                    intent = new Intent(Splash.this, SignIn.class);
                }
                else {
                    intent = new Intent(Splash.this, MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
