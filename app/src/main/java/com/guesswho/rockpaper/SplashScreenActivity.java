package com.guesswho.rockpaper;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

/**
 * Created by Maksym on 5/20/17.
 */

public class SplashScreenActivity extends AppCompatActivity implements LoadingTask.LoadingTaskFinishedListener {


    ProgressBar progressBar;
    private static int SPLASH_TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashsreen);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        new LoadingTask(progressBar, this).execute();
    }

    private void startApp() {
        Intent homeIntent = new Intent(SplashScreenActivity.this, Game.class);
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

    private ProgressBar progressBar;

    private LoadingTaskFinishedListener finishedListener;

    public LoadingTask(ProgressBar progressBar, LoadingTaskFinishedListener finishedListener) {
        this.progressBar = progressBar;
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
        int count = 10;
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
        progressBar.setProgress(values[0]); // This is ran on the UI thread so it is ok to update our progress bar ( a UI view ) here
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        finishedListener.onTaskFinished(); // Tell whoever was listening we have finished
    }
}