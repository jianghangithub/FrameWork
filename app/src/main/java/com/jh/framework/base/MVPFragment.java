package com.jh.framework.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.jh.framework.retrofit.model.BaseModel;

/**
 * Created by jh on 2018/5/31.
 */

public abstract class MVPFragment<P extends BasePresenter> extends BaseFragment implements BaseView {
    protected P presenter;

    protected abstract P createPresenter();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        presenter = createPresenter();
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
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
        showtoast(msg);
    }

    @Override
    public void onErrorCode(BaseModel model) {
    }
}
