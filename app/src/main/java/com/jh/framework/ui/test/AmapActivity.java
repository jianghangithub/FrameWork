package com.jh.framework.ui.test;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.jh.framework.R;
import com.jh.framework.base.BaseActivity;
import com.library.amap.LocationHelper;

public class AmapActivity extends BaseActivity {
    TextView tv_info;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_amap;
    }

    @Override
    public void initData() {
        getLifecycle().addObserver(new LocationHelper(this));
    }

    @Override
    public void initView() {
        tv_info = findViewById(R.id.tv_info);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, AmapActivity.class);
        context.startActivity(intent);
    }
}
