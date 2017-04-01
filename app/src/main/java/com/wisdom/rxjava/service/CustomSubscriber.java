package com.wisdom.rxjava.service;

import com.wisdom.rxjava.base.BaseActivity;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wisdom on 17/3/31.
 */

public class CustomSubscriber<T> implements Observer<T> {
    private BaseActivity activity;

    protected CustomSubscriber(BaseActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onSubscribe(Disposable d) {
        activity.addDisposable(d);
    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onComplete() {

    }
}
