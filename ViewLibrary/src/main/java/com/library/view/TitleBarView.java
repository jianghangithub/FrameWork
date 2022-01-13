package com.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TitleBarView extends RelativeLayout {
    TextView tv_title, tv_right;
    ImageView view_Finish;
    RelativeLayout rl_root;

    public TitleBarView(Context context) {
        super(context);
    }

    public TitleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.TitleBarView);
        String title = attributes.getString(R.styleable.TitleBarView_title_bar_title);
        if (!TextUtils.isEmpty(title)) {
            tv_title.setText(title);
        }
        String right_text = attributes.getString(R.styleable.TitleBarView_title_bar_right);
        if (!TextUtils.isEmpty(right_text)) {
            tv_right.setText(right_text);
        }
        boolean right_text_visiable = attributes.getBoolean(R.styleable.TitleBarView_title_bar_right_visiable, false);
        tv_right.setVisibility(right_text_visiable ? VISIBLE : GONE);


        boolean cancle_button_visiable = attributes.getBoolean(R.styleable.TitleBarView_title_bar_cancle_button_visiable, true);
        view_Finish.setVisibility(cancle_button_visiable ? VISIBLE : GONE);

        int right_img = attributes.getResourceId(R.styleable.TitleBarView_title_bar_right_img, R.drawable.icon_setting);
        tv_right.setBackgroundResource(right_img);

        int bac_color = attributes.getColor(R.styleable.TitleBarView_title_bar_bac_color, 0xff000000);
        rl_root.setBackgroundColor(bac_color);
        attributes.recycle();
    }

    public TitleBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.view_title_bar, this);
        rl_root = findViewById(R.id.rl_root);
        tv_right = findViewById(R.id.tv_right);
        tv_title = findViewById(R.id.tv_title);
        view_Finish = findViewById(R.id.view_Finish);

        view_Finish.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cancleClickListener != null)
                    cancleClickListener.cancleClick();
            }
        });
        tv_right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rightBTNClickListener != null) {
                    rightBTNClickListener.rightClick();
                }
            }
        });
    }

    public void setTitle(String title) {
        tv_title.setText(title);
    }

    private CancleClickListener cancleClickListener;
    private RightBTNClickListener rightBTNClickListener;

    public void setCancleClickListener(CancleClickListener listener) {
        cancleClickListener = listener;
    }

    public void setRightBTNClickListener(RightBTNClickListener listener) {
        rightBTNClickListener = listener;
    }

    public interface CancleClickListener {
        void cancleClick();
    }

    public interface RightBTNClickListener {
        void rightClick();
    }
}
