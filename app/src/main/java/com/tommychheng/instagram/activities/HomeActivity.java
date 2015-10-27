package com.tommychheng.instagram.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.instagram.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.tommychheng.instagram.helpers.Utils;
import com.tommychheng.instagram.models.InstagramPost;
import com.tommychheng.instagram.views.PostsAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fresco.initialize(this);

        setContentView(R.layout.activity_home);

        List<InstagramPost> posts = loadPosts();
        setupPostList(posts);
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

    public List<InstagramPost> loadPosts() {
        List<InstagramPost> posts = new ArrayList<InstagramPost>();
        try {
            JSONObject json = Utils.loadJsonFromAsset(this, "popular.json");
            posts = Utils.decodePostsFromJsonResponse(json);
        } catch (Exception e) {
            Log.e("HomeActivity", e.toString());
        }

        return posts;
    }

    public void setupPostList(List<InstagramPost> posts) {
        RecyclerView rvMovies = (RecyclerView) findViewById(R.id.rvPosts);
        PostsAdapter postsAdapter = new PostsAdapter(posts);
        rvMovies.setAdapter(postsAdapter);
        rvMovies.setLayoutManager(new GridLayoutManager(this, 1));
    }
}
