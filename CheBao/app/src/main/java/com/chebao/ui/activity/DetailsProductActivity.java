package com.chebao.ui.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chebao.Adapter.ViewPagerFramentAdapter;
import com.chebao.R;
import com.chebao.bean.BorrowDetailBean;
import com.chebao.ui.fragment.Fragemt_Explain;
import com.chebao.ui.fragment.Fragemt_Notes;
import com.chebao.ui.fragment.Fragment_Data;
import com.pvj.xlibrary.loadinglayout.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建日期：2018/5/4 on 14:14
 * 描述:产品介绍
 * 作者:jackson Administrator
 */
public class DetailsProductActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.day_text)
    TextView dayText;
    @Bind(R.id.month_text)
    TextView monthText;
    @Bind(R.id.season_text)
    TextView seasonText;

    @Bind(R.id.buy)
    TextView buy;

    @Bind(R.id.putong_view)
    ImageView putongView;
    @Bind(R.id.month_team)
    ImageView monthTeam;
    @Bind(R.id.season_team)
    ImageView seasonTeam;


    @Bind(R.id.title)
    TextView title;
    List<Fragment> fragmentList;
    ViewPagerFramentAdapter viewPagerFramentAdapter;
    BorrowDetailBean bean;

    String id;
    int borrowStatus;
    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        fragmentList = new ArrayList<>();
        Intent intent = getIntent();

        id = intent.getStringExtra("id");
        Bundle bundle = getIntent().getExtras();
        bean= (BorrowDetailBean) bundle.getSerializable("bean");
        viewPagerFramentAdapter = new ViewPagerFramentAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(viewPagerFramentAdapter);
        viewPager.setOnPageChangeListener(this);

        Fragemt_Explain fragment_day1 = new Fragemt_Explain();
        Bundle bundle1 = new Bundle();
//                    bundle1.putSerializable("data", (Serializable) oneBean);
        bundle1.putString("id", id);
        bundle1.putInt("borrowStatus", borrowStatus);
        fragment_day1.setArguments(bundle1);
        fragmentList.add(fragment_day1);

        Fragment_Data fragment_day2 = new Fragment_Data();
        Bundle bundle2 = new Bundle();
        bundle2.putString("id", id);
        bundle2.putInt("borrowStatus", borrowStatus);
        fragment_day2.setArguments(bundle2);
        fragmentList.add(fragment_day2);

        Fragemt_Notes fragment_day3 = new Fragemt_Notes();
        Bundle bundle3 = new Bundle();
        bundle3.putString("id", id);
        fragment_day3.setArguments(bundle3);
        bundle3.putInt("borrowStatus", borrowStatus);
        fragmentList.add(fragment_day3);

        viewPagerFramentAdapter.notifyDataSetChanged();


//        if (!(Boolean) SharedPreferencesUtils.getParam(this, "islogin", false)) {
//            topay.setText(getResources().getString(R.string.logintopay));
//            topay.setBackground(null);
//            topay.setTextColor(getResources().getColor(R.color.mouth_btn_bg));
//        } else {
//            topay.setText(getResources().getString(R.string.topay));
//            topay.setTextColor(getResources().getColor(R.color.ll_nor));
//
//            topay.setBackground(getResources().getDrawable(R.drawable.button_border));
//        }

    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }


    @OnClick({R.id.buy, R.id.back, R.id.month_rl, R.id.day_rl, R.id.season_rl})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.buy:
                intent=new Intent(this,PayActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", (Serializable) bean);
                bundle.putString("id", id + "");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.day_rl:
                viewPager.setCurrentItem(0);
                break;
            case R.id.month_rl:
                viewPager.setCurrentItem(1);
                break;
            case R.id.season_rl:
                viewPager.setCurrentItem(2);
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

    private void switchBtn(int position) {
        dayText.setTextColor(Utils.getColor(this, R.color.black_home_four_midle));
        monthText.setTextColor(Utils.getColor(this, R.color.black_home_four_midle));
        seasonText.setTextColor(Utils.getColor(this, R.color.black_home_four_midle));
        putongView.setVisibility(View.INVISIBLE);
        monthTeam.setVisibility(View.INVISIBLE);
        seasonTeam.setVisibility(View.INVISIBLE);
        switch (position) {
            case 0:
                dayText.setTextColor(Utils.getColor(this, R.color.org_home));
                putongView.setVisibility(View.VISIBLE);
                break;
            case 1:
                monthText.setTextColor(Utils.getColor(this, R.color.org_home));
                monthTeam.setVisibility(View.VISIBLE);
                break;
            case 2:
                seasonText.setTextColor(Utils.getColor(this, R.color.org_home));
                seasonTeam.setVisibility(View.VISIBLE);
                break;
        }

    }
}
