package com.tommychheng.instagram.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.codepath.instagram.R;
import com.codepath.oauth.OAuthLoginActivity;
import com.tommychheng.instagram.networking.InstagramClient;

/**
 * Created by tchheng on 10/31/15.
 */
public class LoginActivity extends OAuthLoginActivity<InstagramClient> {
    // This fires once the user is authenticated, or fires immediately
    // if the user is already authenticated.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void onLoginSuccess() {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }

    // Fires if the authentication process fails for any reason.
    @Override
    public void onLoginFailure(Exception e) {
        e.printStackTrace();
    }

    // Method to be called to begin the authentication process
    // assuming user is not authenticated.
    // Typically used as an event listener for a button for the user to press.
    public void loginToRest(View view) {
        getClient().connect();
    }
}