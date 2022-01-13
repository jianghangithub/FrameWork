package com.jh.framework.ui.main;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import java.lang.ref.WeakReference;

public class BannerViewHolder extends Handler implements LifecycleObserver {
    private WeakReference<Context> weakReference;

    public BannerViewHolder(Context context) {
        weakReference = new WeakReference<>(context);
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case 0:
                sendEmptyMessageDelayed(0, 3000);
                break;
            case 1:
                removeMessages(0);
                break;
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void onStart() {
        sendEmptyMessage(0);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void onStop() {
        sendEmptyMessage(1);
    }
}
