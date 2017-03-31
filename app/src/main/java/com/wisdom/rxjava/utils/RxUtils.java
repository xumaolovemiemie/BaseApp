package com.wisdom.rxjava.utils;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by wisdom on 17/3/31.
 */

public class RxUtils {
    public static final class RetryWhenNoInternet
        implements Func1<Observable<? extends Throwable>, Observable<?>> {

        private final int _maxRetries;
        private final int _retryDelayMillis;
        private int _retryCount;

        public RetryWhenNoInternet(final int maxRetries, final int retryDelayMillis) {
            _maxRetries = maxRetries;
            _retryDelayMillis = retryDelayMillis;
            _retryCount = 0;
        }

        @Override
        public Observable<?> call(Observable<? extends Throwable> inputObservable) {
            return inputObservable.flatMap((Func1<Throwable, Observable<?>>) throwable -> {
                if (++_retryCount < _maxRetries && !WisdomUtils.isNetWorkAvilable()) {
                    Log.e("RetryWhenNoInternet", "call: " + _retryCount);
                    return Observable.timer(_retryCount * _retryDelayMillis, TimeUnit.MILLISECONDS);
                }
                return Observable.error(throwable);
            });
        }
    }
}
