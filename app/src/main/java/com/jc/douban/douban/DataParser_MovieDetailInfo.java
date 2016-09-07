package com.jc.douban.douban;

import android.util.Log;

import com.jc.douban.model.GalleryItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jc on 9/7/2016.
 */
public class DataParser_MovieDetailInfo {
    private static final String TAG="dataParser_movie_detail";

    private static final String SUBJECTS="subjects";

    private static final String TITLE="title";
    private static final String ORIGINAL_TITLE="original_title";

    private static final String RATING="rating";
    private static final String CASTS="casts";
    private static final String DIRECTORS="directors";
    private static final String COLLECT_COUNT= "collect_count";
    private static final String YEAR="year";

    private static final String MOVIE_ID="id";
    private static final String MOVIE_YEAR="year";




    private static final String CAST_ID="id";
    private static final String CAST_NAME="name";

    private static final String DIRECTOR_ID="id";
    private static final String DIRECTOR_NAME="name";

    private static final String MOVIE_IMAGE="images";
    private static final String MOVIE_COUNTRIES="countries";

    private static final String IMAGE_LARGE="large";



    public static GalleryItem getMovieInfo(JSONObject dataJson){



        GalleryItem item=new GalleryItem();

        try{

            /**
             * 获取国家
             "countries": [
             "美国"
             ],
             */
            JSONArray countriesJSONArray=dataJson.getJSONArray(MOVIE_COUNTRIES);
            for(int i=0;i<countriesJSONArray.length();i++){
                item.addCountry(countriesJSONArray.optString(i));
            }


            /**
             * 电影名
             */

            item.setTitle(dataJson.getString(TITLE));


            /**
             * 图片网址
             *选large
             *
             * 后期  可以根据网络类型选择
             *  "images": {
                     "small": "https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p2375019545.jpg",
                     "large": "https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p2375019545.jpg",
                     "medium": "https://img3.doubanio.com/view/movie_poster_cover/spst/public/p2375019545.jpg"
                     },
             */

            JSONObject imagesJSONObject=dataJson.getJSONObject(MOVIE_IMAGE);
            item.setImageUrl(imagesJSONObject.getString(IMAGE_LARGE));




        }catch (JSONException e){
            Log.e(TAG,"json parser error"+e);
        }



        return item;
    }

}
