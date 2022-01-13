package com.jh.framework.retrofit.interceptor;

import android.util.Log;

import androidx.annotation.NonNull;

import com.jh.framework.utils.AppUtils;
import com.jh.framework.utils.NetUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by JH on 2018/4/23.
 */

public class GetParamInterceptor implements Interceptor {
    private static final int TIMEOUT_DISCONNECT = 60 * 60 * 24 * 7; //7天

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();

        boolean netAvailable = NetUtils.isNetworkConnected(AppUtils.getApp());

        Request.Builder builder = request.newBuilder().url(getHttpUrl(request));
        if (netAvailable) {
            request = builder
                    //网络可用 强制从网络获取数据
                    .cacheControl(CacheControl.FORCE_NETWORK)
                    .build();
        } else {
            request = builder
                    //网络不可用 从缓存获取
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        Response response = chain.proceed(request);

        MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        Log.e("---", response.request().url().toString());
        if (netAvailable) {
            response = response.newBuilder().body(ResponseBody.create(mediaType, content)).build();
        } else {
            Log.e("---", "无网络");
            response = response.newBuilder().removeHeader("Pragma")
                    // 无网络时，设置超时为1周
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + TIMEOUT_DISCONNECT)
                    .build();
        }
        return response;
    }

    private HttpUrl getHttpUrl(Request original) {
        HttpUrl originalHttpUrl = original.url();
        String token = "MIEftFMl+/xuOFwWYROi7uuHDvpbj4pSXfflsgDh99/zVf484YQn5PexxDcIs1wL/7a2CNr5MjyMtbuFeIkQV6ooZfHUCIxkDCmIGEO/XWx7zF9LRoE/y963eoe4yKBOa3ZU+/3FJrpnf5yiVGgcOqruP+3F+dKdHB6fxroSmJbHCNJksb7lGiPofRih/CZsQOkueSgQmsTPe9ACqB6dSCvG9VXeFZReDasvg4HCpCxqnpmyUvuaocq6Wg/oMvwsR9B0Je62LodLbaWIYT6MWpAikYh46bQgpTtfBoU2pU/yjARj28ynW2iZUwD0aH+0cCw82jXj+Nyf/I1E000seMbOm+hOHYLrEvWEAJKDW33YAiUSXjdwZcRzBIvINAH5OqrTzek9HmFtFG+sWRuGDvpYF1BZsD191sTMT3BfL9sJVMXjHcUIssAfJ2ravhNEk1Pzv0Jte6OzpmwVj/AgWFLk1OOJexPU/PaqgM4iGsCDfRYGPxANulmCSUIl60OVUXUPrsbnGpLxI3ixyRSk4IXDFuT9oMHs5IDj9X7UjXdgjMgm95B/33JUH2culnaNwiGY2ZDZr4ar/hp3tKFQTr5Z3NQCThOs5TOgKZ/aufvtlbPONm3Etxf8hgUMM2wT1B/M3MBrCr+rpdQXzOtzVHAfUutNb3TWnZFAhQU5xK+y4O8tIzeB76fJLxMgsbv2sXcZyEXGcfItu4iPD2/jcv0xT9Q+GAeJoCUD8jwwb9HbJQyXifXJp5urXRTG+RKobH+NsSQH1IPKy2kkIYaldRKSpSzzT04viOmG1rVisdxgy50qnW9Jh/YIqRyI8ZzvNNTYNEVvCK+jueyn4oD7Pn5nvmdIve+LdVtNiSAUpi5EPLK80TwihMkjoE2QTXHxslZOWxhx+Irz8mHZOk3yPQWJ7WNgBmB4zX5UQK2i34Y9QIED9rUN8y1PDkSKIiqSKvne7nsP4jNnIvHMyFmj09TGHzHtxLf4bqUU3ADGNChPXlAMwCdwkOrRltwcZWw3UAyuHJT+Sz7IDPaiO2fSQNjnYxQem/trYxQ+XcDC9bdVDhoDn7Z42dDHQjRmqz6AxdJiop1xNyDfqL7/64stM93vFDiKTeS6Jonqj51MxZsKWPjuh6dAjAJl9RQjerTsq8yg440Qay1p3ipoi7khP4T6YDOJTdeJy9FiJ+QNj0SxnLfRA1nqJA==";
//        try {
//            token = URLEncoder.encode(token, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        token = "MIEftFMl%2B%2FxuOFwWYROi7uuHDvpbj4pSXfflsgDh99%2FzVf484YQn5PexxDcIs1wL%2F7a2CNr5MjyMtbuFeIkQV6ooZfHUCIxkDCmIGEO%2FXWx7zF9LRoE%2Fy963eoe4yKBOa3ZU%2B%2F3FJrpnf5yiVGgcOqruP%2B3F%2BdKdHB6fxroSmJbHCNJksb7lGiPofRih%2FCZsQOkueSgQmsTPe9ACqB6dSCvG9VXeFZReDasvg4HCpCxqnpmyUvuaocq6Wg%2FoMvwsR9B0Je62LodLbaWIYT6MWpAikYh46bQgpTtfBoU2pU%2FyjARj28ynW2iZUwD0aH%2B0cCw82jXj%2BNyf%2FI1E000seMbOm%2BhOHYLrEvWEAJKDW33YAiUSXjdwZcRzBIvINAH5OqrTzek9HmFtFG%2BsWRuGDvpYF1BZsD191sTMT3BfL9sJVMXjHcUIssAfJ2ravhNEk1Pzv0Jte6OzpmwVj%2FAgWFLk1OOJexPU%2FPaqgM4iGsCDfRYGPxANulmCSUIl60OVUXUPrsbnGpLxI3ixyRSk4IXDFuT9oMHs5IDj9X7UjXdgjMgm95B%2F33JUH2culnaNwiGY2ZDZr4ar%2Fhp3tKFQTr5Z3NQCThOs5TOgKZ%2FaufvtlbPONm3Etxf8hgUMM2wT1B%2FM3MBrCr%2BrpdQXzOtzVHAfUutNb3TWnZFAhQU5xK%2By4O8tIzeB76fJLxMgsbv2sXcZyEXGcfItu4iPD2%2Fjcv0xT9Q%2BGAeJoCUD8jwwb9HbJQyXifXJp5urXRTG%2BRKobH%2BNsSQH1IPKy2kkIYaldRKSpSzzT04viOmG1rVisdxgy50qnW9Jh%2FYIqRyI8ZzvNNTYNEVvCK%2Bjueyn4oD7Pn5nvmdIve%2BLdVtNiSAUpi5EPLK80TwihMkjoE2QTXHxslZOWxhx%2BIrz8mHZOk3yPQWJ7WNgBmB4zX5UQK2i34Y9QIED9rUN8y1PDkSKIiqSKvne7nsP4jNnIvHMyFmj09TGHzHtxLf4bqUU3ADGNChPXlAMwCdwkOrRltwcZWw3UAyuHJT%2BSz7IDPaiO2fSQNjnYxQem%2FtrYxQ%2BXcDC9bdVDhoDn7Z42dDHQjRmqz6AxdJiop1xNyDfqL7%2F64stM93vFDiKTeS6Jonqj51MxZsKWPjuh6dAjAJl9RQjerTsq8yg440Qay1p3ipoi7khP4T6YDOJTdeJy9FiJ%2BQNj0SxnLfRA1nqJA%3D%3D";
        return originalHttpUrl.newBuilder()
                .addQueryParameter("key", "value")
                .addQueryParameter("key1", "value1")
                .build();
    }
}
