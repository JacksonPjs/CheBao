package com.chebao.ui.activity.web;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.chebao.MainActivity;
import com.chebao.MyApplication;
import com.chebao.R;
import com.chebao.ui.activity.BaseActivity;
import com.chebao.utils.DialogUtils;
import com.chebao.utils.LoadDialogUtils;
import com.pvj.xlibrary.loadinglayout.LoadingLayout;
import com.pvj.xlibrary.log.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 创建日期：2018/5/19 on 18:41
 * 描述:
 * 作者:jackson Administrator
 */
public class AboutActivity extends BaseActivity {

    @Bind(R.id.webView)
    WebView webView;
//    @Bind(R.id.contentLayout)
//    LoadingLayout contentLayout;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        //   title.setText();

//        url ="file:///android_asset/security.html";
//        url ="https://www.baidu.com";
        url = getIntent().getStringExtra("url");

//        String title1 = getIntent().getStringExtra("title");


        initWebView();
    }

    private void initWebView() {

        final Dialog dialog=LoadDialogUtils.createProgressDialog(this,"请稍候...");
        LoadDialogUtils.DialogShow("请稍候...");
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                if (newProgress == 100) {
                    LoadDialogUtils.Dialogdismiss();

//                    if (contentLayout != null)
//                        contentLayout.setStatus(LoadingLayout.Success);
                    if (webView != null)
                        url = webView.getUrl();
                } else {
//                    if (contentLayout != null)
//                        contentLayout.setStatus(LoadingLayout.Loading);

                }
            }
        });


        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);


        webView.setWebViewClient(new WebViewClient());


        webView.getSettings().setJavaScriptEnabled(true);


        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.addJavascriptInterface(new JavaScriptObject(this), "enduoApp");
        webView.getSettings().setUseWideViewPort(true); //设置加载进来的页面自适应手机屏幕（可缩放）
        webView.getSettings().setLoadWithOverviewMode(true);

        webView.getSettings().setAppCacheEnabled(true);

        webView.getSettings().setBlockNetworkImage(false); // 解决图片不显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        webView.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finish();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webView != null) webView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webView != null) webView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            ViewGroup parent = (ViewGroup) webView.getParent();
            if (parent != null) {
                parent.removeView(webView);
            }
            webView.removeAllViews();
            webView.destroy();
        }
    }

    public class JavaScriptObject {
        Context mContxt;

        //sdk17版本以上加上注解
        public JavaScriptObject(Context mContxt) {
            this.mContxt = mContxt;
        }

        @JavascriptInterface
        public void goRecharge() {
            //   T.ShowToastForShort(mContxt,"goReCharge");
            finish();
            Logger.d("goReCharge");

        }

        @JavascriptInterface
        public void goUserIndex() {
            //   T.ShowToastForShort(mContxt,"goUserIndex");
            MyApplication.instance.Allfinlish();
            Logger.d("goUserIndex");
        }

        @JavascriptInterface
        public void goInvest() {
            //   T.ShowToastForShort(mContxt,"goUserIndex");
            Intent intent = new Intent(mContxt, MainActivity.class);
            intent.putExtra("index", 1);
            mContxt.startActivity(intent);
            Logger.d("goInvest()");
        }

        @JavascriptInterface
        public void onFinish() {
            finish();
            Logger.d("onFinish()");
        }
    }

}