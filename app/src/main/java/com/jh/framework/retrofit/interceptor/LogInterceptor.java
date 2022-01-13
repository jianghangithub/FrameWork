package com.jh.framework.retrofit.interceptor;

import androidx.annotation.NonNull;


import com.library.view.log.Logger;

import java.io.IOException;
import java.net.URLDecoder;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class LogInterceptor implements Interceptor {
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        String url = request.url().toString();
        StringBuilder sb = new StringBuilder();
        if ("POST".equalsIgnoreCase(request.method())) {
            sb.append("Post Param:");
            sb.append(getParam(request.body()));
        }
        Response response = chain.proceed(request);
        ResponseBody body = response.body();
        MediaType mediaType = body.contentType();
        String result = body.string();
        Logger.i("url: %s \n %s \nResult: %s ", url, sb.toString(), result);
        return response.newBuilder().body(ResponseBody.create(mediaType, result)).build();
    }

    /**
     * 读取参数
     *
     * @param requestBody requestBody
     * @return requestBody
     */
    private String getParam(RequestBody requestBody) {
        if (requestBody == null) return "null";
        Buffer buffer = new Buffer();
        String logparm;
        try {
            requestBody.writeTo(buffer);
            logparm = buffer.readUtf8();
            logparm = URLDecoder.decode(logparm, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
            return "null";
        }
        return logparm;
    }
}
