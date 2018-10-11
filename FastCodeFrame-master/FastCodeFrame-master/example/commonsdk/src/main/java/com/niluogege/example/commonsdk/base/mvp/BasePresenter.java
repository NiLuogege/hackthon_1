package com.niluogege.example.commonsdk.base.mvp;


import java.lang.ref.WeakReference;

/**
 * Created by niluogege on 2018/8/23.
 * <p>
 * 基类 Presenter
 */

public abstract class BasePresenter<V extends IView, M extends IModel> implements IPresenter<V> {
    protected WeakReference<V> mViewRef;
    protected M mModel;


    @Override
    public void attach(V view) {
        mViewRef = new WeakReference<>(view);
        mModel = initModel();

    }

    @Override
    public void dettach() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }

        if (mModel!=null) mModel.onDestory();
    }

    protected abstract M initModel();


}
