package com.example.animation;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AnimView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder holder;
    private AnimThread animThread;

    public AnimView(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    	setWillNotDraw(false);
        animThread = new AnimThread(holder);
        animThread.setRunning(true);
        animThread.start();
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