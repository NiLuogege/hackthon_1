package com.niluogege.example.module_user;

import com.niluogege.example.commonsdk.network.RetrofitManager;

/**
 * Created by niluogege on 2018/8/23.
 */

public class RestfulApi {
    private static IdeaApiService ideaApiService = null;
    private static IdeaApiService settingApiService = null;

    public static IdeaApiService getIdeaApiService() {
        if (ideaApiService == null) {
            ideaApiService = RetrofitManager.getInstence().getRetrofit(IdeaApiService.class, "http://gank.io/api/data/");
        }
        return ideaApiService;
    }

    public static IdeaApiService getSettingApiService() {
        if (settingApiService == null) {
            settingApiService = RetrofitManager.getInstence().getRetrofit(IdeaApiService.class, "https://xhj.aihuishou.com/");
        }
        return settingApiService;
    }
}
