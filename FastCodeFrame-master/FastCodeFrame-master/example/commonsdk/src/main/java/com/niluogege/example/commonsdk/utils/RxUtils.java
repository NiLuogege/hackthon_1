package com.niluogege.example.commonsdk.utils;

import com.niluogege.example.commonsdk.base.BaseActivity;
import com.niluogege.example.commonsdk.base.mvp.IView;
import com.niluogege.example.commonsdk.network.ProgressHelper;
import com.niluogege.example.commonsdk.network.RetryWithDelay;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by niluogege on 2018/8/24.
 */

public class RxUtils {

    public static <T> ObservableTransformer<T, T> simpleFlow(BaseActivity activity) {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .compose(activity.bindToLifecycle())//compose方法需要在subscribeOn方法之后使用，因为在测试的过程中发现，将compose方法放在subscribeOn方法之前，如果在被观察者中执行了阻塞方法，比如Thread.sleep()，取消订阅后该阻塞方法不会被中断。
                .retryWhen(new RetryWithDelay())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ProgressHelper.applyProgressBar(activity));
    }

    public static <T> ObservableTransformer<T, T> simpleFlow(IView view) {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(view))
                .compose(ProgressHelper.applyProgressBar(view));
    }
}
