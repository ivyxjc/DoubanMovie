package com.jc.doubanmovie_4.fragment.main_page;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jc.doubanmovie_4.adapter.RecyclerViewAdapter_Main;
import com.jc.doubanmovie_4.douban.DoubanFetchrMain;
import com.jc.doubanmovie_4.fragment.base.MainFragmentBase;
import com.jc.doubanmovie_4.model.MainItem;

import java.util.ArrayList;

public class MovieComingSoonFragment extends MainFragmentBase {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mItems=new ArrayList<>();
        mRecyclerViewAdapter = new RecyclerViewAdapter_Main(getActivity(), mItems, true);

        new FetchItemTask(0).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }

    @Override
    protected void loadMoreData(int start) {
        new FetchItemTask(start).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }

    private class FetchItemTask extends AsyncTask<Void, Void, ArrayList<MainItem>> {
        private int start = 0;

        public FetchItemTask(int s) {
            start = s;
        }

        @Override
        protected ArrayList<MainItem> doInBackground(Void... params) {
            if (start < mRecyclerViewAdapter.getStart()) {
                return null;
            }

            DoubanFetchrMain df = new DoubanFetchrMain(1, start);
            mRecyclerViewAdapter.setTotalCount(df.getTotalCount());

            return df.fetchItems_MoviesComingSoon();
        }

        @Override
        protected void onPostExecute(ArrayList<MainItem> galleryItems) {
            mItems = galleryItems;
            if (mItems == null) {
                return;
            }
            mRecyclerViewAdapter.addMoreData(mItems, start);
            Log.i("eeeee", "dataaaa size: " + mItems.size() + " start: " + start);
        }
    }
}
