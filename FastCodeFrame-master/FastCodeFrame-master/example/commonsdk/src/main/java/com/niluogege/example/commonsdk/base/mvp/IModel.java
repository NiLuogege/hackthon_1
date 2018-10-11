package com.niluogege.example.commonsdk.base.mvp;

/**
 * Created by niluogege on 2018/8/23.
 * 每个 Model 都需要实现此类,以满足规范
 */
public interface IModel {

    /**
     * 在{@link IPresenter#dettach()} (Object)}中调用
     */
    void onDestory();

}
