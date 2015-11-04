package com.tommychheng.instagram.services;

import android.app.Activity;
import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.tommychheng.instagram.core.MainApplication;
import com.tommychheng.instagram.helpers.Utils;
import com.tommychheng.instagram.models.InstagramPost;
import com.tommychheng.instagram.models.InstagramPosts;
import com.tommychheng.instagram.persistence.InstagramClientDatabase;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by tchheng on 11/3/15.
 */
public class InstagramService extends IntentService {
    static final String TAG = "InstagramService";
    public static final String KEY_RESULTS = "KeyResults";
    public static final String KEY_RESULT_CODE = "KeyResultCode";
    public static final String ACTION = "com.tommychheng.instagramservice";

    public InstagramService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "onHandleIntent");

        try {
            MainApplication.getRestClient().getHomeFeed(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    final List<InstagramPost> posts = Utils.decodePostsFromJsonResponse(response);
                    final InstagramPosts postsContainer = new InstagramPosts(posts);

                    Log.i(TAG, "onSuccess - sending back postsContainer");
                    sendIntent(postsContainer);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.e(TAG, String.valueOf(statusCode));
                    Log.e(TAG, responseString);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject response) {
                    Log.e(TAG, response.toString());
                }
            });

        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    protected void sendIntent(InstagramPosts postsContainer) {
        Intent i = new Intent(ACTION);
        i.putExtra(KEY_RESULT_CODE, Activity.RESULT_OK);
        i.putExtra(KEY_RESULTS, postsContainer);
        Log.i(TAG, "onHandleIntent - Sent broadcast");
        LocalBroadcastManager.getInstance(this).sendBroadcast(i);
    }
}
