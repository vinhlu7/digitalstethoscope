package com.example.digitalstethoscope.util.calculating;

import org.jtransforms.fft.DoubleFFT_1D;

import android.os.AsyncTask;
import android.util.Log;

public class CalcFFTTask extends AsyncTask<double[], Void, double[]> {
    public final static int FFT_SIZE = 512;
    private static final String TAG = "CalcFFTTask";
    private static final int RESULTS_LENGTH = 257; // FFT_SIZE+1/2
    private Window window = null;

    protected void onPreExecute() {
        String text = "Performing FFT. Simulated slowdown.";
        Log.d(TAG, text);
    }

    @Override
    protected double[] doInBackground(double[]... params) {
        DoubleFFT_1D fft = new DoubleFFT_1D(FFT_SIZE);
        window = new Window(FFT_SIZE, Window.WindowType.HANN);
        // x is twice the size because theres real components and complex
        // even parts of the x array are the real components
        // odd parts of the x array are the complex components
        double[] intermediate = java.util.Arrays
                .copyOf(params[0], FFT_SIZE * 2);
        this.applyWindow(intermediate);
        // x.length = 1024
        // fft.realForwardFull(intermediate);
        fft.realForwardFull(intermediate);
        // return this.formatResults(intermediate);
        return this.expandResult(this.formatResults(intermediate));
    }

    protected void onPostExecute(double[] result) {
        // Toast.makeText(mContext, "Finished FFT calculations.",
        // Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Finished FFT calculations.");
    }

    private void applyWindow(double[] intermediate) {
        for (int i = 0; i < this.window.getLength(); i++) {
            intermediate[2 * i] *= this.window.getPoint(i);
            intermediate[2 * i + 1] *= this.window.getPoint(i);
        }
    }

    private double[] formatResults(double[] intermediate) {
        // 1.

        // format the data
        // sum(hann(512)) = 255
        // s = abs(s)/(wlen^2)/255
        // double scale = 255 / (512 ^ 2);
        double scale = 255 / Math.pow(512, 2);
        // double scale = 1;
        double[] absolute = new double[RESULTS_LENGTH];
        double inter = 0.0;
        // intermediate is size 1024 with bottom half all zero
        for (int i = 0; i < RESULTS_LENGTH; i++) {
            // absolute of complex number
            inter = ((Math.sqrt(Math.pow(intermediate[i * 2], 2.0)
                    + Math.pow(intermediate[i * 2 + 1], 2.0))));
            // scale by sum(window)/(wlen^2)
            absolute[i] = scale * inter;

            // DC component and nyquist point
            absolute[i] *= 2;

            // amplitude to db scale min = -120dB
            absolute[i] = 20 * Math.log10(absolute[i] + 0.000001);
        }

        // scale
        return absolute;
    }

    private double[] expandResult(double[] flat) {
        double[] temp = java.util.Arrays.copyOf(flat, flat.length * 2);
        for (int i = 0; i < flat.length; i++) {
            temp[2 * i] = flat[i];
            temp[2 * i + 1] = flat[i];
        }
        return temp;
    }
}