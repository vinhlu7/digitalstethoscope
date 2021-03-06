package com.example.digitalstethoscope.animation.animationwiththread;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.Log;
import android.view.SurfaceHolder;

public class AnimThread extends Thread implements Observer {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final String TAG = "AnimThread";

    private SurfaceHolder holder = null;
    private boolean running = true;
    private int x_position = 150;
    private int rowToPaint = 0;
    private ColorArray colorArray = new ColorArray(WIDTH, HEIGHT);
    private float[][] sampleStft = new float[HEIGHT][WIDTH];
    private Paint paint = new Paint();
    private Paint paintBar = new Paint();
    private Canvas canvas = null;
    private Color color = null;
    private float[] test = { 174f, 1f, 1f };
    private Random random = new Random();
    private Bitmap bitmap = Bitmap.createBitmap(WIDTH, HEIGHT,
            Bitmap.Config.RGB_565);
    private int[] colors = { Color.rgb(179, 5, 5), Color.RED,
            Color.rgb(255, 154, 1), Color.YELLOW, Color.rgb(102, 255, 102),
            Color.CYAN, Color.rgb(0, 128, 255), Color.rgb(13, 5, 232) };
    private boolean refresh = true;

    public AnimThread(SurfaceHolder holder) {
        this.holder = holder;
    }

    @Override
    public void run() {
        Log.d(TAG, "Running");
        paint.setTextSize(paint.getTextSize() * 2);
        while (running) {
            try {
                canvas = holder.lockCanvas();
                synchronized (holder) {
                    // draw
                    canvas.drawColor(Color.BLACK);
                    paint.setColor(Color.WHITE);
                    // x-axis
                    canvas.drawText("0", 150, 630, paint);
                    canvas.drawText("2", 250, 630, paint);
                    canvas.drawText("4", 350, 630, paint);
                    canvas.drawText("6", 450, 630, paint);

                    // y-axis
                    canvas.drawText("0", 130, 590, paint);
                    canvas.drawText("500", 110, 490, paint);

                    // color key bar
                    paintBar.setShader(new LinearGradient(0, 0, 0, HEIGHT,
                            colors, null, Shader.TileMode.MIRROR));
                    canvas.drawRect(1070f, 10f, 1120f, 600f, paintBar);
                    canvas.drawText("0", 1130, 40, paint);
                    canvas.drawText("-20", 1130, 130, paint);
                    canvas.drawText("-40", 1130, 240, paint);
                    canvas.drawText("-60", 1130, 350, paint);
                    canvas.drawText("-80", 1130, 460, paint);
                    canvas.drawText("-100", 1130, 540, paint);
                    canvas.drawText("-120", 1130, 600, paint);

                    postInvalidate();
                }
                this.refresh = false;

            } catch (NullPointerException npe) {
                Log.d("AnimThread", "NullPointer in run()");
                running = false;
            } finally {
                if (canvas != null && this.refresh) {
                    holder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    private void postInvalidate() {
        Log.d(TAG, "Screen has been refreshed in postInvalidate");
        double[] columnArray = new double[HEIGHT];

        for (int i = 0; i < HEIGHT; i++) {
            columnArray[i] = -120 + (int) (Math.random() * (121));
        }

        bitmap = Bitmap.createBitmap(colorArray.castInt(), WIDTH, HEIGHT,
                Bitmap.Config.RGB_565);
        canvas.drawBitmap(bitmap, x_position, 0, null);
        bitmap = null;
    }

    public void setRunning(boolean b) {
        running = b;
    }

    @Override
    // this method gets called when a new frame is made available
    public void update(Observable observable, Object data) {
        if (data instanceof double[]) {
            Log.d(TAG, "In update callback. Screen should refresh.");
            this.refresh = true;
        }

    }
}
