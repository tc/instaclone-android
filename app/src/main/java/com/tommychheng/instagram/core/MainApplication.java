package com.tommychheng.instagram.core;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.tommychheng.instagram.networking.InstagramClient;

public class MainApplication extends Application {
    private static final String TAG = "MainApplication";
    private static MainApplication instance;

    public static MainApplication sharedApplication() {
        assert(instance != null);
        return instance;
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
        Fresco.initialize(this);
    }

    public static InstagramClient getRestClient() {
        return (InstagramClient) InstagramClient.getInstance(InstagramClient.class, sharedApplication());
    }
}
