package com.jh.framework.utils.cache;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CacheDao {
    @Query("select * from byqp_cache")
    List<Cache> getAllCache();

    @Query("select * from byqp_cache where `key`=:key limit 1")
    Cache getCacheBean(int key);

    @Query("select data from byqp_cache where `key`=:key limit 1")
    String getCacheData(int key);

    @Query("select save_time from byqp_cache where `key`=:key limit 1")
    long getCacheTime(int key);

    @Query("select * from byqp_cache where `key`=:key limit 1")
    LiveData<Cache> getLDCache(int key);

    @Query("select data from byqp_cache where `key`=:key limit 1")
    String getCacheStr(int key);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Cache cache);

    @Delete
    void delete(Cache cache);
}
