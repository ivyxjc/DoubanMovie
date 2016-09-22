package com.jc.doubanmovie_4.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jc on 8/28/2016.
 */
public class MainItem {

    private String mMovieId;//电影id
    private String mTitle;//电影名
    private String mOriginalTitle;//电影原名
    private String mAverageRating;//电影平均分数



    private Map<Integer,String> mActors;//演员
    private Map<Integer,String> mDirectors;//导演

    private Map<Integer,String> mActorsImgUrl;//演员图片
    private Map<Integer,String> mDirectorsImgUrl;//导演图片

    private Map<Integer,String> mWriters;

    private String mCollectNum;//看过的人数
    private String mWishtCount;//想看的人数

    private String mRatingCount;//评价人数
    private String mYear;//电影年份

    private String mImageUrl;// 图片url

    private ArrayList<String> mCountries;//出品国家
    private ArrayList<String> mGenres;//电影风格


    private String mSummary;//电影简介


    public MainItem(){
        mActors =new HashMap<>();
        mDirectors=new HashMap<>();
        mCountries=new ArrayList<>();
        mGenres=new ArrayList<>();
        mActorsImgUrl=new HashMap<>();
        mDirectorsImgUrl=new HashMap<>();
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


    public String getRatingCount() {
        return mRatingCount;
    }

    public void setRatingCount(String ratingCount) {
        mRatingCount = ratingCount;
    }


    public String getYear() {
        return mYear;
    }

    public void setYear(String year) {
        mYear = year;
    }

    public Map<Integer, String> getActors() {
        return mActors;
    }

    public void addActor(Integer i, String s){
        mActors.put(i,s);
    }

    public Map<Integer, String> getDirectors() {
        return mDirectors;
    }



    public void addDirector(Integer i,String s){
        mDirectors.put(i,s);
    }

    public Map<Integer, String> getWriters() {
        return mWriters;
    }

    public void addWriter(Integer i,String s) {
        mWriters.put(i,s);
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

    public void setCountries(ArrayList<String> countries){
        mCountries=countries;
    }

    public void setWishtCount(String a){
        mWishtCount=a;
    }

    public String getWishtCount(){
        return mWishtCount;
    }

    public void setGenres(ArrayList<String> as){
        mGenres=as;
    }

    public ArrayList<String> getGenres() {
        return mGenres;
    }

    public void addGenre(String s){
        mGenres.add(s);
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }



    public void addActorImgUrl(Integer i,String s){
        mActorsImgUrl.put(i,s);
    }

    public Map<Integer, String> getActorsImgUrl() {
        return mActorsImgUrl;
    }

    public void addDirectorImgUrl(Integer i,String s){
        mDirectorsImgUrl.put(i,s);
    }

    public Map<Integer, String> getDirectorsImgUrl() {
        return mDirectorsImgUrl;
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
