package com.jc.doubanmovie_4.model;

/**
 * Created by jc on 9/19/2016.
 */
public class DetailCast {
    private String id;
    private String imgUrl;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    private String name;
}
