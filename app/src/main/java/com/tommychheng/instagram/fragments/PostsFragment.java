package com.tommychheng.instagram.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.instagram.R;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.tommychheng.instagram.activities.HomeActivity;
import com.tommychheng.instagram.core.MainApplication;
import com.tommychheng.instagram.helpers.Utils;
import com.tommychheng.instagram.models.InstagramPost;
import com.tommychheng.instagram.networking.InstagramClient;
import com.tommychheng.instagram.persistence.InstagramClientDatabase;
import com.tommychheng.instagram.views.PostsAdapter;

import org.json.JSONObject;

import java.util.List;

import org.apache.http.Header;

/**
 * Created by tchheng on 10/31/15.
 */
public class PostsFragment extends Fragment {
    final static String TAG = "PostsFragment";
    SwipeRefreshLayout swipeContainer = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        setHasOptionsMenu(true);

        setupSwipeRefresh(view);

        loadPosts();

        return view;
    }

    public void setupSwipeRefresh(View view) {
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadPosts();
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    public void loadPosts() {
        if (isNetworkAvailable()) {
            InstagramClientDatabase.getInstance(getActivity()).getAllInstagramPosts();
        } else {
            try {
                MainApplication.getRestClient().getHomeFeed(new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        final List<InstagramPost> posts = Utils.decodePostsFromJsonResponse(response);
                        setupPostList(posts);

                        InstagramClientDatabase.getInstance(getActivity()).emptyAllTables();
                        InstagramClientDatabase.getInstance(getActivity()).addInstagramPosts(posts);

                        if (swipeContainer != null) {
                            swipeContainer.setRefreshing(false);
                        }
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
    }

    public void setupPostList(List<InstagramPost> posts) {
        RecyclerView rv = (RecyclerView) getView().findViewById(R.id.rvPosts);
        PostsAdapter adapter = new PostsAdapter(posts);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new GridLayoutManager(getActivity(), 1));
    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
