package com.guesswho.rockpaper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.guesswho.rockpaper.activities.Credentials;
import com.guesswho.rockpaper.activities.UserProfileActivity;

/**
 * Created by Maksym on 6/22/17.
 * Company: Activaire
 */

public class FacebookLogin extends FragmentActivity {

    private static final String TAG = "FacebookLogin";

    SharedPreferences prefs;
    LoginButton loginButton;
    TextView textView;
    public CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;

    public FacebookLogin() {

    }
    private void addToSharedPrefs(String key, String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    private void diplayWelcomeMessage(Profile profile) {
        if (profile != null) {
            textView.setText("Welcome \n" + profile.getFirstName());
        }
    }

    private void startProfileActivity(Profile profile) {
        if (profile != null) {
            Intent intent = new Intent(this, UserProfileActivity.class);
            intent.putExtra("name", profile.getName());
            intent.putExtra("imageUrl", profile.getProfilePictureUri(200, 200).toString());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
        diplayWelcomeMessage(profile);
        if (profile != null) {
            addToSharedPrefs("name", profile.getName());
            addToSharedPrefs("imageUrl", profile.getProfilePictureUri(200, 200).toString());
//            Credentials.facebookName = profile.getName();
//            Credentials.imgUrl = profile.getProfilePictureUri(200, 200).toString();
        }

        //  startProfileActivity(profile);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Pass Result into the CallbackManager
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facebook_login_layout);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.btn_fbLogin);
        textView = (TextView) findViewById(R.id.textView_FbLoginStatus);
        // loginButton.setReadPermissions("user_friends");
        loginButton.setReadPermissions("public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "onSuccess: " + loginResult.getAccessToken().getUserId());
                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();
                diplayWelcomeMessage(profile);
                if (profile != null) {
                    addToSharedPrefs("name", profile.getName());
                    addToSharedPrefs("imageUrl", profile.getProfilePictureUri(200, 200).toString());
//                    Credentials.facebookName = profile.getName();
//                    Credentials.imgUrl = profile.getProfilePictureUri(200, 200).toString();
                }
//                addToSharedPrefs("name", profile.getName());
//                addToSharedPrefs("imageUrl", profile.getProfilePictureUri(200, 200).toString());
                // startProfileActivity(profile);
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "onCancel: ");
                textView.setText("Login Cancelled");
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
                diplayWelcomeMessage(newProfile);
                if (newProfile != null) {
                    addToSharedPrefs("name", newProfile.getName());
                    addToSharedPrefs("imageUrl", newProfile.getProfilePictureUri(200, 200).toString());
//                    Credentials.facebookName = newProfile.getName();
//                    Credentials.imgUrl = newProfile.getProfilePictureUri(200, 200).toString();
                }
//                addToSharedPrefs("name", newProfile.getName());
//                addToSharedPrefs("imageUrl", newProfile.getProfilePictureUri(200, 200).toString());
                //    startProfileActivity(newProfile);
            }
        };
        accessTokenTracker.startTracking();
        profileTracker.startTracking();
    }

    @Override
    public void onStop() {
        super.onStop();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }
}
