package com.chebao;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.chebao.net.OkHttpUtils;
import com.pvj.xlibrary.loadinglayout.LoadingLayout;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";

    public static Context context ;

    public static MyApplication instance;
    List<Activity> activities;
    @Override
    public void onCreate() {
        super.onCreate();
        context= this ;
        instance = this;
        activities = new ArrayList<Activity>();
//        JPushInterface.init(this);

        //初始化单列的OkHttpClient 对象
        initOkHttpUtils();
        //初始化Glide对象
        initGlide();

        LoadingLayout.getConfig()
                .setLoadingPageLayout(R.layout.loading);
        UMConfigure.init(this,  UMConfigure.DEVICE_TYPE_PHONE, "");
//        UMConfigure.init(this, "5b012b73a40fa3083400009b", "Cs", UMConfigure.DEVICE_TYPE_PHONE,
//                "");
        UMConfigure.setLogEnabled(true);
        UMConfigure.setEncryptEnabled(true);

    }

    private void initGlide() {
        Glide.get(this).register(GlideUrl.class, InputStream.class,new OkHttpUrlLoader.Factory(OkHttpUtils.getOkHttpClient()));

    }

    private void initOkHttpUtils() {
        OkHttpClient okHttpClient = OkHttpUtils.getOkHttpClient();
        Log.i(TAG,"---->initOkHttpUtils: "+okHttpClient.toString());

    }


    public void Allfinlish() {
        for (int i = 0; i < activities.size(); i++) {
            Activity activity = activities.get(i);

            if (activity != null) {
                activity.finish();
            }
        }
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }


    {
        PlatformConfig.setWeixin("wx7e91f706808db432", "fcb4e308a82cbb9a6f080d560725c5b2");
//        //豆瓣RENREN平台目前只能在服务器端配置
        PlatformConfig.setSinaWeibo("1729131688", "38bc23d701bfaff06878aa2edace54be", "http://sns.whalecloud.com");
//        PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
        PlatformConfig.setQQZone("1106938486", "pxNDddc34enSl4ks");
//        PlatformConfig.setTwitter("3aIN7fuF685MuZ7jtXkQxalyi", "MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO");
//        PlatformConfig.setAlipay("2015111700822536");
//        PlatformConfig.setLaiwang("laiwangd497e70d4", "d497e70d4c3e4efeab1381476bac4c5e");
//        PlatformConfig.setPinterest("1439206");
//        PlatformConfig.setKakao("e4f60e065048eb031e235c806b31c70f");
//        PlatformConfig.setDing("dingoalmlnohc0wggfedpk");
//        PlatformConfig.setVKontakte("5764965", "5My6SNliAaLxEm3Lyd9J");
//        PlatformConfig.setDropbox("oz8v5apet3arcdy", "h7p2pjbzkkxt02a");

    }
}

