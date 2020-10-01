package com.hrishistudio.vnit.omkarmic.omkarmichealthapp.ViewHolders;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.Dashboard.NewDeviceInstructions;
import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.R;
import com.squareup.picasso.Picasso;

public class DeviceViewHolder extends RecyclerView.ViewHolder {

    private View mView;
    private ImageView device_image_view;
    private TextView device_name_view;
    private TextView device_model_view;

    public DeviceViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
        device_image_view = (ImageView)mView.findViewById(R.id.select_device_image);
        device_name_view = (TextView) mView.findViewById(R.id.select_device_name);
        device_model_view = (TextView) mView.findViewById(R.id.select_device_model);
    }

    public void setmView(String name, String image, String model){
        device_name_view.setText(name);
        device_model_view.setText(model);
        try{
            Picasso.get().load(image).into(device_image_view);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setOnClickListener(final Context context, final String id){
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewDeviceInstructions.class);
                intent.putExtra("device_model_id", id);
                context.startActivity(intent);
            }
        });
    }

}
