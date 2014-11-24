package com.example.animation;

import android.graphics.Bitmap;

import java.util.Random;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public class AnimThread extends Thread {

    private SurfaceHolder holder;
    Random random;
    private boolean running = true;
	int x_position =0;
    Canvas canvas = null;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 900;
    private ColorArray colorArray = new ColorArray(WIDTH,HEIGHT);
    

    Bitmap bitmap = Bitmap.createBitmap(WIDTH, HEIGHT,Bitmap.Config.RGB_565);
    //int[]pixels = new int[bitmap.getHeight()*bitmap.getWidth()];
    
    public AnimThread(SurfaceHolder holder) {
        this.holder = holder;
    }

    @Override
    public void run() {
    	random = new Random();
      //  for (int i=0; i<bitmap.getWidth()*bitmap.getHeight(); i++)
        //    pixels[i] = Color.BLUE;
        //bitmap.setPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        
        while(running ) {
           // Canvas canvas = null;

            try {
                canvas = holder.lockCanvas();
                 synchronized (holder) {
                    // draw
                	canvas.drawColor(Color.BLACK);
                	//i++;
                	//System.out.println("post called: " + i);
                	postInvalidate();
                	/*
                    canvas.drawColor(Color.BLACK);
                    Paint paint = new Paint();
                    /*
                   
                    paint.setColor(Color.WHITE);
                    canvas.drawCircle(60, i++, 50, paint);
                    paint.setColor(Color.RED);
                    canvas.drawCircle(j++, 100, 50, paint);
	
                	 float x = 4;
                     float y = 4;
                     for(int column =0; column<250; column++){
                    	 x++;
                    	 y = 4; //reset y plot point when move to next column
                    	 for(int row=0; row<610; row++){ //max height is around 610
                    	 	 y++ ;
                    	 	 paint.setColor(Color.RED);
                    	 	 canvas.drawPoint(x, y, paint);
                    	 	 
                    	 }
                     }
                     */
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
    	int color = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    	for(int i=0;i<HEIGHT;i++){
    		columnArray[i] = color;
    	}
    	colorArray.insert(columnArray);
    	//for(int i=0;i<HEIGHT*10;i++){
    	//	pixels[i] = Color.RED;
    	//}
    	bitmap = Bitmap.createBitmap(colorArray.castInt(),WIDTH,HEIGHT,Bitmap.Config.RGB_565);

        canvas.drawBitmap(bitmap, x_position,0, null); 
        
        

         
    	
    	
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
