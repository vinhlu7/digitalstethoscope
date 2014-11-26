package com.example.digitalstethoscope.activities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import com.example.digitalstethoscope.animation.Animation;
import com.example.digitalstethoscope.util.fileexplorer.FileChooser;
import com.example.digitalstethoscope.R;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class LandingActivity extends Activity {

	static final int REQUEST_ENABLE_BT = 1;
	// static boolean BLUETOOTH_ON = false;
	static final String WT_12_MAC = "00:07:80:45:08:C4";
	private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
			.getDefaultAdapter();;
	private BluetoothDevice wt12 = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_landing);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.landing, menu);
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

	public void onClickEnableBluetooth(View v) {
		if (mBluetoothAdapter == null) {
			// Device does not support Bluetooth
			CharSequence text = "Device does not support Bluetooth.";
			Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
			return;
		}

		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableBtIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		}
	}

	// For now, the device to connect to is hardcoded using the
	// constant WT_12_MAC
	public void onClickConnectDevice(View v) {
		if (mBluetoothAdapter.isEnabled()) {
			Set<BluetoothDevice> pairedDevices = mBluetoothAdapter
					.getBondedDevices();
			List<BluetoothSocket> sockets = new ArrayList<BluetoothSocket>();

			String text = "Paired device address: ";
			if (!pairedDevices.isEmpty()) {
				for (BluetoothDevice device : pairedDevices) {
					Toast.makeText(this, text + device.getAddress(),
							Toast.LENGTH_SHORT).show();

					try {
						Toast.makeText(
								this,
								"Attempt to create insecure RFCOMM for: "
										+ device.getAddress(),
								Toast.LENGTH_SHORT).show();
						sockets.add(device.createInsecureRfcommSocketToServiceRecord(UUID
								.fromString("00001101-0000-1000-8000-00805F9B34FB")));
					} catch (IOException e) {
						Toast.makeText(
								this,
								"Failed to create insecure RFCOMM for: "
										+ device.getAddress(),
								Toast.LENGTH_SHORT).show();
						// TODO Auto-generated catch block
						// e.printStackTrace();
					}
				}
				if (!sockets.get(0).isConnected()) {
					new BluetoothConnectTask().execute(sockets.get(0));
				} else {
					Toast.makeText(this, "Already connected to device.",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(this, "No paired devices.", Toast.LENGTH_SHORT)
						.show();
			}

		}

	}

	public void onClickOpenWaveFile(View v) {
		Intent i = new Intent(this, FileChooser.class);
		startActivity(i);
	}

	public void onClickRealtimeStreaming(View v) {
		Intent i = new Intent(getApplicationContext(), RealtimeActivity.class);

		if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) {
			startActivity(i);
		} else {
			Toast.makeText(this, "Enable Bluetooth.", Toast.LENGTH_SHORT)
					.show();
		}

	}

	public void onClickTestPlotting(View v) {
		new DelayTask().execute(5);
		Intent intent = new Intent(this, Animation.class);
		startActivity(intent);
	}

	public void onClickTestCanvas(View v) {
		Intent i = new Intent(getApplicationContext(), TestCanvasActivity.class);
		startActivity(i);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_ENABLE_BT) {
			// Make sure the request was successful
			if (resultCode == RESULT_OK) {
				Toast.makeText(this, "Enabled BlueTooth.", Toast.LENGTH_SHORT)
						.show();
				return;
			}
		}
	}

	public class BluetoothConnectTask extends
			AsyncTask<BluetoothSocket, Void, Boolean> {

		protected void onPreExecute() {
			String text = "Attempting to connect to device.";
			Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT)
					.show();
			Log.d("debugthisshit", "Attempt Bluetooth Connect");
		}

		@Override
		protected Boolean doInBackground(BluetoothSocket... params) {
			try {
				params[0].connect();
			} catch (IndexOutOfBoundsException indexbound) {
				Log.d("debugthisshit", "Index out of bounds.");
				// Toast.makeText(getApplicationContext(),
				// "Index out of bounds.", Toast.LENGTH_SHORT).show();
			} catch (IOException e) {
				Log.d("debugthisshit", "Can't connect to socket.");
				// Toast.makeText(getApplicationContext(),
				// "Can't connect to socket.", Toast.LENGTH_SHORT).show();
			}
			// TODO Auto-generated method stub
			return (Boolean) params[0].isConnected();
		}

		protected void onPostExecute(Boolean isConnected) {
			String text = (isConnected) ? "Success" : "Failed";
			Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT)
					.show();
			Log.d("debugthisshit", text);
		}
	}

	// Responsive delay to UI thread
	// How to use:
	// int seconds = 3;
	// new DelayTask().execute(seconds)
	public class DelayTask extends AsyncTask<Integer, Void, Void> {

		protected void onPreExecute() {
			Toast.makeText(getApplicationContext(), "Delay", Toast.LENGTH_SHORT)
					.show();
			Log.d("debugthisshit", "DelayTask preExecute");
		}

		@Override
		protected Void doInBackground(Integer... params) {
			int delaymilliseconds = params[0] * 1000;
			try {
				Thread.sleep(delaymilliseconds);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// TODO Auto-generated method stub
			return null;
		}

		protected void onPostExecute(Void unused) {
			Toast.makeText(getApplicationContext(), "Exit Delay",
					Toast.LENGTH_SHORT).show();
			Log.d("debugthisshit", "DelayTask postExecute");
		}

	}
}
