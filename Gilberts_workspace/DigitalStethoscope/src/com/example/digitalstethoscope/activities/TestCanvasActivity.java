package com.example.digitalstethoscope.activities;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import wav.WavFile.WavFile;
import wav.WavFile.WavFileException;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
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
import android.widget.Toast;

import com.example.digitalstethoscope.R;
import com.example.digitalstethoscope.util.calculating.CalcFFTTask;
import com.example.digitalstethoscope.util.fileexplorer.FileChooser;

public class TestCanvasActivity extends Activity implements OnClickListener {
    private WavFile wav;
    private String fullPath = null;

    // private TestCanvasView testCanvasView;
    private TextView testCanvasView;
    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_canvas);
        start = (Button) findViewById(R.id.CalcFFTButton);
        testCanvasView = (TextView) findViewById(R.id.TestCanvasView);
        testCanvasView.setText("Start");
        // testCanvasView = (TestCanvasView)
        // findViewById(R.id.TestCanvasViewClass);
        start.setOnClickListener(this);
        // new PrepareViewTask().execute();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                this.fullPath = data.getStringExtra("fullpath");
                try {
                    this.wav = WavFile.openWavFile(new File(this.fullPath));
                } catch (Exception e) {
                    System.out.println("Error" + e);
                }
            }
        }
    }// onActivityResult

    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.CalcFFTButton:
            if (this.wav != null) {
                testCanvasView.setText("After button click wav open");
                Log.d("TestCanvasActivity", "After button click wav open");
                int framesRead = 0;
                double[] buffer = new double[CalcFFTTask.FFT_SIZE
                        * this.wav.getNumChannels()];
                try {
                    framesRead = this.wav.readFrames(buffer,
                            CalcFFTTask.FFT_SIZE);
                } catch (IOException e) {
                } catch (WavFileException wfe) {
                }
                CalcFFTTask calcffttask = new CalcFFTTask(this);
                calcffttask.execute(buffer);

                try {
                    int lines = 20;
                    double[] results = calcffttask.get(5, TimeUnit.SECONDS);
                    testCanvasView.setText("First " + lines
                            + " FFT calculations.\n"
                            + formatDouble(lines, results));
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    // e.printStackTrace();
                    Toast.makeText(this, "Interrupted Exception",
                            Toast.LENGTH_SHORT).show();
                } catch (ExecutionException e) {
                    // TODO Auto-generated catch block
                    // e.printStackTrace();
                    Toast.makeText(this, "Execution Exception",
                            Toast.LENGTH_SHORT).show();
                } catch (TimeoutException e) {
                    // TODO Auto-generated catch block
                    // e.printStackTrace();
                    Toast.makeText(this, "Timeout Exception",
                            Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "Please select a .WAV file first.",
                        Toast.LENGTH_SHORT).show();
                /*
                 * testCanvasView.setText("After button click random");
                 * Log.d("debugthisshit", "After button click random"); //new
                 * PrepareViewTask().execute(); double[] testinput = new
                 * double[CalcFFTTask.FFT_SIZE];
                 * 
                 * for (int i= 0; i < CalcFFTTask.FFT_SIZE; i++) { testinput[i]
                 * = Math.random(); } new CalcFFTTask().execute(testinput);
                 */
            }

        }
    }

    public void onClickOpenWav(View view) {

        switch (view.getId()) {
        case R.id.OpenWavButton:
            Intent fileChooserIntent = new Intent(this, FileChooser.class);
            startActivityForResult(fileChooserIntent, 1);
            /*
             * System.out.println("end of open wav"); try{
             * System.out.println("start of try in open wav"); this.wav =
             * WavFile.openWavFile(new File(result)); numChannels =
             * this.wav.getNumChannels(); // Create a buffer of 512 frames
             * buffer = new double[CalcFFTTask.FFT_SIZE * numChannels]; }catch
             * (Exception e) { System.out.println("Error" + e); }
             * System.out.println("end of open wav");
             */
        }
        /*
         * switch(view.getId()) { case R.id.OpenWavButton:
         * testCanvasView.setText("After button openWav click");
         * Log.d("debugthisshit", "After button openWav click"); File file =
         * Environment.getExternalStorageDirectory(); this.wav = new WavFile();
         * int numChannels = 0; double[] buffer = new double[1];
         * testCanvasView.setText("Extern stor dir: " + file);
         * 
         * try { this.wav = WavFile.openWavFile(new File(file +
         * "/wav/seashell.wav")); //seashell.display(); // Get the number of
         * audio channels in the wav file numChannels =
         * this.wav.getNumChannels(); // Create a buffer of 512 frames buffer =
         * new double[CalcFFTTask.FFT_SIZE * numChannels]; } catch (Exception e)
         * { System.out.println("Error" + e); }
         * testCanvasView.setText("Succes opening wav file.");
         * Log.d("debugthisshit", "Succes opening wav file."); }
         */
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

    public Integer[] getScreenSize() {
        Integer dim[] = { 0, 0 };
        Point p = new Point();
        WindowManager wm = getWindowManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            wm.getDefaultDisplay().getSize(p);
            dim[0] = p.x;
            // subtract offset caused by buttons
            dim[1] = p.y;
        } else {
            Display d = wm.getDefaultDisplay();
            dim[0] = d.getWidth();
            // subtract offset caused by buttons
            dim[1] = d.getHeight();
        }

        Log.d("debugthisshit", "dim[0] = " + dim[0]);

        Log.d("debugthisshit", "dim[1] = " + dim[1]);

        return dim;
    }

    public static String formatDouble(int lines, double[] fftresults) {
        StringBuilder str = new StringBuilder();
        String pre = "";
        String mid = "";
        lines *= 2;

        for (int i = 0; i < lines; i += 2) {
            pre = (fftresults[i] >= 0.0) ? " " : "";
            mid = (fftresults[i + 1] >= 0.0) ? "+" : "";
            str.append(String.format("%s%.4f %s %.4fi \n", pre, fftresults[i],
                    mid, fftresults[i + 1]));
        }
        return str.toString();
        // return "stuff";
    }
}
