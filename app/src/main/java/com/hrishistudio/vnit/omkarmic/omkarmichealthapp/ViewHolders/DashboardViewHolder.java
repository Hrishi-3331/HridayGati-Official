package com.hrishistudio.vnit.omkarmic.omkarmichealthapp.ViewHolders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.Dashboard.DashboardDetail;
import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.Models.Parameter;
import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class DashboardViewHolder extends RecyclerView.ViewHolder {

    private View mView;
    private TextView parameter_name_view;
    private TextView parameter_detail_view;
    private TextView parameter_last_reading_view;
    private TextView parameter_normal_reading_view;
    private BarChart parameter_chart;
    private Parameter parameter;
    private RoundedImageView imageView;

    public DashboardViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
        parameter_name_view = (TextView)mView.findViewById(R.id.parameter_name);
        parameter_detail_view = (TextView)mView.findViewById(R.id.parameter_detail);
        parameter_last_reading_view = (TextView)mView.findViewById(R.id.parameter_last_reading);
        parameter_normal_reading_view = (TextView)mView.findViewById(R.id.parameter_normal_reading);
        parameter_chart = (BarChart) mView.findViewById(R.id.parameter_chart);
        imageView = (RoundedImageView)mView.findViewById(R.id.parameter_icon);
    }

    public void setDashboard(Context context, Parameter parameter, Double last_value){
        this.parameter = parameter;
        parameter_name_view.setText(parameter.getName());
        parameter_detail_view.setText(parameter.getDetail());
        parameter_normal_reading_view.setText(String.valueOf(parameter.getNormal_value()));
        parameter_last_reading_view.setText(String.valueOf(last_value));
        switch (parameter.getName().trim()){
            case "Heart Rate":
                imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.heart_rate));
                break;

            case "Body Temperature":
                imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.temp));
                break;

            case "Oxygen Level":
                imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.heart_rate));
                break;

            default:
                imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.bp));
                break;
        }
    }

    public void setChart(ArrayList<Double> values, ArrayList<String> time){
        BarData data = new BarData(time, getDataSet(values));
        parameter_chart.setData(data);
        parameter_chart.animateXY(2000, 2000);
    }

    private BarDataSet getDataSet(ArrayList<Double> values) {
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            BarEntry entry = new BarEntry(values.get(i).floatValue(), i);
            valueSet1.add(entry);
        }
        BarDataSet barDataSet = new BarDataSet(valueSet1, parameter.getName());
        barDataSet.setColor(Color.parseColor("#00c853"));
        return barDataSet;
    }

    public void setListner(final Context context){
        mView.findViewById(R.id.parameter_details_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DashboardDetail.class);
                context.startActivity(intent);
            }
        });
    }
}
