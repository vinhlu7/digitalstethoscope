package com.example.animation;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public class AnimThread extends Thread {

    private SurfaceHolder holder;
    private boolean running = true;
    int i,j= 0;
    int next_column = 150;
    int screen_width = 800;
    int screen_height = 900;
    float[][] stft_array = new float[800][900];
    Canvas canvas = null;
    
    public AnimThread(SurfaceHolder holder) {
        this.holder = holder;
    }

    @Override
    public void run() {
        
    	System.out.println("assigned");
        for(int i=0;i<800;i++){
        	for(int j=0;j<900;j++){
        		stft_array[i][j] = -100 + (int)(Math.random()*(121));
        	}
        }
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
    	//Canvas canvas = null;
    	//canvas.drawColor(Color.BLACK);
        Paint paint = new Paint();
        for(int column = next_column; column<screen_width;){
        	 for(int row=0; row<screen_height; row++){ //max height is around 900
        	 	 //paint.setColor(Color.RED);
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
        	 	 for(int x = 150; x<=column;x++){
        	 		 canvas.drawPoint(x, row, paint);
        	 	 }
        	 }
        	 //if(column >= 1){
        		 next_column = column+1;
    			 return;
    		 //}
         }
	}

	public void setRunning(boolean b) {
        running = b;
    }
}
