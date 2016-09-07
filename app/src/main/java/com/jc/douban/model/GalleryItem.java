package com.jc.douban.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jc on 8/28/2016.
 */
public class GalleryItem {

    private String mMovieId;//电影id
    private String mTitle;//电影名
    private String mOriginalTitle;//电影原名
    private String mAverageRating;//电影平均分数
    private Map<Integer,String> mCasts;//演员
    private Map<Integer,String> mDirectors;//导演

    private String mCollectNum;//看过的人数
    private String mYear;//电影年份

    private String mImageUrl;// 图片url

    private ArrayList<String> mCountries;//出品国家

    public GalleryItem(){
        mCasts=new HashMap<>();
        mDirectors=new HashMap<>();
        mCountries=new ArrayList<>();
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

    public void addCountry(String s){
        mCountries.add(s);
    }

    public ArrayList<String> getCountries(){
        return mCountries;
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("Title: "+mTitle+'\n')
                .append("Year: "+mYear+'\n')
                .append("imageUrl: "+mImageUrl+'\n')
                .append("countries: "+mCountries+'\n');

        return stringBuilder.toString();
    }
}
