package com.tommychheng.instagram.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.instagram.R;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.tommychheng.instagram.core.MainApplication;
import com.tommychheng.instagram.helpers.Utils;
import com.tommychheng.instagram.models.InstagramSearchTag;
import com.tommychheng.instagram.models.InstagramUser;
import com.tommychheng.instagram.networking.InstagramClient;
import com.tommychheng.instagram.views.TagSearchResultsAdapter;
import com.tommychheng.instagram.views.UserSearchResultsAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

/**
 * Created by tchheng on 10/31/15.
 */
public class SearchUsersResultFragment extends Fragment {
    final String TAG = "SearchUsersResult";

    RecyclerView mRv;
    String mQuery = "";

    public static SearchUsersResultFragment newInstance(String query) {
        SearchUsersResultFragment frag = new SearchUsersResultFragment();
        Bundle args = new Bundle();
        args.putString("query", query);
        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_search_user, container, false);

        mRv = (RecyclerView) view.findViewById(R.id.rvSearchUser);

        mQuery = getArguments().getString("query");
        loadUsers(mQuery);

        return view;
    }

    public void loadUsers(String query) {
        try {
            MainApplication.getRestClient().searchUsers(query, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.e(TAG, response.toString());

                    final List<InstagramUser> users = Utils.decodeUsersFromJsonResponse(response);
                    setupList(users);
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

    public void setupList(List<InstagramUser> users) {
        UserSearchResultsAdapter adapter = new UserSearchResultsAdapter(users);

        if (mRv != null) {
            mRv.setAdapter(adapter);
            mRv.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        } else {
            Log.i(TAG, "mRv is null");
        }
    }

    private List<InstagramUser> getTestSet() {
        final List<InstagramUser> users = new ArrayList<InstagramUser>();
        InstagramUser user = new InstagramUser();
        user.userName = "username";
        user.fullName = "Tom Bear";
        user.userId = "adfasdkfjk";
        user.profilePictureUrl = "https://igcdn-photos-g-a.akamaihd.net/hphotos-ak-xfa1/t51.2885-19/11349315_1620970341492406_1971976479_a.jpg";

        users.add(user);

        return users;
    }
}
