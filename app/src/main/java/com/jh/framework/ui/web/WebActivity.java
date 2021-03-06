package com.jh.framework.ui.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.jh.framework.R;
import com.jh.framework.base.BaseActivity;
import com.library.view.SlidingLayout;

public class WebActivity extends BaseActivity {
    private SlidingLayout mFrameLayout;
    private WebView mWebView;

    WebConfig mConfig;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    public void initView() {
        mFrameLayout = findViewById(R.id.mFrameLayout);
        mFrameLayout.bindActivity(this);
        mWebView = findViewById(R.id.mWebView);
    }

    @Override
    public void initData() {
        initWebView();

        Intent intent = getIntent();
        if (intent != null) {
            mConfig = (WebConfig) intent.getSerializableExtra("config");
        }
        if (mConfig != null)
            mWebView.loadUrl(mConfig.getLoadData());
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        switch (newConfig.orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                break;
        }

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        WebSettings settings = mWebView.getSettings();
        //??????Http???Https????????????
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);


        settings.setDatabaseEnabled(true);
        settings.setDatabasePath("/data/data");
        settings.setDomStorageEnabled(true);

        settings.setJavaScriptEnabled(true);//??????js??????
        settings.setUseWideViewPort(false);//????????????????????????webview?????????
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//????????????????????????
        settings.supportMultipleWindows();//?????????
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//??????webview?????????
        settings.setAllowFileAccess(true);//????????????????????????
        settings.setNeedInitialFocus(true);//???webview??????requestFocus??????webview????????????
        settings.setJavaScriptCanOpenWindowsAutomatically(true);//????????????JS???????????????
        settings.setLoadsImagesAutomatically(true);//????????????????????????
        settings.setSupportZoom(false);//????????????
        settings.setBuiltInZoomControls(false);//????????????

//        mWebView.setInitialScale(35);//??????????????????
        settings.setGeolocationEnabled(true);//??????????????????
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);//?????????????????????

        //javascriptInterface = new JavascriptInterface();
        //mWebView.addJavascriptInterface(javascriptInterface, "java2js_laole918");
        mWebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);//?????????????????????
        mWebView.setWebChromeClient(new MyWebChromeClient());
        mWebView.setWebViewClient(new MyWebViewClient());
    }

    private class MyWebChromeClient extends WebChromeClient {
        private View mCustomView;
        private CustomViewCallback mCustomViewCallback;

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            super.onShowCustomView(view, callback);
            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            mCustomView = view;
            mFrameLayout.addView(mCustomView);
            mCustomViewCallback = callback;
            mWebView.setVisibility(View.GONE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        public void onHideCustomView() {
            mWebView.setVisibility(View.VISIBLE);
            if (mCustomView == null) {
                return;
            }
            mCustomView.setVisibility(View.GONE);
            mFrameLayout.removeView(mCustomView);
            mCustomViewCallback.onCustomViewHidden();
            mCustomView = null;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            super.onHideCustomView();
        }
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            //???????????????????????????????????????????????????
            if (url.contains("tel:")) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(url)));
                return true;
            }

            if (url.startsWith("http:") || url.startsWith("https:")) {
                view.loadUrl(url);
                return true;
            }
		/*	WebView.HitTestResult hitTestResult = view.getHitTestResult();
			//hitTestResult==null?????????????????????
			if (!TextUtils.isEmpty(url) && hitTestResult == null) {
				view.loadUrl(url);
				return true;
			}*/

            return super.shouldOverrideUrlLoading(view, url);
        }

        /**
         * ????????????
         */
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        /**
         * ????????????
         */
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        /**
         * ??????????????????????????????????????????
         */
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        /**
         * HTTPS?????????????????????https://?????????????????????????????????
         * ????????????????????????
         * ??????7.0??????
         */
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            if (error.getPrimaryError() == android.net.http.SslError.SSL_INVALID) {// ?????????????????????bug
                handler.proceed();        //????????????????????????
            } else {
                handler.cancel();        //????????????
            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        mWebView.destroy();
        super.onDestroy();
    }

    public static void start(Context context, WebConfig config) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("config", config);
        context.startActivity(intent);
    }

    public static void start(Context context, String url) {
        start(context, new WebConfig(url));
    }

}
