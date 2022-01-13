package com.jh.framework.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {

    //存储的sharedpreferences文件名
    private static final String FILE_NAME = "user_info";

    /**
     * 保存数据到文件
     *
     * @param context 1
     * @param key 1
     * @param data 1
     */
    public static void saveData(Context context, String key, Object data) {

        String type = data.getClass().getSimpleName();
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        switch (type) {
            case "Integer":
                editor.putInt(key, (Integer) data);
                break;
            case "Boolean":
                editor.putBoolean(key, (Boolean) data);
                break;
            case "String":
                editor.putString(key, (String) data);
                break;
            case "Float":
                editor.putFloat(key, (Float) data);
                break;
            case "Long":
                editor.putLong(key, (Long) data);
                break;
        }

        editor.apply();
    }

    /**
     * 从文件中读取数据
     *
     * @param context 1
     * @param key 1
     * @param defValue 1
     * @return 1
     */
    public static Object getData(Context context, String key, Object defValue) {

        String type = defValue.getClass().getSimpleName();
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        //defValue为为默认值，如果当前获取不到数据就返回它
        switch (type) {
            case "Integer":
                return sharedPreferences.getInt(key, (Integer) defValue);
            case "Boolean":
                return sharedPreferences.getBoolean(key, (Boolean) defValue);
            case "String":
                return sharedPreferences.getString(key, (String) defValue);
            case "Float":
                return sharedPreferences.getFloat(key, (Float) defValue);
            case "Long":
                return sharedPreferences.getLong(key, (Long) defValue);
        }

        return null;
    }

    public static String getToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("token", "");
    }

    public static String getPhone(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("phonenum", "");
    }
}
