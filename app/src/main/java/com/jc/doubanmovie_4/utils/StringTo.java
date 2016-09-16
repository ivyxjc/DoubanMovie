package com.jc.doubanmovie_4.utils;

import java.util.ArrayList;

/**
 * Created by jc on 9/8/2016.
 */
public class StringTo {

    /**
     *
     * 将String转换为ArrayList
     * String的格式大致如下  ["aaa","bbb","ccc"]，只需要aaa,bbb,ccc
     * 用于DataParse_MovieDetailInfo 中电影风格 电影国家的获取
     * @param s
     * @return
     */
    public  static ArrayList<String> stringToArrayList(String s){
        ArrayList<String> res=new ArrayList<>();

        String[] sArray=s.split(",");
        sArray[0]=sArray[0].substring(1,sArray[0].length());
        int sArrLength=sArray.length;

        sArray[sArrLength-1]=sArray[sArrLength-1].substring(0,sArray[sArrLength-1].length()-1);

        for(int i=0;i<sArray.length;i++){
            sArray[i]=sArray[i].substring(1,sArray[i].length()-1);
            res.add(sArray[i]);
        }
        return res;
    }
}
