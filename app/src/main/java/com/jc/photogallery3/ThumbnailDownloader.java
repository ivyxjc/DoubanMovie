package com.jc.photogallery3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jc on 9/3/2016.
 */
public class ThumbnailDownloader<Token> extends HandlerThread {
    private static final String TAG="ThumbnailDownloader";

    private static final int MESSAGE_DOWNLOAD=0;

    Handler mHandler;

    Map<Token,String> requesMap=
            Collections.synchronizedMap(new HashMap<Token, String>());

    Handler mResponseHandler;
    Listener<Token> mListener;


    public void queueThumbnail(Token token,String url){
        requesMap.put(token,url);

        mHandler.obtainMessage(MESSAGE_DOWNLOAD,token)
                .sendToTarget();
    }


    public void clearQueue(){
        mHandler.removeMessages(MESSAGE_DOWNLOAD);
        requesMap.clear();
    }



    public interface Listener<Token>{
        void onThumbnailDownloaded(Token token,Bitmap thumbnail);
    }


    public void setListener(Listener<Token> listener) {
        mListener = listener;
    }

    public ThumbnailDownloader(Handler responseHandler){
        super(TAG);
        mResponseHandler=responseHandler;

    }




    @Override
    protected void onLooperPrepared() {
        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==MESSAGE_DOWNLOAD){
                    @SuppressWarnings("unchecked")
                    Token token=(Token)msg.obj;
                    Log.i(TAG,"Got a request for url :"+requesMap.get(token));
                    handleRequest(token);
                }
            }
        };
    }

    public void handleRequest(final Token token){
        try {
            final String url=requesMap.get(token);
            if(url==null){
                return;
            }
            byte[] bitmapBytes=new FlickrFetchr().getUrlBytes(url);
            final Bitmap bitmap= BitmapFactory
                    .decodeByteArray(bitmapBytes,0,bitmapBytes.length);
            Log.i(TAG,"Bitmap created");

            mResponseHandler.post(new Runnable() {
                @Override
                public void run() {
                    if(requesMap.get(token)!=url){
                        return;
                    }
                    requesMap.remove(token);
                    mListener.onThumbnailDownloaded(token,bitmap);
                }
            });

        }catch (IOException e){
            Log.e(TAG,"Error downloading image",e);
        }


    }
}
