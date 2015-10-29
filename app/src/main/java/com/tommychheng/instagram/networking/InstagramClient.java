package com.tommychheng.instagram.networking;

import android.nfc.Tag;
import android.preference.PreferenceActivity;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.model.OAuthConfig;

/**
 * Created by tchheng on 10/29/15.
 */
public class InstagramClient {
    final static String TAG = "InstagramClient";
    final static String clientID = "41cd0066b82f47e69db868af15c4b370";
    final static String popularUrl = "https://api.instagram.com/v1/media/popular";

    public static void getPopularFeed(JsonHttpResponseHandler responseHandler) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("client_id", clientID);

        client.get(popularUrl, params, responseHandler);
    }
}
