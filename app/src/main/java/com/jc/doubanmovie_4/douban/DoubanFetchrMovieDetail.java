package com.jc.doubanmovie_4.douban;

import android.net.Uri;
import android.util.Log;

import com.jc.doubanmovie_4.LogKeys;
import com.jc.doubanmovie_4.model.MainItem;
import com.jc.doubanmovie_4.utils.network.NetworkData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by jc on 9/18/2016.
 */
public class DoubanFetchrMovieDetail {

    private static final String ENDPOINT="https://api.douban.com";
    private static final String VERSION="v2";
    private static final String TOPIC_MOVIE="movie";
    //电影信息 /v2/movie/subject/:id
    private static final String MOVIE_DETAIL="subject/";

    public MainItem fetchItems_MovieDetailInfo(String id){
        MainItem item=new MainItem();
        try{
            String url= Uri.parse(ENDPOINT).buildUpon()
                    .appendPath(VERSION)
                    .appendPath(TOPIC_MOVIE)
                    .appendPath(MOVIE_DETAIL)
                    .appendPath(id)
                    .build().toString();
            Log.i(LogKeys.DOUBAN_FETCHR_MAIN,"url "+url);

            String xmlString = NetworkData.getUrl(url);
            JSONObject jsonObject=new JSONObject(xmlString);

            MainItem galleryItem = DataParser_MovieDetailInfo.getMovieInfo(jsonObject);
            item=galleryItem;
            Log.i(LogKeys.DOUBAN_FETCHR_MAIN,"Received data (movie details): "+ galleryItem);


        }catch (IOException ioe){
            Log.e(LogKeys.DOUBAN_FETCHR_MAIN,"Failed to fetch items"+ioe);
        }catch (JSONException jsonE){
            Log.e(LogKeys.DOUBAN_FETCHR_MAIN,"Failed to build jsonObject"+jsonE);
        }

        return item;
    }
}
