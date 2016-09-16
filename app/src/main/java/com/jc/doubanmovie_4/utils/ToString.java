package com.jc.doubanmovie_4.utils;


import java.util.ArrayList;
import java.util.Map;

/**
 * Created by jc on 9/8/2016.
 */
public class ToString {

    /**
     * //将ArrayList转换为str
     * 用于MovieDetaiFragment中处理 电影风格 电影国家
     */

    public static String arrayToString(ArrayList<String> al){

        StringBuilder res=new StringBuilder();
        for(String country:al){
            res.append(country+" ");
        }
        return res.toString();
    }

    /**
     * 将Map<Integer,String> 转换为str
     * 用于MovieDetaiFragment中处理 演员 导演
     */

    public static String mapToString(Map<Integer,String> map){
        StringBuilder res=new StringBuilder();
        int index=0;
        for(Integer key:map.keySet()){
            index++;
            if(index==3){
                res.append("\n");
            }
            res.append(map.get(key)+", ");
        }
        return res.toString();
    }
}
