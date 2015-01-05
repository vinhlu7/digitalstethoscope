package com.example.digitalstethoscope.animation.animationwiththread;

import java.io.File;
import java.io.IOException;

import wav.WavFile.WavFile;
import wav.WavFile.WavFileException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.Window;

import com.example.digitalstethoscope.structures.WavFileReader;

public class AnimationActivity extends Activity {
    private AnimView view;
    private SurfaceHolder holder;
    private static final String TAG = "AnimationActivity";
    private Thread fileReading = null;
    private WavFileReader reader = null;
    private WavFile wav = null;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "In animation onCreate");
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // this.view = (AnimView) findViewById(R.id.animview);
        // setContentView(R.layout.activity_test_plotting);
        Log.d(TAG, "Exit animation onCreate");
        Intent prevCanvasAct = getIntent();

        //
        // create new thread to open the wav file and read samples
        // this is the subject being observed

        try {
            this.wav = WavFile.openWavFile(new File(prevCanvasAct
                    .getStringExtra("fullpath")));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.d(TAG, "IOException");
        } catch (WavFileException e) {
            // TODO Auto-generated catch block
            Log.d(TAG, "WavFileException");
        }
        if (this.wav != null) {
            this.reader = new WavFileReader(this.wav);
            // create the event handler to update the screen
            // this is the observer that updates status
            AnimView viewtest = new AnimView(this);

            // subscribe the observer to the event source/observable
            reader.addObserver(viewtest);
            setContentView(viewtest);
            // viewtest.getAnimThread().start();

            this.fileReading = new Thread(reader);
            // fileReading.start();
        }
    }

    public void onRestart() {
        Log.d(TAG, "In animation onStart");
        super.onRestart();
    }

    public void onStart() {
        Log.d(TAG, "In animation onStart");
        super.onStart();
        if (this.fileReading != null) {
            fileReading.start();
        }
    }

    public void onResume() {
        Log.d(TAG, "In animation onResume");
        super.onResume();
        if (this.fileReading != null) {
            reader.resume();
        }
    }

    public void onPause() {
        Log.d(TAG, "In animation onPause");
        super.onPause();
        if (this.fileReading != null) {
            reader.pause();
        }
    }

    public void onStop() {
        Log.d(TAG, "In animation onStop");
        super.onStop();
        if (this.fileReading != null) {
            reader.pause();
            reader.deleteObservers();
            this.fileReading.interrupt();
            this.fileReading = null;
            this.reader = null;
        }
    }

    /*
     * public void onStart() { super.onStart(); // this.view = (AnimView)
     * findViewById(R.id.animview); //
     * setContentView(R.layout.activity_test_plotting); // view = new
     * AnimView(this); // setContentView(view); Log.d("Animation",
     * "Finished with setContextView"); }
     * 
     * public void onResume() { super.onResume(); this.view = (AnimView)
     * findViewById(R.id.animview);
     * setContentView(R.layout.activity_test_plotting); }
     * 
     * public void onPause() { super.onPause(); }
     * 
     * public void onStop() { super.onStop(); this.view = null; }
     * 
     * public void onDestroy() { super.onDestroy(); this.view = null; }
     */
}
