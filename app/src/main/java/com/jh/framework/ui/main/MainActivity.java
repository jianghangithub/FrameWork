package com.jh.framework.ui.main;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.jh.framework.R;
import com.jh.framework.base.MVPActivity;
import com.jh.framework.ui.main.model.HomeData;
import com.jh.framework.ui.main.presenter.MainPresenter;
import com.jh.framework.ui.main.view.MainView;
import com.jh.framework.ui.test.AmapActivity;
import com.jh.framework.ui.test.PhoneInfoActivity;
import com.jh.framework.utils.glide.GlideEngine;
import com.library.takephoto.EasyPhotos;
import com.library.takephoto.setting.Setting;
import com.library.ucrop.UCropHelper;
import com.library.versionupdata.VersionUpdateHelper;
import com.library.versionupdata.builder.DownloadBuilder;
import com.library.versionupdata.builder.NotificationBuilder;
import com.library.versionupdata.builder.UIData;
import com.library.versionupdata.callback.APKDownloadListener;
import com.library.versionupdata.callback.CustomVersionDialogListener;
import com.library.versionupdata.callback.OnCancelListener;
import com.library.versionupdata.callback.RequestVersionListener;
import com.library.view.log.Logger;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends MVPActivity<MainPresenter> implements MainView {
    ImageView mImageView;
    TextView tv_anim;

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        // JPermissionUtil.requestAllPermission(this);
        // presenter.getMainPageInfo();
    }


    public void onCheckUpdate(View v) {
        DownloadBuilder downloadBuilder = VersionUpdateHelper.getInstance()
                .requestVersion()
                .setRequestUrl("https://www.baidu.com")
                .request(new RequestVersionListener() {
                    @Nullable
                    @Override
                    public UIData onRequestVersionSuccess(String result) {
                        Log.e("----", "success");
                        return UIData.create()
                                .setTitle("版本升级")
                                .setContent("1.更新内容")
                                .setDownloadUrl("http://test-1251233192.coscd.myqcloud.com/1_1.apk");
                    }

                    @Override
                    public void onRequestVersionFailure(String message) {
                        Log.e("----", "ErrorMsg=" + message);
                    }
                });

        downloadBuilder.setForceRedownload(false)//是否强制更新
                .setApkDownloadListener(new MyAPKDownloadListener())//强制更新点击取消后的操作
                .setSilentDownload(false)//静默下载
                .setForceRedownload(false)//无论本地是否有缓存的APK，都重新下载
                .setShowDownloadingDialog(true)//显示下载进度
                .setShowNotification(true)//显示Notification
                .setNotificationBuilder(createCustomNotification())//构建Notification
                .setShowDownloadFailDialog(true)//显示下载失败弹窗
                .setDirectDownload(false)//直接安装（不弹窗提示用户点击安装）
                .setOnCancelListener(new MyCancleClickListener())//取消下载监听
                .setCustomVersionDialogListener(createCustomDialog())
                .setDownloadAPKPath(Environment.getExternalStorageDirectory() + "/Download/")
                .executeMission(this);
    }

    private CustomVersionDialogListener createCustomDialog() {
        return new CustomVersionDialogListener() {
            @Override
            public Dialog getCustomVersionDialog(Context context, UIData versionBundle) {
                return new AlertDialog.Builder(context)
                        .setCancelable(false)
                        .setTitle(versionBundle.getTitle())
                        .setMessage(versionBundle.getContent())
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("立即下载", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create();
            }
        };
    }

    public void onLog(View view) {
        Logger.e("sdfsdfwetwer534645wer54444444444444444444444444444444444444444443twregsfgdfhrywe4tergsteyer");
    }


    private class MyCancleClickListener implements OnCancelListener {
        @Override
        public void onCancel() {

        }
    }

    private NotificationBuilder createCustomNotification() {
        return NotificationBuilder.create()
                .setRingtone(true)
                .setIcon(R.mipmap.ic_launcher)
                .setTicker("custom_ticker")
                .setContentTitle("custom title")
                .setContentText("更新内容");
    }

    private class MyAPKDownloadListener implements APKDownloadListener {

        @Override
        public void onDownloading(int progress) {

        }

        @Override
        public void onDownloadSuccess(File file) {

        }

        @Override
        public void onDownloadFail() {

        }

    }

    public void onClickCamera(View v) {
        EasyPhotos.createCamera(this)
                .setFileProviderAuthority("com.jh.framework.fileprovider")
                .start(1001);
    }

    public void onClickTuKu(View v) {
        EasyPhotos.createAlbum(this, true, GlideEngine.getInstance())
                .setCameraLocation(Setting.LIST_FIRST)
                .setFileProviderAuthority("com.jh.framework.fileprovider")
                .start(1001);
    }

    public void getCpuInfo(View v) {
        PhoneInfoActivity.start(this);
    }

    @Override
    public void initView() {
        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AmapActivity.start(MainActivity.this);
            }
        });
        mImageView = findViewById(R.id.mImageView);
        tv_anim = findViewById(R.id.tv_anim);
        tv_anim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_anim.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_throb));
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1001) {

                //返回图片地址集合：如果你只需要获取图片的地址，可以用这个
                ArrayList<String> resultPaths = data.getStringArrayListExtra(EasyPhotos.RESULT_PATHS);
                String path = resultPaths.get(0);
                Log.e("----", "Path=" + path);

                String path1 = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).getPath();
                Log.e("----", "path1=" + path1);
                UCropHelper.startUCrop(this, path, UCrop.REQUEST_CROP, 500, 500);
                return;
            }
            if (requestCode == UCrop.REQUEST_CROP) {
                final Uri resultUri = UCrop.getOutput(data);
                Log.e("----", "resultUri=" + resultUri.getPath());
                Glide.with(mImageView).load(resultUri).into(mImageView);
            }
        }
    }

    public void onNetTest(View view) {
        presenter.getMainPageInfo();
    }

    @Override
    public void onSuccess(HomeData homePageInfo) {
        Glide.with(this).load(homePageInfo.getIssueList().get(0).getItemList().get(0).getImage()).into(mImageView);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
