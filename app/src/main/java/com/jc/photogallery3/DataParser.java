package com.jc.photogallery3;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jc on 8/28/2016.
 */
public class DataParser {
    private static final String TAG="dataParser";

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



    public static GalleryItem getMovieName(JSONObject dataJson, int num){
//        Map<String,String> res=new HashMap<>();
        GalleryItem res=new GalleryItem();
        try {
            JSONArray subjectsJSONArray=dataJson.getJSONArray(SUBJECTS);
            JSONObject movieJSONObject=subjectsJSONArray.getJSONObject(num);
            //获取电影名
            String title=movieJSONObject.getString(TITLE);
            res.setTitle(title);
            //获取电影原名
            String originalTitle=movieJSONObject.getString(ORIGINAL_TITLE);
            res.setOriginalTitle(originalTitle);

            /**获取评分
             * 评分格式如下:
             *  "rating": {
                "max": 10,
                "average": 7.4,
                "stars": "40",
                "min": 0
                },
             *
             *
             */
            JSONObject ratingJSON=movieJSONObject.getJSONObject(RATING);
            String averageRating=ratingJSON.getString("average");
            res.setAverageRating(averageRating);

            /**
             * 获取电影风格
             *  "genres": [
                "动作",
                "悬疑",
                "惊悚"
                 ],
             */




            /**
             * 获取主演名单
             *   "casts": [
                 {
                     "alt": "https://movie.douban.com/celebrity/1054443/",
                     "avatars": {
                     "small": "https://img3.doubanio.com/img/celebrity/small/620.jpg",
                     "large": "https://img3.doubanio.com/img/celebrity/large/620.jpg",
                     "medium": "https://img3.doubanio.com/img/celebrity/medium/620.jpg"
                    },
                     "name": "马特·达蒙",
                     "id": "1054443"
                 },
                 {
                     "alt": "https://movie.douban.com/celebrity/1047994/",
                     "avatars": {
                     "small": "https://img1.doubanio.com/img/celebrity/small/259.jpg",
                     "large": "https://img1.doubanio.com/img/celebrity/large/259.jpg",
                     "medium": "https://img1.doubanio.com/img/celebrity/medium/259.jpg"
                    },
                     "name": "汤米·李·琼斯",
                     "id": "1047994"
                 },
             */
//            JSONArray casts=movieJSONObject.getJSONArray(CASTS);
//            StringBuilder castsStringBuilder=new StringBuilder();
//
//            for(int i=0;i<casts.length();i++){
//                JSONObject castJSON=casts.getJSONObject(i);
//                castsStringBuilder.append(castJSON.get("name")+"AAAA");
//            }
//            res.put(MapKeys.CASTS,castsStringBuilder.toString());


            /**
             * 获取导演信息
             *
             * "directors": [
                 {
                     "alt": "https://movie.douban.com/celebrity/1025193/",
                     "avatars": {
                     "small": "https://img3.doubanio.com/img/celebrity/small/9714.jpg",
                     "large": "https://img3.doubanio.com/img/celebrity/large/9714.jpg",
                     "medium": "https://img3.doubanio.com/img/celebrity/medium/9714.jpg"
                    },
                     "name": "保罗·格林格拉斯",
                     "id": "1025193"
                 }
                 ],
             */

//            JSONArray directors=movieJSONObject.getJSONArray(DIRECTORS);
//            StringBuilder directorsStringBuilder=new StringBuilder();
//            for(int i=0;i<directors.length();i++){
//                JSONObject directorJSON=directors.getJSONObject(i);
//                directorsStringBuilder.append(directorJSON.get("name")+"AAAA");
//            }
//            res.put(MapKeys.DIRECTORS,directorsStringBuilder.toString());

            /** 看过的人数
             *   "collect_count": 43662,
             */

            String collectCount=movieJSONObject.getString(COLLECT_COUNT);
            res.setCollectNum(collectCount);

            /**
             * 年份
             */

            String year=movieJSONObject.getString(MOVIE_YEAR);
            res.setYear(year);

            /**
             * id
             */

            String movieId=movieJSONObject.getString(MOVIE_ID);
            res.setMovieId(movieId);
;
            return res;
        }catch (JSONException e){
            Log.e(TAG,"json parser error"+e);
        }
        return res;
    }
}
