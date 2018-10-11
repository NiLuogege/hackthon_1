package com.niluogege.example.module_user.mvp.presenter.setting;

import com.niluogege.example.commonsdk.base.mvp.BasePresenter;
import com.niluogege.example.commonsdk.network.DefaultObserver;
import com.niluogege.example.commonsdk.utils.RxUtils;
import com.niluogege.example.module_user.mvp.contract.setting.SettingContract;
import com.niluogege.example.module_user.mvp.model.business.setting.SettingModel;
import com.niluogege.example.module_user.mvp.model.entity.setting.AppSettingInfo;

/**
 * Created by niluogege on 2018/8/27.
 */

public class SettingPresenter extends BasePresenter<SettingContract.View,SettingModel> {


    public void initData() {

        mModel.loadSetting()
                .compose(RxUtils.simpleFlow(mViewRef.get()))
                .subscribe(new DefaultObserver<AppSettingInfo>() {
                    @Override
                    protected void onsuccess(AppSettingInfo appSettingInfo) {
                        mViewRef.get().onLoadSuccess(appSettingInfo);
                    }

                    @Override
                    protected void onFail(Throwable throwable) {
                        mViewRef.get().onLoadFail(throwable);
                    }
                });
    }

    @Override
    protected SettingModel initModel() {
        return new SettingModel();
    }
}
