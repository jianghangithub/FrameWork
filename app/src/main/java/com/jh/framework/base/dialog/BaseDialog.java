package com.jh.framework.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

import com.jh.framework.R;
import com.jh.framework.utils.DensityUtil;


/**
 * Dialog基类
 * Created by l on 2016/1/26.
 */
public abstract class BaseDialog extends Dialog {
    public BaseDialog(Context context) {
        super(context, R.style.style_dialog_common);
    }

    /**
     * 设置拦截点击返回按钮事件,会回调onClickBackKey方法
     */
    public void setInterceptBackKey(boolean is) {
        isShieldBackKey = is;
    }

    /*是否锁定 返回按钮,锁定之后当窗口弹出的时候返回按键将失效*/
    private boolean isShieldBackKey = false;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onClickBackKey();
            if (isShieldBackKey) return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 当点击返回按钮的时候
     */
    public void onClickBackKey() {
    }


    public void setSmartContentView(View view) {
        view.setMinimumWidth(DensityUtil.dip2px(300));
        setContentView(view);
        ViewGroup.MarginLayoutParams mParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        mParams.leftMargin = DensityUtil.dip2px(25);
        mParams.rightMargin = DensityUtil.dip2px(25);

    }
}
