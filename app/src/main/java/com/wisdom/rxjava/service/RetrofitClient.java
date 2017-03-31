package com.wisdom.rxjava.service;

import android.os.Build;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.wisdom.rxjava.BaseApp;
import com.wisdom.rxjava.BuildConfig;
import com.wisdom.rxjava.utils.WisdomUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wisdom on 17/3/31.
 */

public class RetrofitClient {

    private static OkHttpClient mOkHttpClient;
    private static OkHttpClient.Builder builder;

    static {
        builder = initOkHttpClient();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor).addNetworkInterceptor(new StethoInterceptor());
        }
        mOkHttpClient = builder.build();
    }

    public static Retrofit createAdapter() {
        String baseUrl = "https://m.xiaolumeimei.com";
        return new Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(mOkHttpClient)
            .build();
    }

    private static OkHttpClient.Builder initOkHttpClient() {
        if (mOkHttpClient == null) {
            synchronized (RetrofitClient.class) {
                if (mOkHttpClient == null) {
                    Cache cache = new Cache(new File(BaseApp.getInstance().getCacheDir(), "OkHttpCache"),
                        1024 * 1024 * 100);
                    builder = new OkHttpClient.Builder()
                        .readTimeout(20, TimeUnit.SECONDS)
                        .connectTimeout(20, TimeUnit.SECONDS)
                        .writeTimeout(20, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true)
                        .cache(cache)
                        .addInterceptor(chain -> {
                            Request request = chain.request();
                            if (WisdomUtils.isNetWorkAvilable()) {
                                request = request.newBuilder()
                                    .header("User-Agent", "Android/" + Build.VERSION.RELEASE + " xlmmApp/"
                                        + String.valueOf(BuildConfig.VERSION_CODE) + " Mobile/"
                                        + Build.MODEL + " NetType/" + WisdomUtils.getNetType())
                                    .build();
                            } else {
                                request = request.newBuilder()
                                    .cacheControl(CacheControl.FORCE_CACHE)
                                    .header("User-Agent", "Android/" + Build.VERSION.RELEASE + " xlmmApp/"
                                        + String.valueOf(BuildConfig.VERSION_CODE) + " Mobile/"
                                        + Build.MODEL + " NetType/" + WisdomUtils.getNetType())
                                    .build();
                            }
                            okhttp3.Response response = chain.proceed(request);
                            if (WisdomUtils.isNetWorkAvilable()) {
                                int maxAge = 60 * 60;
                                response.newBuilder()
                                    .removeHeader("Pragma")
                                    .header("Cache-Control", "public, max-age=" + maxAge)
                                    .build();
                            } else {
                                int maxStale = 60 * 60 * 24 * 28;
                                response.newBuilder()
                                    .removeHeader("Pragma")
                                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                    .build();
                            }
                            return chain.proceed(request);
                        })
                        .cookieJar(new CookieJar() {
                            private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

                            @Override
                            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                                cookieStore.put(url, cookies);
                            }

                            @Override
                            public List<Cookie> loadForRequest(HttpUrl url) {
                                List<Cookie> cookies = cookieStore.get(url);
                                return cookies != null ? cookies : new ArrayList<>();
                            }
                        });
                }
            }
        }
        return builder;
    }

}

