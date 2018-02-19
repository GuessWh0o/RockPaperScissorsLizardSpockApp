package com.guesswho.rockpaper.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.guesswho.rockpaper.R;
import com.guesswho.rockpaper.utils.SharedPrefsUtil;

import java.io.InputStream;

/**
 * Created by GuessWh0o on 05.20.2017.
 * Email: developerint97@gmail.com
 */

public class UserProfileActivity extends AppCompatActivity {

    private ShareDialog shareDialog;
    private static final String TAG = "UserProfileActivity";
    private static ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        SharedPrefsUtil sharedPrefsUtil = new SharedPrefsUtil(getApplicationContext());
        imageView = findViewById(R.id.profilePhoto);
        shareDialog = new ShareDialog(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareLinkContent content = new ShareLinkContent.Builder().build();
                shareDialog.show(content);
            }
        });

        String name = sharedPrefsUtil.getString(Constants.nameKey);
        String imageUrl = sharedPrefsUtil.getString(Constants.imageUrlKey);

        TextView nameView = findViewById(R.id.profileName);
        nameView.setText(name);

        initLogoutButton();
        new UserProfileActivity.DownloadImage().execute(imageUrl);
    }

    private void initLogoutButton() {
        Button logout = findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                startActivity(new Intent(UserProfileActivity.this, MenuActivity.class));
                finish();
            }
        });
    }

    private static class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {
            String urlDisplay = urls[0];
            Bitmap profileIcon = null;
            try {
                InputStream inputStream = new java.net.URL(urlDisplay).openStream();
                profileIcon = BitmapFactory.decodeStream(inputStream);
            } catch (Exception ex) {
                Log.e(TAG, ex.getMessage());
                ex.printStackTrace();
            }
            return profileIcon;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }
}
