package com.example.digitalstethoscope.animation.animationwithtask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SpectrogramView extends SurfaceView implements
        SurfaceHolder.Callback {
    private SurfaceHolder holder = null;
    private SpectrogramRefreshTask refresh = null;

    public SpectrogramView(Context context) {
        super(context);
        this.holder = getHolder();
        holder.addCallback(this);
    }

    public SpectrogramView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.holder = getHolder();
        holder.addCallback(this);
    }

    public SpectrogramView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    // Called immediately after any changes to size or formatting of the surface
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
        // TODO Auto-generated method stub

    }

    @Override
    // Called immediately after surface creation.
    public void surfaceCreated(SurfaceHolder holder) {
        Canvas canvas = this.holder.lockCanvas();
        // when surface is created, init the canvas to black
        canvas.drawColor(Color.BLACK);
        // then start a SpectrogramRefreshTask to refresh the surface
        refresh = new SpectrogramRefreshTask(this.holder, canvas);

        refresh.execute();
        Log.d("SpectrogramView", "Finished with loop.");
    }

    @Override
    // Called immediately before surface is destroyed
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub

    }

}
