package com.niluogege.example.commonsdk.network;

import com.google.gson.JsonParseException;
import com.niluogege.example.commonsdk.network.exception.ApiException;
import com.niluogege.example.commonsdk.utils.ToastUtils;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by niluogege on 2018/8/23.
 * <p>
 * 默认处理请求结果的 Observer
 */

public abstract class DefaultObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(T t) {
        onsuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException) {     //HTTP错误
            ToastUtils.show("服务器异常");
        } else if (e instanceof ConnectException || e instanceof UnknownHostException) {   //连接错误
            ToastUtils.show("网络连接失败,请检查网络");
        } else if (e instanceof InterruptedIOException) {   //连接超时
            ToastUtils.show("连接超时,请稍后再试");
        } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {   //解析错误
            ToastUtils.show("解析服务器响应数据失败");
        } else if (e instanceof ApiException) {
            ToastUtils.show(e.getLocalizedMessage());
        }

        e.printStackTrace();
        onFail(e);
    }

    @Override
    public void onComplete() {

    }

    /**
     * 数据访问成功
     *
     * @param t
     */
    protected abstract void onsuccess(T t);

    /**
     * 数据访问失败
     *
     * @param throwable
     */
    protected abstract void onFail(Throwable throwable);
}
