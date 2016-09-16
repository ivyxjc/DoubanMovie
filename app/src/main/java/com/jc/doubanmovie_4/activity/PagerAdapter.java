package com.jc.doubanmovie_4.activity;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.jc.doubanmovie_4.fragment.MainFragment;
import com.jc.doubanmovie_4.fragment.MovieComingSoonFragment;
import com.jc.doubanmovie_4.fragment.TopRatedFragment;
import com.jc.doubanmovie_4.fragment.USBoxFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                MainFragment tab1 = new MainFragment();
                return tab1;
            case 1:
                MovieComingSoonFragment tab2 = new MovieComingSoonFragment();
                return tab2;
            case 2:
                USBoxFragment tab3 = new USBoxFragment();
                return tab3;
            case 3:
                TopRatedFragment tab4=new TopRatedFragment();
                return tab4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}