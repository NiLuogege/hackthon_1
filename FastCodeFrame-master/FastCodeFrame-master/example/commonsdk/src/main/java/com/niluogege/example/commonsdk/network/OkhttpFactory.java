package com.niluogege.example.commonsdk.network;

import com.niluogege.example.commonsdk.base.BaseApplication;
import com.niluogege.example.commonsdk.network.https.HttpsUtils;
import com.niluogege.example.commonsdk.network.interceptor.HeaderInterceptor;
import com.niluogege.example.commonsdk.network.interceptor.HttpCacheInterceptor;
import com.niluogege.example.commonsdk.network.interceptor.LogInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

/**
 * Created by niluogege on 2018/8/23.
 * <p>
 * okhttp工厂类
 */

public class OkhttpFactory {
    private static final long TIMEOUT_CONNECT = 20;//单位是s
    private static final long TIMEOUT_READ = 20;//单位是s
    private static final long TIMEOUT_WRITE = 20;//单位是s


    public static OkHttpClient getOkHttpClient() {

        return getDefaultOkHttpBuilder().build();
    }

    public static OkHttpClient.Builder getDefaultOkHttpBuilder() {

        //配置到cache目录中
        File cacheFile = new File(BaseApplication.getApplication().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb

        return new OkHttpClient
                .Builder()
                .connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)//连接超时时间
                .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)//读取中断时等待超时时间
                .writeTimeout(TIMEOUT_WRITE, TimeUnit.SECONDS)//写入中断时等待超时时间
                .addInterceptor(new HeaderInterceptor())//设置请求头拦截器
                .addInterceptor(new LogInterceptor())//设置log拦截器
                .addNetworkInterceptor(new HttpCacheInterceptor())//缓存的拦截器
                .addInterceptor(new HttpCacheInterceptor())//缓存的拦截器
                .connectionPool(new ConnectionPool(5, 5, TimeUnit.MINUTES))//创建链接池，这里和源码保持一直
                .sslSocketFactory(HttpsUtils.getSslSocketFactory(null, null, null))//配置可以使用https
                .hostnameVerifier((s, sslSession) -> true)//配置可以使用https
                .cache(cache);//配置缓存
    }


}
