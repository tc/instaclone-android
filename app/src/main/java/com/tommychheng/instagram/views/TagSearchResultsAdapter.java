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
import com.tommychheng.instagram.helpers.Utils;
import com.tommychheng.instagram.models.InstagramPost;
import com.tommychheng.instagram.models.InstagramSearchTag;

import java.util.List;

/**
 * Created by tchheng on 10/31/15.
 */
public class TagSearchResultsAdapter extends RecyclerView.Adapter<TagSearchResultsAdapter.TagSearchResultsViewHolder> {
    List<InstagramSearchTag> tags;
    Context context;

    public TagSearchResultsAdapter(List<InstagramSearchTag> tags) {
        this.tags = tags;
    }

    public void clear() {
        tags.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<InstagramSearchTag> list) {
        tags.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public TagSearchResultsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.layout_item_search_tag, parent, false);

        TagSearchResultsViewHolder viewHolder = new TagSearchResultsViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TagSearchResultsViewHolder holder, int position) {
        final InstagramSearchTag tag = tags.get(position);
        holder.tag = tag;
        holder.tvSearchTag.setText(tag.tag);
        holder.tvSearchTagCount.setText(Utils.formatNumberForDisplay(tag.count) + " posts");
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    public static class TagSearchResultsViewHolder extends RecyclerView.ViewHolder {
        public InstagramSearchTag tag;
        public TextView tvSearchTag;
        public TextView tvSearchTagCount;

        public TagSearchResultsViewHolder(View layoutView) {
            super(layoutView);
            tvSearchTag = (TextView) layoutView.findViewById(R.id.tvSearchTag);
            tvSearchTagCount = (TextView) layoutView.findViewById(R.id.tvSearchTagCount);

            layoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO go to the tag's profile page
                    // context can be referenced via v.getContext()
                }
            });
        }
    }
}