package com.library.aspectj.permission.bean;

import java.util.List;

/**
 *  被拒绝的权限的信息（点击了不再提示）
 */

public class DenyInfo {

    private int requestInfo;
    private List<String> deniedPermissions;

    public DenyInfo(int requestInfo, List<String> deniedPermissions) {
        this.requestInfo = requestInfo;
        this.deniedPermissions = deniedPermissions;
    }

    public int getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(int requestInfo) {
        this.requestInfo = requestInfo;
    }

    public List<String> getDeniedPermissions() {
        return deniedPermissions;
    }

    public void setDeniedPermissions(List<String> deniedPermissions) {
        this.deniedPermissions = deniedPermissions;
    }
}
