package com.jh.framework.utils.cache;

import android.text.TextUtils;

import androidx.annotation.Keep;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Keep
@Entity(tableName = "byqp_cache")
public class Cache {
    @PrimaryKey
    private int key;
    private String data;
    @ColumnInfo(name = "save_time")
    private long saveTime;

    public Cache() {
        resetSaveTime();
    }

    @Ignore
    public Cache(int key, String data) {
        this.key = key;
        this.data = data;
        resetSaveTime();
    }

    private void resetSaveTime() {
        saveTime = System.currentTimeMillis();
    }

    public boolean hasValue() {
        return !TextUtils.isEmpty(data);
    }

    /**
     * 从保存开始到当前的时间
     */
    public long getSavedTime() {
        return System.currentTimeMillis() - saveTime;
    }

    public String getData() {
        return data;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }


    public void setData(String data) {
        this.data = data;
    }

    public long getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(long saveTime) {
        this.saveTime = saveTime;
    }

}
