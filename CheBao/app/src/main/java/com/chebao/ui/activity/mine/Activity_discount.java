package com.chebao.ui.activity.mine;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.chebao.Adapter.ViewPagerFramentAdapter;
import com.chebao.R;
import com.chebao.ui.activity.BaseActivity;
import com.chebao.ui.fragment.mine.Fragment_NoUseDiscount;
import com.chebao.ui.fragment.mine.Fragment_OutOfDateDisount;
import com.chebao.ui.fragment.mine.Fragment_UseDiscount;
import com.chebao.utils.onclick.AntiShake;
import com.pvj.xlibrary.banner.Banner;
import com.pvj.xlibrary.banner.BannerIndicator;
import com.pvj.xlibrary.loadinglayout.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/15.
 * 我的红包
 */

public class Activity_discount extends BaseActivity implements ViewPager.OnPageChangeListener {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.putong_team)
    TextView putongTeam;
    @Bind(R.id.xinshou_team)
    TextView xinshouTeam;
    @Bind(R.id.jilu_team)
    TextView jiluTeam;
    @Bind(R.id.biao_viewpager)
    ViewPager biaoViewpager;
    @Bind(R.id.putong_view)
    ImageView puttongView;
    @Bind(R.id.xinshou_view)
    ImageView xinshouView;
    @Bind(R.id.jilu_view)
    ImageView juluView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount);
        ButterKnife.bind(this);
        title.setText("我的红包");
        init();

    }

    private  void init(){


        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new Fragment_NoUseDiscount());
        fragmentList.add(new Fragment_UseDiscount());
        fragmentList.add(new Fragment_OutOfDateDisount());
        ViewPagerFramentAdapter adapter = new ViewPagerFramentAdapter(getSupportFragmentManager(),fragmentList);
        biaoViewpager.setAdapter(adapter);
        biaoViewpager.setOnPageChangeListener(this);

    }
    @OnClick({R.id.putong_team, R.id.xinshou_team, R.id.jilu_team})
    public void onClick(View view) {
        if (AntiShake.check(view.getId())) {    //判断是否多次点击
            return;
        }
        switch (view.getId()) {
            case R.id.putong_team:
                biaoViewpager.setCurrentItem(0);
                break;
            case R.id.xinshou_team:
                biaoViewpager.setCurrentItem(1);
                break;
            case R.id.jilu_team:
                biaoViewpager.setCurrentItem(2);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switchBtn(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    private void switchBtn(int position){
        puttongView.setVisibility(View.GONE);
        xinshouView.setVisibility(View.GONE);
        juluView.setVisibility(View.GONE);

        putongTeam.setTextColor(Utils.getColor(this,R.color.black_home_four_midle));
        xinshouTeam.setTextColor(Utils.getColor(this,R.color.black_home_four_midle));
        jiluTeam.setTextColor(Utils.getColor(this,R.color.black_home_four_midle));


        if (position==0){
            putongTeam.setTextColor(Utils.getColor(this,R.color.colorPrimary));
            puttongView.setVisibility(View.VISIBLE);
//            jiluTeam.setTextColor(Utils.getColor(this,R.color.text_newbie));
//            xinshouTeam.setTextColor(Utils.getColor(this,R.color.text_newbie));
        }else if (position==1){
//            putongTeam.setTextColor(Utils.getColor(this,R.color.text_newbie));
//            jiluTeam.setTextColor(Utils.getColor(this,R.color.text_newbie));
            xinshouTeam.setTextColor(Utils.getColor(this,R.color.colorPrimary));
            xinshouView.setVisibility(View.VISIBLE);
        }else if (position==2){
//            putongTeam.setTextColor(Utils.getColor(this,R.color.text_newbie));
//            xinshouTeam.setTextColor(Utils.getColor(this,R.color.text_newbie));
            jiluTeam.setTextColor(Utils.getColor(this,R.color.colorPrimary));
            juluView.setVisibility(View.VISIBLE);
        }
    }

}
