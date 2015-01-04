package com.example.digitalstethoscope.animation.animationwithtask;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.example.digitalstethoscope.R;

public class SpectrogramActivity extends Activity {
    private SpectrogramView spectrogram = null;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("SpectrogramActivity", "In animation onCreate");
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // this.spectrogram = (SpectrogramView)
        // findViewById(R.id.spectrogramview);
        setContentView(R.layout.activity_test_plotting);
        Log.d("SpectrogramActivity", "Finished with setContextView");
    }
    /*
     * public void onStart() {
     * 
     * }
     * 
     * public void onResume() {
     * 
     * }
     * 
     * public void onPause() {
     * 
     * }
     * 
     * public void onStop() {
     * 
     * }
     * 
     * public void onDestroy() {
     * 
     * }
     */
}
