package com.jh.framework.retrofit.interceptor;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ParamInterceptor implements Interceptor {
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        String public_param_type = request.header("public_param_type");
        Request.Builder requestBuilder = request.newBuilder();
        HttpUrl.Builder urlBuilder = request.url().newBuilder();
        //增加请求公共参数
        urlBuilder.addQueryParameter("key1", "value1")
                .addQueryParameter("key2", "value2");
        if (TextUtils.isEmpty(public_param_type) || !public_param_type.equalsIgnoreCase("no_time")) {
            //header含有notime标识，不添加t公共参数
            //用于特殊处理游客登录时保证验证码里面的时间戳与参数传递一致
            urlBuilder.addQueryParameter("t", String.valueOf(System.currentTimeMillis() / 1000));
        }
        HttpUrl httpUrl = urlBuilder.build();
        requestBuilder.url(httpUrl);

        return chain.proceed(requestBuilder.build());
    }

}
