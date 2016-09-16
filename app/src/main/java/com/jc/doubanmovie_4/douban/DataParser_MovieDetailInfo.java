package com.jc.doubanmovie_4.douban;

import android.util.Log;

import com.jc.doubanmovie_4.LogKeys;
import com.jc.doubanmovie_4.model.MainItem;
import com.jc.doubanmovie_4.utils.StringTo;

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
    private static final String RATING_AVERAGE="average";
    private static final String WISH_COUNT="wish_count";
    private static final String MOVIE_GENRES="genres";

    //获取演员的JSONObjectArray
    private static final String CASTS="casts";
    //获取演员的ID Name
    private static final String CAST_ID="id";
    private static final String CAST_NAME="name";

    //获取导演的JSONObjectArray
    private static final String DIRECTORS="directors";
    //获取导演的ID Name
    private static final String DIRECTOR_ID="id";
    private static final String DIRECTOR_NAME="name";

    //获取编剧的JSONObjectArray
    private static final String WRITERS="writers";
    //获取编剧的ID Name
    private static final String WRITERS_ID="id";
    private static final String WRITERS_NAME="name";

    //获取电影简介
    private static final String MOVIE_SUMMARY="summary";

    private static final String COLLECT_COUNT= "collect_count";
    private static final String YEAR="year";

    private static final String MOVIE_ID="id";
    private static final String MOVIE_YEAR="year";








    private static final String MOVIE_IMAGE="images";
    private static final String MOVIE_COUNTRIES="countries";

    private static final String IMAGE_LARGE="large";



    public static MainItem getMovieInfo(JSONObject dataJson){



        MainItem item=new MainItem();

        try{

            /**
             * 获取国家
             "countries": [
             "美国"
             ],
             */

            String movie_countries=dataJson.getString(MOVIE_COUNTRIES);

            item.setCountries(StringTo.stringToArrayList(movie_countries));

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

            /**
             * 获取评分
             *     "rating": {
                     "max": 10,
                     "average": 7.5,
                     "stars": "40",
                     "min": 0
                     },
             */

            JSONObject ratingJSONObject=dataJson.getJSONObject(RATING);
            item.setAverageRating(ratingJSONObject.getString(RATING_AVERAGE));

            /**
             * 想看的人数

             */
            item.setWishtCount(dataJson.getString(WISH_COUNT));


            /**
             * 电影风格
             *  "genres": [
                 "动作",
                 "科幻",
                 "冒险"
                 ],
             */
            String movie_genres=dataJson.getString(MOVIE_GENRES);

            item.setGenres(StringTo.stringToArrayList(movie_genres));
//            String[] movie_genresArray=movie_genres.split(",");
//            movie_genresArray[0]=movie_genresArray[0].substring(1,movie_genresArray[0].length()-1);
//            int genresArrLength=movie_genresArray.length;
//
//            movie_genresArray[genresArrLength-1]=movie_genresArray[genresArrLength-1].substring(0,movie_genresArray[genresArrLength-1].length()-2);
//
//            for(int i=0;i<movie_genresArray.length;i++){
//                movie_genresArray[i]=movie_genresArray[i].substring(1,movie_genresArray[i].length()-1);
//                item.addGenre(movie_genresArray[i]);
//            }

            /**
             *电影主演
             *
             */
            JSONArray castsJSONArray=dataJson.getJSONArray(CASTS);
            for(int i=0;i<castsJSONArray.length();i++){
                JSONObject castJSONObject=castsJSONArray.getJSONObject(i);
                item.addCast(castJSONObject.getInt(CAST_ID),castJSONObject.getString(CAST_NAME));
            }


            /**
             * 电影导演
             */
            JSONArray directorsJSONArray=dataJson.getJSONArray(DIRECTORS);
            Log.i(LogKeys.DATAPARSER_MOVIEDETAIL,"dataparser director array: "+directorsJSONArray.length());
            for(int i=0;i<directorsJSONArray.length();i++){
                JSONObject directorJSONObject=directorsJSONArray.getJSONObject(i);
                item.addDirector(directorJSONObject.getInt(DIRECTOR_ID),directorJSONObject.getString(DIRECTOR_NAME));
            }


            /**
             * 编剧
             *
             * api中未提供 ， 参考中提供 但是json数据中没有
             */

//            JSONArray writersJSONArray=dataJson.getJSONArray(WRITERS);
//            for(int i=0;i<writersJSONArray.length();i++){
//                JSONObject writerJSONObject=writersJSONArray.getJSONObject(i);
//                item.addWriter(writerJSONObject.getInt(WRITERS_ID),writerJSONObject.getString(WRITERS_NAME));
//            }

            /**
             * 电影简介
             */
            item.setSummary(dataJson.getString(MOVIE_SUMMARY));



        }catch (JSONException e){
            Log.e(TAG,"json parser error"+e);
        }



        return item;
    }



}
