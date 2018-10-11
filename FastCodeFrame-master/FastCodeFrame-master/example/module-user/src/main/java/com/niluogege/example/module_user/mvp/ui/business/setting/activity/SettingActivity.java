package com.niluogege.example.module_user.mvp.ui.business.setting.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.niluogege.example.commonsdk.base.BaseActivity;
import com.niluogege.example.commonsdk.utils.ARoutePath;
import com.niluogege.example.commonsdk.utils.ToastUtils;
import com.niluogege.example.module_user.R;
import com.niluogege.example.module_user.mvp.contract.setting.SettingContract;
import com.niluogege.example.module_user.mvp.model.entity.setting.AppSettingInfo;
import com.niluogege.example.module_user.mvp.presenter.setting.SettingPresenter;

/**
 * Created by niluogege on 2018/8/27.
 */
@Route(path = ARoutePath.USER_SETTING)
public class SettingActivity extends BaseActivity<SettingContract.View, SettingPresenter> implements SettingContract.View {

    private TextView tv;

    @Override
    protected SettingPresenter initPresenter() {
        return new SettingPresenter();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_setting);
        tv = findViewById(R.id.tv);
    }

    public void click(View view) {
        if (mPresenter != null) mPresenter.initData();
    }


    @Override
    public void onLoadSuccess(AppSettingInfo appSettingInfo) {
        tv.setText(appSettingInfo.toString());
    }

    @Override
    public void onLoadFail(Throwable throwable) {
        ToastUtils.show(throwable.getLocalizedMessage());
    }

}
