package com.jh.framework.ui.test;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.jh.framework.R;
import com.jh.framework.base.BaseActivity;
import com.jh.framework.utils.PhoneInfoUtils;

public class PhoneInfoActivity extends BaseActivity {
    TextView tv_cpu_name, tv_cores_number, tv_cpu_freq;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_phone_info;
    }

    @Override
    public void initView() {
        tv_cpu_name = findViewById(R.id.tv_cpu_name);
        tv_cores_number = findViewById(R.id.tv_cores_number);
        tv_cpu_freq = findViewById(R.id.tv_cpu_freq);
    }

    @Override
    public void initData() {
        tv_cpu_name.setText("CPU型号：" + PhoneInfoUtils.getCpuName());
        tv_cores_number.setText("CPU核心数：" + PhoneInfoUtils.getNumCores());
        tv_cpu_freq.setText("CPU频率：" + PhoneInfoUtils.getMinCpuFreq());
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, PhoneInfoActivity.class);
        context.startActivity(intent);
    }
}
