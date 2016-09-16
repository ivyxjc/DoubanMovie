package com.jc.doubanmovie_4.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jc.doubanmovie_4.LogKeys;
import com.jc.doubanmovie_4.TransferKeys;
import com.jc.doubanmovie_4.douban.FlickrFetchr;
import com.jc.doubanmovie_4.R;
import com.jc.doubanmovie_4.fragment.base.MainFragmentBase;
import com.jc.doubanmovie_4.model.MainItem;

import java.util.ArrayList;



/**
 * Created by jc on 8/27/2016.
 */
public class MainFragment extends MainFragmentBase {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new FetchItemTask().execute();
    }

    private class FetchItemTask extends AsyncTask<Void, Void, ArrayList<MainItem>> {
        @Override
        protected ArrayList<MainItem> doInBackground(Void... params) {
            return new FlickrFetchr().fetchItems_SubjectInTheaters();
        }

        @Override
        protected void onPostExecute(ArrayList<MainItem> galleryItems) {
            mItems = galleryItems;
            setupAdapter();
        }
    }


}