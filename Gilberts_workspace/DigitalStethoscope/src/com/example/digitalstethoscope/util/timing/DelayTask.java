package com.example.digitalstethoscope.util.timing;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

//Responsive delay to UI thread
	// How to use:
	// int seconds = 3;
	// new DelayTask().execute(seconds)
	public class DelayTask extends AsyncTask<Integer, Void, Void> {
		private Context mContext;
		
		public DelayTask(Context previousContext) {
			this.mContext = previousContext;
		}
		
		protected void onPreExecute() {
			//testCanvasView.setText("Delay.");
			Log.d("DelayTask", "DelayTask preExecute");
		}
		
		@Override
		protected Void doInBackground(Integer... params) {
			int delaymilliseconds = params[0]*1000;
			try {
				Thread.sleep(delaymilliseconds);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				Log.d("DelayTask", e.toString());
				//e.printStackTrace();
			}
			// TODO Auto-generated method stub
			return null;
		}
		
		protected void onPostExecute(Void unused) {
			//testCanvasView.setText("Exit Delay.");
			Log.d("DelayTask", "DelayTask postExecute");
		}
				
	}
