package com.jc.doubanmovie_4.douban;

import android.net.Uri;
import android.util.Log;

import com.jc.doubanmovie_4.LogKeys;
import com.jc.doubanmovie_4.model.MainItem;
import com.jc.doubanmovie_4.utils.network.NetworkData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by jc on 8/27/2016.
 */
public class DoubanFetchrMain {


    private static final String ENDPOINT="https://api.douban.com";
    private static final String VERSION="v2";
    private static final String TOPIC_MOVIE="movie";
    //正在上映 /v2/movie/in_theaters
    private static final String SUBJECT_IN_THEATERS="in_theaters";

    //即将上映
    private static final String MOVIE_COMING_SOON="coming_soon";
    //北美票房榜 /v2/movie/us_box
    private static final String MOVIE_US_BOX="us_box";
    //Top250   /v2/movie/top250
    private static final String TOP_RATED="top250";

    private static final String SUBJECTS="subjects";

    //getTotalDataCount()
    private static final String TOTAL="total";

    private JSONObject mJSONObject;

    /**
     *
     * @param id 0:subject in theaters;
     *           1:coming soon ;
     *           2 movie us box ;
     *           3 top rated  ;
     *
     */
    public DoubanFetchrMain(int id,int start){
        String xmlString;
        try{
            switch (id){
                case 0:
                    xmlString=NetworkData.getUrl(buildSubjectInTheaters(start));
                    mJSONObject=new JSONObject(xmlString);
                    break;
                case 1:
                    xmlString=NetworkData.getUrl(buildMovieComingSoon(start));
                    mJSONObject=new JSONObject(xmlString);
                    break;
                case 2:
                    xmlString=NetworkData.getUrl(buildMovieUSBox(start));
                    mJSONObject=new JSONObject(xmlString);
                    break;
                case 3:
                    xmlString=NetworkData.getUrl(buildMovieTopRated(start));
                    mJSONObject=new JSONObject(xmlString);
                    break;
            }
        }catch (IOException e){
            Log.e(LogKeys.NETWORK_GETDATA_ERROR,"get network data error");
        }catch (JSONException jsonE){
            Log.e(LogKeys.DOUBAN_FETCHR_MAIN,"Failed to build jsonObject"+jsonE);
        }
    }

    public int getTotalCount(){
        int res=0;
        try{
            res=mJSONObject.getInt(TOTAL);
        }catch (JSONException e){
            Log.e(LogKeys.DOUBAN_FETCHR_MAIN,"failed to get total count json",e);
        }
        return res;
    }

    private String buildSubjectInTheaters(int i){
        String url= Uri.parse(ENDPOINT).buildUpon()
                .appendPath(VERSION)
                .appendPath(TOPIC_MOVIE)
                .appendPath(SUBJECT_IN_THEATERS)
                .appendQueryParameter("start",i+"")
                .build().toString();

        return url;
    }

    private String buildMovieComingSoon(int i){
        String url= Uri.parse(ENDPOINT).buildUpon()
                .appendPath(VERSION)
                .appendPath(TOPIC_MOVIE)
                .appendPath(MOVIE_COMING_SOON)
                .appendQueryParameter("start",i+"")
                .build().toString();

        return url;
    }

    private String buildMovieUSBox(int i){
        String url= Uri.parse(ENDPOINT).buildUpon()
                .appendPath(VERSION)
                .appendPath(TOPIC_MOVIE)
                .appendPath(MOVIE_US_BOX)
                .appendQueryParameter("start",i+"")
                .build().toString();
        return url;
    }

    private String buildMovieTopRated(int i){
        String url= Uri.parse(ENDPOINT).buildUpon()
                .appendPath(VERSION)
                .appendPath(TOPIC_MOVIE)
                .appendPath(TOP_RATED)
                .appendQueryParameter("start",i+"")
                .build().toString();
        return url;
    }

    public ArrayList<MainItem> fetchItems_SubjectInTheaters(){
        ArrayList<MainItem> items=new ArrayList<>();
        try{
            JSONArray JSONArray=mJSONObject.getJSONArray(SUBJECTS);
            for(int i=0;i<JSONArray.length();i++){
                MainItem galleryItem = DataParser_MovieMain.getMovieInfo(mJSONObject,i);
                items.add(galleryItem);
                Log.i(LogKeys.DOUBAN_FETCHR_MAIN,"Received data : "+ galleryItem);
            }
        }catch (JSONException jsonE){
            Log.e(LogKeys.DOUBAN_FETCHR_MAIN,"Failed to build jsonObject"+jsonE);
        }
        return items;
    }

    public ArrayList<MainItem> fetchItems_MoviesComingSoon(){
        ArrayList<MainItem> items=new ArrayList<>();
        try {
            JSONArray JSONArray = mJSONObject.getJSONArray(SUBJECTS);
            for (int i = 0; i < JSONArray.length(); i++) {
                MainItem galleryItem = DataParser_MovieMain.getMovieInfo(mJSONObject, i);
                items.add(galleryItem);
                Log.i(LogKeys.DOUBAN_FETCHR_MAIN, "Received data : " + i + " :" + galleryItem);
            }
        }catch (JSONException jsonE){
            Log.e(LogKeys.DOUBAN_FETCHR_MAIN,"Failed to build jsonObject"+jsonE);
        }
        return items;
    }

    public ArrayList<MainItem> fetchItems_MoviesUSBox(){
        ArrayList<MainItem> items=new ArrayList<>();
        try{
            JSONArray JSONArray=mJSONObject.getJSONArray(SUBJECTS);
            for(int i=0;i<JSONArray.length();i++){
                MainItem galleryItem = DataParser_MovieUSBox.getMovieInfo(mJSONObject,i);
                items.add(galleryItem);
                Log.i(LogKeys.DOUBAN_FETCHR_MAIN,"Received data : "+ galleryItem);
            }
        }catch (JSONException jsonE){
            Log.e(LogKeys.DOUBAN_FETCHR_MAIN,"Failed to build jsonObject"+jsonE);
        }
        return items;
    }

    public ArrayList<MainItem> fetchItems_TopRated(){
        ArrayList<MainItem> items=new ArrayList<>();
        try{
            JSONArray JSONArray=mJSONObject.getJSONArray(SUBJECTS);
            for(int i=0;i<JSONArray.length();i++){
                MainItem galleryItem = DataParser_MovieMain.getMovieInfo(mJSONObject,i);
                items.add(galleryItem);
                Log.i(LogKeys.DOUBAN_FETCHR_MAIN,"Received data : "+ galleryItem);
            }
        }catch (JSONException jsonE){
            Log.e(LogKeys.DOUBAN_FETCHR_MAIN,"Failed to build jsonObject"+jsonE);
        }
        return items;
    }
}
