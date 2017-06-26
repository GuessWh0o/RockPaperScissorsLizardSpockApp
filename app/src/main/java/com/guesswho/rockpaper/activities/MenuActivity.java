package com.guesswho.rockpaper.activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.guesswho.rockpaper.FacebookLogin;
import com.guesswho.rockpaper.R;

/**
 * Created by Maksym on 6/22/17.
 * Company: Activaire
 */

public class MenuActivity extends AppCompatActivity {


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
        if (id == R.id.menuItem_profile) {
            startActivity(new Intent(this, UserProfileActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static final String TAG = "MenuActivity";
    Button button_Play;
    Button button_Exit;
    Button button_Login;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initResources();
    }

    private void initResources() {
        mFragmentManager = getFragmentManager();

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
                    startActivity(GameActivity.class);
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
        startActivity(new Intent(this, FacebookLogin.class));
    }
}
