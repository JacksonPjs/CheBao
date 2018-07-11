package com.chebao.ui.activity.web;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.chebao.MainActivity;
import com.chebao.MyApplication;
import com.chebao.R;
import com.chebao.net.NetService;
import com.chebao.ui.activity.BaseActivity;
import com.chebao.ui.activity.mine.TransactionActivity;
import com.chebao.utils.SharedPreferencesUtils;
import com.pvj.xlibrary.log.Logger;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.chebao.utils.edncodeUtils.getCookie;

/**
 * 创建日期：2018/6/15 on 11:07
 * 描述:
 * 作者:jackson Administrator
 */
public class WebNotitleHtmlActivity extends BaseActivity {

    @Bind(R.id.webView)
    WebView webView;
    String url;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_nonolode);
        ButterKnife.bind(this);
        activity = this;
        url = getIntent().getStringExtra("url");


        initWebView();
    }

    @Override
    public void onBack(View view) {
        super.onBack(view);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("index", 2);
        startActivity(intent);
        finish();
    }

    private void initWebView() {
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

            }
        });
//        webView.setWebViewClient(new MyWebViewClient());
        WebSettings settings = webView.getSettings();
//        settings.setUseWideViewPort(true);
//        settings.setLoadWithOverviewMode(true);
//        settings.setSupportZoom(false);
//        settings.setBuiltInZoomControls(false);
//
//
//        settings.setSupportZoom(true);          //允许缩放
//        settings.setBuiltInZoomControls(true);  //原网页基础上缩放
//        settings.setUseWideViewPort(true);      //任意比例缩放
        settings.setJavaScriptEnabled(true);     //允许加载javascript


        webView.getSettings().setDomStorageEnabled(true);
//        webView.getSettings().setDatabaseEnabled(true);
//        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.addJavascriptInterface(new JavaScriptObject(this), "enduoApp");
        webView.getSettings().setUseWideViewPort(true); //设置加载进来的页面自适应手机屏幕（可缩放）
        webView.getSettings().setLoadWithOverviewMode(true);

//        webView.getSettings().setAppCacheEnabled(true);

        webView.getSettings().setBlockNetworkImage(false); // 解决图片不显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ){
//            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        }

        try {
            if (Build.VERSION.SDK_INT >= 16) {
                Class<?> clazz = webView.getSettings().getClass();
                Method method = clazz.getMethod(
                        "setAllowUniversalAccessFromFileURLs", boolean.class);
                if (method != null) {
                    method.invoke(webView.getSettings(), true);
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        webView.loadData(url, "text/html; charset=UTF-8", null);


//        webView.loadUrl(url);
    }

    // 监听 所有点击的链接，如果拦截到我们需要的，就跳转到相对应的页面。
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//这里进行url拦截
            if (url != null && url.contains("wechat/borrowList.html")) {
                Intent intent = new Intent(WebNotitleHtmlActivity.this, MainActivity.class);
                intent.putExtra("index", 1);
                startActivity(intent);

                finish();
                return true;
            }


            return super.shouldOverrideUrlLoading(view, url);
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageFinished(view, url);


        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
//                if (webView.canGoBack()) {
//                    webView.goBack();
//                } else {
//                    finish();
//                }
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("index", 2);
                startActivity(intent);
                finish();
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
        public void goAgain(String s) {
            //再一次
            OkHttpUtils
                    .post()
                    .url(NetService.API_SERVER_Url + "wechat/duanwuNextActive.html?act=" + s)
                    .addHeader("Cookie", getCookie())
                    .addParams("", "")
                    .build()
                    .execute(new com.zhy.http.okhttp.callback.Callback<String>() {

                        @Override
                        public String parseNetworkResponse(com.squareup.okhttp.Response response) throws IOException {
                            return response.body().string();
                        }

                        @Override
                        public void onError(Request request, Exception e) {

                        }

                        @Override
                        public void onResponse(String o) {
                            Logger.json(o);
                            Intent intent = new Intent(WebNotitleHtmlActivity.this, WebNotitleHtmlActivity.class);
                            intent.putExtra("url", "" + o);
                            startActivity(intent);

                        }


                    });

        }

        @JavascriptInterface
        public String goCookie() {
            return (String) SharedPreferencesUtils.getParam(MyApplication.context, "token", "");
        }
        @JavascriptInterface
        public void goTransaction() {
            //去交易记录
            Intent intent = new Intent(mContxt, TransactionActivity.class);
            mContxt.startActivity(intent);
            finish();
            Logger.d("goTransaction()");
        }



        @JavascriptInterface
        public void onFinish() {
            Intent intent = new Intent(mContxt, MainActivity.class);
            intent.putExtra("index", 1);
            mContxt.startActivity(intent);
            finish();
            Logger.d("onFinish()");
        }

        @JavascriptInterface
        public void onNext(String s) {
//            finish();
            Logger.d("onFinish()");
            Logger.d("onFinish()" + s);
            OkHttpUtils
                    .post()
                    .url(NetService.API_SERVER_Url + "wechat/duanwuNextActive.html?act=" + s)
                    .addHeader("Cookie", getCookie())
                    .addParams("", "")
                    .build()
                    .execute(new com.zhy.http.okhttp.callback.Callback<String>() {

                        @Override
                        public String parseNetworkResponse(com.squareup.okhttp.Response response) throws IOException {
                            return response.body().string();
                        }

                        @Override
                        public void onError(Request request, Exception e) {

                        }

                        @Override
                        public void onResponse(String o) {
                            Logger.json(o);
                            Intent intent = new Intent(WebNotitleHtmlActivity.this, WebNotitleHtmlActivity.class);
                            intent.putExtra("url", "" + o);
                            startActivity(intent);

                        }


                    });

        }

    }

}