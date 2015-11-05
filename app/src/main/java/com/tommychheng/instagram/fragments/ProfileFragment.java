package com.tommychheng.instagram.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codepath.instagram.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tommychheng.instagram.models.InstagramPost;
import com.tommychheng.instagram.models.InstagramUser;
import com.tommychheng.instagram.views.PostsAdapter;

import java.util.ArrayList;

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

    String userId;

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
        //userId = getArguments().getString("userId");

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getActivity().setTitle("tommychheng");
    }

    public void loadUser(String userId) {

    }

    public void displayUser(InstagramUser user) {
        tvProfileViewPostCount.setText(String.valueOf(100));
        tvProfileViewFollowerCount.setText(String.valueOf(98));
        tvProfileViewFollowingCount.setText(String.valueOf(324));

        tvProfileViewName.setText("Tommy Chheng");
        tvProfileViewBio.setText("This is a bio");
        tvProfileViewLink.setText("http://tommy.chheng");

    }
}
