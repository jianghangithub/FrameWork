package com.library.view.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

import java.text.MessageFormat;

public class MsgCodeCountDown extends CountDownTimer {
    private TextView view;

    public MsgCodeCountDown(long millisInFuture, long countDownInterval, TextView view) {
        super(millisInFuture, countDownInterval);
        this.view = view;
    }

    @Override
    public void onTick(long l) {
        //防止计时过程中重复点击
        view.setClickable(false);
        view.setText(MessageFormat.format("{0}秒", l / 1000));
    }

    @Override
    public void onFinish() {
        //重新给Button设置文字
        view.setText("重新获取");
        //设置可点击
        view.setClickable(true);
    }
}
