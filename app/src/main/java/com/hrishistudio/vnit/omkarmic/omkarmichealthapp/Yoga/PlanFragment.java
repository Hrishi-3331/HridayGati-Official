package com.hrishistudio.vnit.omkarmic.omkarmichealthapp.Yoga;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.Models.YogaPlan;
import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;

public class PlanFragment extends Fragment {

    private View mView;
    private TextView titleView;
    private TextView ageView;
    private TextView levelView;
    private TextView durationView;
    private TextView feeView;
    private RoundedImageView imageView;
    private ArrayList<YogaPlan> plans;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.plan_layout, container, false);
        titleView = (TextView) mView.findViewById(R.id.plan_title);
        ageView = (TextView) mView.findViewById(R.id.plan_age_group);
        levelView = (TextView) mView.findViewById(R.id.plan_level);
        durationView = (TextView) mView.findViewById(R.id.plan_duration);
        feeView = (TextView) mView.findViewById(R.id.plan_fee);
        imageView = (RoundedImageView) mView.findViewById(R.id.plan_image);
        plans = new ArrayList<>();
        return mView;
    }

    private void setData(int pos){
        YogaPlan plan = plans.get(pos % plans.size());
        titleView.setText(plan.getTitle());
        ageView.setText(plan.getAge_group());
        levelView.setText(plan.getLevel());
        durationView.setText(plan.getDuration());
        feeView.setText(plan.getFee());
        try{
            Picasso.get().load(plan.getImage()).into(imageView);
        }
        catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseDatabase.getInstance().getReference().child("yoga").child("plans").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()){
                    plans.add(iterator.next().getValue(YogaPlan.class));
                }
                Bundle bundle = getArguments();
                int pos = bundle.getInt("position");
                setData(pos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
