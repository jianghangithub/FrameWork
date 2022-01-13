package com.library.versionupdata.callback;

import android.app.Dialog;
import android.content.Context;

import com.library.versionupdata.builder.UIData;

/**
 * Created by allenliu on 2018/1/18.
 */

public interface CustomVersionDialogListener {
    Dialog getCustomVersionDialog(Context context, UIData versionBundle);
}
