package com.jh.framework.utils.cache;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.jh.framework.utils.AppUtils;


@Database(entities = {Cache.class}, version = 1)
public abstract class CacheDatabase extends RoomDatabase {
    private static CacheDatabase instance;

    public abstract CacheDao cacheDao();

    public static void init(Context context) {
        instance = Room.databaseBuilder(context.getApplicationContext(), CacheDatabase.class, "cache.db")
                .fallbackToDestructiveMigration()
                .addMigrations(MIGRATION_1_2)
                .allowMainThreadQueries()
                .build();
    }

    public static CacheDatabase getInstance() {
        if (instance == null) {
            synchronized (CacheDatabase.class) {
                if (instance == null)
                    init(AppUtils.getApp());
            }
        }
        return instance;
    }


    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
//                    为旧表添加新的字段
            database.execSQL("ALTER TABLE 'User' ADD COLUMN 'UserType' INTEGER NOT NULL DEFAULT 0");
        }
    };


}
