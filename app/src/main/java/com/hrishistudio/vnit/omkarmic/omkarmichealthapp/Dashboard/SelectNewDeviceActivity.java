package com.hrishistudio.vnit.omkarmic.omkarmichealthapp.Dashboard;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.Models.Device;
import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.R;
import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.ViewHolders.DeviceViewHolder;

public class SelectNewDeviceActivity extends AppCompatActivity {

    private RecyclerView device_list;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_new_device);

        mRef = FirebaseDatabase.getInstance().getReference().child("devices");
        device_list = (RecyclerView)findViewById(R.id.select_device_list);

        RecyclerView.LayoutManager manager = new GridLayoutManager(SelectNewDeviceActivity.this, 2, LinearLayoutManager.VERTICAL, false);
        device_list.setLayoutManager(manager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Device, DeviceViewHolder> adapter = new FirebaseRecyclerAdapter<Device, DeviceViewHolder>(Device.class, R.layout.device_layout, DeviceViewHolder.class, mRef) {
            @Override
            protected void populateViewHolder(DeviceViewHolder viewHolder, Device model, int position) {
                viewHolder.setmView(model.getName(), model.getImage(), model.getModel());
                viewHolder.setOnClickListener(SelectNewDeviceActivity.this, getRef(position).getKey());
            }
        };

        device_list.setAdapter(adapter);
    }

    public void goBack(View view){
        finish();
    }
}
