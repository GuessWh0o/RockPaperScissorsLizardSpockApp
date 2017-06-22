package com.guesswho.rockpaper;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

/**
 * Created by Maksym on 6/22/17.
 * Company: Activaire
 */

public class FacebookLogin extends DialogFragment {

    private static final String TAG = "FacebookLogin";
    
    LoginButton loginButton;
    TextView textView;
    CallbackManager callbackManager;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Pass Result into the CallbackManager
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.facebook_login_layout, container, false);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
       // getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLUE));

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        loginButton = (LoginButton) rootView.findViewById(R.id.btn_fbLogin);
        textView = (TextView) rootView.findViewById(R.id.textView_FbLoginStatus);
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "onSuccess: " + loginResult.getAccessToken().getUserId());
                textView.setText("Login Successful \n" + loginResult.getAccessToken().getUserId());
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

        return rootView;
    }
}
