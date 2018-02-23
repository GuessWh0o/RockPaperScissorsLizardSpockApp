package com.guesswho.rockpaper.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guesswho.rockpaper.DialogFragmentYesNo;
import com.guesswho.rockpaper.FacebookLogin;
import com.guesswho.rockpaper.FirebaseLogin;
import com.guesswho.rockpaper.R;
import com.guesswho.rockpaper.utils.Constants;
import com.guesswho.rockpaper.utils.SharedPrefsUtil;

/**
 * Created by GuessWh0o on 18.02.2018.
 * Email: developerint97@gmail.com
 */

public class MenuActivity extends AppCompatActivity {

    private static final String TAG = "MenuActivity";

    private TextView TV_welcome;
    private LinearLayout LL_buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initResources();
    }

    private void initResources() {
        Button button_Play = findViewById(R.id.btn_play);
        button_Play.setOnClickListener(listener);

        Button button_Login = findViewById(R.id.btn_login);
        button_Login.setOnClickListener(listener);

        Button button_Exit = findViewById(R.id.btn_exit);
        button_Exit.setOnClickListener(listener);

        TextView TV_welcome = findViewById(R.id.tv_welcome);
        TV_welcome.setText(getResources().getString(R.string.welcome_loser_do_you_want_to_test_your_luck , checkAndReturnName()));

        LL_buttons = findViewById(R.id.ll_buttons);

        Animation upToDownAnim = AnimationUtils.loadAnimation(this, R.anim.uptodown_transition);
        Animation downToUpAnim = AnimationUtils.loadAnimation(this, R.anim.downtoup_transition);
        LL_buttons.setAnimation(downToUpAnim);
        TV_welcome.setAnimation(upToDownAnim);
    }

    View.OnClickListener listener = v -> {
        switch (v.getId()) {
            case R.id.btn_play:
                startActivity(GameActivity.class);
                break;
            case R.id.btn_login:
                startGoogleLogin();
                //Logging screen
                break;
            case R.id.btn_exit:
                finishAffinity();
                break;
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

    private void startGoogleLogin() {
        Log.d(TAG, "startGoogleLogin: ");
        startActivity(new Intent(this, FirebaseLogin.class));
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

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        DialogFragmentYesNo yesNoDialog = new DialogFragmentYesNo();
        yesNoDialog.show(fm, null);
    }

}
