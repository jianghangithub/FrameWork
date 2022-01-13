package com.jh.framework.utils;

import android.content.Context;
import android.util.AttributeSet;

import androidx.core.widget.NestedScrollView;

/**
 * Created by JH on 2018/4/4.
 */

public class MyNestedScrollView extends NestedScrollView {

    private int count = 0;
    private OnScrollToBottomListener onScrollToBottom;

    public void setOnScrollToBottomLintener(OnScrollToBottomListener listener) {
        onScrollToBottom = listener;
    }


    public interface OnScrollToBottomListener {
        void onScrollBottomListener(boolean isBottom);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

    }

    public MyNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyNestedScrollView(Context context) {
        super(context);
    }
}
