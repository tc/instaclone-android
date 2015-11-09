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
import com.tommychheng.instagram.networking.InstagramClient;
import com.tommychheng.instagram.views.TagSearchResultsAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

/**
 * Created by tchheng on 10/31/15.
 */
public class SearchTagsResultFragment extends Fragment implements SearchResultFragment {
    final String TAG = "SearchTagsResult";
    String mQuery = "";
    RecyclerView mRv;
    TagSearchResultsAdapter adapter;

    public static SearchTagsResultFragment newInstance(String query) {
        SearchTagsResultFragment frag = new SearchTagsResultFragment();
        Bundle args = new Bundle();
        args.putString("query", query);
        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_tag, container, false);

        mRv = (RecyclerView) view.findViewById(R.id.rvSearchTag);
        adapter = new TagSearchResultsAdapter(new ArrayList<InstagramSearchTag>());
        mRv.setAdapter(adapter);
        mRv.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        mQuery = getArguments().getString("query");
        search(mQuery);

        return view;
    }

    public void search(String query) {
        try {
            MainApplication.getRestClient().searchTags(query, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.e(TAG, response.toString());

                    final List<InstagramSearchTag> tags = Utils.decodeSearchTagsFromJsonResponse(response);
                    setupList(tags);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.e(TAG, responseString);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject response) {
                    Log.e(TAG, String.valueOf(statusCode));
                }
            });

        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    public void setupList(List<InstagramSearchTag> tags) {
        adapter.clear();
        adapter.addAll(tags);
    }

    private List<InstagramSearchTag> getTestTags() {
        final List<InstagramSearchTag> tags = new ArrayList<InstagramSearchTag>();
        InstagramSearchTag tag = new InstagramSearchTag();
        tag.count = 45;
        tag.tag = "test";
        tags.add(tag);
        return tags;
    }
}
