package com.tommychheng.instagram.activities;

import com.codepath.instagram.R;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.tommychheng.instagram.helpers.Utils;
import com.tommychheng.instagram.models.InstagramComment;
import com.tommychheng.instagram.models.InstagramPost;
import com.tommychheng.instagram.networking.InstagramClient;
import com.tommychheng.instagram.views.CommentsAdapter;
import com.tommychheng.instagram.views.PostsAdapter;

import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by tchheng on 10/29/15.
 */
public class CommentsActivity extends AppCompatActivity {
    private static final String TAG = "CommentsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        String mediaId = (String) getIntent().getStringExtra("mediaId");

        loadComments(mediaId);
    }

    private void loadComments(String mediaId) {
        try {
            InstagramClient.getComments(mediaId, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.e(TAG, response.toString());

                    final List<InstagramComment> comments = Utils.decodeCommentsFromJsonResponse(response);
                    Collections.reverse(comments);
                    setupComments(comments);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
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

    public void setupComments(List<InstagramComment> comments) {
        RecyclerView rv = (RecyclerView) findViewById(R.id.rvComments);
        CommentsAdapter adapter = new CommentsAdapter(comments);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new GridLayoutManager(this, 1));
    }
}