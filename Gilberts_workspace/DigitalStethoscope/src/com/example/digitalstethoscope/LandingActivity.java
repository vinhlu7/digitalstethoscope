package com.example.digitalstethoscope;

import java.util.Random;

import android.app.Activity;
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


public class LandingActivity extends Activity {

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
    
    public void onClickConnectBluetooth(View v) {
    	
    }
    
    public void onClickOpenWaveFile(View v) {
    	
    }
    
    public void onClickRealtimeStreaming(View v) {
    	Intent i = new Intent(getApplicationContext(), RealtimeActivity.class);
    	startActivity(i);
    }
    
    public void onClickTestPlotting(View v) {
    	
    }
    
    public void onClickTestCanvas(View v) {
    	Intent i = new Intent(getApplicationContext(), TestCanvasActivity.class);
    	startActivity(i);
    }
}
