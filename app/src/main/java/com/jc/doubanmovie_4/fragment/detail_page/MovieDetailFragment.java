package com.jc.doubanmovie_4.fragment.detail_page;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jc.doubanmovie_4.LogKeys;
import com.jc.doubanmovie_4.R;
import com.jc.doubanmovie_4.TransferKeys;
import com.jc.doubanmovie_4.douban.DoubanFetchrMain;
import com.jc.doubanmovie_4.douban.DoubanFetchrMovieDetail;
import com.jc.doubanmovie_4.model.MainItem;
import com.jc.doubanmovie_4.utils.ToString;

/**
 * Created by jc on 9/13/2016.
 */
public class MovieDetailFragment extends Fragment {
    private final String TAG="MovieDetailFragment";


    ImageView mMovieImageview;
    TextView mMovieTitle_tv;
    TextView mMovieCountry_tv;
    TextView mCasts_tv;
    TextView mDirectors_tv;
    TextView mWriters_tv;
    TextView mMovieGenres_tv;
    TextView mMovieSummary_tv;

    private String mMovieTitle;
    private String mMovieId;

    private LruCache<String,Bitmap> mBitmapCache;

    private MainItem mItem;

    private RequestManager mRequestManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mRequestManager=Glide.with(getActivity());

        Intent intent = getActivity().getIntent();
        mMovieTitle = (String) intent.getSerializableExtra(TransferKeys.MAIN_MOVIE_DETAIL_MOVIE_NAME);
        mMovieId = (String) intent.getSerializableExtra(TransferKeys.MAIN_MOVIE_DETAIL_ID);
        new FetchItemTask().execute();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"Background thread destroyed");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        initViews(view);

        mMovieTitle_tv.setText(mMovieTitle);


        Log.i(LogKeys.MOVIE_DETAIL_FRAGMENT, "movie title is set" + mMovieId);


        return view;
    }

    private void initViews(View view) {
        mMovieTitle_tv = (TextView) view.findViewById(R.id.movie_detail_title);
        mMovieCountry_tv=(TextView)view.findViewById(R.id.movie_detail_country);
        mMovieImageview=(ImageView)view.findViewById(R.id.movie_detail_imageview);
        mCasts_tv=(TextView)view.findViewById(R.id.movie_detail_casts);
        mDirectors_tv=(TextView)view.findViewById(R.id.movie_detail_directors);
//        mWriters_tv=(TextView)view.findViewById(R.id.movie_detail_writers);
        mMovieGenres_tv=(TextView)view.findViewById(R.id.movie_detail_genres);
        mMovieSummary_tv=(TextView)view.findViewById(R.id.movie_detail_summary);
    }

    private class FetchItemTask extends AsyncTask<Void,Void,MainItem> {
        @Override
        protected MainItem doInBackground(Void... params) {
            DoubanFetchrMovieDetail df=new DoubanFetchrMovieDetail();
            return df.fetchItems_MovieDetailInfo(mMovieId);
        }

        @Override
        protected void onPostExecute(MainItem galleryItems) {
            mItem=galleryItems;

            mMovieCountry_tv.setText(ToString.arrayToString(mItem.getCountries()));
            mMovieGenres_tv.setText(ToString.arrayToString(mItem.getGenres()));

            mCasts_tv.setText(ToString.mapToString(mItem.getCasts()));
            mDirectors_tv.setText(ToString.mapToString(mItem.getDirectors()));
            mMovieSummary_tv.setText(mItem.getSummary());


            mRequestManager
                    .load(mItem.getImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(mMovieImageview);





        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        mRequestManager.onDestroy();
    }
}

