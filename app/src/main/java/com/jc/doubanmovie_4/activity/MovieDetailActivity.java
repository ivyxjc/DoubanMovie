package com.jc.doubanmovie_4.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jc.doubanmovie_4.activity.base.SingleFragmentActivity;
import com.jc.doubanmovie_4.fragment.detail_page.MovieDetailFragment;

/**
 * Created by jc on 9/5/2016.
 */
public class MovieDetailActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(int i) {
        return new MovieDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
