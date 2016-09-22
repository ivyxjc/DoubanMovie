package com.jc.doubanmovie_4.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.jc.doubanmovie_4.R;
import com.jc.doubanmovie_4.model.DetailCast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jc on 9/19/2016.
 */
public class RecyclerViewAdapter_Detail extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    private ArrayList<DetailCast> mData;
    private Context mContext;

    public RecyclerViewAdapter_Detail(Context context) {
        mContext = context;
        mData=new ArrayList<>();
    }


    public void addMoreData(ArrayList<DetailCast> datas) {
        mData.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_cast_detail, parent, false);
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

    private class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mGalleryItemText;
        ImageView mItemSimpleActorImage;
        TextView mItemSimpleActorText;

        public ItemViewHolder(View view) {
            super(view);
            mItemSimpleActorText=(TextView)view.findViewById(R.id.item_simple_actor_text);
            mItemSimpleActorImage=(ImageView)view.findViewById(R.id.item_simple_actor_image);
            view.setOnClickListener(this);
        }

        public void update(){
            DetailCast mItem=mData.get(getLayoutPosition());
            mItemSimpleActorText.setText(mItem.getName());

            RequestManager requestManager= Glide.with(mContext);
            requestManager.load(mItem.getImgUrl())
                    .into(mItemSimpleActorImage);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
