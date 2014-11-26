package com.example.digitalstethoscope.util.bluetooth;

import java.io.IOException;

import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class BluetoothConnectTask extends
        AsyncTask<BluetoothSocket, Void, Boolean> {
    private Context mContext = null;

    public BluetoothConnectTask(Context context) {
        this.mContext = context;
    }

    protected void onPreExecute() {
        String text = "Attempting to connect to device.";
        Toast.makeText(this.mContext, text, Toast.LENGTH_SHORT).show();
        Log.d("LandingActivity", "Attempt Bluetooth Connect");
    }

    @Override
    protected Boolean doInBackground(BluetoothSocket... params) {
        try {
            params[0].connect();
        } catch (IndexOutOfBoundsException indexbound) {
            Log.d("LandingActivity", "Index out of bounds.");
            // Toast.makeText(getApplicationContext(),
            // "Index out of bounds.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.d("LandingActivity", "Can't connect to socket.");
            // Toast.makeText(getApplicationContext(),
            // "Can't connect to socket.", Toast.LENGTH_SHORT).show();
        }
        // TODO Auto-generated method stub
        return (Boolean) params[0].isConnected();
    }

    protected void onPostExecute(Boolean isConnected) {
        String text = (isConnected) ? "Success" : "Failed";
        Toast.makeText(this.mContext, text, Toast.LENGTH_SHORT).show();
        Log.d("LandingActivity", text);
    }
}