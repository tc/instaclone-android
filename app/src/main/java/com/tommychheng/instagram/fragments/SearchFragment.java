package com.tommychheng.instagram.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.instagram.R;
import com.tommychheng.instagram.activities.HomeActivity;
import com.tommychheng.instagram.views.HomeFragmentPageAdapter;
import com.tommychheng.instagram.views.SearchPageAdapter;

/**
 * Created by tchheng on 10/31/15.
 */
public class SearchFragment extends Fragment {
    final static String TAG = "SearchFragment";

    View mView;
    MenuItem miActionProgressItem;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_search, container, false);

        setHasOptionsMenu(true);

        return mView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        miActionProgressItem = menu.findItem(R.id.miActionProgress);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i(TAG, query);

                ViewPager viewPager = (ViewPager) mView.findViewById(R.id.search_viewpager);
                if (viewPager != null) {
                    setupViewPager(viewPager, query);
                }
                TabLayout tabLayout = (TabLayout) mView.findViewById(R.id.search_tabs);
                tabLayout.setupWithViewPager(viewPager);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void showProgressBar() {
        // Show progress item
        if (miActionProgressItem != null) {
            miActionProgressItem.setVisible(true);
        }
    }

    public void hideProgressBar() {
        // Hide progress item
        if (miActionProgressItem != null) {
            miActionProgressItem.setVisible(false);
        }
    }

    private void setupViewPager(ViewPager viewPager, String query) {
        SearchPageAdapter adapter = new SearchPageAdapter(getChildFragmentManager(), getContext());
        Fragment tagsFragment = SearchTagsResultFragment.newInstance(query);
        Fragment usersFragment = SearchUsersResultFragment.newInstance(query);
        adapter.addFragment(tagsFragment, "Tags");
        adapter.addFragment(usersFragment, "Users");

        viewPager.setAdapter(adapter);
    }
}
