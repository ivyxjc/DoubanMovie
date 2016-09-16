package com.jc.doubanmovie_4.fragment.base;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jc.doubanmovie_4.LogKeys;
import com.jc.doubanmovie_4.TransferKeys;
import com.jc.doubanmovie_4.activity.MovieDetailActivity;
import com.jc.doubanmovie_4.R;
//import com.jc.doubanmovie_4.ThumbnailDownloader;
import com.jc.doubanmovie_4.adapter.RecyclerViewAdapter_Main;
import com.jc.doubanmovie_4.model.MainItem;

import java.util.ArrayList;



/**
 * Created by jc on 8/27/2016.
 */
public abstract class MainFragmentBase extends Fragment {

    private static final String TAG = "MainFragment";
    private RecyclerView mRecyclerView;


    protected ArrayList<MainItem> mItems;

    protected RecyclerViewAdapter_Main mRecyclerViewAdapter;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItems=new ArrayList<>();
        setRetainInstance(true);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "Background thread destroyed");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.main_rv);

        //类似ListView显示用
      //mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //类似GridView显示
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        mRecyclerViewAdapter=new RecyclerViewAdapter_Main(getActivity(),mItems,false);
        mRecyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter_Main.OnItemClickListener() {
            @Override
            public void onItemClick(String id,String title) {
                Log.i(LogKeys.MAIN_FRAGMENT, "item is clicked ");
                Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                intent.putExtra(TransferKeys.MAIN_MOVIE_DETAIL_ID, id);
                intent.putExtra(TransferKeys.MAIN_MOVIE_DETAIL_MOVIE_NAME, title);
                startActivity(intent);
                Log.i(LogKeys.MAIN_FRAGMENT, "startactivity");
            }
        });
        return v;
    }



    protected void setupAdapter() {
        if (getActivity() == null || mRecyclerView == null)
            return;
        if (mItems != null) {
            mRecyclerView.setAdapter(new RecyclerViewAdapter_Main(getActivity(),mItems,true));
        } else {
            mRecyclerView.setAdapter(null);
        }
    }


    private class GalleryItemAdapter extends ArrayAdapter<MainItem> {

        public GalleryItemAdapter(ArrayList<MainItem> items) {
            super(getActivity(), 0, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.item_movie_main, parent, false);
            }

            ImageView imageView = (ImageView) convertView
                    .findViewById(R.id.gallery_item_imageView);

            TextView textView = (TextView) convertView
                    .findViewById(R.id.gallery_item_text);

            MainItem item = getItem(position);
            Glide
                    .with(getActivity())

                    .load(item.getImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(imageView);

            textView.setText(item.getTitle());
            return convertView;
        }
    }


    private abstract class FetchItemTask extends AsyncTask<Void, Void, ArrayList<MainItem>> {
        @Override
        protected abstract ArrayList<MainItem> doInBackground(Void... params);

        @Override
        protected abstract void onPostExecute(ArrayList<MainItem> galleryItems);
    }



}