package com.hrishistudio.vnit.omkarmic.omkarmichealthapp.Yoga;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.Models.YogaPlan;
import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.R;

import java.util.ArrayList;
import java.util.Iterator;

public class YogaPlanSelection extends AppCompatActivity {

    private ViewPager viewPager;
    private PlanPagerAdapter adapter;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoga_plan_selection);

        viewPager = (ViewPager)findViewById(R.id.plans_view);
        adapter = new PlanPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        pos = 0;

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                pos = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    public class PlanPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<YogaPlan> plans;
        private DatabaseReference mRef;

        PlanPagerAdapter(FragmentManager fm) {
            super(fm);
            plans = new ArrayList<>();
            mRef = FirebaseDatabase.getInstance().getReference().child("yoga").child("plans");
            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null){
                        Iterator<DataSnapshot> iterable = dataSnapshot.getChildren().iterator();
                        while (iterable.hasNext()){
                            plans.add(iterable.next().getValue(YogaPlan.class));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        @Override
        public Fragment getItem(int position) {
            PlanFragment fragment = new PlanFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return 5;
        }
    }

    public void nextPlan(View view){
        pos = (pos + 1)%adapter.getCount();
        viewPager.setCurrentItem(pos, true);
    }

    public void previousPlan(View view){
        pos = (pos - 1)%adapter.getCount();
        viewPager.setCurrentItem(pos, true);
    }
}
