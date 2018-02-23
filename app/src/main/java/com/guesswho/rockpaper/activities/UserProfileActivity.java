package com.guesswho.rockpaper.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.guesswho.rockpaper.R;
import com.guesswho.rockpaper.utils.Constants;
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

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        checkForAccountFirebase();

        SharedPrefsUtil sharedPrefsUtil = new SharedPrefsUtil(getApplicationContext());
        imageView = findViewById(R.id.profilePhoto);
        shareDialog = new ShareDialog(this);

        initFB_Button();

        String name = sharedPrefsUtil.getString(Constants.nameKey);
        String imageUrl = sharedPrefsUtil.getString(Constants.imageUrlKey);

        TextView nameView = findViewById(R.id.profileName);
        nameView.setText(name);

        initLogoutButton();
        new UserProfileActivity.DownloadImage().execute(imageUrl);

        setActionBar();
    }

    private void initFB_Button() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            ShareLinkContent content = new ShareLinkContent.Builder().build();
            shareDialog.show(content);
        });
    }

    private void checkForAccountFirebase() {
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = firebaseAuth -> {
            if(firebaseAuth.getCurrentUser() == null) {
                Toast.makeText(this, "You don't have a profile yet. Press LogIn Button", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UserProfileActivity.this, MenuActivity.class));
            }
        };
    }

    private void setActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(this,"Back button clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    private void initLogoutButton() {
        Button logout = findViewById(R.id.btn_logout);
        logout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
//            startActivity(new Intent(UserProfileActivity.this, MenuActivity.class));
//            finish();
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
