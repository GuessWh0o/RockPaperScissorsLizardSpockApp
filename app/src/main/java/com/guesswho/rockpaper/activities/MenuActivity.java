package com.guesswho.rockpaper.activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.guesswho.rockpaper.FacebookLogin;
import com.guesswho.rockpaper.R;
import com.guesswho.rockpaper.utils.SharedPrefsUtil;

/**
 * Created by GuessWh0o on 18.02.2018.
 * Email: developerint97@gmail.com
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
        button_Play = findViewById(R.id.btn_play);
        button_Play.setOnClickListener(listener);

        button_Login = findViewById(R.id.btn_login);
        button_Login.setOnClickListener(listener);

        button_Exit = findViewById(R.id.btn_exit);
        button_Exit.setOnClickListener(listener);

        TextView TV_welcome = findViewById(R.id.tv_welcome);
        TV_welcome.setText(getResources().getString(R.string.welcome_loser_do_you_want_to_test_your_luck , checkAndReturnName()));
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_play:
                    startActivity(GameActivity.class);
                    break;
                case R.id.btn_login:
                    startFbLogin();
                    //Logging screen
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
        startActivity(new Intent(this, FacebookLogin.class));
    }

    private String checkAndReturnName() {
        SharedPrefsUtil sharedPrefsUtil = new SharedPrefsUtil(getApplicationContext());
        String name = sharedPrefsUtil.getString(Constants.nameKey);
        if(name == null || name.length() < 1) {
            name = "Loser";
        }
        return name;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuItem_profile) {
            startActivity(new Intent(this, UserProfileActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
