package com.library.versionupdata.callback;

import android.app.Dialog;
import android.content.Context;

import com.library.versionupdata.builder.UIData;

/**
 * Created by allenliu on 2018/1/18.
 */

public interface CustomDownloadingDialogListener {
    Dialog getCustomDownloadingDialog(Context context, int progress, UIData versionBundle);

    void updateUI(Dialog dialog, int progress, UIData versionBundle);
}
