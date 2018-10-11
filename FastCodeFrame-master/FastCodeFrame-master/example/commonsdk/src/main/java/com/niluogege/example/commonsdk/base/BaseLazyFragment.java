package com.niluogege.example.commonsdk.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.niluogege.example.commonsdk.base.mvp.IPresenter;
import com.niluogege.example.commonsdk.base.mvp.IView;
import com.trello.rxlifecycle2.components.support.RxFragment;

/**
 * 懒加载Fragment
 */
public abstract class BaseLazyFragment<V extends IView,P extends IPresenter> extends RxFragment {


    protected P mPresenter = null;//如果当前页面逻辑简单, Presenter 可以为 null

    protected View mRootView;
    protected Context mContext;
    protected boolean isVisible;
    private boolean isPrepared;
    private boolean isFirst = true;

    //--------------------system method callback------------------------//


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mPresenter = initPresenter();
        mPresenter.attach((V)this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = initView(inflater, container, savedInstanceState);
        }

        return mRootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        initPrepare();
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            setUserVisibleHint(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.dettach();
        mPresenter = null;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }


    /**
     * 懒加载
     */
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        if (isFirst) {
            initData();
        } else {
            initDataAgain();
        }
        isFirst = false;
    }

    /**
     * 初始化presenter
     */
    protected abstract P initPresenter();


    /**
     * 在onActivityCreated中调用的方法，可以用来进行初始化操作。
     */
    protected abstract void initPrepare();

    /**
     * fragment被设置为不可见时调用
     */
    protected abstract void onInvisible();

    /**
     * 加载数据 isFirst 为true的情况下调用
     */
    protected abstract void initData();

    /**
     * 加载数据 isFirst 为false的情况下调用
     */
    protected void initDataAgain() {

    }

    /**
     * 初始化布局，请不要把耗时操作放在这个方法里，这个方法用来提供一个
     * 基本的布局而非一个完整的布局，以免ViewPager预加载消耗大量的资源。
     */
    protected abstract View initView(LayoutInflater inflater,
                                     @Nullable ViewGroup container,
                                     @Nullable Bundle savedInstanceState);
}
