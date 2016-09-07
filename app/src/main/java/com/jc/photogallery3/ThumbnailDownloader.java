package com.jc.photogallery3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.util.Log;


import com.jc.photogallery3.douban.FlickrFetchr;
import com.jc.photogallery3.model.GalleryItem;

import java.io.IOException;
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

    Map<Token,String> requestMap =
            Collections.synchronizedMap(new HashMap<Token, String>());

    Handler mResponseHandler;
    Listener<Token> mListener;


    //预加载
    private LruCache<String, Bitmap> mMemoryCache;

    public void queueThumbnail(Token token,String url){
        requestMap.put(token,url);

        mHandler.obtainMessage(MESSAGE_DOWNLOAD,token)
                .sendToTarget();
    }


    public void clearQueue(){
        mHandler.removeMessages(MESSAGE_DOWNLOAD);
        requestMap.clear();
    }



    public interface Listener<Token>{
        void onThumbnailDownloaded(Token token,Bitmap thumbnail);
    }


    public void setListener(Listener<Token> listener) {
        mListener = listener;
    }

    public ThumbnailDownloader(Handler responseHandler,LruCache<String,Bitmap> bitmapLruCache){
        super(TAG);
        mResponseHandler=responseHandler;

        mMemoryCache=bitmapLruCache;

    }


    @Override
    protected void onLooperPrepared() {
        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==MESSAGE_DOWNLOAD){
                    @SuppressWarnings("unchecked")
                    Token token=(Token)msg.obj;
                    Log.i(TAG,"Got a request for url :"+ requestMap.get(token));
                    handleRequest(token);
                }
            }
        };
    }

    public void handleRequest(final Token token){
        try {
            final String url= requestMap.get(token);
            if(url==null){
                return;
            }
            final Bitmap bitmap;
            if(mMemoryCache.get(url)!=null){
                bitmap=mMemoryCache.get(url);
                Log.i(TAG,"Bitmap is existed");
                Log.i(TAG,"memorycache size"+mMemoryCache.size());
            }else{
                byte[] bitmapBytes=new FlickrFetchr().getUrlBytes(url);
                bitmap= BitmapFactory
                        .decodeByteArray(bitmapBytes,0,bitmapBytes.length);
                mMemoryCache.put(url,bitmap);
                Log.i(TAG,"toke : "+token +" url "+url);
                Log.i(TAG,"Bitmap created");
                Log.i(TAG,"memorycache size"+mMemoryCache.size());
            }

            mResponseHandler.post(new Runnable() {
                @Override
                public void run() {
                    if(requestMap.get(token)!=url){
                        return;
                    }
                    requestMap.remove(token);
                    mListener.onThumbnailDownloaded(token,bitmap);
                }
            });

        }catch (IOException e){
            Log.e(TAG,"Error downloading image",e);
        }
    }


}
