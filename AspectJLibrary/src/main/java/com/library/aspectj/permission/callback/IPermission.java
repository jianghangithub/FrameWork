package com.library.aspectj.permission.callback;

import java.util.List;

public interface IPermission {

    //授权
    public void ganted();

    //被拒绝，点击了不再提示
    public void denied(int requestCode, List<String> denyList);

    //取消授权
    public void canceled(int requestCode);

}
