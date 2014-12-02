package com.example.digitalstethoscope.util.calculating;

import org.jtransforms.fft.DoubleFFT_1D;

import android.os.AsyncTask;
import android.util.Log;

public class CalcFFTTask extends AsyncTask<double[], Void, double[]> {
    public final static int FFT_SIZE = 512;

    // private double[] frame;

    // private double[] result;

    public CalcFFTTask() {
    }

    protected void onPreExecute() {
        String text = "Performing FFT. Simulated slowdown.";
        // testCanvasView.setText(text);
        // Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
        // new DelayTask(mContext).execute(2);
        Log.d("CalcFFTTask", text);
    }

    @Override
    protected double[] doInBackground(double[]... params) {

        DoubleFFT_1D fft = new DoubleFFT_1D(FFT_SIZE);
        // x is twice the size because theres real components and complex
        // even parts of the x array are the real components
        // odd parts of the x array are the complex components
        double[] x = java.util.Arrays.copyOf(params[0], FFT_SIZE * 2);

        fft.realForwardFull(x);
        // this.result = x;
        // TODO Auto-generated method stub
        return x;
    }

    protected void onPostExecute(double[] result) {
        // Toast.makeText(mContext, "Finished FFT calculations.",
        // Toast.LENGTH_SHORT).show();
        Log.d("CalcFFTTask", "Finished FFT calculations.");
    }
}