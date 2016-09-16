package com.jc.doubanmovie_4.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jc.doubanmovie_4.R;
import com.jc.doubanmovie_4.douban.FlickrFetchr;
import com.jc.doubanmovie_4.fragment.base.MainFragmentBase;
import com.jc.doubanmovie_4.model.MainItem;

import java.util.ArrayList;

public class USBoxFragment extends MainFragmentBase {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new FetchItemTask().execute();
    }

    private class FetchItemTask extends AsyncTask<Void, Void, ArrayList<MainItem>> {
        @Override
        protected ArrayList<MainItem> doInBackground(Void... params) {
            return new FlickrFetchr().fetchItems_MoviesUSBox();
        }

        @Override
        protected void onPostExecute(ArrayList<MainItem> galleryItems) {
            mItems = galleryItems;
            setupAdapter();
        }
    }

}