package com.wisdom.rxjava.service;

import com.wisdom.rxjava.base.BaseSubscriberContext;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wisdom on 17/3/31.
 */

public class CustomSubscriber<T> implements Observer<T> {
    private BaseSubscriberContext context;

    protected CustomSubscriber(BaseSubscriberContext context) {
        this.context = context;
    }

    @Override
    public void onSubscribe(Disposable d) {
        context.addDisposable(d);
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
