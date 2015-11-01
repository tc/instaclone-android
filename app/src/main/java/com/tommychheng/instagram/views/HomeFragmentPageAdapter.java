package com.tommychheng.instagram.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.codepath.instagram.R;
import com.tommychheng.instagram.helpers.SmartFragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tchheng on 10/31/15.
 */
public class HomeFragmentPageAdapter extends SmartFragmentStatePagerAdapter {
    private final Context mContext;
    private final List<Fragment> mFragments = new ArrayList<>();
    public static int[] tabIconId = {R.drawable.ic_home, R.drawable.ic_search, R.drawable.ic_capture, R.drawable.ic_notifs, R.drawable.ic_profile};

    public HomeFragmentPageAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    public void addFragment(Fragment fragment) {
        mFragments.add(fragment);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        Drawable image = mContext.getResources().getDrawable(HomeFragmentPageAdapter.tabIconId[position], null);
//        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
//        // Replace blank spaces with image icon
//        SpannableString sb = new SpannableString("   Hello");
//        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
//        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        return sb;
//    }
}