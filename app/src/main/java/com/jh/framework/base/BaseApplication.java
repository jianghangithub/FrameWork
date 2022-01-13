package com.jh.framework.base;

import android.app.Application;

import com.library.view.log.Logger;

/**
 * Created by JH on 2017/11/22.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init();
    }
}
