package com.jc.photogallery3;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by jc on 8/27/2016.
 */
public class PhotoGalleryFragment extends Fragment {

    private static final String TAG="PhotoGalleryFragment";
    GridView mGridView;
    ArrayList<GalleryItem> mItems;

    ThumbnailDownloader<ImageView> mThumbnailThread;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_photo_gallery,container,false);

        mGridView=(GridView)v.findViewById(R.id.gridView);

        setupAdapter();

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        new FetchItemTask().execute();

        mThumbnailThread=new ThumbnailDownloader<>(new Handler());
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
                convertView   =getActivity().getLayoutInflater()
                        .inflate(R.layout.gallery_item,parent,false);
            }

            ImageView imageView=(ImageView)convertView
                    .findViewById(R.id.gallery_item_imageView);

//            imageView.setImageResource(android.R.drawable.btn_default);

            GalleryItem item=getItem(position);
            mThumbnailThread.queueThumbnail(imageView,item.getImageUrl());
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
