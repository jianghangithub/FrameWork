package com.library.aspectj.aop;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class TestAspect {
    @Before("call(* com.demo.aspectjdemo.aspectj.Anim.testMethod())")
    public void testMethodCall(JoinPoint joinPoint) {
        Log.e("-----", "before->" + joinPoint.getTarget().toString() + "#" + joinPoint.getSignature().getName());
    }
}
