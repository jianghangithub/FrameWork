package com.library.versionupdata.net;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.library.versionupdata.VersionUpdateHelper;
import com.library.versionupdata.builder.DownloadBuilder;
import com.library.versionupdata.builder.RequestVersionBuilder;
import com.library.versionupdata.builder.UIData;
import com.library.versionupdata.callback.RequestVersionListener;
import com.library.versionupdata.http.HttpHelper;
import com.library.versionupdata.http.HttpRequestMethod;

import java.io.IOException;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author AllenLiu
 * @version 1.0
 * @date 2019/4/30
 * @since 1.0
 */
public class RequestVersionManager {
    public static RequestVersionManager getInstance() {
        return Holder.instance;
    }

    public static class Holder {
        static RequestVersionManager instance = new RequestVersionManager();

    }

    /**
     * 请求版本接口
     * #issue 239
     */
    public void requestVersion(final DownloadBuilder builder, final Context context) {
        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                RequestVersionBuilder requestVersionBuilder = builder.getRequestVersionBuilder();
                OkHttpClient client = HttpHelper.getHttpClient();
                HttpRequestMethod requestMethod = requestVersionBuilder.getRequestMethod();
                Request request = null;
                switch (requestMethod) {
                    case GET:
                        request = HttpHelper.get(requestVersionBuilder).build();
                        break;
                    case POST:
                        request = HttpHelper.post(requestVersionBuilder).build();
                        break;
                    case POSTJSON:
                        request = HttpHelper.postJson(requestVersionBuilder).build();
                        break;
                }
                final RequestVersionListener requestVersionListener = requestVersionBuilder.getRequestVersionListener();
                Handler handler = new Handler(Looper.getMainLooper());
                if (requestVersionListener != null) {
                    try {
                        final Response response = client.newCall(request).execute();
                        if (response.isSuccessful()) {
                            final String result = response.body() != null ? response.body().string() : null;

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    UIData versionBundle = requestVersionListener.onRequestVersionSuccess(result);
                                    if (versionBundle != null) {
                                        builder.setVersionBundle(versionBundle);
                                        builder.download(context);
                                    }
                                }
                            });

                        } else {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    requestVersionListener.onRequestVersionFailure(response.message());
                                    VersionUpdateHelper.getInstance().cancelAllMission(context);
                                }
                            });
                        }
                    } catch (final IOException e) {
                        e.printStackTrace();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                requestVersionListener.onRequestVersionFailure(e.getMessage());
                                VersionUpdateHelper.getInstance().cancelAllMission(context);
                            }
                        });

                    }
                } else {
                    throw new RuntimeException("using request version function,you must set a requestVersionListener");
                }
            }
        });

    }
}
