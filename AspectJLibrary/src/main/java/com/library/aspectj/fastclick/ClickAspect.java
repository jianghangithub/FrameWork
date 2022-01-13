package com.library.aspectj.fastclick;

import android.os.SystemClock;
import android.view.View;

import com.library.aspectj.R;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * 点击事件的切入
 * Created by Extends on 2018/5/4/004.
 */
@Aspect
public class ClickAspect {
    private static final String TAG = "ClickAspect";
    private static final int MIN_CLICK_DELAY_TIME = 600;
    private static int TIME_TAG = R.id.click_time;

    //是否允许快速点击
    private boolean isFastClick = false;

    @Before("@annotation(com.library.aspectj.fastclick.FastClick)")
    public void judgeFastClick(JoinPoint joinPoint) throws Throwable {
        isFastClick = true;
    }

    @Around("execution(* android.view.View.OnClickListener.onClick(..))")
    public void aroundClick(ProceedingJoinPoint joinPoint) throws Throwable {
        //判断是否需要快速点击
        if (isFastClick) {
            joinPoint.proceed();
            isFastClick = false;
            return;
        }

        View view = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof View) {
                view = ((View) arg);
                break;
            }
        }
        if (view != null) {
            Object tag = view.getTag(TIME_TAG);
            long lastClickTime = (tag != null) ? (long) tag : 0;
            long currentTime = SystemClock.uptimeMillis();
            if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                //过滤掉600毫秒内的连续点击
                view.setTag(TIME_TAG, currentTime);
                joinPoint.proceed();//执行原方法
            }
        }
    }
}
