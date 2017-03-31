package com.wisdom.rxjava.service.api;

import com.wisdom.rxjava.entity.PortalBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wisdom on 17/3/31.
 */

public interface ActivityApi {

    @GET("/rest/v1/portal")
    Observable<PortalBean> getPortalBean(
        @Query("exclude_fields") String exclude_fields
    );
}
