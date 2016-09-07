package com.jc.photogallery3.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jc on 8/28/2016.
 */
public class GalleryItem {

    private String mMovieId;
    private String mTitle;
    private String mOriginalTitle;
    private String mAverageRating;
    private Map<Integer,String> mCasts;
    private Map<Integer,String> mDirectors;

    private String mCollectNum;
    private String mYear;

    private String mImageUrl;// 图片url


    public GalleryItem(){
        mCasts=new HashMap<>();
        mDirectors=new HashMap<>();
    }


    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        mOriginalTitle = originalTitle;
    }

    public String getMovieId() {
        return mMovieId;
    }

    public void setMovieId(String movieId) {
        mMovieId = movieId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getAverageRating() {
        return mAverageRating;
    }

    public void setAverageRating(String averageRating) {
        mAverageRating = averageRating;
    }

    public String getCollectNum() {
        return mCollectNum;
    }

    public void setCollectNum(String collectNum) {
        mCollectNum = collectNum;
    }

    public String getYear() {
        return mYear;
    }

    public void setYear(String year) {
        mYear = year;
    }


    public void addCast(Integer i,String s){
        mCasts.put(i,s);
    }

    public void addDirector(Integer i,String s){
        mDirectors.put(i,s);
    }

    public void setImageUrl(String url){
        mImageUrl=url;
    }

    public String getImageUrl(){
        return mImageUrl;
    }


    @Override
    public String toString() {
        return mTitle+"\n"+mYear+"\n"+mAverageRating+"\n"+mCollectNum ;
    }
}
