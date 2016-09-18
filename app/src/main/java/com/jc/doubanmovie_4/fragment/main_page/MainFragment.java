package com.jc.doubanmovie_4.fragment.main_page;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jc.doubanmovie_4.LogKeys;
import com.jc.doubanmovie_4.douban.DoubanFetchrMain;
import com.jc.doubanmovie_4.fragment.base.MainFragmentBase;
import com.jc.doubanmovie_4.model.MainItem;

import java.util.ArrayList;
import java.util.concurrent.Executor;


/**
 * Created by jc on 8/27/2016.
 */
public class MainFragment extends MainFragmentBase {

    /**
     * 要使FetchItemTask顺序执行,否则当下滑触发loadMoreData时,会出现多个线程,若这几个线程同时
     * 进行,会导致数据重复加载,这些Task顺序执行,只需要在doInBackground执行时
     * 检查star<mRecyclerViewAdapter.getStart()
     * 若true,则说明该线程为重复线程 return null
     * 否则, 执行之后的步骤
     */

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("eeeee","oncreate fetch");
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
            if(start<mRecyclerViewAdapter.getStart()){
                return null;
            }
            Log.i("eeeee","start: "+start);
            Log.i("eeeee","getStart: "+mRecyclerViewAdapter.getStart());
            DoubanFetchrMain df=new DoubanFetchrMain(0,start);
            Log.i("eeeee","df: "+df.getTotalCount()+"");
            mRecyclerViewAdapter.setTotalCount(df.getTotalCount());
            return df.fetchItems_SubjectInTheaters();
        }

        @Override
        protected void onPostExecute(ArrayList<MainItem> galleryItems){
            mItems = galleryItems;
            if(mItems==null){
                return;
            }
            mRecyclerViewAdapter.addMoreData(mItems,start);
            Log.i("eeeee","dataaaa size: "+mItems.size()+" start: "+start);
        }

    }


}