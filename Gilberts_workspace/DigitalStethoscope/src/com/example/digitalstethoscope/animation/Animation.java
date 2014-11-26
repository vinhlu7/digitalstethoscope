package com.example.digitalstethoscope.animation;

import android.os.Bundle;
import android.view.Window;
import android.app.Activity;


public class Animation extends Activity {
    private AnimView view;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = new AnimView(this);
        setContentView(view);
    }
}
