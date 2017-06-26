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

import java.io.InputStream;

public class UserProfileActivity extends AppCompatActivity {

    SharedPreferences prefs;
    private ShareDialog shareDialog;
    private Button logout;
    private static final String TAG = "UserProfileActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        shareDialog = new ShareDialog(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareLinkContent content = new ShareLinkContent.Builder().build();
                shareDialog.show(content);
            }
        });

        String name = prefs.getString("name", "Facebook Name");
        String imageUrl = prefs.getString("imageUrl", "PHOTO");

//        Bundle inBundle = getIntent().getExtras();
//        String name = inBundle.get("name").toString();
//        String imageUrl = inBundle.get("imageUrl").toString();

        TextView nameView = (TextView)findViewById(R.id.profileName);
        nameView.setText(name);
        logout = (Button) findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                startActivity(new Intent(UserProfileActivity.this, MenuActivity.class));
                finish();
            }
        });
        new UserProfileActivity.DownloadImage((ImageView) findViewById(R.id.profilePhoto)).execute(imageUrl);
    }

    public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImage(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String urlDisplay = urls[0];
            Bitmap profileIcon = null;
            try {
                InputStream inputStream = new java.net.URL(urlDisplay).openStream();
                profileIcon = BitmapFactory.decodeStream(inputStream);
            } catch(Exception ex) {
                Log.e(TAG, ex.getMessage());
                ex.printStackTrace();
            }
            return profileIcon;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }

}
