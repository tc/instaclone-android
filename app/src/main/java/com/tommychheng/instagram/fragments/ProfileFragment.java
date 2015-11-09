package com.tommychheng.instagram.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codepath.instagram.R;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.tommychheng.instagram.core.MainApplication;
import com.tommychheng.instagram.helpers.Utils;
import com.tommychheng.instagram.models.InstagramPost;
import com.tommychheng.instagram.models.InstagramSearchTag;
import com.tommychheng.instagram.models.InstagramUser;
import com.tommychheng.instagram.views.PostsAdapter;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tchheng on 11/5/15.
 */
public class ProfileFragment extends Fragment {
    final static String TAG = "ProfileFragment";

    SimpleDraweeView ivProfileViewProfileImage;
    TextView tvProfileViewPostCount;
    TextView tvProfileViewFollowerCount;
    TextView tvProfileViewFollowingCount;
    TextView tvProfileViewName;
    TextView tvProfileViewBio;
    TextView tvProfileViewLink;

    public static ProfileFragment newInstance(String userId) {
        Log.i(TAG, "ProfileFragment#newInstance " + userId);
        ProfileFragment frag = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString("userId", TextUtils.isEmpty(userId) ? "self" : userId);
        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ivProfileViewProfileImage = (SimpleDraweeView) view.findViewById(R.id.ivProfileViewProfileImage);
        tvProfileViewPostCount = (TextView) view.findViewById(R.id.tvProfileViewPostCount);
        tvProfileViewFollowerCount = (TextView) view.findViewById(R.id.tvProfileViewFollowerCount);
        tvProfileViewFollowingCount = (TextView) view.findViewById(R.id.tvProfileViewFollowingCount);
        tvProfileViewName = (TextView) view.findViewById(R.id.tvProfileViewName);
        tvProfileViewBio = (TextView) view.findViewById(R.id.tvProfileViewBio);
        tvProfileViewLink = (TextView) view.findViewById(R.id.tvProfileViewLink);

        setHasOptionsMenu(true);

        Bundle args = getArguments();
        String userId = "self";
        if (args != null) {
            userId = args.getString("userId", "self");
        }
        loadUser(userId);

        return view;
    }

    public void loadUser(String userId) {
        try {
            MainApplication.getRestClient().getUser(userId, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        InstagramUser user = Utils.decodeUserFromJsonResponse(response);
                        displayUser(user);
                    } catch (Exception e) {
                        Log.e(TAG, "ProfileFragment#onSuccess");
                        Log.e(TAG, e.toString());
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.e(TAG, "ProfileFragment#onFailure");
                    Log.e(TAG, responseString);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject response) {
                    Log.e(TAG, "ProfileFragment#onFailure");
                    Log.e(TAG, response.toString());
                }
            });

        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    public void displayUser(InstagramUser user) {
        Log.i(TAG, "ProfileFragment#displayUser " + user.userName);
        getActivity().setTitle(user.userName);

        tvProfileViewPostCount.setText(String.valueOf(user.mediaCount));
        tvProfileViewFollowerCount.setText(String.valueOf(user.followedByCount));
        tvProfileViewFollowingCount.setText(String.valueOf(user.followsCount));

        tvProfileViewName.setText(user.fullName);
        tvProfileViewBio.setText(user.bio);
        tvProfileViewLink.setText(user.website);

        Uri profileImageUri = Uri.parse(user.profilePictureUrl);
        ivProfileViewProfileImage.setImageURI(profileImageUri);

        GenericDraweeHierarchyBuilder builder =
                new GenericDraweeHierarchyBuilder(getContext().getResources());
        GenericDraweeHierarchy hierarchy = builder.build();
        hierarchy.setPlaceholderImage(R.drawable.gray_oval);
        ivProfileViewProfileImage.setHierarchy(hierarchy);
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(5f);
        roundingParams.setRoundAsCircle(true);
        ivProfileViewProfileImage.getHierarchy().setRoundingParams(roundingParams);
    }
}
