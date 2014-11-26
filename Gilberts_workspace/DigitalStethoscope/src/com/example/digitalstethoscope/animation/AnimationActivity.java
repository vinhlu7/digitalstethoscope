package com.example.digitalstethoscope.animation;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

public class AnimationActivity extends Activity {
    private AnimView view;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("Animation", "In animation onCreate");
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = new AnimView(this);
        setContentView(view);
        Log.d("Animation", "Finished with setContextView");
    }
}
