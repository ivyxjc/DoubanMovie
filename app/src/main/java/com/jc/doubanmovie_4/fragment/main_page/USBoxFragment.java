package com.jc.doubanmovie_4.fragment.main_page;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jc.doubanmovie_4.douban.DoubanFetchrMain;
import com.jc.doubanmovie_4.fragment.base.MainFragmentBase;
import com.jc.doubanmovie_4.model.MainItem;

import java.util.ArrayList;

public class USBoxFragment extends MainFragmentBase {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new FetchItemTask(0).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }

    @Override
    protected void loadMoreData(int start) {
        new FetchItemTask(start).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }

    private class FetchItemTask extends AsyncTask<Void, Void, ArrayList<MainItem>> {
        private int start=0;

        public FetchItemTask(int s){
            start=s;
        }
        @Override
        protected ArrayList<MainItem> doInBackground(Void... params) {
            if(start<mRecyclerViewAdapter.getStart())
                return null;

            DoubanFetchrMain df=new DoubanFetchrMain(2,start);
            mRecyclerViewAdapter.setTotalCount(df.getTotalCount());
            return df.fetchItems_MoviesUSBox();
        }

        @Override
        protected void onPostExecute(ArrayList<MainItem> galleryItems){
            mItems = galleryItems;
            if(mItems==null){
                return;
            }
            mRecyclerViewAdapter.addMoreData(mItems,start);
        }

    }
}