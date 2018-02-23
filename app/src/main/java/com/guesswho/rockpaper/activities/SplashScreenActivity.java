package com.guesswho.rockpaper.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.guesswho.rockpaper.R;

import static java.lang.Thread.sleep;

/**
 * Created by GuessWh0o on 05.20.2017.
 * Email: developerint97@gmail.com
 */

public class SplashScreenActivity extends AppCompatActivity {

    private TextView TV_splash;
    private ImageView IV_splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashsreen);

        TV_splash = findViewById(R.id.tv_splash);
        IV_splash = findViewById(R.id.iv_splash);
    }

    private void startApp() {
        Intent homeIntent = new Intent(SplashScreenActivity.this, MenuActivity.class);
        startActivity(homeIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Animation splashAnim = AnimationUtils.loadAnimation(this, R.anim.splash_screen_transition);
        TV_splash.startAnimation(splashAnim);
        IV_splash.startAnimation(splashAnim);

        new Thread(() -> {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                startApp();
                finish();
            }
        }).start();
    }
}