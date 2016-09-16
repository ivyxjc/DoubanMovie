package com.jc.doubanmovie_4.douban;

import android.util.Log;

import com.jc.doubanmovie_4.model.MainItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jc on 8/28/2016.
 * 主要用于正在热映，即将上映，口碑榜三个页面，    北美票房榜的json数据格式有很大不同
 */
public class DataParser_MovieMain {
    private static final String TAG="dataParser_MovieMain";

    private static final String SUBJECTS="subjects";

    private static final String TITLE="title";
    private static final String ORIGINAL_TITLE="original_title";

    private static final String RATING="rating";

    private static final String MOVIE_ID="id";
    private static final String MOVIE_YEAR="year";

    private static final String MOVIE_IMAGE="images";

//    private static final String CASTS="casts";
//    private static final String DIRECTORS="directors";
//    private static final String COLLECT_COUNT= "collect_count";
//    private static final String YEAR="year";




//    private static final String CAST_ID="id";
//    private static final String CAST_NAME="name";
//
//    private static final String DIRECTOR_ID="id";
//    private static final String DIRECTOR_NAME="name";




    /**
     * 获取的信息有电影照片，电影名称，电影ID，电影评分（--页面之中会显示相关内容）        电影年份，电影风格
     * @param dataJson
     * @param num
     * @return
     */


    public static MainItem getMovieInfo(JSONObject dataJson, int num){
//        Map<String,String> res=new HashMap<>();
        MainItem res=new MainItem();
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
             * 获取电影照片
             *  "images": {
             "small": "https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p2377455123.jpg",
             "large": "https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p2377455123.jpg",
             "medium": "https://img3.doubanio.com/view/movie_poster_cover/spst/public/p2377455123.jpg"
             },
             */

            JSONObject movieImage=movieJSONObject.getJSONObject(MOVIE_IMAGE);
            String movieImageUrl=movieImage.getString("large");
            res.setImageUrl(movieImageUrl);

            /**
             *获取电影Id
             *
             */
            String movieId=movieJSONObject.getString(MOVIE_ID);
            res.setMovieId(movieId);

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
//            for (int i=0;i<casts.length();i++){
//                JSONObject castJSON=casts.getJSONObject(i);
//                res.addCast(Integer.parseInt(castJSON.get(CAST_ID).toString()),castJSON.get(CAST_NAME).toString());
//            }

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
//            for (int i=0;i<directors.length();i++){
//                JSONObject directorJSON=directors.getJSONObject(i);
//                res.addCast(Integer.parseInt(directorJSON.get(DIRECTOR_ID).toString()),directorJSON.get(DIRECTOR_NAME).toString());
//            }
//            for(int i=0;i<directors.length();i++){
//                JSONObject directorJSON=directors.getJSONObject(i);
//                directorsStringBuilder.append(directorJSON.get("name")+"AAAA");
//            }
//            res.put(MapKeys.DIRECTORS,directorsStringBuilder.toString());

            /** 看过的人数
             *   "collect_count": 43662,
             */

//            String collectCount=movieJSONObject.getString(COLLECT_COUNT);
//            res.setCollectNum(collectCount);

            /**
             * 年份
             */

            String year=movieJSONObject.getString(MOVIE_YEAR);
            res.setYear(year);

            /**
             * id
             */


;
            return res;
        }catch (JSONException e){
            Log.e(TAG,"json parser error"+e);
        }
        return res;
    }
}
