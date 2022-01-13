package com.library.versionupdata;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.library.versionupdata.builder.DownloadBuilder;
import com.library.versionupdata.builder.RequestVersionBuilder;
import com.library.versionupdata.builder.UIData;
import com.library.versionupdata.http.HttpHelper;
import com.library.versionupdata.ui.VersionService;

public class VersionUpdateHelper {
    private static VersionUpdateHelper helper;

    private VersionUpdateHelper() {}

    public static VersionUpdateHelper getInstance() {
        if (helper == null)
            synchronized (VersionUpdateHelper.class) {
                if (helper == null)
                    helper = new VersionUpdateHelper();
            }
        return helper;
    }


    public void cancelAllMission(Context context) {
        HttpHelper.getHttpClient().dispatcher().cancelAll();
        Intent intent = new Intent(context, VersionService.class);
        VersionService.builder = null;
        context.getApplicationContext().stopService(intent);
    }

    /**
     * @param versionBundle developer should return version bundle ,to use when showing UI page,could be null
     * @return download builder for download setting
     */
    public DownloadBuilder downloadOnly(@Nullable UIData versionBundle) {
        return new DownloadBuilder(null, versionBundle);
    }

    /**
     * use request version function
     *
     * @return requestVersionBuilder
     */
    public RequestVersionBuilder requestVersion() {
        return new RequestVersionBuilder();
    }

}
