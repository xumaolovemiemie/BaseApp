package com.wisdom.rxjava.base;


import io.reactivex.disposables.Disposable;

/**
 * Created by wisdom on 17/4/2.
 */

public interface BaseSubscriberContext {

    void addDisposable(Disposable d);
}
