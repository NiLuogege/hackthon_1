package com.niluogege.example.module_user.mvp.contract.setting;

import com.niluogege.example.commonsdk.base.mvp.IModel;
import com.niluogege.example.commonsdk.base.mvp.IView;
import com.niluogege.example.module_user.mvp.model.entity.setting.AppSettingInfo;

/**
 * Created by niluogege on 2018/8/27.
 */

public interface SettingContract {

    interface View extends IView {
        void onLoadSuccess(AppSettingInfo appSettingInfo);

        void onLoadFail(Throwable throwable);
    }

    interface Model extends IModel {

    }

}
