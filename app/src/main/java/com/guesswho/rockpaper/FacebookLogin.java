package com.guesswho.rockpaper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.guesswho.rockpaper.utils.Constants;
import com.guesswho.rockpaper.activities.UserProfileActivity;
import com.guesswho.rockpaper.utils.SharedPrefsUtil;

/**
 * Created by GuessWh0o on 06.22.2017.
 * Email: developerint97@gmail.com
 */

public class FacebookLogin extends FragmentActivity {

    private static final String TAG = "FacebookLogin";

    private LoginButton loginButton;
    private TextView textView;
    public CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;

    public FacebookLogin() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facebook_login_layout);

        //prefs = PreferenceManager.getDefaultSharedPreferences(this);

        callbackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.btn_fbLogin);
        textView = findViewById(R.id.textView_FbLoginStatus);
        // loginButton.setReadPermissions("user_friends");
        loginButton.setReadPermissions("public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "onSuccess: " + loginResult.getAccessToken().getUserId());
                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();
                displayWelcomeMessage(profile);
                if (profile != null) {
                    addToSharedPrefs(Constants.nameKey, profile.getName());
                    addToSharedPrefs(Constants.imageUrlKey, profile.getProfilePictureUri(200, 200).toString());
                }
                startProfileActivity(profile);
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "onCancel: ");
                textView.setText(R.string.login_cancelled);
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "onError: " + error);
            }
        });
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {

            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                displayWelcomeMessage(newProfile);
                if (newProfile != null) {
                    addToSharedPrefs(Constants.nameKey, newProfile.getName());
                    addToSharedPrefs(Constants.imageUrlKey, newProfile.getProfilePictureUri(200, 200).toString());
                }
                startProfileActivity(newProfile);
            }
        };
        accessTokenTracker.startTracking();
        profileTracker.startTracking();
    }

    @Override
    public void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
        displayWelcomeMessage(profile);
        if (profile != null) {
            addToSharedPrefs(Constants.nameKey, profile.getName());
            addToSharedPrefs(Constants.imageUrlKey, profile.getProfilePictureUri(200, 200).toString());
      //      Constants.facebookName = profile.getName();
      //      Constants.imgUrl = profile.getProfilePictureUri(200, 200).toString();
        }

        startProfileActivity(profile);
    }

    @Override
    public void onStop() {
        super.onStop();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Pass Result into the CallbackManager
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }



    private void addToSharedPrefs(String key, String value) {
        SharedPrefsUtil sharedPrefsUtil = new SharedPrefsUtil(getApplicationContext());
        sharedPrefsUtil.saveString(key, value);
    }

    private void displayWelcomeMessage(Profile profile) {
        if (profile != null) {
            textView.setText(getString(R.string.welcome, profile.getFirstName()));
        }
    }

    private void startProfileActivity(Profile profile) {
        if (profile != null) {
            Intent intent = new Intent(this, UserProfileActivity.class);
            intent.putExtra(Constants.nameKey, profile.getName());
            intent.putExtra(Constants.imageUrlKey, profile.getProfilePictureUri(200, 200).toString());
            startActivity(intent);
        }
    }
}
