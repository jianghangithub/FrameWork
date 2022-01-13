package com.library.aspectj.permission.bean;

/**
 * 被取消授权的信息
 */

public class CancelInfo {

    private int requestCode;

    public CancelInfo(int requestCode) {
        this.requestCode = requestCode;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }
}
