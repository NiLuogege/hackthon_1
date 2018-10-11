package com.niluogege.example.commonsdk.network.interceptor;


import android.text.TextUtils;

import com.niluogege.example.commonsdk.utils.NetworkUtils;
import com.niluogege.example.commonsdk.utils.ILog;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by niluogege on 2018/3/21.
 * <p>
 * 缓存的拦截器
 */

public class HttpCacheInterceptor implements Interceptor {
    private static final int TIMEOUT_DISCONNECT = 60 * 60 * 24 * 7; //7天
    private static final int TIMEOUT_CONNECT = 0; //0秒


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!NetworkUtils.isConnected()) {  //没网强制从缓存读取
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            ILog.d("no network");
        }

        Response originalResponse = chain.proceed(request);
        if (NetworkUtils.isConnected()) {
            //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
            String cacheTime = request.header("cacheTime");

            if (TextUtils.isEmpty(cacheTime)) {//不需要缓存
                cacheTime = TIMEOUT_CONNECT + "";//缓存时间为0
            }

            ILog.e("cacheTime=" + cacheTime);//打印缓存时间

            return originalResponse.newBuilder()
                    .removeHeader("Pragma")//remove掉其他的缓存头，避免服务端进行一些限制，导致客户端不能进行缓存。
                    .removeHeader("Cache-Control")//好要remove掉其他的缓存头，避免服务端进行一些限制，导致客户端不能进行缓存。
                    .header("Cache-Control", "public, max-age=" + cacheTime)//添加自己的Cache-Control
                    .build();
        } else {
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + TIMEOUT_DISCONNECT)//离线的时候为7天的缓存。
                    .build();
        }
    }
}
