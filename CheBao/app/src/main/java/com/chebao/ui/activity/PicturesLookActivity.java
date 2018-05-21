package com.chebao.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chebao.R;

/**
 * Created by Administrator on 2016/12/30.
 */

public class PicturesLookActivity extends BaseActivity {
    int url  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pictures_layout);
        ImageView imageView = (ImageView) findViewById(R.id.pic);

        url = getIntent().getIntExtra("url",R.mipmap.error);


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
