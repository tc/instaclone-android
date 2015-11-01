package com.tommychheng.instagram.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.codepath.instagram.R;
import com.tommychheng.instagram.fragments.PostsFragment;
import com.tommychheng.instagram.fragments.SearchFragment;
import com.tommychheng.instagram.views.HomeFragmentPageAdapter;


public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(HomeFragmentPageAdapter.tabIconId[i]);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        HomeFragmentPageAdapter adapter = new HomeFragmentPageAdapter(getSupportFragmentManager(), this);
        adapter.addFragment(new PostsFragment());
        adapter.addFragment(new SearchFragment());

        viewPager.setAdapter(adapter);
    }
}
