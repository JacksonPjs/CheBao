package com.chebao.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chebao.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 创建日期：2018/5/9 on 14:34
 * 描述:
 * 作者:jackson Administrator
 */
public class SecurityWebActivity extends BaseActivity {
    @Bind(R.id.webView)
    WebView webView;

    //    String url="http://139.224.52.46:8010/snoppa/static/kylinguide.html";
    String url="file:///android_asset/security.html";

    boolean IsShow=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        initWebView();

    }





    private class WebAppInterface {
        Context mContext;

        WebAppInterface(Context c) {
            mContext = c;
        }

        /**
         * 参数处理
         *
         *
         */
        @JavascriptInterface
        public void EnterDevice() {
//            startActivity(new Intent(guideWebActivity.this,bluetoothConnectDialogActivity.class));
            Intent intent = new Intent();
            intent.putExtra("code", "guide");
            setResult(RESULT_OK, intent);


        }
    }
    private void initWebView() {
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (IsShow)
                    webView.loadUrl("javascript:window.showa()");
            }
        });
        WebSettings ws = webView.getSettings();
        ws.setJavaScriptEnabled(true);//开启JavaScript支持
        webView.setVerticalScrollBarEnabled(false);
        webView.addJavascriptInterface(new WebAppInterface(this), "kylin");
        webView.getSettings().setDomStorageEnabled(true);
        webView.loadUrl(url);
    }





    //销毁Webview
    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.clearHistory();

            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }
}