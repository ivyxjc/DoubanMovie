package com.jc.douban.fragment;

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

import com.jc.douban.LogKeys;
import com.jc.douban.TransferKeys;
import com.jc.douban.activity.MovieDetailActivity;
import com.jc.douban.douban.FlickrFetchr;
import com.jc.douban.R;
import com.jc.douban.ThumbnailDownloader;
import com.jc.douban.model.GalleryItem;

import java.util.ArrayList;



/**
 * Created by jc on 8/27/2016.
 */
public class MainFragment extends Fragment {

    private static final String TAG="MainFragment";
    private GridView mGridView;
    private ArrayList<GalleryItem> mItems;

    private ThumbnailDownloader<ImageView> mThumbnailThread;

    private LruCache<String,Bitmap> mBitmapCache;


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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_main,container,false);

        mGridView=(GridView)v.findViewById(R.id.gridView);

//        mGridView.setOnItemClickListener(this);
        setUpOnClickListener();

        return v;
    }


    /**
     * 设置相关的监听, 包括 mGridView,
     */
    private void setUpOnClickListener(){
        //设置各项的监听
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(LogKeys.MAIN_FRAGMENT,"item is clicked ");
                GalleryItem item=(GalleryItem) parent.getItemAtPosition(position);
                Intent intent=new Intent(getActivity(), MovieDetailActivity.class);
                intent.putExtra(TransferKeys.MAIN_MOVIE_DETAIL_ID,item.getMovieId());
                intent.putExtra(TransferKeys.MAIN_MOVIE_DETAIL_MOVIE_NAME,item.getTitle());
                startActivity(intent);
                Log.i(LogKeys.MAIN_FRAGMENT,"startactivity");
            }
        });
    }

    private void setupAdapter(){
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
            return new FlickrFetchr().fetchItems_SubjectInTheaters();
        }

        @Override
        protected void onPostExecute(ArrayList<GalleryItem> galleryItems) {
            mItems=galleryItems;
            setupAdapter();
        }
    }
}
