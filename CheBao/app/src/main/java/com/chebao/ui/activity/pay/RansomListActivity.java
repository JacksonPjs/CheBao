package com.chebao.ui.activity.pay;

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
import com.chebao.ui.activity.BaseActivity;
import com.chebao.ui.activity.PayActivity;
import com.chebao.ui.activity.pay.RandomFragment.Fragment_Random;
import com.chebao.ui.activity.pay.RandomFragment.Fragment_invest;
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
 * 创建日期：2018/5/30 on 15:59
 * 描述:
 * 作者:jackson Administrator
 */
public class RansomListActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.day_text)
    TextView dayText;
    @Bind(R.id.month_text)
    TextView monthText;


    @Bind(R.id.putong_view)
    ImageView putongView;
    @Bind(R.id.month_team)
    ImageView monthTeam;


    @Bind(R.id.title)
    TextView title;
    List<Fragment> fragmentList;
    ViewPagerFramentAdapter viewPagerFramentAdapter;

    int borrowStatus;
    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randomlist);
        ButterKnife.bind(this);
        title.setText("记录");
        init();
    }

    private void init() {
        fragmentList = new ArrayList<>();
        Intent intent = getIntent();

        viewPagerFramentAdapter = new ViewPagerFramentAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(viewPagerFramentAdapter);
        viewPager.setOnPageChangeListener(this);

        Fragment_invest fragment_day1 = new Fragment_invest();
//        Bundle bundle1 = new Bundle();
////                    bundle1.putSerializable("data", (Serializable) oneBean);
//        bundle1.putInt("borrowStatus", borrowStatus);
//        fragment_day1.setArguments(bundle1);
        fragmentList.add(fragment_day1);

        Fragment_Random fragment_day2 = new Fragment_Random();
//        Bundle bundle2 = new Bundle();
//        bundle2.putString("id", id);
//        bundle2.putInt("borrowStatus", borrowStatus);
//        fragment_day2.setArguments(bundle2);
        fragmentList.add(fragment_day2);



        viewPagerFramentAdapter.notifyDataSetChanged();


//

    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }


    @OnClick({ R.id.back, R.id.month_rl, R.id.day_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.day_rl:
                viewPager.setCurrentItem(0);
                break;
            case R.id.month_rl:
                viewPager.setCurrentItem(1);
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
        putongView.setVisibility(View.INVISIBLE);
        monthTeam.setVisibility(View.INVISIBLE);
        switch (position) {
            case 0:
                dayText.setTextColor(Utils.getColor(this, R.color.org_home));
                putongView.setVisibility(View.VISIBLE);
                break;
            case 1:
                monthText.setTextColor(Utils.getColor(this, R.color.org_home));
                monthTeam.setVisibility(View.VISIBLE);
                break;

        }

    }
}