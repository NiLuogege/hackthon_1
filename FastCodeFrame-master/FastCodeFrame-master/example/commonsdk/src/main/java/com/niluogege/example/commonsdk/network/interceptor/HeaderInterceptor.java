package com.niluogege.example.commonsdk.network.interceptor;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by niluogege on 2018/8/23.
 * <p>
 * 请求头拦截器
 */

public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        Request request = chain.request().newBuilder()
                .header("OS", "android")//添加所需的头信息
                .build();

        return chain.proceed(request);
    }
}
