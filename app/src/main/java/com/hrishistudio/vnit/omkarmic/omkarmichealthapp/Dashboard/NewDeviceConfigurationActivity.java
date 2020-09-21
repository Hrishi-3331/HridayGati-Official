package com.hrishistudio.vnit.omkarmic.omkarmichealthapp.Dashboard;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.Home.MainActivity;
import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.Meta.Constants;
import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.Models.Device;
import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.R;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

public class NewDeviceConfigurationActivity extends AppCompatActivity {

    private String model;
    private TextView blob1;
    private TextView blob2;
    private TextView blob3;
    private TextView blob4;
    private Device device;

    private LinearLayout step1_layout;
    private LinearLayout step2_layout;
    private LinearLayout step3_layout;
    private LinearLayout step4_layout;

    private ProgressDialog mDialog;
    private int current_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_device_configuration);

        model = getIntent().getStringExtra("device_model_id");
        current_state = 0;

        blob1 = (TextView)findViewById(R.id.step1_blob);
        blob2 = (TextView)findViewById(R.id.step2_blob);
        blob3 = (TextView)findViewById(R.id.step3_blob);
        blob4 = (TextView)findViewById(R.id.step4_blob);

        step1_layout = (LinearLayout)findViewById(R.id.step1_layout);
        step2_layout = (LinearLayout)findViewById(R.id.step2_layout);
        step3_layout = (LinearLayout)findViewById(R.id.step3_layout);
        step4_layout = (LinearLayout)findViewById(R.id.step4_layout);

        mDialog = new ProgressDialog(NewDeviceConfigurationActivity.this);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setTitle("Configuring your device");
        mDialog.setMessage("Please wait");
    }

    private void moveToNextStep(){
        switch (current_state){
            case 0:
                blob1.setBackgroundTintList(ContextCompat.getColorStateList(NewDeviceConfigurationActivity.this, android.R.color.holo_green_light));
                step1_layout.animate().translationXBy(1000).setDuration(500).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        step1_layout.setVisibility(View.GONE);
                        step2_layout.setVisibility(View.VISIBLE);
                        step3_layout.setVisibility(View.GONE);
                        step4_layout.setVisibility(View.GONE);
                    }
                }).start();
                break;

            case 1:
                blob2.setBackgroundTintList(ContextCompat.getColorStateList(NewDeviceConfigurationActivity.this, android.R.color.holo_green_light));
                step2_layout.animate().translationXBy(1000).setDuration(500).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        step1_layout.setVisibility(View.GONE);
                        step2_layout.setVisibility(View.GONE);
                        step3_layout.setVisibility(View.VISIBLE);
                        step4_layout.setVisibility(View.GONE);
                    }
                }).start();
                break;

            case 2:
                blob3.setBackgroundTintList(ContextCompat.getColorStateList(NewDeviceConfigurationActivity.this, android.R.color.holo_green_light));
                step3_layout.animate().translationXBy(1000).setDuration(500).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        step1_layout.setVisibility(View.GONE);
                        step2_layout.setVisibility(View.GONE);
                        step3_layout.setVisibility(View.GONE);
                        step4_layout.setVisibility(View.VISIBLE);
                    }
                }).start();
                break;

            case 3:

                break;
        }
    }

    private boolean checkBTState() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if(adapter == null) {
            Toast.makeText(this, "Your phone is incompatible to connect with this device.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (adapter.isEnabled()) {
                return true;
            } else {
                Intent enableBtIntent = new Intent(adapter.ACTION_REQUEST_ENABLE);
                Toast.makeText(this, "Bluetooth disabled. Please enable your phone bluetooth", Toast.LENGTH_SHORT).show();
                startActivityForResult(enableBtIntent, 10);
                return false;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && requestCode == RESULT_OK){
            VerifyID(new View(NewDeviceConfigurationActivity.this));
        }
    }

    public void VerifyID(View view){
        EditText idField = (EditText)findViewById(R.id.device_uid);
        EditText nameField = (EditText)findViewById(R.id.device_nickname);
        EditText addField = (EditText)findViewById(R.id.device_mac_address);

        ImageView pblob1 = (ImageView)findViewById(R.id.step2_part1_blob);
        ImageView pblob2 = (ImageView)findViewById(R.id.step2_part2_blob);
        ImageView pblob3 = (ImageView)findViewById(R.id.step2_part3_blob);

        String name = nameField.getText().toString();
        String id = idField.getText().toString();
        String mac_addr = addField.getText().toString();

        if (name.isEmpty()){
            Toast.makeText(this, "Please enter device nickname", Toast.LENGTH_SHORT).show();
        }
        else if (id.isEmpty()){
            Toast.makeText(this, "Please enter device UID", Toast.LENGTH_SHORT).show();
        }
        else if (mac_addr.isEmpty()){
            Toast.makeText(this, "Please enter device MAC address", Toast.LENGTH_SHORT).show();
        }
        else if (checkBTState()){
            mDialog.show();
            /*
            DeviceVerificationService verificationService = new DeviceVerificationService(mac_addr, id);
            DeviceVerificationService.VerificationResult result = verificationService.runVerification();
            if (result.isVerified()) Toast.makeText(this, "Verification success", Toast.LENGTH_SHORT).show();
            else Toast.makeText(this, "Verification failed  because " + result.getFailureCause(), Toast.LENGTH_SHORT).show();
            */
            addTestDevice(name, id, mac_addr);
        }
    }

    private void addTestDevice(final String name, final String id, final String address) {
        FirebaseDatabase.getInstance().getReference().child("devices").child(model).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue()!= null){
                    device = (Device)dataSnapshot.getValue(Device.class);
                    ArrayList<String> list = device.getParameters();
                    try {
                        writeFile(name, id, address, list);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    SharedPreferences preferences = getSharedPreferences(Constants.APP_DATABASE_KEY, MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt(Constants.MY_DEVICES_COUNT, preferences.getInt(Constants.MY_DEVICES_COUNT, 0) + 1);
                    editor.apply();
                    editor.commit();
                    writeSampleData(list);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void writeSampleData(final ArrayList<String> parameters){
        FirebaseDatabase.getInstance().getReference().child("sample_data").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                    ArrayList<ArrayList<Double>> parameter_list = new ArrayList<>();
                    for (String parameter : parameters){
                        String sample_data = dataSnapshot.child(parameter).getValue().toString();
                        StringTokenizer tk = new StringTokenizer(sample_data);
                        int n = tk.countTokens();
                        ArrayList<Double> values = new ArrayList<>();
                        for (int i = 0; i < n; i++){
                            values.add(Double.valueOf(tk.nextToken()));
                        }
                        parameter_list.add(values);
                    }
                    try {
                        writeDatabaseFile(parameter_list);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void writeDatabaseFile(ArrayList<ArrayList<Double>> parameter_list) throws Exception {
        OutputStreamWriter writer = new OutputStreamWriter(getApplicationContext().openFileOutput(Constants.DATABASE_FILE, Context.MODE_APPEND));
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date date = format.parse("06:00");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        for (int i = 0; i < parameter_list.get(0).size(); i++){
            StringBuilder builder = new StringBuilder();
            builder.append(format.format(calendar.getTime()));
            for (ArrayList<Double> list : parameter_list){
                builder.append(" ").append(list.get(i));
            }
            writer.write(builder.toString() + "\n");
            calendar.add(Calendar.MINUTE, 10);
        }
        writer.close();
        Intent intent = new Intent(NewDeviceConfigurationActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mDialog.dismiss();
        startActivity(intent);
        finish();
    }

    private void writeFile(String name, String id, String address, ArrayList<String> list) throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getApplicationContext().openFileOutput(Constants.META_DATA_FILE_NAME, Context.MODE_PRIVATE));
        StringBuilder data = new StringBuilder("DEVICE " + name + "OM" + "ID " + id + "OM" + "ADDRESS " + address + "\n");
        data.append("PARAMETERS ").append(list.size());
        for (String parameter : list) data.append(" ").append(parameter);
        outputStreamWriter.write(data.toString());
        outputStreamWriter.close();
    }

    public void goBack(View view){
        finish();
    }
}