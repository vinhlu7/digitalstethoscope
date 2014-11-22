package com.example.surface_test;


import java.util.Random; 
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
 
public class MainActivity extends Activity {
  
 MySurfaceView mySurfaceView;
  
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       mySurfaceView = new MySurfaceView(this);
       setContentView(mySurfaceView);
   }
   
   @Override
 protected void onResume() {
  // TODO Auto-generated method stub
  super.onResume();
  mySurfaceView.onResumeMySurfaceView();
 }
 
 @Override
 protected void onPause() {
  // TODO Auto-generated method stub
  super.onPause();
  mySurfaceView.onPauseMySurfaceView();
 }
 
 class MySurfaceView extends SurfaceView implements Runnable{
     
    Thread thread = null;
    SurfaceHolder surfaceHolder;
    volatile boolean running = false;
     
    //private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG); //might cause lag 
    private Paint paint = new Paint();
    Random random;
 
  public MySurfaceView(Context context) {
   super(context);
   // TODO Auto-generated constructor stub
   surfaceHolder = getHolder();
   random = new Random();
  }
   
  public void onResumeMySurfaceView(){
   running = true;
   thread = new Thread(this);
   thread.start();
  }
   
  public void onPauseMySurfaceView(){
   boolean retry = true;
   running = false;
   while(retry){
    try {
     thread.join();
     retry = false;
    } catch (InterruptedException e) {
     // TODO Auto-generated catch block
     e.printStackTrace();
    }
   }
  }
 
  @Override
  public void run() {
   // TODO Auto-generated method stub
	  float[][] stft_array = new float[500][500];
	  int max_db = 20;
	  int min_db = -100;

	  for(int i=0; i<500;i++){
		  for(int j=0; j<500;j++){
			  stft_array[i][j] = -100 + (int)(Math.random()*(121));
		  }
	  }

   while(running){
    if(surfaceHolder.getSurface().isValid()){
     Canvas canvas = surfaceHolder.lockCanvas();
     //... drawing begins here
 
     paint.setStyle(Paint.Style.STROKE);
        //paint.setStrokeWidth(3); //might cause lag
      
     //int w = canvas.getWidth();
     //int h = canvas.getHeight();
    // int x = random.nextInt(w-1); 
     //int y = random.nextInt(h-1);
     float x = 4;
     float y = 4;
     for(int column =0; column<500; column++){
    	 x++;
    	 y = 4; //reset y plot point when move to next column
    	 for(int row=0; row<500; row++){ //max height is around 610
    	 	 y++ ;
     //int r = random.nextInt(255);
     //int g = random.nextInt(255);
     //int b = random.nextInt(255);
     //paint.setColor(0xff000000 + (r << 16) + (g << 8) + b);

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
    	 	//paint.setColor(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255))); 
    	 	canvas.drawPoint(x, y, paint);
    	 }
     }
     surfaceHolder.unlockCanvasAndPost(canvas);
    }
   }
  }
     
   }
}