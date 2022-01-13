package com.jh.framework.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jh.framework.dialog.ProgressDialog;

/**
 * Created by JH on 2018/3/29.
 */

public abstract class BaseFragment extends Fragment {

    private boolean isViewInitiated;
    private boolean isVisibleToUser;
    private boolean isDataInitiated;

    private ProgressDialog progressDialog;
    private Toast toast;

    abstract int getLayoutId();

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        if (view != null) {
//            ViewGroup parent = (ViewGroup) view.getParent();
//            if (parent != null) {
//                parent.removeView(view);
//            }
//            return view;
//        }
        view = inflater.inflate(getLayoutId(), container, false);

        initView(view);

        return view;
    }

    public void initListener() {
    }

    public void initView(View view) {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initListener();
        initData();
        isViewInitiated = true;
        prepareFetchData();
    }

    public void initData() {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }

    public abstract void fetchData();

    public void prepareFetchData() {
        prepareFetchData(false);
    }

    public void prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            fetchData();
            isDataInitiated = true;
        }
    }

    @SuppressLint("ShowToast")
    public void showtoast(String s) {
        if (toast == null) {
            toast = Toast.makeText(getContext().getApplicationContext(), s, Toast.LENGTH_LONG);
        } else {
            toast.setText(s);
        }
        toast.show();
    }

    /**
     * 显示进度对话框,带有消息
     */
    public void showProgressDialog(String message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
        }
        progressDialog.setTipText(message);
        progressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    public void closeProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
