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

    private SurfaceHolder holder;
    private AnimThread animThread;
    private static final String TAG = "AnimView";

    // from animThread
    private int x_position = 150;
    private float [] test = {40f,1f,1f};
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
            CalcFFTTask calcffttask = new CalcFFTTask();
            calcffttask.execute((double[]) data);
            double[] arr = null;
            try {
                arr = calcffttask.get();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                Log.d(TAG, "Interrupted fft task");
            } catch (ExecutionException e) {
                // TODO Auto-generated catch block
                Log.d(TAG, "Execution fft task");
            }
            StringBuilder debugresults = new StringBuilder();
            debugresults.append("Debug results: ");
            for (int i = 0; i < 10; i++) {
                debugresults.append(String.format("%.4f, ", arr[i]));
            }

            Log.d(TAG, debugresults.toString());

            int[] scaled = scaleFFTResults(arr);

            StringBuilder debugscaled = new StringBuilder();
            debugscaled.append("Debug scaled: ");
            for (int i = 0; i < 10; i++) {
                debugscaled.append(String.format("%d, ", scaled[i]));
            }

            Log.d(TAG, debugscaled.toString());
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
                    double[] columnArray = new double[HEIGHT];

                    // columnArray values should be from 0 to -120
                    // 0 for darkest red, -120 for darkest blue
                    // for (int i = 0; i < scaled.length; i += 2) {
                    // // columnArray[i] = (int) (Math.random() * -120);
                    // // columnArray[i + 1] = (int) (Math.random() * -120);
                    // // if (scaled != null) {
                    // columnArray[i] = scaled[i / 2];
                    // columnArray[i + 1] = scaled[i / 2];
                    // // }
                    // }

                    // no expanded version
                    for (int i = 0; i < scaled.length; i++) {
                        // columnArray[i] = (int) (Math.random() * -120);
                        // columnArray[i + 1] = (int) (Math.random() * -120);
                        // if (scaled != null) {
                    	//System.out.println("scaled is: "+ scaled[i]);
                        columnArray[i] = scaled[i];
                    	//columnArray[i] = -40;
                        // }
                    }
                    colorArray.insert(columnArray);
                    bitmap = Bitmap.createBitmap(colorArray.getColor(), WIDTH,
                            HEIGHT, Bitmap.Config.RGB_565); //was colorArray.castInt()
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

    private int[] scaleFFTResults(double[] results) {
        // format the data
        // sum(hann(512)) = 255
        // s = abs(s)/(wlen^2)/255
        // double scale = 255 / (512 ^ 2);
        double scale = 1;
        int[] absolute = new int[results.length];
        if (results.length == 0) {
            return null;
        } else {
            // build new absolute array; should be size 256
            absolute = new int[results.length / 2];
            double inter = 0.0;
            for (int i = 0; i < results.length / 2; i++) {
                inter = (scale * (Math.sqrt(Math.pow(results[i], 2.0)
                        + Math.pow(results[i + 1], 2.0))));
                absolute[i] = (int) (20 * Math.log10(inter));
                // absolute[i] = (int) (Math.random() * -120);
            }

            // scale
            return absolute;
        }

    }
}