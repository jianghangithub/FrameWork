package com.jh.framework.utils.cache;

import static java.io.File.separator;

import android.os.Environment;


import com.library.view.log.Logger;

import java.io.File;
import java.io.FileWriter;

/**
 * 文件缓存
 * Created by l on 2017/9/20.
 */

public class FileCache {
    private static FileCache mInstace;

    private FileCache() {
    }

    public static FileCache getInstace() {
        if (mInstace == null) {
            synchronized (FileCache.class) {
                mInstace = new FileCache();
            }
        }
        return mInstace;
    }

    /*缓存根目录*/
//    public static final File SDRootPath = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ? Environment.getExternalStorageDirectory() : Environment.getStorageDirectory();
    private static final File SDRootPath = Environment.getExternalStorageDirectory();
    private static final String DIR_ROOT = "LotteryApp";/* 客户端缓存目录*/
    private static final String DIR_CACHE = "cache";/*缓存文件*/
    private static final String DIR_LOG = "log";/*日志*/
    private static final String DIR_DOWNLOAD = "download";/*下载文件缓存目录*/
    private static final String DIR_TEMPORARY = "filetemporary";/*临时缓存文件*/


    /*获得根目录*/
    public File getRootDir() {
        File file = new File(SDRootPath.getAbsolutePath() + separator + DIR_ROOT);
        return createFileDir(file) ? file : null;
    }

    public File getDownloadDir() {
        File file = new File(getRootDir() + separator + DIR_DOWNLOAD);
        return createFileDir(file) ? file : null;
    }

    public File getTemporaryDir() {
        File file = new File(getRootDir() + separator + DIR_TEMPORARY);
        return createFileDir(file) ? file : null;
    }

    public File getCacheDir() {
        File file = new File(getRootDir() + separator + DIR_CACHE);
        return createFileDir(file) ? file : null;
    }

    public File getLogDir() {
        File file = new File(getRootDir() + separator + DIR_LOG);
        return createFileDir(file) ? file : null;
    }


    public boolean createFileDir(File... files) {
        if (!existSDCard()) return false;
        for (File file : files) {
            if (!file.exists()) file.mkdirs();
        }
        return true;
    }

    public File createFile(File dir, String name) {
        if (!dir.exists()) {
            createFileDir(dir);
        }
        File file = new File(dir, name);
        return file;
    }

    /*检查SD卡是否可用*/
    public static boolean existSDCard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            Logger.d("FileCache", "检测到 SD卡不存在");
            return false;
        }
    }


    public static void saveString(File dir, String name, String str) {
        try {
            FileWriter fw = new FileWriter(Environment.getExternalStorageDirectory().getPath() + "aaa" + "/cmd.txt");//SD卡中的路径
            fw.flush();
            fw.write(str);
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
