package com.niluogege.example.module_user.mvp.model.business.setting;

import com.niluogege.example.module_user.RestfulApi;
import com.niluogege.example.module_user.mvp.contract.setting.SettingContract;
import com.niluogege.example.module_user.mvp.model.entity.setting.AppSettingInfo;

import io.reactivex.Observable;

/**
 * Created by niluogege on 2018/8/27.
 */

public class SettingModel implements SettingContract.Model {


    public Observable<AppSettingInfo> loadSetting() {

        return RestfulApi.getSettingApiService().getAppSetting2();
    }

    @Override
    public void onDestory() {

    }
}
