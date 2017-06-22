package com.guesswho.rockpaper;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by Maksym on 6/22/17.
 * Company: Activaire
 */

public class MenuActivity extends AppCompatActivity {


    private static final String TAG = "MenuActivity";
    Button button_Play;
    Button button_Exit;
    Button button_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initResources();
    }

    private void initResources() {
        button_Play = (Button) findViewById(R.id.btn_play);
        button_Play.setOnClickListener(listener);

        button_Login = (Button) findViewById(R.id.btn_login);
        button_Login.setOnClickListener(listener);

        button_Exit = (Button) findViewById(R.id.btn_exit);
        button_Exit.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_play:
                    startActivity(Game.class);
                    break;
                case R.id.btn_login:
                    startFbLogin();
                    //Loging screen
                    break;
                case R.id.btn_exit:
                    finishAffinity();
                    break;
            }
        }
    };

    private void startActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    private void startFbLogin() {

        Log.d(TAG, "startFbLogin: ");
        FragmentManager fm = getFragmentManager();
        FacebookLogin facebookLogin = new FacebookLogin();
        facebookLogin.show(fm, "Sample Fragment");
    }
}
