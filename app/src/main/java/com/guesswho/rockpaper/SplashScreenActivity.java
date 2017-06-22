package com.guesswho.rockpaper;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import me.itangqi.waveloadingview.WaveLoadingView;

/**
 * Created by Maksym on 5/20/17.
 */

public class SplashScreenActivity extends AppCompatActivity implements LoadingTask.LoadingTaskFinishedListener {

    WaveLoadingView mWaveLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashsreen);
        setWaveLoadingView();


        new LoadingTask(this, mWaveLoadingView).execute();
    }

    private void setWaveLoadingView() {
        mWaveLoadingView = (WaveLoadingView) findViewById(R.id.waveLoadingView);
        mWaveLoadingView.setProgressValue(0);
        mWaveLoadingView.setShapeType(WaveLoadingView.ShapeType.CIRCLE);
        mWaveLoadingView.setAmplitudeRatio(60);
        mWaveLoadingView.setTopTitleStrokeColor(Color.GREEN);
        mWaveLoadingView.setTopTitleStrokeWidth(3);
        mWaveLoadingView.setAnimDuration(3000);
        mWaveLoadingView.startAnimation();
    }

    private void startApp() {
        Intent homeIntent = new Intent(SplashScreenActivity.this, MenuActivity.class);
        startActivity(homeIntent);
    }

    @Override
    public void onTaskFinished() {
        startApp();
    }
}

class LoadingTask extends AsyncTask<String, Integer, Integer> {

    public interface LoadingTaskFinishedListener {
        void onTaskFinished();
    }
    private WaveLoadingView mWaveLoadingView;
    private LoadingTaskFinishedListener finishedListener;

    public LoadingTask(LoadingTaskFinishedListener finishedListener, WaveLoadingView waveLoadingView) {
        mWaveLoadingView = waveLoadingView;
        this.finishedListener = finishedListener;
    }


    @Override
    protected Integer doInBackground(String... params) {

        if (resourcesDontAlreadyExist()) {
            downloadResources();
        }
        return null;
    }

    private boolean resourcesDontAlreadyExist() {
        // Here you would query your app's internal state to see if this download had been performed before
        // Perhaps once checked save this in a shared preference for speed of access next time
        return true; // returning true so we show the splash every time
    }


    private void downloadResources() {
        // We are just imitating some process thats takes a bit of time (loading of resources / downloading)
        int count = 20;
        for (int i = 0; i < count; i++) {

            // Update the progress bar after every step
            int progress = (int) ((i / (float) count) * 100);
            publishProgress(progress);

            // Do some long loading things
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignore) {
            }
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        mWaveLoadingView.setProgressValue(values[0]);
//        progressBar.setProgress(values[0]); // This is ran on the UI thread so it is ok to update our progress bar ( a UI view ) here
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        finishedListener.onTaskFinished(); // Tell whoever was listening we have finished
    }
}
