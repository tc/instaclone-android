package com.tommychheng.instagram.views;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.codepath.instagram.R;
import com.tommychheng.instagram.fragments.SearchResultFragment;
import com.tommychheng.instagram.fragments.SearchTagsResultFragment;
import com.tommychheng.instagram.fragments.SearchUsersResultFragment;
import com.tommychheng.instagram.helpers.SmartFragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tchheng on 10/31/15.
 */
public class SearchPageAdapter extends SmartFragmentStatePagerAdapter {
    private final Context mContext;
    String mQuery = "";

    public SearchPageAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    public void setQuery(String query) {
        mQuery = query;

        for (int i = 0; i < getCount(); i++) {
            SearchResultFragment frag = (SearchResultFragment) getRegisteredFragment(i);
            if (frag != null) {
                frag.search(mQuery);
            }
        }
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return SearchTagsResultFragment.newInstance(mQuery);
            case 1:
                return SearchUsersResultFragment.newInstance(mQuery);
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Tags";
            case 1:
                return "Users";
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
