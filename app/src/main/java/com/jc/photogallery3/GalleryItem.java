package com.jc.photogallery3;

/**
 * Created by jc on 8/28/2016.
 */
public class GalleryItem {

    private String mMovieId;
    private String mTitle;
    private String mOriginalTitle;
    private String mAverageRating;
//    private ArrayList<Map<String,String>> mCasts;



//    private String[] mDirectors;
    private String mCollectNum;
    private String mYear;


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

//    public String[] getCasts() {
//        return mCasts;
//    }
//
//    public void setCasts(String[] casts) {
//        mCasts = casts;
//    }
//
//    public String[] getDirectors() {
//        return mDirectors;
//    }
//
//    public void setDirectors(String[] directors) {
//        mDirectors = directors;
//    }

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


    @Override
    public String toString() {
        return mTitle+"\n"+mYear+"\n"+mAverageRating+"\n"+mCollectNum ;
    }
}
