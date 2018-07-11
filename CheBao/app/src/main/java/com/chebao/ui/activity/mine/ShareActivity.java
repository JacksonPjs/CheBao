package com.chebao.ui.activity.mine;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chebao.R;
import com.chebao.ui.activity.BaseActivity;
import com.chebao.utils.ImageUtil;
import com.chebao.utils.QRCodeUtil;
import com.chebao.utils.SharedPreferencesUtils;
import com.chebao.utils.onclick.AntiShake;
import com.chebao.widget.dialog.ShareDialog;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.utils.SocializeUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.umeng.socialize.utils.ContextUtil.getContext;

/**
 * 创建日期：2018/6/5 on 16:10
 * 描述:分享页
 * 作者:jackson Administrator
 */
public class ShareActivity extends BaseActivity {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.iv_qrcode)
    ImageView ivQrcode;
    @Bind(R.id.link_tv)
    TextView link;

    Activity activity;
    private ProgressDialog dialog;
    String username;
    String url;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_share);
        ButterKnife.bind(this);
        activity=this;
        title.setText("邀请好友");
        init();

    }
    public void init(){
        dialog = new ProgressDialog(this);
        username= SharedPreferencesUtils.getUserName(getContext());
        url="http://www.chebaojr.com/wechat/regIndex.html?tel="+username;
         bitmap=QRCodeUtil.getInstance().encodeTransactionString(url,
                500);
//        ivQrcode.setImageBitmap(bitmap);
        link.setText(url+"");
    }

    @OnClick({R.id.iv_qrcode,R.id.share_umeng,R.id.share_dialog})
    public void onClick(View view) {
        if (AntiShake.check(view.getId())) {    //判断是否多次点击
            return;
        }
       switch (view.getId()){
           case R.id.share_dialog:
               final ShareDialog shareDialog=new ShareDialog(activity);
               shareDialog.setBitmap(bitmap);
               shareDialog.show();
               shareDialog.setOnLongClickShareListener(new ShareDialog.OnLongClickShareListener() {
                   @Override
                   public void onFinish(String str) {
                       ImageUtil.SaveBitmapFromView(activity,shareDialog.getIv_qrcode());
                   }
               });

               break;


           case R.id.share_umeng:
               if(Build.VERSION.SDK_INT>=23){
                   String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                           Manifest.permission.ACCESS_FINE_LOCATION,
                           Manifest.permission.CALL_PHONE,
                           Manifest.permission.READ_LOGS,
                           Manifest.permission.READ_PHONE_STATE,
                           Manifest.permission.READ_EXTERNAL_STORAGE,
                           Manifest.permission.SET_DEBUG_APP,
                           Manifest.permission.SYSTEM_ALERT_WINDOW,
                           Manifest.permission.GET_ACCOUNTS,
                           Manifest.permission.WRITE_APN_SETTINGS};
                   ActivityCompat.requestPermissions(this,mPermissionList,123);
               }
               UMImage image = new UMImage(ShareActivity.this, R.mipmap.ic_launcher_round);

               UMWeb web = new UMWeb(url);
               web.setTitle("鑫贝通宝-人脉总动员，邀请送现金红包，可提现！");//标题
               web.setThumb(image);  //缩略图
               web.setDescription("邀请好友注册，百元现金红包+现金券壕礼送不停，邀请多多，现金多多，可提至银行卡！");//描述
               new ShareAction(activity).withMedia(web)
                       .setDisplayList(SHARE_MEDIA.QQ,SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE
                      , SHARE_MEDIA.SINA)
                       .setCallback(shareListener).open();
               break;

       }

    }

    private UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            SocializeUtils.safeShowDialog(dialog);
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(activity,"成功了",Toast.LENGTH_LONG).show();
            SocializeUtils.safeCloseDialog(dialog);
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(activity,"失败"+t.getMessage(),Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(activity,"取消了",Toast.LENGTH_LONG).show();

        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }
}
