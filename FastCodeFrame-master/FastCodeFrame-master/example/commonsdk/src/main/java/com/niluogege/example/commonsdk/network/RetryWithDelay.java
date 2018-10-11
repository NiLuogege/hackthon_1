package com.niluogege.example.commonsdk.network;

import android.util.Log;

import com.niluogege.example.commonsdk.utils.ILog;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class RetryWithDelay implements Function<Observable<Throwable>, ObservableSource<?>> {
    private static final int MAX_RETRIES = 3;//重试次数
    private static final int DELAY_MILLIS = 2;//重试间隔


    private final String TAG = this.getClass().getSimpleName();
    private final int maxRetries;
    private final int retryDelaySecond;
    private int retryCount;

    public RetryWithDelay() {
        this(MAX_RETRIES, DELAY_MILLIS);
    }

    public RetryWithDelay(int maxRetries, int retryDelaySecond) {
        this.maxRetries = maxRetries;
        this.retryDelaySecond = retryDelaySecond;
    }

    @Override
    public ObservableSource<?> apply(@NonNull Observable<Throwable> throwableObservable) throws Exception {
        return throwableObservable
                .flatMap((Function<Throwable, ObservableSource<?>>) throwable -> {
                    if (++retryCount <= maxRetries) {
                        // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
                        ILog.d(TAG, "Observable get error, it will try after " + retryDelaySecond + " second, retry count " + retryCount);
                        return Observable.timer(retryDelaySecond, TimeUnit.SECONDS);
                    }
                    // Max retries hit. Just pass the error along.
                    return Observable.error(throwable);
                });
    }
}