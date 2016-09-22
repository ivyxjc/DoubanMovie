package com.jc.doubanmovie_4.fragment.base;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jc.doubanmovie_4.LogKeys;
import com.jc.doubanmovie_4.TransferKeys;
import com.jc.doubanmovie_4.activity.MovieDetailActivity;
import com.jc.doubanmovie_4.R;
//import com.jc.doubanmovie_4.ThumbnailDownloader;
import com.jc.doubanmovie_4.adapter.RecyclerViewAdapter_Main;
import com.jc.doubanmovie_4.model.MainItem;
import com.jc.doubanmovie_4.utils.StringTo;

import java.util.ArrayList;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;


/**
 * Created by jc on 8/27/2016.
 */
public abstract class MainFragmentBase extends Fragment {

    private int currentPage;

    private static final String TAG = "MainFragmentBase";
    protected RecyclerView mRecyclerView;


    protected ArrayList<MainItem> mItems;

    protected RecyclerViewAdapter_Main mRecyclerViewAdapter;

    protected void setupAdapter() {
        Log.i(LogKeys.MAIN_FRAGMENT, "new RecyclerViewApater");
        mRecyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter_Main.OnItemClickListenerCustom() {
            @Override
            public void onItemClick(String id, String title, String imgUrl) {
                Log.i(LogKeys.MAIN_FRAGMENT, "item is clicked ");
                Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                intent.putExtra(TransferKeys.MAIN_MOVIE_DETAIL_ID, id);
                intent.putExtra(TransferKeys.MAIN_MOVIE_DETAIL_MOVIE_NAME, title);
                intent.putExtra(TransferKeys.MAIN_MOVIE_DETAIL_MOVIE_IMGURL,imgUrl);
                startActivity(intent);
                Log.i(LogKeys.MAIN_FRAGMENT, "startactivity");
            }
        });

        if (getActivity() == null || mRecyclerView == null) {
            Log.i(LogKeys.MAIN_FRAGMENT, "getActivity==null or mRcyclerView==null");
            return;
        }
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        Log.i(LogKeys.MAIN_FRAGMENT, "mRcyclerView set up adapter");


            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
            {
                int lastVisibleItem;
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView,
                                                 int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (newState == SCROLL_STATE_IDLE
                            && lastVisibleItem + 1 >= mRecyclerViewAdapter.getItemCount()
                            && mRecyclerViewAdapter.getItemCount() < mRecyclerViewAdapter.getTotalDataCount()) {
//                        Log.i("cccc","last: "+lastVisibleItem);
//                        Log.i("cccc","adapterSize:"+mRecyclerViewAdapter.getItemCount());
//                        Log.i("cccc","total: "+mRecyclerViewAdapter.getTotalDataCount());
//                        Toast.makeText(getActivity(),"onScrollStateChanged",Toast.LENGTH_SHORT).show();
                        loadMoreData(mRecyclerViewAdapter.getStart());

                    }else if (newState == SCROLL_STATE_IDLE){
//                        Log.i("cccc","last: "+lastVisibleItem);
//                        Log.i("cccc","adapterSize: "+mRecyclerViewAdapter.getItemCount());
//                        Log.i("cccc","total: "+mRecyclerViewAdapter.getTotalDataCount());
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                    lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                }
            });

    }

    protected abstract void loadMoreData(int start);

    private abstract class FetchItemTask extends AsyncTask<Void, Void, ArrayList<MainItem>> {
        @Override
        protected abstract ArrayList<MainItem> doInBackground(Void... params);

        @Override
        protected abstract void onPostExecute(ArrayList<MainItem> galleryItems);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItems=new ArrayList<>();
        mRecyclerViewAdapter = new RecyclerViewAdapter_Main(getActivity(), mItems, false);

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

        setupAdapter();
        return v;
    }






}