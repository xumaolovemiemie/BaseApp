package com.wisdom.rxjava.utils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wisdom on 17/3/31.
 */

public class DefaultTransform<T> implements Observable.Transformer<T, T> {
    @Override
    public Observable<T> call(Observable<T> tObservable) {
        return tObservable
            .doOnError(throwable -> WisdomUtils.Log(throwable.getLocalizedMessage()))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread(), true)
            .retryWhen(new RxUtils.RetryWhenNoInternet(2, 2000));
    }
}
