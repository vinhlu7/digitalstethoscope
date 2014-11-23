package com.example.animation;

import android.os.Bundle;
import android.app.Activity;


public class Animation extends Activity {
    private AnimView view;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = new AnimView(this);
        setContentView(view);
    }
}
