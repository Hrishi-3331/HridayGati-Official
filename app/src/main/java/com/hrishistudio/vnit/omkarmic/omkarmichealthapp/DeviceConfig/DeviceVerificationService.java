package com.hrishistudio.vnit.omkarmic.omkarmichealthapp.DeviceConfig;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.UUID;

public class DeviceVerificationService {
    private BluetoothAdapter btAdapter;
    private BluetoothSocket btSocket = null;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private String address;
    private String id;

    public DeviceVerificationService(String address, String id) {
        this.address = address;
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        this.id = id;
    }

    public VerificationResult runVerification(){

        BluetoothDevice mDevice = btAdapter.getRemoteDevice(address);
        btAdapter.cancelDiscovery();

        int counter = 0;

        do {
            try {
                btSocket = mDevice.createRfcommSocketToServiceRecord(MY_UUID);
                btSocket.connect();
                counter++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }while (!btSocket.isConnected() && counter < 3);

        if (btSocket.isConnected()) System.out.println("Connection established");
        else System.out.println("Connection failed!");

        RequestThread requestThread = new RequestThread(btSocket);
        ResponseThread responseThread = new ResponseThread(requestThread, btSocket);

        requestThread.start();
        responseThread.start();

        while (responseThread.isAlive()){}

        try{
            btSocket.close();
        }catch (Exception e){

        }

        return new VerificationResult(true, "Testing");
    }

    private class ResponseThread extends Thread{

        private BluetoothSocket socket;
        private InputStream iStream;
        private RequestThread requestThread;

        public ResponseThread(RequestThread requestThread, BluetoothSocket socket) {
            this.socket = socket;
            this.requestThread = requestThread;
            InputStream temp = null;
            try {
                temp = this.socket.getInputStream();
            }catch (Exception e){

            }
            iStream = temp;
        }

        @Override
        public void run() {
            super.run();
            try {
                byte[] buffer = new byte[1024];
                int bytes = -1;
                int attemp = 1;
                do {
                    System.out.println("Attempt " + attemp++);
                    bytes = iStream.read(buffer);
                }
                while (bytes == -1);
                System.out.println(new String(buffer, 0, bytes));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class RequestThread extends Thread{

        private BluetoothSocket socket;
        private OutputStream oStream;

        public RequestThread(BluetoothSocket socket){
            this.socket = socket;
            oStream = null;
            OutputStream temp = null;
            try {
                temp = socket.getOutputStream();
            }catch (Exception e){

            }
            oStream = temp;
        }

        @Override
        public void run() {
            super.run();
            try {
                oStream.write("Hello Omkarmic".getBytes());
            }catch (Exception e){

            }
        }
    }

    public class VerificationResult{
        private boolean verified;
        private String failureCause;

        public VerificationResult(boolean verified, String failureCause) {
            this.verified = verified;
            this.failureCause = failureCause;
        }

        public boolean isVerified() {
            return verified;
        }

        public String getFailureCause() {
            return failureCause;
        }
    }
}
