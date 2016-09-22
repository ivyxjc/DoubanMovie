package com.jc.doubanmovie_4.fragment.detail_page;

import android.content.Intent;
import android.gesture.GestureLibraries;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jc.doubanmovie_4.LogKeys;
import com.jc.doubanmovie_4.R;
import com.jc.doubanmovie_4.TransferKeys;
import com.jc.doubanmovie_4.activity.WebActivity;
import com.jc.doubanmovie_4.adapter.RecyclerViewAdapter_Detail;
import com.jc.doubanmovie_4.douban.DoubanFetchrMovieDetail;
import com.jc.doubanmovie_4.model.DetailCast;
import com.jc.doubanmovie_4.model.MainItem;
import com.jc.doubanmovie_4.utils.ImageUtil;
import com.jc.doubanmovie_4.utils.ToString;

import java.util.ArrayList;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jc on 9/13/2016.
 */
public class MovieDetailFragment extends Fragment {
    private final String TAG = "MovieDetailFragment";
    @Bind(R.id.detail_header_subject_iv)
    ImageView mDetailHeaderSubjectIv;

    @Bind(R.id.detail_toolbar_subject)
    Toolbar mDetailToolbarSubject;
    @Bind(R.id.detail_toolbar_container_subject)
    CollapsingToolbarLayout mDetailToolbarContainerSubject;
    @Bind(R.id.detail_header_container_subject)
    AppBarLayout mDetailHeaderContainerSubject;
    @Bind(R.id.detail_subject_title_tv)
    TextView mDetailSubjectTitleTv;
    @Bind(R.id.detail_subject_rating_rb)
    RatingBar mDetailSubjectRatingRb;
    @Bind(R.id.detail_subject_rating_tv)
    TextView mDetailSubjectRatingTv;
    @Bind(R.id.detail_subject_comment_num_tv)
    TextView mDetailSubjectCommentNumTv;
    @Bind(R.id.detail_subject_rating_layout)
    LinearLayout mDetailSubjectRatingLayout;
    @Bind(R.id.detail_subject_genres_tv)
    TextView mDetailSubjectGenresTv;
    @Bind(R.id.detail_subject_countries_tv)
    TextView mDetailSubjectCountriesTv;
    @Bind(R.id.detail_subject_summary_tv)
    TextView mDetailSubjectSummaryTv;
    @Bind(R.id.detail_summary_tip_tv)
    TextView mDetailSummaryTipTv;
    @Bind(R.id.detail_subject_actor_tip_tv)
    TextView mDetailSubjectActorTipTv;
    @Bind(R.id.detail_subject_actor_rv)
    RecyclerView mDetailSubjectActorRv;
    @Bind(R.id.detail_subject_recommend_tip_tv)
    TextView mDetailSubjectRecommendTipTv;
    @Bind(R.id.detail_subject_recommend_rv)
    RecyclerView mDetailSubjectRecommendRv;
    @Bind(R.id.detail_subject_container_linearlayout)
    LinearLayout mDetailSubjectContainerLinearLayout;
    @Bind(R.id.detail_nested_scroll_view)
    NestedScrollView mDetailNestedScrollView;
    @Bind(R.id.detail_container_cl)
    CoordinatorLayout mDetailContainerCl;
    @Bind(R.id.refresh_subject)
    SwipeRefreshLayout mRefreshSubject;
    @Bind(R.id.detail_web)
    FloatingActionButton mDetailWeb;


    private String mMovieTitle;
    private String mMovieId;

    private LruCache<String, Bitmap> mBitmapCache;

    private MainItem mItem;

    private RequestManager mRequestManager;

    private RecyclerViewAdapter_Detail adapter_detail;
    private ArrayList<DetailCast> mDatas;
    private FetchItemTask mFetchItemTask;
    protected boolean isScrolling;

    private float titleDy = Float.MAX_VALUE;

