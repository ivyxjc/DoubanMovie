package com.jc.doubanmovie_4.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jc.doubanmovie_4.LogKeys;
import com.jc.doubanmovie_4.R;
import com.jc.doubanmovie_4.model.MainItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jc on 9/15/2016.
 */
public class RecyclerViewAdapter_Main extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final String TAG="RecycleViewAdapter_Main";

    private final Context mContext;
    private List<MainItem> mData;
    private OnItemClickListenerCustom mCallback;
    private boolean mIsComingMovie;
    private int mTotalCount;
    private RequestManager mRequestManager;

    public interface OnItemClickListenerCustom {
        void onItemClick(String id,String title,String url);
    }

    public RecyclerViewAdapter_Main(Context context, List<MainItem> data,
                          boolean isComingMovie) {

        this.mContext = context;
        this.mData = data;
        this.mIsComingMovie=isComingMovie;
        mRequestManager=Glide.with(mContext);
    }

//    public void refresh(ArrayList<MainItem> datas){
//        mData.addAll(datas);
//        notifyDataSetChanged();
//    }


    public void addMoreData(ArrayList<MainItem> datas,int start){
        Log.i("bbbbbbbb","datassss size: "+datas.size()+" start: "+start);
//        if(start==mData.size())
//            return;
        mData.addAll(datas);
        notifyDataSetChanged();
    }

    public int getStart(){
        return mData.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(mContext)
                                .inflate(R.layout.item_movie_main,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.i(LogKeys.RECYCLER_ADAPATER_MAIN,"onBIndViewHolder");
        ((ItemViewHolder)holder).update();
    }


    //该类别总共有多少数据项
    public void setTotalCount(int num){
        mTotalCount=num;
    }

    public int getTotalDataCount(){
        return mTotalCount;
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnItemClickListener(OnItemClickListenerCustom listener){
        this.mCallback=listener;
    }



    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        ImageView imageMovie;
        LinearLayout ratingLayout;
        RatingBar ratingBar;
        TextView ratingText;

        TextView textTitle;
        MainItem mMainItem;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageMovie=(ImageView)itemView.findViewById(R.id.gallery_item_imageView);
            textTitle=(TextView)itemView.findViewById(R.id.gallery_item_text);
            ratingLayout=(LinearLayout)itemView.findViewById(R.id.gallery_item_rating_layout);
            ratingBar=(RatingBar)itemView.findViewById(R.id.gallery_item_rating_rb) ;
            ratingText=(TextView)itemView.findViewById(R.id.gallery_item_rating_text);

            itemView.setOnClickListener(this);
        }

        public void update() {
            mMainItem = mData.get(getLayoutPosition());

            String title = mMainItem.getTitle();
            textTitle.setText(title);
            if(mIsComingMovie){
                ratingLayout.setVisibility(View.INVISIBLE);
            }else{
                ratingLayout.setVisibility(View.VISIBLE);
                ratingBar.setRating(Float.parseFloat(mMainItem.getAverageRating())/2);
                ratingText.setText(mMainItem.getAverageRating());
            }

            textTitle.setText(mMainItem.getTitle());
            // get display image url by prerence
            String url = mMainItem.getImageUrl();
            mRequestManager
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(imageMovie);
        }


        @Override
        public void onClick(View v) {
            if(mCallback!=null){
                Log.i(LogKeys.RECYCLER_ADAPATER_MAIN,"before OnClick");
                mCallback.onItemClick(mMainItem.getMovieId(),mMainItem.getTitle(),mMainItem.getImageUrl());
                Log.i(TAG,"after OnClick");
            }
            Log.i(LogKeys.RECYCLER_ADAPATER_MAIN,"mCallback==null");
        }
    }
}
