package com.jc.doubanmovie_4.activity;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;

import com.jc.doubanmovie_4.activity.base.SingleFragmentActivity;
import com.jc.doubanmovie_4.fragment.MovieDetailFragment;

/**
 * Created by jc on 9/5/2016.
 */
public class MovieDetailActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment(int i) {
        return new MovieDetailFragment();
    }

//    @Override
//    protected View.OnClickListener createOnClickListener() {
//        return new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        };
//    }
}
