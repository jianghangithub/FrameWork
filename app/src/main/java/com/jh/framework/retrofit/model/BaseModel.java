package com.jh.framework.retrofit.model;

/**
 * Created by JH on 2018/3/30.
 */

public class BaseModel<T> {
    private int status;
    private String msg;
    private Common common;
    private T models;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Common getCommon() {
        return common;
    }

    public void setCommon(Common common) {
        this.common = common;
    }

    public T getModels() {
        return models;
    }

    public void setModels(T models) {
        this.models = models;
    }
}
