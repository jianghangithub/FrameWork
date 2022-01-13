package com.jh.framework.utils.cache;

import android.text.TextUtils;

public class CacheDataUtil {
    public static final int KEY_CHACHE_MENU_CONFIG = 0;
    public static final int KEY_USER_INFO = 1;

    //baseUrl缓存
    public static final int KEY_BASE_URL = 4;

    public static String getCacheData(int key) {
        CacheDao cacheDao = CacheDatabase.getInstance().cacheDao();
        if (cacheDao == null) return "";
        String cacheData = cacheDao.getCacheData(key);
        if (TextUtils.isEmpty(cacheData)) return "";
        return cacheData;
    }

    public static long getCacheTime(int key) {
        CacheDao cacheDao = CacheDatabase.getInstance().cacheDao();
        return cacheDao.getCacheTime(key);
    }

    public static long getSaveTime(int key) {
        return System.currentTimeMillis() - getCacheTime(key);
    }

    public static void save(int key, String data) {
        CacheDao cacheDao = CacheDatabase.getInstance().cacheDao();
        Cache cache = new Cache(key, data);
        cacheDao.insert(cache);
    }


}
