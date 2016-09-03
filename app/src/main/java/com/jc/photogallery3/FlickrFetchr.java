package com.jc.photogallery3;

import android.net.Uri;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by jc on 8/27/2016.
 */
public class FlickrFetchr {

    public static final String TAG="FlickrTetchr";
    private static final String ENDPOINT="https://api.douban.com";
    private static final String VERSION="v2";
    private static final String TOPIC_MOVIE="movie";
    //正在上映 /v2/movie/in_theaters
    private static final String SUBJECT_IN_THEATERS="in_theaters";

    byte[] getUrlBytes(String urlSpec) throws IOException{

        URL url=new URL(urlSpec);

        HttpURLConnection connection=(HttpURLConnection)url.openConnection();

        try {
            ByteArrayOutputStream out=new ByteArrayOutputStream();
            InputStream in=connection.getInputStream();

            if(connection.getResponseCode()!=HttpURLConnection.HTTP_OK){
                return null;
            }

            int bytesRead=0;

            byte[] buffer=new byte[1024];

            while ((bytesRead=in.read(buffer))>0){
                out.write(buffer,0,bytesRead);
            }

            out.close();
            return out.toByteArray();
        }finally {
            connection.disconnect();
        }

    }

    public String getUrl(String urlSpec) throws IOException{
        return new String(getUrlBytes(urlSpec));
    }


    public ArrayList<GalleryItem> fetchItems(){
        ArrayList<GalleryItem> items=new ArrayList<>();
        try{
            String url= Uri.parse(ENDPOINT).buildUpon()
                    .appendPath(VERSION)
                    .appendPath(TOPIC_MOVIE)
                    .appendPath(SUBJECT_IN_THEATERS)
                    .build().toString();
            Log.i(TAG,"url "+url);

            String xmlString =getUrl(url);
            JSONObject jsonObject=new JSONObject(xmlString);

            for(int i=0;i<20;i++){
                GalleryItem galleryItem =DataParser.getMovieName(jsonObject,i);
                items.add(galleryItem);
                Log.i(TAG,"Received data: "+ galleryItem);
            }


        }catch (IOException ioe){
            Log.e(TAG,"Failed to fetch items"+ioe);
        }catch (JSONException jsonE){
            Log.e(TAG,"Failed to build jsonObject"+jsonE);
        }

        return items;
    }
}
