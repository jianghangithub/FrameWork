package com.jh.framework.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.jh.framework.retrofit.model.BaseModel;

public abstract class MVPActivity<P extends BasePresenter> extends BaseActivity implements BaseView {
    protected P presenter;

    protected abstract P createPresenter();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        presenter = createPresenter();
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
    }


    @Override
    public void showLoading() {
        showProgressDialog("数据加载中..");
    }

    @Override
    public void hideLoading() {
        closeProgressDialog();
    }

    @Override
    public void showError(String msg) {
        showToast(msg);
    }

    @Override
    public void onErrorCode(BaseModel model) {
    }
}
