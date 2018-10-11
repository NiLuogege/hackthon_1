package com.niluogege.example.module_user;


import com.niluogege.example.module_user.mvp.model.entity.setting.AppSettingInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by dell on 2017/4/1.
 */

public interface IdeaApiService {

    /**
     * 获取baseUrl
     */
    @Headers("cacheTime:" + 60 * 5)
    @GET("app/setting")
    Observable<AppSettingInfo> getAppSetting2();


}
