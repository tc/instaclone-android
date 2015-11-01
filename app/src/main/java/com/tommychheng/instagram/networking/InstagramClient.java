package com.tommychheng.instagram.networking;

import android.content.Context;
import android.nfc.Tag;
import android.preference.PreferenceActivity;
import android.util.Log;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tommychheng.instagram.helpers.Constants;

import org.scribe.builder.api.Api;
import org.scribe.model.OAuthConfig;

/**
 * Created by tchheng on 10/29/15.
 */
public class InstagramClient extends OAuthBaseClient {

    public static final Class<? extends Api> REST_API_CLASS = InstagramApi.class;
    final static String TAG = "InstagramClient";

    final static String REST_URL = "https://api.instagram.com/v1";
    private static final String REST_CONSUMER_KEY = "7fb830b3d9f944caadde9827cacc50a2";
    private static final String REST_CONSUMER_SECRET = "eaaed381d5264040930f3930937c2aa2";
    final static String REST_CALLBACK_URL = "oauth://codepath.com";
    final static String REDIRECT_URI = Constants.REDIRECT_URI;
    final static String SCOPE = Constants.SCOPE;

    final static String clientID = "41cd0066b82f47e69db868af15c4b370";
    final static String popularUrl = "https://api.instagram.com/v1/media/popular";
    final static String commentsUrl = "https://api.instagram.com/v1/media/{mediaId}/comments";

    final static String USERS_SEARCH_PATH = "users/search?q={query}";
    final static String TAGS_SEARCH_PATH = "tags/search?q={query}";
    final static String FEED_PATH = "users/self/feed";

    public InstagramClient(Context context) {
        super(context, REST_API_CLASS, REST_URL,
                REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL, SCOPE);
    }

    public void getHomeFeed(JsonHttpResponseHandler handler) {
        String apiUrl = getApiUrl(FEED_PATH);
        Log.i(TAG, "url: " + apiUrl);
        RequestParams params = new RequestParams();
        client.get(apiUrl, params, handler);
    }

    public void searchUsers(String query, JsonHttpResponseHandler handler) {
        String apiUrl = getApiUrl(USERS_SEARCH_PATH.replace("{query}", query));
        RequestParams params = new RequestParams();
        client.get(apiUrl, params, handler);
    }

    public void searchTags(String query, JsonHttpResponseHandler handler) {
        String apiUrl = getApiUrl(TAGS_SEARCH_PATH.replace("{query}", query));
        RequestParams params = new RequestParams();
        client.get(apiUrl, params, handler);
    }

    public void getPopularFeed(JsonHttpResponseHandler responseHandler) {
        AsyncHttpClient nonAuthClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("client_id", clientID);

        nonAuthClient.get(popularUrl, params, responseHandler);
    }

    public void getComments(String mediaId, JsonHttpResponseHandler responseHandler) {
        AsyncHttpClient nonAuthClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("client_id", clientID);

        nonAuthClient.get(commentsUrl.replace("{mediaId}", mediaId), params, responseHandler);
    }
}
