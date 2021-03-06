package com.wisdom.rxjava.module;

import com.wisdom.rxjava.entity.PortalBean;
import com.wisdom.rxjava.entity.ProductBean;
import com.wisdom.rxjava.service.RetrofitClient;
import com.wisdom.rxjava.service.api.ActivityApi;
import com.wisdom.rxjava.utils.DefaultTransform;

import io.reactivex.Observable;


/**
 * Created by wisdom on 17/3/31.
 */

public class ActivityModule {
    private static ActivityModule module = new ActivityModule();

    public static ActivityModule getInstance() {
        return module;
    }

    public Observable<PortalBean> getPortalBean(String exclude_fields) {
        return RetrofitClient.createAdapter()
            .create(ActivityApi.class)
            .getPortalBean(exclude_fields)
            .compose(new DefaultTransform<>());
    }

    public Observable<ProductBean> getProduct(int id) {
        return RetrofitClient.createAdapter()
            .create(ActivityApi.class)
            .getProduct(id)
            .compose(new DefaultTransform<>());
    }
}
