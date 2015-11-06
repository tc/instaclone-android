package com.tommychheng.instagram.views;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.instagram.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tommychheng.instagram.fragments.ProfileFragment;
import com.tommychheng.instagram.models.InstagramSearchTag;
import com.tommychheng.instagram.models.InstagramUser;

import java.util.List;

/**
 * Created by tchheng on 10/31/15.
 */
public class UserSearchResultsAdapter extends RecyclerView.Adapter<UserSearchResultsAdapter.SearchResultsViewHolder> {
    final String TAG = "UserResultsAdapter";
    List<InstagramUser> users;
    Context context;
    View.OnClickListener onUserClickListener;

    public UserSearchResultsAdapter(List<InstagramUser> users, View.OnClickListener listener) {
        this.onUserClickListener = listener;
        this.users = users;
    }

    public UserSearchResultsAdapter(List<InstagramUser> users) {
        this.users = users;
    }

    public void clear() {
        users.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<InstagramUser> list) {
        users.addAll(list);
        notifyDataSetChanged();
    }

    public void setOnUserClickListener(View.OnClickListener v) {
        onUserClickListener = v;
    }

    @Override
    public SearchResultsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.layout_item_search_user, parent, false);

        SearchResultsViewHolder viewHolder = new SearchResultsViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SearchResultsViewHolder holder, int position) {
        final InstagramUser user = users.get(position);
        holder.user = user;
        holder.tvUsername.setText(user.userName);
        holder.tvFullname.setText(user.fullName);
        Uri profileImageUri = Uri.parse(user.profilePictureUrl);
        holder.ivProfileImage.setImageURI(profileImageUri);

        if (onUserClickListener != null) {
            Log.i(TAG, "onBindViewHolder#onUserClickListener set");
            holder.containerView.setOnClickListener(onUserClickListener);
        }

        if (user != null) {
            Log.i(TAG, "onBindViewHolder#setTag");
            holder.containerView.setTag(user.userId);
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class SearchResultsViewHolder extends RecyclerView.ViewHolder {
        public View containerView;
        public InstagramUser user;
        public TextView tvUsername;
        public TextView tvFullname;
        public ImageView ivProfileImage;

        public SearchResultsViewHolder(View layoutView) {
            super(layoutView);
            tvUsername = (TextView) layoutView.findViewById(R.id.tvSearchUserName);
            tvFullname = (TextView) layoutView.findViewById(R.id.tvSearchUserFullname);
            ivProfileImage = (SimpleDraweeView) layoutView.findViewById(R.id.ivSearchUserProfileImage);

            containerView = layoutView;
        }

    }
}