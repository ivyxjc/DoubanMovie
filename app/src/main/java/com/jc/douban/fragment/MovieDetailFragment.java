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
import android.widget.ImageView;
import android.widget.TextView;

import com.jc.douban.LogKeys;
import com.jc.douban.R;
import com.jc.douban.ThumbnailDownloader;
import com.jc.douban.TransferKeys;
import com.jc.douban.douban.FlickrFetchr;
import com.jc.douban.model.GalleryItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by jc on 9/5/2016.
 */
public class MovieDetailFragment extends Fragment {
    private final String TAG="MovieDetailFragment";


    ImageView mMovieImageview;
    TextView mMovieTitle_tv;
    TextView mMovieCountry_tv;
    TextView mCasts_tv;
    TextView mDirectors_tv;

    private String mMovieTitle;
    private String mMovieId;

    private ThumbnailDownloader<ImageView> mThumbnailThread;

    private LruCache<String,Bitmap> mBitmapCache;

    private GalleryItem mItem;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getActivity().getIntent();
        mMovieTitle = (String) intent.getSerializableExtra(TransferKeys.MAIN_MOVIE_DETAIL_MOVIE_NAME);
        mMovieId = (String) intent.getSerializableExtra(TransferKeys.MAIN_MOVIE_DETAIL_ID);


        new FetchItemTask().execute();


        // 获取到可用内存的最大值，使用内存超出这个值会引起OutOfMemory异常。
        // LruCache通过构造函数传入缓存值，以KB为单位。
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        // 使用最大可用内存值的1/8作为缓存的大小。
        int cacheSize = maxMemory / 2;
        mBitmapCache=new LruCache<String, Bitmap>(cacheSize);

        mThumbnailThread=new ThumbnailDownloader<>(new Handler(),mBitmapCache);
        mThumbnailThread.setListener(new ThumbnailDownloader.Listener<ImageView>() {
            @Override
            public void onThumbnailDownloaded(ImageView imageView, Bitmap thumbnail) {
                imageView.setImageBitmap(thumbnail);
            }
        });

        mThumbnailThread.start();
        mThumbnailThread.getLooper();
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
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        initViews(view);

        mMovieTitle_tv.setText(mMovieTitle);


//        mThumbnailThread.queueThumbnail(mMovieImageview,mItem.getImageUrl());

        Log.i(LogKeys.MOVIE_DETAIL_FRAGMENT, "movie title is set" + mMovieId);


        return view;
    }

    private void initViews(View view) {
        mMovieTitle_tv = (TextView) view.findViewById(R.id.movie_title);
        mMovieCountry_tv=(TextView)view.findViewById(R.id.movie_country);
        mMovieImageview=(ImageView)view.findViewById(R.id.movie_pic_imageview);
        mMovieImageview=(ImageView)view.findViewById(R.id.movie_pic_imageview);
    }

    private class FetchItemTask extends AsyncTask<Void,Void,GalleryItem> {
        @Override
        protected GalleryItem doInBackground(Void... params) {
            return new FlickrFetchr().fetchItems_MovieDetailInfo(mMovieId);
        }

        @Override
        protected void onPostExecute(GalleryItem galleryItems) {
            mItem=galleryItems;
            mMovieCountry_tv.setText(mItem.getCountries().toString());
            mThumbnailThread.queueThumbnail(mMovieImageview,mItem.getImageUrl());
        }
    }

}
