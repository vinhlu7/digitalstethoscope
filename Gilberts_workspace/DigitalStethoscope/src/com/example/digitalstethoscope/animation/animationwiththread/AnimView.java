package com.example.digitalstethoscope.animation.animationwiththread;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AnimView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder holder;
    private AnimThread animThread;

    public AnimView(Context context) {
        super(context);
        this.holder = getHolder();
        holder.addCallback(this);
    }

    public AnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.holder = getHolder();
        holder.addCallback(this);
    }

    public AnimView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
        Log.d("AnimView", "surfaceChanged()");
        if (this.animThread.isAlive()) {
            this.animThread.interrupt();
        }
        // animThread = new AnimThread(holder);
        animThread.setRunning(true);
        animThread.start();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("AnimView", "surfaceCreated()");
        // setWillNotDraw(false);
        animThread = new AnimThread(holder);
        // animThread.setRunning(true);
        // animThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        animThread.setRunning(false);
        while (retry) {
            try {
                animThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }
}