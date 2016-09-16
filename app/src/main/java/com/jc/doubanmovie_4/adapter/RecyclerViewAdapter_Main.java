package com.jc.doubanmovie_4.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jc.doubanmovie_4.R;
import com.jc.doubanmovie_4.model.MainItem;

import java.util.List;

/**
 * Created by jc on 9/15/2016.
 */
public class RecyclerViewAdapter_Main extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final Context mContext;
    private List<MainItem> mData;
    private OnItemClickListener mCallback;
    private boolean mIsComingMovie;

    public RecyclerViewAdapter_Main(Context context, List<MainItem> data,
                          boolean isComingMovie) {

        this.mContext = context;
        this.mData = data;
        this.mIsComingMovie=isComingMovie;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if
        View view=LayoutInflater.from(mContext)
                                .inflate(R.layout.item_movie_main,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder)holder).update();
    }





    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mCallback=listener;
    }


    public interface OnItemClickListener {
        void onItemClick(String id,String title);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        ImageView imageMovie;

        RatingBar ratingBar;

        TextView textTitle;

        MainItem mMainItem;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageMovie=(ImageView)itemView.findViewById(R.id.gallery_item_imageView);
            textTitle=(TextView)itemView.findViewById(R.id.gallery_item_text);

            itemView.setOnClickListener(this);
        }

        public void update() {
            mMainItem = mData.get(getLayoutPosition());

            String title = mMainItem.getTitle();
            textTitle.setText(title);

            // get display image url by preference
            String url = mMainItem.getImageUrl();
            Glide.with(mContext)
                    .load(url)
                    .into(imageMovie);
        }


        @Override
        public void onClick(View v) {
            if(mCallback!=null){
                mCallback.onItemClick(mMainItem.getMovieId(),mMainItem.getTitle());
            }
        }
    }
}
