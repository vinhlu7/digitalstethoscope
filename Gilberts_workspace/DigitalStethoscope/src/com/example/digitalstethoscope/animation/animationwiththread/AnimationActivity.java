package com.example.digitalstethoscope.animation.animationwiththread;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.Window;

import com.example.digitalstethoscope.R;

public class AnimationActivity extends Activity {
    private AnimView view;
    private SurfaceHolder holder;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("Animation", "In animation onCreate");
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // this.view = (AnimView) findViewById(R.id.animview);
        setContentView(R.layout.activity_test_plotting);
        Log.d("Animation", "Exit animation onCreate");
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
