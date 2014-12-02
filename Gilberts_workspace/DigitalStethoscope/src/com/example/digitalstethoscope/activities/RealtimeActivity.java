package com.example.digitalstethoscope.activities;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.digitalstethoscope.R;
import com.example.digitalstethoscope.util.bluetooth.BluetoothConnectTask;

public class RealtimeActivity extends Activity {

    private TextView testTextView;
    private static BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
            .getDefaultAdapter();
    private static final String TAG = "RealtimeActivity";

    private boolean isConnected = false;
    private static Set<BluetoothDevice> pairedDevices = mBluetoothAdapter
            .getBondedDevices();
    private static List<BluetoothSocket> sockets = new ArrayList<BluetoothSocket>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtime);

        testTextView = (TextView) findViewById(R.id.TestTextView);
        testTextView.setText("abcd");

        // bluetooth con

        String text = "Paired device address: ";
        if (!pairedDevices.isEmpty()) {
            for (BluetoothDevice device : pairedDevices) {
                Toast.makeText(this, text + device.getAddress(),
                        Toast.LENGTH_SHORT).show();

                try {
                    Toast.makeText(
                            this,
                            "Attempt to create insecure RFCOMM for: "
                                    + device.getAddress(), Toast.LENGTH_SHORT)
                            .show();
                    sockets.add(device.createInsecureRfcommSocketToServiceRecord(UUID
                            .fromString("00001101-0000-1000-8000-00805F9B34FB")));
                } catch (IOException e) {
                    Toast.makeText(
                            this,
                            "Failed to create insecure RFCOMM for: "
                                    + device.getAddress(), Toast.LENGTH_SHORT)
                            .show();
                    // TODO Auto-generated catch block
                    // e.printStackTrace();
                }
            }

        } else {
            Toast.makeText(this, "No paired devices.", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void onClickTestReal(View v) {
        if (!sockets.get(0).isConnected()) {
            BluetoothConnectTask contask = new BluetoothConnectTask(
                    getApplicationContext());
            contask.execute(sockets.get(0));
            try {
                isConnected = contask.get();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                Log.d(TAG, e.toString());
            } catch (ExecutionException e) {
                // TODO Auto-generated catch block
                Log.d(TAG, e.toString());
            }
        } else {
            Log.d(TAG, "Already connected to device.");
        }
        if (sockets.get(0).isConnected()) {
            Log.d(TAG, "Attempting to get inputstream.");
            try {
                InputStream bluetoothstream = sockets.get(0).getInputStream();
                BufferedInputStream bufstream = new BufferedInputStream(
                        bluetoothstream);
                byte[] buf = new byte[30];
                // int msb = bufstream.read();
                // int lsb = bufstream.read();
                bufstream.read(buf, 0, 30);
                StringBuilder acc = new StringBuilder();
                for (int i = 0; i < 30 / 2; i++) {
                    acc.append(String.format("msb: %02X", buf[i]));
                    acc.append(String.format("  lsb: %02X", buf[i + 1]));
                    acc.append("\n");
                }

                // Toast.makeText(this, acc.toString(),
                // Toast.LENGTH_LONG).show();
                testTextView.setText(acc.toString());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                Log.d(TAG, "IOException");
            } catch (Exception e) {
                Log.d(TAG, "Exception");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.realtime, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
