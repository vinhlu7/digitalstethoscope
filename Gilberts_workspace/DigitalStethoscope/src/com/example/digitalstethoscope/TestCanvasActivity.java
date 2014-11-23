package com.example.digitalstethoscope;

import java.util.Random;

import org.jtransforms.fft.DoubleFFT_1D;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class TestCanvasActivity extends Activity implements OnClickListener{
	
	//private TestCanvasView testCanvasView;
	private TextView testCanvasView;
	Button start;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_canvas);
		start = (Button) findViewById(R.id.TestAsyncButton);
		testCanvasView = (TextView) findViewById(R.id.TestCanvasView);
		
		testCanvasView.setText("Start");
		//testCanvasView = (TestCanvasView) findViewById(R.id.TestCanvasViewClass);
		start.setOnClickListener(this);
		//new PrepareViewTask().execute();
	}
	
	public void onClick(View view) {
		switch(view.getId()) {
		case R.id.TestAsyncButton:
			testCanvasView.setText("After button click");
			Log.d("debugthisshit", "After button click");
			//new PrepareViewTask().execute();
			double[] testinput = new double[CalcFFTTask.FFT_SIZE];
			
			for (int i= 0; i < CalcFFTTask.FFT_SIZE; i++) {
				testinput[i] = Math.random();
			}
			new CalcFFTTask().execute(testinput);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_canvas, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public Integer[] getScreenSize()
	{
		Integer dim[] = {0,0};
		Point p = new Point();
		WindowManager wm = getWindowManager();
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.
				HONEYCOMB_MR2)
		{
			wm.getDefaultDisplay().getSize(p);
			dim[0] = p.x;
			// subtract offset caused by buttons
			dim[1] = p.y;
		}
		else
		{
			Display d = wm.getDefaultDisplay();
			dim[0] = d.getWidth();
			// subtract offset caused by buttons
			dim[1] = d.getHeight();
		}
		
		Log.d("debugthisshit", "dim[0] = " + dim[0]);
		
		Log.d("debugthisshit", "dim[1] = " + dim[1]);
		
		return dim;
	}
	
	public class PrepareViewTask extends AsyncTask<Void, Void, Void> {
    	
    	//private int[] rectCoordTemp;
    	
    	//private int[] colorsTemp;
    	
		@Override
		protected Void doInBackground(Void ...args)
		{			
			Log.d("debugthisshit", "Inside doInBackgroung. Doing work.");
			for(int i=0;i<2;i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            //TextView txt = (TextView) findViewById(R.id.TestCanvasView);
			//testCanvasView.setText("Executed");
			
			/*
			Integer[] screenSize = getScreenSize();
			
			rectCoordTemp = new int[4 * (screenSize[0] / 2) * (screenSize[1] / 2)];
			
			colorsTemp = new int[rectCoordTemp.length / 4];
			
			Random rand = new Random();
			
			for(int i = 0; i < colorsTemp.length; i++) {
				colorsTemp[i] = Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
			}
			
			rectCoordTemp[0] = 0;
			rectCoordTemp[1] = 0;
			rectCoordTemp[2] = 2;
			rectCoordTemp[3] = 2;
			
			for(int i = 4; i + 3 < rectCoordTemp.length; i = i + 4) {
				if(i % ((screenSize[0] / 2) * 4) != 0) {
					rectCoordTemp[i] = rectCoordTemp[i - 4] + 2;
					rectCoordTemp[i + 1] = rectCoordTemp[i + 1 - 4];
					rectCoordTemp[i + 2] = rectCoordTemp[i + 2 - 4] + 2;
					rectCoordTemp[i + 3] = rectCoordTemp[i + 3 - 4] + 2;
				}
				else {
					rectCoordTemp[i] = rectCoordTemp[i - ((screenSize[0] / 2) * 4)];
					rectCoordTemp[i + 1] = rectCoordTemp[i + 1 - ((screenSize[0] / 2) * 4)] + 2;
					rectCoordTemp[i + 2] = rectCoordTemp[i + 2 - ((screenSize[0] / 2) * 4)];
					rectCoordTemp[i + 3] = rectCoordTemp[i + 3 - ((screenSize[0] / 2) * 4)] + 2;
				}
			}
			
			Log.d("debugthisshit", "I got here2");
			
			testCanvasView.setPictureData(screenSize[0], screenSize[1], rectCoordTemp, colorsTemp);
			testCanvasView.forceDraw();
			*/
			//testCanvasView.setText("Finished with work. Returning.");
			Log.d("debugthisshit", "Finished with work. Returning.");
			return null;
			
		}
		
		protected void onPreExecute() {
			testCanvasView.setText("Doing work.");
			Log.d("debugthisshit", "Before doInBackgroung. About to do work.");
		}
		
		protected void onPostExecute(Void unused) {
			testCanvasView.setText("Finished async work. View should be updated.");
			Log.d("debugthisshit", "Finished async work. View should be updated.");
			//testCanvasView.setText("Executed");
		}
	}
	
	public class CalcFFTTask extends AsyncTask<double[], Void, double[]> {
		public final static int FFT_SIZE = 512;
		
		//private double[] frame;

	    //private double[] result;
	    
	    protected void onPreExecute() {
	    	testCanvasView.setText("Performing FFT. Simulated slowdown.");
	    	new DelayTask().execute(2);
			Log.d("debugthisshit", "Before doInBackgroung. About to do work.");
	    }    
	    
		@Override
		protected double[] doInBackground(double[]... params) {
			
			DoubleFFT_1D fft = new DoubleFFT_1D(FFT_SIZE);
			// x is twice the size because theres real components and complex
			// even parts of the x array are the real components
			// odd parts of the x array are the complex components
			double[] x = java.util.Arrays.copyOf(params[0], FFT_SIZE * 2);

	        fft.realForwardFull(x);
	        //this.result = x;
			// TODO Auto-generated method stub
			return x;
		}
		
		protected void onPostExecute(double[] result) {
			StringBuilder str = new StringBuilder();
			int printlength = 40;
			String pre = "";
	        String mid = ""; 
	        
			str.append("Finished calculating FFT. First " + printlength/2 + " FFT calcs:\n");
	        for (int i = 0; i < printlength; i += 2) {
	            pre = (result[i] >= 0.0) ? " " : "";
	            mid = (result[i + 1] >= 0.0) ? "+" : "";
	            str.append(String.format("%s%.4f %s %.4fi \n", pre, result[i], mid, result[i + 1]));
	        }			
			testCanvasView.setText(str.toString());
			Log.d("debugthisshit", "Finished async work. View should be updated.");
	    }		
		
	}
	
	// Responsive delay to UI thread
	// How to use:
	// int seconds = 3;
	// new DelayTask().execute(seconds)
	public class DelayTask extends AsyncTask<Integer, Void, Void> {
		
		protected void onPreExecute() {
			testCanvasView.setText("Delay.");
			Log.d("debugthisshit", "DelayTask preExecute");
		}
		
		@Override
		protected Void doInBackground(Integer... params) {
			int delaymilliseconds = params[0]*1000;
			try {
				Thread.sleep(delaymilliseconds);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// TODO Auto-generated method stub
			return null;
		}
		
		protected void onPostExecute(Void unused) {
			testCanvasView.setText("Exit Delay.");
			Log.d("debugthisshit", "DelayTask postExecute");
		}
				
	}

	
}
