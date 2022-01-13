package com.jh.framework.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.jh.framework.R;
import com.library.view.internal.ProgressDrawable;

public class ProgressDialog extends Dialog {
    private ImageView loading_img;

    private TextView loading_msg;

    private ProgressDrawable mProgressDrawable;

    public ProgressDialog(Context context) {
        super(context, R.style.loading_dialog);
        setContentView(R.layout.dialog_progress);
        setCanceledOnTouchOutside(false);

        Window window = getWindow();
        if (window != null)
            window.getAttributes().gravity = Gravity.CENTER;
        loading_img = findViewById(R.id.loading_img);
        loading_msg = findViewById(R.id.loading_msg);
        mProgressDrawable = new ProgressDrawable();
        mProgressDrawable.setColor(0xffFFFFFF);
        loading_img.setImageDrawable(mProgressDrawable);
    }


    @Override
    protected void onStart() {
        super.onStart();
        startAnimate();
    }

    @Override
    protected void onStop() {
        super.onStop();
        endAnimate();
    }

    private void startAnimate() {
        Drawable drawable = loading_img.getDrawable();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        } else {
            loading_img.animate().rotation(36000).setDuration(100000);
        }
    }

    private void endAnimate() {
        Drawable drawable = loading_img.getDrawable();
        if (drawable instanceof Animatable) {
            if (((Animatable) drawable).isRunning()) {
                ((Animatable) drawable).stop();
            }
        } else {
            loading_img.animate().rotation(0).setDuration(0);
        }
    }

    public void setTipText(String msg) {
        if (loading_msg != null) {
            loading_msg.setText(msg);
        }
    }

}
