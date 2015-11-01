package com.tommychheng.instagram.views;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.codepath.instagram.R;
import com.tommychheng.instagram.helpers.SmartFragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tchheng on 10/31/15.
 */
public class SearchPageAdapter extends FragmentPagerAdapter {
    private final Context mContext;
    private final List<Fragment> mFragments = new ArrayList<>();
    private final List<String> mFragmentTitles = new ArrayList<>();

    public SearchPageAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    public void addFragment(Fragment fragment, String title) {
        mFragments.add(fragment);
        mFragmentTitles.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitles.get(position);
    }
}
