package com.jh.framework.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jh.framework.dialog.ProgressDialog;

/**
 * Created by jh on 2018/5/5.
 */

public abstract class BaseActivity extends AppCompatActivity {
    public Context context;
    private ProgressDialog progressDialog;
    public Toast toast;

    protected abstract int getLayoutId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        context = this;
        initView();
        initListener();
        initData();
    }


    public void initListener() {
    }

    public void initData() {
    }

    public void initView() {
    }

    /**
     * 显示进度对话框,带有消息
     */
    public void showProgressDialog(String message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }
        progressDialog.setTipText(message);
        progressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    public void closeProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @SuppressLint("ShowToast")
    public void showToast(String s) {
        if (toast == null) {
            toast = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG);
        } else {
            toast.setText(s);
        }
        toast.show();
    }

}
