package com.example.digitalstethoscope.structures;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.DoubleBuffer;
import java.util.Observable;

import wav.WavFile.WavFile;
import wav.WavFile.WavFileException;
import android.util.Log;

import com.example.digitalstethoscope.util.calculating.CalcFFTTask;

//This reads samples from the wav file and
// places them into its internal DoubleBuffer
//
public class WavFileReader extends Observable implements Runnable {
    private WavFile wav;
    private DoubleBuffer fileSamples;
    private double[] frame;
    private int indx; // the current position processing fileSamples
    public static final int BUF_SIZE = 524288;
    public static final int FRAME_SIZE = 512;
    public static final int HOP_SIZE = FRAME_SIZE / 8;
    public static final String TAG = "WavFileReader";
    private boolean isRunning = false;

    public WavFileReader(WavFile wav) {
        this.wav = wav;
        this.fileSamples = DoubleBuffer.wrap(new double[BUF_SIZE]);
        this.frame = new double[FRAME_SIZE];
        this.indx = 0;
        isRunning = true;
    }

    @Override
    public void run() {
        // read frames from the wav file
        Log.d(TAG, "Running");
        int framesRead = 1;
        double[] buffer = new double[CalcFFTTask.FFT_SIZE
                * this.wav.getNumChannels()];
        // while (isRunning) {
        do {
            if (framesRead != 0) {
                try {
                    framesRead = this.wav.readFrames(buffer,
                            CalcFFTTask.FFT_SIZE);
                    fileSamples.put(buffer);
                } catch (IOException e) {
                } catch (WavFileException wfe) {
                } catch (BufferOverflowException bofe) {
                    break;
                }
            }
            Log.d(TAG, "Finished reading one frame from wav file.");
            transferSamples(FRAME_SIZE);
            setChanged();
            notifyObservers(this.frame);
            // try {
            // Thread.sleep(1000);
            // } catch (InterruptedException e) {
            // // TODO Auto-generated catch block
            // e.printStackTrace();
            // }
            incrementBufferPosition(HOP_SIZE);
        } while (isRunning && indx + FRAME_SIZE < BUF_SIZE
                && indx + FRAME_SIZE <= fileSamples.position() + HOP_SIZE);
        // }
        Log.d(TAG, "Stop running.");
    }

    public void pause() {
        this.isRunning = false;
    }

    public void resume() {
        this.isRunning = true;
    }

    // get the doubles from the DoubleBuffer
    //
    private void transferSamples(int numSamples) {
        for (int i = 0; i < numSamples; i++) {
            try {
                this.frame[i] = this.fileSamples.get(i + indx);
            } catch (IndexOutOfBoundsException ioobe) {
                Log.d(TAG, "Index out of bounds");
                this.frame[i] = 0.0;
                this.isRunning = false;
            }
        }
    }

    private void incrementBufferPosition(int hop) {
        this.indx += hop;
    }

}