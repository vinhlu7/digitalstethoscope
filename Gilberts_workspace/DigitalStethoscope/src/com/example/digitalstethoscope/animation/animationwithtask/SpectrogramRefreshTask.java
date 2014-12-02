package com.example.digitalstethoscope.animation.animationwithtask;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.SurfaceHolder;

import com.example.digitalstethoscope.animation.animationwiththread.ColorArray;

public class SpectrogramRefreshTask extends AsyncTask<Void, Void, Void> {
    private Canvas canvas = null; // the canvas to be modified
    private SurfaceHolder holder = null; // the surfaceholder that holds the
                                         // surface entities
    // Surface params
    private static final int x_position = 150;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private ColorArray colorArray = null;

    public SpectrogramRefreshTask(SurfaceHolder holder, Canvas canvas) {
        this.holder = holder;
        this.canvas = canvas;
        this.colorArray = new ColorArray(WIDTH, HEIGHT);
        this.colorArray.initColor(Color.BLACK);
    }

    protected void onPreExecute() {
        // this.canvas = this.holder.lockCanvas();
        Log.d("SpectrogramRefreshTask", "onPreExecute");
    }

    @Override
    protected Void doInBackground(Void... unused) {
        Log.d("SpectrogramRefreshTask", "doInBackground");

        int[] columnArray = new int[HEIGHT];
        for (int i = 0; i < HEIGHT; i++) {
            columnArray[i] = Color.RED;
        }
        // this.colorArray.insert(columnArray);
        Bitmap bitmap = Bitmap.createBitmap(colorArray.castInt(), WIDTH,
                HEIGHT, Bitmap.Config.RGB_565);
        canvas.drawBitmap(bitmap, x_position, 0, null);
        bitmap = null;
        Log.d("SpectrogramRefreshTask", "About to return from doInBackground");
        return null;
    }

    protected void onPostExecute(Void unused) {
        holder.unlockCanvasAndPost(this.canvas);
        Log.d("SpectrogramRefreshTask", "onPostExecute");
    }

}
