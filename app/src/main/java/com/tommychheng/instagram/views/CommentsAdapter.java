package com.tommychheng.instagram.views;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.format.DateUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codepath.instagram.R;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tommychheng.instagram.activities.CommentsActivity;
import com.tommychheng.instagram.helpers.Utils;
import com.tommychheng.instagram.models.InstagramComment;
import com.tommychheng.instagram.models.InstagramPost;

import java.util.List;


/**
 * Created by tchheng on 10/27/15.
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {
    List<InstagramComment> comments;
    Context context;

    public CommentsAdapter(List<InstagramComment> comments) {
        this.comments = comments;
    }

    @Override
    public CommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.layout_item_comment, parent, false);

        CommentsViewHolder viewHolder = new CommentsViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommentsViewHolder holder, int position) {
        final InstagramComment comment = comments.get(position);

        holder.tvDate.setText(DateUtils.getRelativeTimeSpanString(comment.createdTime * 1000, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS));
        holder.tvCaption.setText(formatCaption(comment.user.userName, comment.text));

        // profile image
        Uri profileImageUri = Uri.parse(comment.user.profilePictureUrl);
        holder.ivProfileImage.setImageURI(profileImageUri);

        GenericDraweeHierarchyBuilder builder =
                new GenericDraweeHierarchyBuilder(context.getResources());
        GenericDraweeHierarchy hierarchy = builder.build();
        hierarchy.setPlaceholderImage(R.drawable.gray_oval);
        holder.ivProfileImage.setHierarchy(hierarchy);
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(5f);
        roundingParams.setRoundAsCircle(true);
        holder.ivProfileImage.getHierarchy().setRoundingParams(roundingParams);
    }

    public SpannableStringBuilder formatCaption(String userName, String caption) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(userName + " " + caption);

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
        return comments.size();
    }

    public static class CommentsViewHolder extends RecyclerView.ViewHolder {
        public SimpleDraweeView ivProfileImage;
        public TextView tvDate;
        public TextView tvCaption;

        public CommentsViewHolder(View layoutView) {
            super(layoutView);
            tvDate = (TextView) layoutView.findViewById(R.id.tvCommentItemDate);
            tvCaption = (TextView) layoutView.findViewById(R.id.tvCommentItemComment);
            ivProfileImage = (SimpleDraweeView) layoutView.findViewById(R.id.ivCommentItemProfileImage);
        }
    }

}