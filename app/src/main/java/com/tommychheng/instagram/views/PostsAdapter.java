package com.tommychheng.instagram.views;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.format.DateUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.instagram.R;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tommychheng.instagram.helpers.Utils;
import com.tommychheng.instagram.models.InstagramPost;

import java.util.List;

/**
 * Created by tchheng on 10/27/15.
 */
public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsViewHolder> {
    List<InstagramPost> posts;
    Context context;

    public PostsAdapter(List<InstagramPost> posts) {
        this.posts = posts;
    }

    @Override
    public PostsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.activity_post, parent, false);

        PostsViewHolder viewHolder = new PostsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PostsViewHolder holder, int position) {
        InstagramPost post = posts.get(position);

        holder.tvUsername.setText(post.user.userName);
        holder.tvDate.setText(DateUtils.getRelativeTimeSpanString(post.createdTime * 1000, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS));

        if (post.likesCount > 0) {
            String likes = Utils.formatNumberForDisplay(post.likesCount) + " likes";
            holder.tvLikes.setText(likes);
        }

        if (post.caption != null) {
            holder.tvCaption.setText(formatCaption(post.user.userName, post.caption), TextView.BufferType.EDITABLE);
        } else {
            holder.tvCaption.setVisibility(View.GONE);
        }

        Uri profileImageUri = Uri.parse(post.user.profilePictureUrl);
        holder.ivProfileImage.setImageURI(profileImageUri);

        GenericDraweeHierarchyBuilder builder =
                new GenericDraweeHierarchyBuilder(context.getResources());
        GenericDraweeHierarchy hierarchy = builder.build();
        hierarchy.setPlaceholderImage(R.drawable.gray_oval);
        holder.ivProfileImage.setHierarchy(hierarchy);

        RoundingParams roundingParams = RoundingParams.fromCornersRadius(5f);
        roundingParams.setRoundAsCircle(true);
        holder.ivProfileImage.getHierarchy().setRoundingParams(roundingParams);

        Uri imageUri = Uri.parse(post.image.imageUrl);
        holder.ivImage.setImageURI(imageUri);
        GenericDraweeHierarchyBuilder builderForImage =
                new GenericDraweeHierarchyBuilder(context.getResources());
        GenericDraweeHierarchy hierarchyForImage = builderForImage.build();
        hierarchyForImage.setPlaceholderImage(R.drawable.gray_rectangle);
        holder.ivImage.setHierarchy(hierarchyForImage);
    }

    public SpannableStringBuilder formatCaption(String userName, String caption) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(userName + " - " + caption);

        ForegroundColorSpan colorSpan = new ForegroundColorSpan(context.getResources().getColor(R.color.blue_text));
        ssb.setSpan(
                colorSpan,
                0,
                userName.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return ssb;
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class PostsViewHolder extends RecyclerView.ViewHolder {
        public SimpleDraweeView ivProfileImage;
        public TextView tvUsername;
        public TextView tvDate;
        public TextView tvLikes;
        public TextView tvCaption;
        public SimpleDraweeView ivImage;

        public PostsViewHolder(View layoutView) {
            super(layoutView);
            tvUsername = (TextView) layoutView.findViewById(R.id.tvUsername);
            tvDate = (TextView) layoutView.findViewById(R.id.tvDate);
            tvLikes = (TextView) layoutView.findViewById(R.id.tvLikes);
            tvCaption = (TextView) layoutView.findViewById(R.id.tvCaption);
            ivProfileImage = (SimpleDraweeView) layoutView.findViewById(R.id.ivProfileImage);
            ivImage = (SimpleDraweeView) layoutView.findViewById(R.id.ivImage);
        }
    }

}