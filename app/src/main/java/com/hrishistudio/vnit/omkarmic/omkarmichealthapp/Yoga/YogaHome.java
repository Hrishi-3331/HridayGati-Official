package com.hrishistudio.vnit.omkarmic.omkarmichealthapp.Yoga;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.R;

public class YogaHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_yoga_home);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUSer = mAuth.getCurrentUser();

        FirebaseDatabase.getInstance().getReference().child("users").child(mUSer.getUid()).child("yoga").child("current_subscription").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null){
                    Intent intent = new Intent(YogaHome.this, PlanConfig.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
