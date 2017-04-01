package com.wisdom.rxjava.service.api;

import com.wisdom.rxjava.entity.PortalBean;
import com.wisdom.rxjava.entity.ProductBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by wisdom on 17/3/31.
 */

public interface ActivityApi {

    @GET("/rest/v1/portal")
    Observable<PortalBean> getPortalBean(
        @Query("exclude_fields") String exclude_fields
    );

    @GET("/rest/v2/modelproducts/{id}")
    Observable<ProductBean> getProduct(
        @Path("id") int id
    );
}
