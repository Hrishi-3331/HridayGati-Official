package com.hrishistudio.vnit.omkarmic.omkarmichealthapp.Dashboard;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.Models.Parameter;
import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.ViewHolders.DashboardViewHolder;
import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.R;

import java.util.ArrayList;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardViewHolder> {

    private ArrayList<String> parameters;
    private ArrayList<ArrayList<Double>> values;
    private ArrayList<String> time;
    private Context context;

    public DashboardAdapter(ArrayList<String> parameters, ArrayList<ArrayList<Double>> values, ArrayList<String> time, Context context) {
        this.parameters = parameters;
        this.values = values;
        this.time = time;
        this.context = context;
    }

    @NonNull
    @Override
    public DashboardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dashboard_layout,viewGroup, false);
       return new DashboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DashboardViewHolder dashboardViewHolder, int i) {
        final int p = i;
        String par = parameters.get(i);
        FirebaseDatabase.getInstance().getReference().child("parameters").child(par).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Parameter parameter = (Parameter) dataSnapshot.getValue(Parameter.class);
                dashboardViewHolder.setDashboard(context,parameter, values.get(p).get(0));
                dashboardViewHolder.setChart(values.get(p), time);
                dashboardViewHolder.setListner(context);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return parameters == null ? 0 : parameters.size();
    }
}
