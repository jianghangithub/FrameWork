package com.library.aspectj.permission;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.library.aspectj.permission.annotation.Permission;
import com.library.aspectj.permission.annotation.PermissionCanceled;
import com.library.aspectj.permission.annotation.PermissionDenied;
import com.library.aspectj.permission.bean.CancelInfo;
import com.library.aspectj.permission.bean.DenyInfo;
import com.library.aspectj.permission.callback.IPermission;
import com.library.aspectj.permission.utils.JPermissionHelper;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@SuppressWarnings("unused")
@Aspect
public class JPermissionAspect {

    private static final String TAG = JPermissionAspect.class.getSimpleName();

    @Pointcut("execution(@com.library.aspectj.permission.annotation.Permission * *(..)) && @annotation(permission)")
    public void requestPermission(Permission permission) {

    }

    @Around("requestPermission(permission)")
    public void aroundJoinPoint(final ProceedingJoinPoint joinPoint, Permission permission) {

        Context context = null;

        final Object object = joinPoint.getThis();
        if (joinPoint.getThis() instanceof Context) {
            context = (Context) object;
        } else if (joinPoint.getThis() instanceof Fragment) {
            context = ((Fragment) object).getActivity();
        } else if (joinPoint.getThis() instanceof android.app.Fragment) {
            context = ((android.app.Fragment) object).getActivity();
        } else if (object instanceof android.view.View) {
            context = ((android.view.View) object).getContext();
        }

        if (context == null) {
            context = JPermissionHelper.getContext();
        }

        if (context == null || permission == null) {
            return;
        }

        JPermissionActivity.permissionRequest(context, permission.value(), permission.requestCode(), new IPermission() {
            @Override
            public void ganted() {
                try {
                    joinPoint.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void denied(int requestCode, List<String> denyList) {
                //获取切面上下文的类型
                Class<?> clz = object.getClass();

                for (; ; ) {

                    if (clz == null) {
                        break;
                    }

                    String clazzName = clz.getCanonicalName();
                    if (clazzName == null || clazzName.startsWith("java.") || clazzName.startsWith("android.")) {
                        break;
                    }

                    //获取类型中的方法
                    Method[] methods = clz.getDeclaredMethods();

                    for (Method method : methods) {
                        //获取该方法是否有PermissionDenied注解
                        boolean isHasAnnotation = method.isAnnotationPresent(PermissionDenied.class);
                        if (isHasAnnotation) {
                            method.setAccessible(true);

                            //获取方法的参数类型，确定其可以回传
                            Class<?>[] parameterTypes = method.getParameterTypes();

                            //需要参数长度为1且类型需要为DenyInfo
                            if (parameterTypes.length != 1) {
                                return;
                            } else if (parameterTypes[0] != DenyInfo.class) {
                                return;
                            }

                            DenyInfo denyInfo = new DenyInfo(requestCode, denyList);

                            PermissionDenied annotation = method.getAnnotation(PermissionDenied.class);
                            int annotationReqCode = annotation.requestCode();

                            try {
                                if (annotationReqCode == JPermissionHelper.DEFAULT_REQUEST_CODE) {  //为默认值时，调用该注视方法
                                    method.invoke(object, denyInfo);
                                } else if (annotationReqCode == requestCode) {    //当设置的回调值与请求值相同时，调用
                                    method.invoke(object, denyInfo);
                                }
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }

                        }
                    }

                    clz = clz.getSuperclass();
                }
            }

            @Override
            public void canceled(int requestCode) {

                //获取切面上下文的类型
                Class<?> clz = object.getClass();

                for (; ; ) {

                    if (clz == null) {
                        break;
                    }

                    String clazzName = clz.getCanonicalName();
                    if (clazzName == null || clazzName.startsWith("java.") || clazzName.startsWith("android.")) {
                        break;
                    }

                    //获取类型中的方法
                    Method[] methods = clz.getDeclaredMethods();

                    for (Method method : methods) {
                        //获取该方法是否有PermissionCanceled注解
                        boolean isHasAnnotation = method.isAnnotationPresent(PermissionCanceled.class);
                        if (isHasAnnotation) {
                            method.setAccessible(true);

                            //获取方法的参数类型，确定其可以回传
                            Class<?>[] parameterTypes = method.getParameterTypes();
                            //需要参数长度为1且类型需要为CancelInfo
                            if (parameterTypes.length != 1) {
                                return;
                            } else if (parameterTypes[0] != CancelInfo.class) {
                                return;
                            }

                            CancelInfo cancelInfo = new CancelInfo(requestCode);

                            PermissionCanceled annotation = method.getAnnotation(PermissionCanceled.class);
                            int annotationReqCode = annotation.requestCode();

                            try {
                                if (annotationReqCode == JPermissionHelper.DEFAULT_REQUEST_CODE) {  //为默认值时，调用该注视方法
                                    method.invoke(object, cancelInfo);
                                } else if (annotationReqCode == requestCode) {    //当设置的回调值与请求值相同时，调用
                                    method.invoke(object, cancelInfo);
                                }
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }

                        }
                    }

                    clz = clz.getSuperclass();
                }
            }
        });
    }
}
