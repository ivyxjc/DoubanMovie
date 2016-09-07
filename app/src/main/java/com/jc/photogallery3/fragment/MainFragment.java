package com.jc.photogallery3.fragment;

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
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jc.photogallery3.douban.FlickrFetchr;
import com.jc.photogallery3.R;
import com.jc.photogallery3.ThumbnailDownloader;
import com.jc.photogallery3.model.GalleryItem;

import java.util.ArrayList;



/**
 * Created by jc on 8/27/2016.
 */
public class MainFragment extends Fragment {

    private static final String TAG="MainFragment";
    GridView mGridView;
    ArrayList<GalleryItem> mItems;

    ThumbnailDownloader<ImageView> mThumbnailThread;

    LruCache<String,Bitmap> mBitmapCache;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_main,container,false);

        mGridView=(GridView)v.findViewById(R.id.gridView);

        setupAdapter();

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        new FetchItemTask().execute();




        // 获取到可用内存的最大值，使用内存超出这个值会引起OutOfMemory异常。
        // LruCache通过构造函数传入缓存值，以KB为单位。
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        // 使用最大可用内存值的1/8作为缓存的大小。
        int cacheSize = maxMemory / 2;
        Log.i(TAG,"max size: "+maxMemory);
        mBitmapCache=new LruCache<String, Bitmap>(cacheSize);



        mThumbnailThread=new ThumbnailDownloader<>(new Handler(),mBitmapCache);
        mThumbnailThread.setListener(new ThumbnailDownloader.Listener<ImageView>() {
            @Override
            public void onThumbnailDownloaded(ImageView imageView, Bitmap thumbnail) {
                if(isVisible()){
                    imageView.setImageBitmap(thumbnail);
                }
            }
        });
        mThumbnailThread.start();
        mThumbnailThread.getLooper();
        Log.i(TAG,"Background thread start");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mThumbnailThread.clearQueue();
        Log.i(TAG,"Background thread destroyed");
    }

    void setupAdapter(){
        if(getActivity()==null||mGridView==null)
            return;
        if(mItems!=null){

            mGridView.setAdapter(new GalleryItemAdapter(mItems));
        }else {
            mGridView.setAdapter(null);
        }
    }


    private class GalleryItemAdapter extends ArrayAdapter<GalleryItem>{

        public GalleryItemAdapter(ArrayList<GalleryItem> items){
            super(getActivity(),0,items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView =getActivity().getLayoutInflater()
                        .inflate(R.layout.item_movie_main,parent,false);
            }

            ImageView imageView=(ImageView)convertView
                    .findViewById(R.id.gallery_item_imageView);

            TextView textView=(TextView)convertView
                    .findViewById(R.id.gallery_item_text);

//            imageView.setImageResource(android.R.drawable.btn_default);

            GalleryItem item=getItem(position);
            mThumbnailThread.queueThumbnail(imageView,item.getImageUrl());
            textView.setText(item.getTitle());
            return convertView;
        }
    }



    private class FetchItemTask extends AsyncTask<Void,Void,ArrayList<GalleryItem>>{
        @Override
        protected ArrayList<GalleryItem> doInBackground(Void... params) {
            return new FlickrFetchr().fetchItems();
        }

        @Override
        protected void onPostExecute(ArrayList<GalleryItem> galleryItems) {
            mItems=galleryItems;
            setupAdapter();
        }
    }
}
