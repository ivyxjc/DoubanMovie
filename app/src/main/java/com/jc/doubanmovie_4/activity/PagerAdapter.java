package com.jc.doubanmovie_4.activity;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;

import com.jc.doubanmovie_4.fragment.main_page.MainFragment;
import com.jc.doubanmovie_4.fragment.main_page.MovieComingSoonFragment;
import com.jc.doubanmovie_4.fragment.main_page.TopRatedFragment;
import com.jc.doubanmovie_4.fragment.main_page.USBoxFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;
    private FragmentTransaction ft;
    private Context mContext;
    private MainFragment tab1;
    private MovieComingSoonFragment tab2;
    private USBoxFragment tab3;
    private TopRatedFragment tab4;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {


        switch (position) {
            case 0:
                tab1 = new MainFragment();
                return tab1;
            case 1:
                tab2 = new MovieComingSoonFragment();
                return tab2;
            case 2:
                tab3 = new USBoxFragment();
                return tab3;
            case 3:
                tab4=new TopRatedFragment();
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