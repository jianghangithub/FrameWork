package com.jh.framework.retrofit;

import android.annotation.SuppressLint;

import com.jh.framework.config.AppConfig;
import com.jh.framework.retrofit.interceptor.LogInterceptor;
import com.jh.framework.utils.AppUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by JH on 2017/11/22.
 */

public class RetrofitHelper {

    //缓存容量
    private final long SIZE_OF_CACHE = 10 * 1024 * 1024; // 10 MiB
    //缓存路径
    private final String cacheFile = AppUtils.getApp().getCacheDir() + "/http";
    private final Cache cache = new Cache(new File(cacheFile), SIZE_OF_CACHE);
    private final OkHttpClient client = new OkHttpClient.Builder()
            //无网络时的拦截器
            .addInterceptor(new LogInterceptor())
            .cache(cache)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build();


    @SuppressLint("StaticFieldLeak")
    private static volatile RetrofitHelper instance = null;
    private Retrofit mRetrofit = null;

    public static RetrofitHelper getInstance() {
        if (instance == null) {
            synchronized (RetrofitHelper.class) {
                if (instance == null) {
                    instance = new RetrofitHelper();
                }
            }
        }
        return instance;
    }

    private RetrofitHelper() {
        init();
    }

    private void init() {
        resetApp();
    }

    private void resetApp() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.baseUrlDebug)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    public void setUrl(String url) {
//        mRetrofit.
    }

    public RetrofitService getServer() {
        return mRetrofit.create(RetrofitService.class);
    }
}
