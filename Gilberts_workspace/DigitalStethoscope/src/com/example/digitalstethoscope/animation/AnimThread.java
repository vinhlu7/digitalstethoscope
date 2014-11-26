package com.example.digitalstethoscope.animation;

import android.graphics.Bitmap;

import java.util.Random;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public class AnimThread extends Thread {

    private SurfaceHolder holder;
    private boolean running = true;
	private int x_position = 150;
	private int rowToPaint = 0;
    private final int WIDTH = 900;
    private final int HEIGHT = 600;
    private ColorArray colorArray = new ColorArray(WIDTH,HEIGHT);
    private float[][]sampleStft = new float[600][900];
    Canvas canvas = null;
    Random random = new Random();
    Bitmap bitmap = Bitmap.createBitmap(WIDTH, HEIGHT,Bitmap.Config.RGB_565);
    //int[]pixels = new int[bitmap.getHeight()*bitmap.getWidth()];
    
    public AnimThread(SurfaceHolder holder) {
        this.holder = holder;
    }

    @Override
    public void run() {
    	//random = new Random();

      //  for (int i=0; i<bitmap.getWidth()*bitmap.getHeight(); i++)
        //    pixels[i] = Color.BLUE;
        //bitmap.setPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
    	
    	for(int row=0;row<HEIGHT;row++){
    		for(int column=0;column<WIDTH;column++){
    			sampleStft[row][column] = -100 + (int)(Math.random()*(121));
    		}
    	}
    	
        while(running ) {
            try {
                canvas = holder.lockCanvas();
                synchronized (holder) {
                    // draw
                	canvas.drawColor(Color.BLACK);
                	postInvalidate();
                	
                }
            }
            finally {
                    if (canvas != null) {
                    	holder.unlockCanvasAndPost(canvas);
                    }
            }
        }
    }

    private void postInvalidate() {
    	int[] columnArray = new int[HEIGHT];
    	//int color = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    	/*
    	columnArray[0] = Color.RED;
    	columnArray[1] = Color.RED;
    	columnArray[2] = Color.RED;
    	columnArray[3] = Color.RED;
    	columnArray[4] = Color.RED;;
    	columnArray[5] = Color.RED;
    	columnArray[6] = Color.RED;
    	
    	for(int i=0;i<HEIGHT;i++){
    		if(sampleStft[rowToPaint][i] == 20 ){
    			columnArray[i] = Color.rgb(162,1,1);
    		}
    		else if (sampleStft[rowToPaint][i] == 0){
    			columnArray[i] = Color.rgb(262,1,1); //red
    		}
    		else if (sampleStft[rowToPaint][i] == -20){
    			columnArray[i] = Color.rgb(262, 154, 0); //orange
    		}
    		else if (sampleStft[rowToPaint][i] == -40){
    			columnArray[i] = Color.YELLOW;
    		}
    		else if (sampleStft[rowToPaint][i] == -60){
    			columnArray[i] = Color.rgb(158,252,180);
    		}
    		else if (sampleStft[rowToPaint][i] == -80){
    			columnArray[i] = Color.CYAN;
    		}
    		else if (sampleStft[rowToPaint][i] == -100){
    			columnArray[i] = Color.BLUE;
    		}
    		else
    			columnArray[i] = Color.WHITE;
    	}
    	rowToPaint++;
    	if(rowToPaint >= HEIGHT){
    		rowToPaint = 0;
    	}
    	
    	columnArray[250] = Color.RED;
    	columnArray[251] = Color.RED;
    	columnArray[252] = Color.RED;
    	columnArray[450] = Color.RED;
    	columnArray[451] = Color.RED;
    	columnArray[452] = Color.RED;
    	*/
    	for(int i=0;i<HEIGHT;i++){
    		columnArray[i] = Color.RED;
    		if(colorArray.inRange(20, 0, columnArray[i])){
    			colorArray.setRGB(columnArray[i]);
    		}
    	}
    	colorArray.insert(columnArray);
    	//for(int i=0;i<HEIGHT*10;i++){
    	//	pixels[i] = Color.RED;
    	//}
    	bitmap = Bitmap.createBitmap(colorArray.castInt(),WIDTH,HEIGHT,Bitmap.Config.RGB_565);
    	//bitmap = Bitmap.createBitmap(WIDTH,HEIGHT,Bitmap.Config.RGB_565);
    	//bitmap.setPixels(colorArray.castInt(), 0, WIDTH, 0, 0, WIDTH, HEIGHT);
        canvas.drawBitmap(bitmap, x_position,0, null);
        bitmap = null;
        //bitmap.recycle();
        
        

         
    	
    	
    	//Canvas canvas = null;
    	//canvas.drawColor(Color.BLACK);
        //canvas.drawBitmap(dot,150,150,null);
        /*
        for(int column = next_column; column<screen_width;){
        	 for(int row=0; row<screen_height; row++){ //max height is around 900
        	 	 paint.setColor(Color.RED);
        	 	canvas.drawPoint(column, row, paint);
        	 	//stft_array[column][row] = -100 + (int)(Math.random()*(121));
        		 
        	 	if(stft_array[column][row] == 20) {
        	 		paint.setColor(Color.rgb(162,1,1));
        	 	} else if (stft_array[column][row] == 0) {
        	 		paint.setColor(Color.RED);
        	 	}
        	 	  else if (stft_array[column][row] == -20) {
        	 		paint.setColor(Color.rgb(255, 154, 0));
        	 	}
        	 	  else if (stft_array[column][row] == -40) {
         	 		paint.setColor(Color.YELLOW);
         	 	}
        	 	  else if (stft_array[column][row] == -60) {
        	 		paint.setColor(Color.rgb(158,252,180));
        	 	}
        	 	  else if (stft_array[column][row] == -80) {
         	 		paint.setColor(Color.CYAN);
         	 	}
        	 	  else if (stft_array[column][row] == -100) {
         	 		paint.setColor(Color.BLUE);
         	 	}
        	 	  else
        	 		paint.setColor(Color.WHITE);
        	 		
        	 	// for(int x = 150; x<=column;x++){
        	 	//	 canvas.drawPoint(x, row, paint);
        	 	// }
        	 }
        	 //if(column >= 1){
        		next_column = column+1;
    			return;
    		 //}
         }
        */
	}

	public void setRunning(boolean b) {
        running = b;
    }
}