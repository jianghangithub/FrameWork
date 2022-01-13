package com.jh.framework.retrofit.observer;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.JsonParseException;
import com.google.gson.stream.MalformedJsonException;
import com.jh.framework.base.BaseView;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * Created by jh on 2018/5/2.
 */

public abstract class NoBaseObserver<T> extends DisposableObserver<T> {
    private BaseView view;
    /**
     * 解析数据失败
     */
    private static final int PARSE_ERROR = 1001;
    /**
     * 网络问题
     */
    private static final int BAD_NETWORK = 1002;
    /**
     * 连接错误
     */
    private static final int CONNECT_ERROR = 1003;
    /**
     * 连接超时
     */
    private static final int CONNECT_TIMEOUT = 1004;


    protected NoBaseObserver(BaseView view) {
        this.view = view;
    }

    @Override
    protected void onStart() {
        if (view != null) {
            view.showLoading();
        }
    }

    @Override
    public void onNext(@NonNull T model) {
        onSuccess(model);

    }

    @Override
    public void onError(Throwable e) {
        Log.e("---", e.toString());
        if (view != null) {
            view.hideLoading();
        }
        if (e instanceof HttpException) {
            //   HTTP错误
            onException(BAD_NETWORK);
        } else if (e instanceof ConnectException || e instanceof UnknownHostException) {
            //   连接错误
            onException(CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {
            //  连接超时
            onException(CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException || e instanceof MalformedJsonException) {
            //  解析错误
            onException(PARSE_ERROR);
        } else {
            onError("未知错误");
        }

    }

    private void onException(int unknownError) {
        switch (unknownError) {
            case CONNECT_ERROR:
                onError("连接错误");
                break;

            case CONNECT_TIMEOUT:
                onError("连接超时");
                break;

            case BAD_NETWORK:
                onError("网络问题");
                break;

            case PARSE_ERROR:
                onError("解析数据失败");
                break;

            default:
                break;
        }
    }


    @Override
    public void onComplete() {
        if (view != null) {
            view.hideLoading();
        }

    }

    public abstract void onSuccess(T o);

    protected abstract void onError(String msg);
}
