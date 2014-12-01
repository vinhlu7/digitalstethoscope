package com.example.digitalstethoscope.animation.animationwiththread;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.Log;
import android.view.SurfaceHolder;

public class AnimThread extends Thread {

    private SurfaceHolder holder;
    private boolean running = true;
    private int x_position = 150;
    private int rowToPaint = 0;
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private ColorArray colorArray = new ColorArray(WIDTH, HEIGHT);
    private float[][] sampleStft = new float[HEIGHT][WIDTH];
    Paint paint = new Paint();
    Paint paintBar = new Paint();
    Canvas canvas = null;
    Color color = null;
    //float [] test = new float[3];
    float[] test = {174f,1f,1f}; 
    Random random = new Random();
    Bitmap bitmap = Bitmap.createBitmap(WIDTH, HEIGHT, Bitmap.Config.RGB_565);
    int[] colors = { Color.rgb(179, 5, 5), Color.RED, Color.rgb(255, 154, 1),
            Color.YELLOW, Color.rgb(102, 255, 102), Color.CYAN,
            Color.rgb(0, 128, 255), Color.rgb(13, 5, 232) };

    public AnimThread(SurfaceHolder holder) {
        this.holder = holder;
    }

    @Override
    public void run() {
        paint.setTextSize(paint.getTextSize() * 2);
        // for (int i=0; i<10; i++)
        // pixels[i] = Color.BLUE;
        // bitmap.setPixels(pixels, 0, bitmap.getWidth(), 0, 0,
        // bitmap.getWidth(), bitmap.getHeight());
        for (int row = 0; row < HEIGHT; row++) {
            for (int column = 0; column < WIDTH; column++) {
                sampleStft[row][column] = -100 + (int) (Math.random() * (121));
            }
        }

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
                    	
                    
                    //Color.RGBToHSV(0,0,255,test);
                   // System.out.println("color is: " + Color.HSVToColor(1,test));
                    //Color.colorToHSV(Color.RED,test);
                    //System.out.println("Test1: "+ test[0]);
                   // System.out.println("Test2: "+ test[1]);
                   // System.out.println("Test3: "+ test[2]);
                    postInvalidate();

                }
            } catch (NullPointerException npe) {
                Log.d("AnimThread", "NullPointer in canvas drawing.");
                running = false;
            } finally {
                if (canvas != null) {
                    holder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    private void postInvalidate() {
        int[] columnArray = new int[HEIGHT];

        for (int i = 0; i < HEIGHT; i++) {
           //columnArray[i] = Color.RED;
            columnArray[i] = -120 + (int)(Math.random()*(121));
        }

        colorArray.insert(columnArray);
        bitmap = Bitmap.createBitmap(colorArray.castInt(), WIDTH, HEIGHT,
                Bitmap.Config.RGB_565);
        // bitmap = Bitmap.createBitmap(WIDTH,HEIGHT,Bitmap.Config.RGB_565);
        // bitmap.setPixels(colorArray.castInt(), 0, WIDTH, 0, 0, WIDTH,
        // HEIGHT);
        canvas.drawBitmap(bitmap, x_position, 0, null);
        bitmap = null;
        // bitmap.recycle();

        // Canvas canvas = null;
        // canvas.drawColor(Color.BLACK);
        // canvas.drawBitmap(dot,150,150,null);
        /*
         * // for(int x = 150; x<=column;x++){ // canvas.drawPoint(x, row,
         * paint); // } } //if(column >= 1){ next_column = column+1; return; //}
         * }
         */
    }

    public void setRunning(boolean b) {
        running = b;
    }
}
