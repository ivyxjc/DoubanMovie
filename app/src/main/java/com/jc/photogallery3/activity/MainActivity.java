package com.jc.photogallery3.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;

import com.jc.photogallery3.activity.base.SingleFragmentActivity;
import com.jc.photogallery3.fragment.MainFragment;

public class MainActivity extends SingleFragmentActivity {
    private Context mContext=this;

    @Override
    protected Fragment createFragment() {
        return new MainFragment();
    }

    @Override
    protected View.OnClickListener createOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,MovieDetailActivity.class);
                startActivity(intent);
            }
        };
    }
}





