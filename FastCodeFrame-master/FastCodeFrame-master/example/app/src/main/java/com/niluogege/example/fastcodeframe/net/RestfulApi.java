package com.niluogege.example.fastcodeframe.net;

import com.niluogege.example.commonsdk.network.RetrofitManager;

/**
 * Created by niluogege on 2018/8/23.
 */

public class RestfulApi {
    private static ApiService apiService = null;


    public static ApiService getApiService() {
        if (apiService == null) {
            apiService = RetrofitManager.getInstence().getRetrofit(ApiService.class, "http://test18.hackathon.t.xianghuanji.com/");
        }
        return apiService;
    }
}
