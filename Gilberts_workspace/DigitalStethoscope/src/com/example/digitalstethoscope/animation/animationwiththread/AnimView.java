package com.example.digitalstethoscope.animation.animationwiththread;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.concurrent.ExecutionException;

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

import com.example.digitalstethoscope.util.calculating.CalcFFTTask;

public class AnimView extends SurfaceView implements SurfaceHolder.Callback,
        Observer {

    private static final String TAG = "AnimView";
    private static final int HORIZONTAL_OFFSET = 150;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int[] DBFS_MAP = { Color.rgb(179, 5, 5), Color.RED,
            Color.rgb(255, 154, 1), Color.YELLOW, Color.rgb(102, 255, 102),
            Color.CYAN, Color.rgb(0, 128, 255), Color.rgb(13, 5, 232) };

    private SurfaceHolder holder;
    private ColorArray colorArray = null;
    private Paint paint = null;
    private Paint paintBar = null;
    private Canvas canvas = null;
    private Random random = null;
    private Bitmap bitmap = null;
    private int[] columnArray = null;

    public AnimView(Context context) {
        super(context);
        this.init();
        holder.addCallback(this);
    }

    public AnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
        holder.addCallback(this);
    }

    public AnimView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init();
        holder.addCallback(this);
    }

    private void init() {
        this.holder = getHolder();
        this.colorArray = new ColorArray(WIDTH, HEIGHT);
        this.paint = new Paint();
        this.paintBar = new Paint();
        this.canvas = null;
        this.random = new Random();
        this.bitmap = Bitmap.createBitmap(WIDTH, HEIGHT, Bitmap.Config.RGB_565);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("AnimView", "surfaceCreated()");
        this.holder = holder;
        paint.setTextSize(paint.getTextSize() * 2);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
        Log.d("AnimView", "surfaceChanged()");
        this.holder = holder;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d("AnimView", "surfaceDestroyed()");
        this.holder = holder;
    }

    private void drawStatic(Canvas canvas) {
        // draw
        canvas.drawColor(Color.BLACK);
        paint.setColor(Color.WHITE);

        // y-axis
        canvas.drawText("0", 130, 590, paint);
        canvas.drawText("500", 110, 530, paint);
        canvas.drawText("1000", 95, 470, paint);
        canvas.drawText("1500", 95, 410, paint);
        canvas.drawText("2000", 95, 350, paint);
        canvas.drawText("2500", 95, 290, paint);
        canvas.drawText("3000", 95, 230, paint);
        canvas.drawText("3500", 95, 170, paint);
        canvas.drawText("4000", 95, 110, paint);

        // color key bar
        paintBar.setShader(new LinearGradient(0, 0, 0, HEIGHT, DBFS_MAP, null,
                Shader.TileMode.MIRROR));
        canvas.drawRect(1070f, 10f, 1120f, 600f, paintBar);
        canvas.drawText("0", 1130, 40, paint);
        canvas.drawText("-20", 1130, 130, paint);
        canvas.drawText("-40", 1130, 240, paint);
        canvas.drawText("-60", 1130, 350, paint);
        canvas.drawText("-80", 1130, 460, paint);
        canvas.drawText("-100", 1130, 540, paint);
        canvas.drawText("-120", 1130, 600, paint);
    }

    @Override
    // Refresh the screen when frame is ready to be processed
    public void update(Observable observable, Object data) {
        if (data instanceof double[]) {
            Log.d(TAG, "in animView update() callback");
            CalcFFTTask calcffttask = new CalcFFTTask();
            calcffttask.execute((double[]) data);
            double[] fftResults = null;
            try {
                // get the formatted results, not the raw
                fftResults = calcffttask.get();
            } catch (InterruptedException e) {
                Log.d(TAG, "Interrupted fft task");
            } catch (ExecutionException e) {
                Log.d(TAG, "Execution fft task");
            }
            // StringBuilder debugresults = new StringBuilder();
            // debugresults.append("Debug results: ");
            // for (int i = 0; i < 10; i++) {
            // debugresults.append(String.format("%.4f, ", fftResults[i]));
            // }
            // Log.d(TAG, debugresults.toString());
            //
            // StringBuilder debugscaled = new StringBuilder();
            // debugscaled.append("Debug scaled: ");
            // for (int i = 0; i < 10; i++) {
            // debugscaled.append(String.format("%d, ", scaled[i]));
            // }
            // Log.d(TAG, debugscaled.toString());

            // run method
            try {
                canvas = holder.lockCanvas();
                synchronized (holder) {
                    // draw static elements to screen
                    this.drawStatic(canvas);

                    // Log.d(TAG,
                    // "Screen has been refreshed in postInvalidate");

                    // this needs to be in CalcFFTTask; format there
                    // format fft results before insert

                    // end

                    colorArray.insert(fftResults);
                    bitmap = Bitmap.createBitmap(colorArray.castInt(), WIDTH,
                            HEIGHT, Bitmap.Config.RGB_565);
                    canvas.drawBitmap(bitmap, HORIZONTAL_OFFSET, 0, null);
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