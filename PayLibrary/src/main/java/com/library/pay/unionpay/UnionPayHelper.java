package com.library.pay.unionpay;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;

public class UnionPayHelper {
    private static final String serverMode = "00";

    public static void startPay(Activity context, String orderNo) {
        if (UPPayAssistEx.checkInstalled(context)) {
//            Toast.makeText(context, "APP打开", Toast.LENGTH_SHORT).show();
            UPPayAssistEx.startPay(context, null, null, orderNo, serverMode);
        } else {
//            Toast.makeText(context, "未安装云闪付，调起jar支付页面", Toast.LENGTH_SHORT).show();
            UPPayAssistEx.startPayByJAR(context, PayActivity.class, null, null, orderNo, serverMode);
        }
    }

    private static final String UPPAY_SUCCESS = "success";
    private static final String UPPAY_FAIL = "fail";
    private static final String UPPAY_CANCEL = "cancel";

    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        String str = data.getStringExtra("pay_result");
        if (str.equalsIgnoreCase(UPPAY_SUCCESS)) {
            //支付成功
        } else if (str.equalsIgnoreCase(UPPAY_FAIL)) {
            //支付失败
        } else if (str.equalsIgnoreCase(UPPAY_CANCEL)) {
            //支付取消
        }
    }
}
