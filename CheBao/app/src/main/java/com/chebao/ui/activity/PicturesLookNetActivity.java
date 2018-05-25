package com.chebao.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chebao.R;

/**
 * 创建日期：2018/5/25 on 15:11
 * 描述:
 * 作者:jackson Administrator
 */
public class PicturesLookNetActivity extends BaseActivity {
    String url  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pictures_layout);
        ImageView imageView = (ImageView) findViewById(R.id.pic);

        url = getIntent().getStringExtra("url");


        Glide.with(this).load(url)
                .error(R.mipmap.error).placeholder(R.mipmap.error).
                into(imageView);


        imageView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
    }
}
