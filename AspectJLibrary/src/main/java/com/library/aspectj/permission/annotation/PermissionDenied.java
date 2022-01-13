package com.library.aspectj.permission.annotation;

import com.library.aspectj.permission.utils.JPermissionHelper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PermissionDenied {

    int requestCode() default JPermissionHelper.DEFAULT_REQUEST_CODE;

}
