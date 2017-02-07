package com.example.elisara.mymind;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Adapter for swiping and clicking between fragments
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    final int mNumOfTabs = 3;
    private String tabTitles[] = new String[]{"Following","Popular", "Explore"};

    public PagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getItemPosition(Object object){
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FollowingFragment followingFragment = new FollowingFragment();
                return followingFragment;
            case 1:
                PopularFragment popularFragment = new PopularFragment();
                return popularFragment;
            case 2:
                ExploreFragment exploreFragment = new ExploreFragment();
                return exploreFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
    public CharSequence getPageTitle(int position){
        return tabTitles[position];
    }

}
