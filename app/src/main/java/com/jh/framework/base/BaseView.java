package com.jh.framework.base;

import com.jh.framework.retrofit.model.BaseModel;

/**
 * Created by jh on 2018/5/2.
 */

public interface BaseView {
    /**
     * 显示dialog
     */
    void showLoading();

    /**
     * 隐藏 dialog
     */

    void hideLoading();

    /**
     * 显示错误信息
     *
     * @param msg 1
     */
    void showError(String msg);

    /**
     * 错误码
     */
    void onErrorCode(BaseModel model);
}
