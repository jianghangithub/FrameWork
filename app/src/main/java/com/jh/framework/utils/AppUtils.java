package com.jh.framework.utils;

import android.annotation.SuppressLint;
import android.app.Application;

/**
 * <pre>
 * desc:
 *
 * function:
 *
 * Created by admin on 2018/7/31.
 * </pre>
 */
public class AppUtils {
    private static Application app;

    private AppUtils() {
    }

    /**
     * 不依赖APP或者Context获得Application
     *
     * @param <T> 项目中配置的Application类
     * @return Application
     */
    public static <T extends Application> T getApp() {
        if (app == null) {
            try {
                @SuppressLint("PrivateApi") Class<?> activityThread = Class.forName("android.app.ActivityThread");
                Object at = activityThread.getMethod("currentActivityThread").invoke(null);
                Object app = activityThread.getMethod("getApplication").invoke(at);
                AppUtils.app = (Application) app;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return (T) app;
    }
}
