package com.tommychheng.instagram.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.codepath.instagram.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.tommychheng.instagram.helpers.Utils;
import com.tommychheng.instagram.models.InstagramPost;
import com.tommychheng.instagram.networking.InstagramClient;
import com.tommychheng.instagram.views.PostsAdapter;

import cz.msebera.android.httpclient.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_home);

        loadPosts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadPosts() {
        try {
//            JSONObject json = Utils.loadJsonFromAsset(this, "popular.json");
//            List<InstagramPost> posts = Utils.decodePostsFromJsonResponse(json);
//            setupPostList(posts);

            InstagramClient.getPopularFeed( new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.e(TAG, response.toString());

                    final List<InstagramPost> posts = Utils.decodePostsFromJsonResponse(response);
                    setupPostList(posts);
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

    public void setupPostList(List<InstagramPost> posts) {
        RecyclerView rv = (RecyclerView) findViewById(R.id.rvPosts);
        PostsAdapter adapter = new PostsAdapter(posts);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new GridLayoutManager(this, 1));
    }

}
