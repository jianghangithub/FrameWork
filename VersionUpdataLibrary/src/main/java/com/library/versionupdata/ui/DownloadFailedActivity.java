package com.library.versionupdata.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.library.versionupdata.VersionUpdateHelper;
import com.library.versionupdata.R;
import com.library.versionupdata.eventbus.AllenEventType;
import com.library.versionupdata.utils.ALog;
import com.library.versionupdata.utils.AllenEventBusUtil;

public class DownloadFailedActivity extends AllenBaseActivity implements DialogInterface.OnCancelListener {
    private Dialog downloadFailedDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showDowloadFailedDialog();
    }

    @Override
    public void showDefaultDialog() {
        downloadFailedDialog = new AlertDialog.Builder(this).setMessage(getString(R.string.versionchecklib_download_fail_retry)).setPositiveButton(getString(R.string.versionchecklib_confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                retryDownload();
            }
        }).setNegativeButton(getString(R.string.versionchecklib_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onCancel(downloadFailedDialog);
            }
        }).create();
        downloadFailedDialog.setCanceledOnTouchOutside(false);
        downloadFailedDialog.setCancelable(true);
        downloadFailedDialog.show();
    }

    @Override
    public void showCustomDialog() {
        if (getVersionBuilder() != null) {
            downloadFailedDialog = getVersionBuilder().getCustomDownloadFailedListener().getCustomDownloadFailed(this, getVersionBuilder().getVersionBundle());
            View retryView = downloadFailedDialog.findViewById(R.id.versionchecklib_failed_dialog_retry);
            if (retryView != null) {
                retryView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        retryDownload();
                    }
                });
            }
            View cancelView = downloadFailedDialog.findViewById(R.id.versionchecklib_failed_dialog_cancel);
            if (cancelView != null) {
                cancelView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onCancel(downloadFailedDialog);
                    }
                });
            }
            downloadFailedDialog.show();
        }
    }

    private void showDowloadFailedDialog() {
        AllenEventBusUtil.sendEventBus(AllenEventType.CLOSE_DOWNLOADING_ACTIVITY);

        if (getVersionBuilder() != null && getVersionBuilder().getCustomDownloadFailedListener() != null) {
            ALog.e("show customization failed dialog");
            showCustomDialog();
        } else {
            ALog.e("show default failed dialog");
            showDefaultDialog();
        }
        downloadFailedDialog.setOnCancelListener(this);
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {
        ALog.e("on cancel");
        cancelHandler();
        checkForceUpdate();
        VersionUpdateHelper.getInstance().cancelAllMission(this);
        finish();
    }

    private void retryDownload() {
        AllenEventBusUtil.sendEventBus(AllenEventType.START_DOWNLOAD_APK);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (downloadFailedDialog != null && downloadFailedDialog.isShowing())
            downloadFailedDialog.dismiss();
//        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (downloadFailedDialog != null && !downloadFailedDialog.isShowing())
            downloadFailedDialog.show();
    }
}