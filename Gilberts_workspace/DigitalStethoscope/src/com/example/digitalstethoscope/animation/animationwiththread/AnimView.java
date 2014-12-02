package com.example.digitalstethoscope.animation.animationwiththread;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AnimView extends SurfaceView implements SurfaceHolder.Callback,
        Observer {

    private SurfaceHolder holder;
    private AnimThread animThread;
    private static final String TAG = "AnimView";

    // from animThread
    private int x_position = 150;
    private int rowToPaint = 0;
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private ColorArray colorArray = new ColorArray(WIDTH, HEIGHT);
    private float[][] sampleStft = new float[HEIGHT][WIDTH];
    Paint paint = new Paint();
    Paint paintBar = new Paint();
    Canvas canvas = null;
    Random random = new Random();
    Bitmap bitmap = Bitmap.createBitmap(WIDTH, HEIGHT, Bitmap.Config.RGB_565);
    int[] colors = { Color.rgb(179, 5, 5), Color.RED, Color.rgb(255, 154, 1),
            Color.YELLOW, Color.rgb(102, 255, 102), Color.CYAN,
            Color.rgb(0, 128, 255), Color.rgb(13, 5, 232) };

    public AnimView(Context context) {
        super(context);
        this.holder = getHolder();
        holder.addCallback(this);
        // animThread = new AnimThread(holder);
    }

    public AnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.holder = getHolder();
        holder.addCallback(this);
        // animThread = new AnimThread(holder);
    }

    public AnimView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.holder = getHolder();
        holder.addCallback(this);
        // animThread = new AnimThread(holder);
    }

    // public Thread getAnimThread() {
    // return this.animThread;
    // }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
        Log.d("AnimView", "surfaceChanged()");
        this.holder = holder;
        // if (this.animThread.isAlive()) {
        // this.animThread.interrupt();
        // }
        // animThread = new AnimThread(holder);
        // animThread.setRunning(true);
        // animThread.start();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("AnimView", "surfaceCreated()");
        this.holder = holder;
        // setWillNotDraw(false);
        // animThread = new AnimThread(holder);
        // animThread.setRunning(true);
        // animThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d("AnimView", "surfaceDestroyed()");
        this.holder = holder;
        // boolean retry = true;
        // // animThread.setRunning(false);
        // while (retry) {
        // try {
        // animThread.join();
        // retry = false;
        // } catch (InterruptedException e) {
        // }
        // }
    }

    @Override
    public void update(Observable observable, Object data) {
        if (data instanceof double[]) {
            Log.d(TAG, "in animView update() callback");

            // run method
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

                    paintBar.setShader(new LinearGradient(0, 0, 0, HEIGHT,
                            colors, null, Shader.TileMode.MIRROR));
                    canvas.drawRect(1070f, 10f, 1120f, 600f, paintBar);

                    // postinvalidate
                    Log.d(TAG, "Screen has been refreshed in postInvalidate");
                    int[] columnArray = new int[HEIGHT];

                    for (int i = 0; i < HEIGHT; i++) {
                        columnArray[i] = Color.RED;
                    }

                    colorArray.insert(columnArray);
                    bitmap = Bitmap.createBitmap(colorArray.castInt(), WIDTH,
                            HEIGHT, Bitmap.Config.RGB_565);
                    canvas.drawBitmap(bitmap, x_position, 0, null);
                    bitmap = null;
                }

            } catch (NullPointerException npe) {
                Log.d(TAG, "NullPointer in try block.)");
            } finally {
                if (canvas != null) {
                    holder.unlockCanvasAndPost(canvas);
                }
            }

        }

    }
}