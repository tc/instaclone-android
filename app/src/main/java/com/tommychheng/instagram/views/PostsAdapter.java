package com.tommychheng.instagram.views;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.instagram.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tommychheng.instagram.models.InstagramPost;

import java.util.List;

/**
 * Created by tchheng on 10/27/15.
 */
public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsViewHolder> {
    List<InstagramPost> posts;

    public PostsAdapter(List<InstagramPost> posts) {
        this.posts = posts;
    }

    @Override
    public PostsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.activity_post, parent, false);

        PostsViewHolder viewHolder = new PostsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PostsViewHolder holder, int position) {
        InstagramPost post = posts.get(position);

        holder.tvCaption.setText(post.caption);

        Uri imageUri = Uri.parse(post.image.imageUrl);
        holder.ivImage.setImageURI(imageUri);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class PostsViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCaption;
        public SimpleDraweeView ivImage;

        public PostsViewHolder(View layoutView) {
            super(layoutView);
            tvCaption = (TextView) layoutView.findViewById(R.id.tvCaption);
            ivImage = (SimpleDraweeView) layoutView.findViewById(R.id.ivImage);
        }
    }

}