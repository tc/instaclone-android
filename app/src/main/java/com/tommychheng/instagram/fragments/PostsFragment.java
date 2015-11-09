package com.tommychheng.instagram.fragments;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
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
import com.tommychheng.instagram.models.InstagramPosts;
import com.tommychheng.instagram.networking.InstagramClient;
import com.tommychheng.instagram.persistence.InstagramClientDatabase;
import com.tommychheng.instagram.views.PostsAdapter;
import com.tommychheng.instagram.services.InstagramService;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

/**
 * Created by tchheng on 10/31/15.
 */
public class PostsFragment extends Fragment {
    final static String TAG = "PostsFragment";
    SwipeRefreshLayout swipeContainer = null;
    RecyclerView rv;
    PostsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");

        View view = inflater.inflate(R.layout.fragment_post, container, false);
        rv = (RecyclerView) view.findViewById(R.id.rvPosts);


        View.OnClickListener onUserSelectListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fts = PostsFragment.this.getFragmentManager().beginTransaction();

                String userId = v.getTag() != null ? v.getTag().toString() : "self";

                Log.i(TAG, "userOnClick " + userId);

                fts.replace(R.id.fragment_main_container, ProfileFragment.newInstance(userId))
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack("userSearchResult")
                        .commit();
            }
        };

        adapter = new PostsAdapter(new ArrayList<InstagramPost>(), onUserSelectListener);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 1));

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
                Log.i(TAG, "onRefresh");

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
            Log.i(TAG, "Sending intent for instagram service");
            Intent i = new Intent(getContext(), InstagramService.class);
            getContext().startService(i);
        } else {
            Log.i(TAG, "Loading instagram posts");
            List<InstagramPost> posts = InstagramClientDatabase.getInstance(getContext()).getAllInstagramPosts();
            setupPostList(posts);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Instagram");

        Log.i(TAG, "onResume, registering " + InstagramService.ACTION);
        IntentFilter in = new IntentFilter(InstagramService.ACTION);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(postsReceiver, in);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(postsReceiver);
    }

    private BroadcastReceiver postsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int resultCode = intent.getIntExtra(InstagramService.KEY_RESULT_CODE, Activity.RESULT_CANCELED);

            Log.i(TAG, "onReceive- received broadcast " + String.valueOf(resultCode));
            if (resultCode == Activity.RESULT_OK) {
                // Extract the json string from the bundle and save it to SharedPreferences.
                InstagramPosts resultValue = (InstagramPosts)intent.getSerializableExtra(InstagramService.KEY_RESULTS);

                setupPostList(resultValue.posts);
                if (swipeContainer != null) {
                    swipeContainer.setRefreshing(false);
                }
            }
        }
    };

    public void setupPostList(List<InstagramPost> posts) {
        adapter.addAll(posts);
    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
