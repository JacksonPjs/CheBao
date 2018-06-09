package com.chebao.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.chebao.Adapter.AnnouncementAdapter;
import com.chebao.Adapter.ViewPagerFramentAdapter;
import com.chebao.R;
import com.chebao.bean.AnnouncementBean;
import com.chebao.bean.BorrowDetailBean;
import com.chebao.net.NetWorks;
import com.chebao.ui.fragment.Fragemt_Explain;
import com.chebao.ui.fragment.Fragemt_Notes;
import com.chebao.ui.fragment.Fragment_Data;
import com.chebao.ui.fragment.NoticeListFragment;
import com.chebao.ui.fragment.consultationAppFragemnt;
import com.pvj.xlibrary.loadinglayout.LoadingLayout;
import com.pvj.xlibrary.loadinglayout.Utils;
import com.pvj.xlibrary.loadingrecyclerview.LoadMoreRecyclerLoadingLayout;
import com.pvj.xlibrary.log.Logger;
import com.pvj.xlibrary.recyclerview.RecycleViewDivider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

/**
 *
 * Created by Administrator on 2016/12/30.
 */

public class AnnouncementListActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

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
    BorrowDetailBean bean;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_list);
        ButterKnife.bind(this);
        title.setText("消息");
        init();
    }

    private void init() {
        fragmentList = new ArrayList<>();


        viewPagerFramentAdapter = new ViewPagerFramentAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(viewPagerFramentAdapter);
        viewPager.setOnPageChangeListener(this);

        NoticeListFragment fragment_day1 = new NoticeListFragment();

        fragmentList.add(fragment_day1);

        consultationAppFragemnt fragment_day2 = new consultationAppFragemnt();

        fragmentList.add(fragment_day2);



        viewPagerFramentAdapter.notifyDataSetChanged();


    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }


    @OnClick({ R.id.back, R.id.month_rl, R.id.day_rl})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case  R.id.back:
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
