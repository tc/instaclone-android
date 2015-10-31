package com.tommychheng.instagram.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.instagram.R;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.tommychheng.instagram.activities.HomeActivity;
import com.tommychheng.instagram.helpers.Utils;
import com.tommychheng.instagram.models.InstagramPost;
import com.tommychheng.instagram.networking.InstagramClient;
import com.tommychheng.instagram.views.PostsAdapter;

import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by tchheng on 10/31/15.
 */
public class PostsFragment extends Fragment {
    final static String TAG = "PostsFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        setHasOptionsMenu(true);

        loadPosts();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        ((HomeActivity)getActivity()).setToolbarTitle("Instaclone");
    }

    public void loadPosts() {
        try {
            InstagramClient.getPopularFeed(new JsonHttpResponseHandler() {
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
        RecyclerView rv = (RecyclerView) getView().findViewById(R.id.rvPosts);
        PostsAdapter adapter = new PostsAdapter(posts);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new GridLayoutManager(getActivity(), 1));
    }
}