    private String imgUrl;
    private String title;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mRequestManager = Glide.with(getActivity());

//        mDetailToolbarContainerSubject.setTitle("");
//            setSupportActionBar(mToolbar);
//            ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getActivity().getIntent();
        imgUrl=intent.getStringExtra(TransferKeys.MAIN_MOVIE_DETAIL_MOVIE_IMGURL);
        mMovieTitle=intent.getStringExtra(TransferKeys.MAIN_MOVIE_DETAIL_MOVIE_NAME);
        mMovieTitle = (String) intent.getSerializableExtra(TransferKeys.MAIN_MOVIE_DETAIL_MOVIE_NAME);
        mMovieId = (String) intent.getSerializableExtra(TransferKeys.MAIN_MOVIE_DETAIL_ID);
        mDatas=new ArrayList<>();
        mFetchItemTask=new FetchItemTask();
        mFetchItemTask.execute();
//        new FetchItemTask().execute();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        mFetchItemTask.cancel(false);
        Log.i(TAG, "Background thread destroyed");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        ButterKnife.bind(this, view);
        Log.i(LogKeys.MOVIE_DETAIL_FRAGMENT, "movie title is set" + mMovieId);
        mDetailSubjectContainerLinearLayout.setVisibility(View.VISIBLE);
        mDetailSubjectTitleTv.setText(mMovieTitle);
        mRequestManager
                .load(imgUrl)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .listener(new RequestListener<String, Bitmap>() {
                    @Override
                    public boolean onException(Exception e, String model,
                                               Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, String model,
                                                   Target<Bitmap> target, boolean isFromMemoryCache,
                                                   boolean isFirstResource) {

                        Bitmap blurBitmap = ImageUtil.fastBlur(resource, 20);
                        BitmapDrawable drawable = new BitmapDrawable(getResources(), blurBitmap);

                        // set alpha of bitmap to reduce brightness
                        drawable.setAlpha(192);
                        // set blur background of toolbar
                        mDetailToolbarContainerSubject.setBackground(drawable);

                        return false;
                    }
                }).into(mDetailHeaderSubjectIv);


        mDetailSubjectActorRv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        mDetailHeaderContainerSubject.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                mRefreshSubject.setEnabled(verticalOffset==0);
            }
        });

        mDetailNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (titleDy == Float.MAX_VALUE) {
                    // scroll distance of display title in toolbar
                    titleDy = mDetailSubjectTitleTv.getY() + mDetailSubjectTitleTv.getHeight();
                }

                if (scrollY >= titleDy) {
                    if (mItem.getTitle() != null) {
                        mDetailToolbarContainerSubject.setTitle(mItem.getTitle());
                    }
                } else {
                    mDetailToolbarContainerSubject.setTitle("");
                }
            }
        });


        setupAdapter();
        return view;
    }

    public void setupAdapter(){
        adapter_detail=new RecyclerViewAdapter_Detail(getActivity());
        mDetailSubjectActorRv.setVisibility(View.VISIBLE);
        mDetailSubjectActorRv.setAdapter(adapter_detail);
    }


    private class FetchItemTask extends AsyncTask<Void, Void, MainItem> {
        @Override
        protected MainItem doInBackground(Void... params) {
            DoubanFetchrMovieDetail df = new DoubanFetchrMovieDetail();
            return df.fetchItems_MovieDetailInfo(mMovieId);
        }

        @Override
        protected void onPostExecute(MainItem galleryItems) {
            mItem = galleryItems;

            //电影名 1 textview

            //电影国家 1 textview
            mDetailSubjectCountriesTv.setText(ToString.arrayToString(mItem.getCountries()));
            //电影风格 1 textview
            mDetailSubjectGenresTv.setText(ToString.arrayToString(mItem.getGenres()));
            //电影评分 以及评价人数 1 ratingbar 2 textview
            mDetailSubjectRatingLayout.setVisibility(View.VISIBLE);
            mDetailSubjectRatingRb.setRating(Float.parseFloat(mItem.getAverageRating())/2);
            mDetailSubjectCommentNumTv.setText(mItem.getRatingCount());
            mDetailSubjectRatingTv.setText(mItem.getAverageRating()+getString(R.string.count));


            mDetailSubjectSummaryTv.setText(mItem.getSummary());



            Map<Integer,String> mActorsTmp=mItem.getActors();
            Map<Integer,String> mActorsImgUrlTmp=mItem.getActorsImgUrl();
            Log.i(LogKeys.MOVIE_DETAIL_FRAGMENT, mActorsTmp.size()+"");
            for(int i:mActorsTmp.keySet()){
                DetailCast tt=new DetailCast();
                Log.i(LogKeys.MOVIE_DETAIL_FRAGMENT, mActorsTmp.get(i)+"");
                tt.setName(mActorsTmp.get(i));
                tt.setId(i+"");
                tt.setImgUrl(mActorsImgUrlTmp.get(i));
                mDatas.add(tt);
            }
            mDetailSubjectActorRv.setVisibility(View.VISIBLE);

            Log.i(LogKeys.MOVIE_DETAIL_FRAGMENT, "mDatas size:"+mDatas.size()+"");
            adapter_detail.addMoreData(mDatas);
            mDetailWeb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getActivity(),WebActivity.class);
                    intent.putExtra(TransferKeys.DETAIL_WEB_ID,mMovieId);
                    intent.putExtra(TransferKeys.DETAIL_WEB_TITLE,mMovieTitle);
                    Log.i("sssssss","id"+mItem.getMovieId());
                    startActivity(intent);
                    Log.i("sssssss","startAfter");
                }
            });
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

