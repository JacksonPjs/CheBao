package com.chebao.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chebao.Adapter.ViewPagerFramentAdapter;
import com.chebao.R;
import com.pvj.xlibrary.loadinglayout.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Fragment_Find extends BaseFragment implements ViewPager.OnPageChangeListener {
    @Bind(R.id.biao_viewpager)
    ViewPager biaoViewpager;
    @Bind(R.id.title)
    TextView title;

    @Bind(R.id.putong_team)
    TextView putongTeam;
    @Bind(R.id.xinshou_team)
    TextView xinshouTeam;

    @Bind(R.id.putong_view)
    ImageView putongView;
    @Bind(R.id.xinshou_view)
    ImageView xinshouView;


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_find,
                null);
        ButterKnife.bind(this,rootView);
        title.setText("发现");
        return rootView;
    }

    @Override
    public void initData() {
        super.initData();
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new Fragment_Find_Start());
        fragmentList.add(new Fragment_Find_End());
        ViewPagerFramentAdapter adapter = new ViewPagerFramentAdapter(getChildFragmentManager(),fragmentList);
        biaoViewpager.setAdapter(adapter);
        biaoViewpager.setOnPageChangeListener( this);
    }
    @OnClick({R.id.putong_team, R.id.xinshou_team})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.putong_team:
                biaoViewpager.setCurrentItem(0);
                break;
            case R.id.xinshou_team:
                biaoViewpager.setCurrentItem(1);
                break;
        }
    }
    @Override
    public void fillDate() {

    }

    @Override
    public void requestData() {

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

        putongTeam.setTextColor(Utils.getColor(getActivity(),R.color.black_home_four_midle));
        xinshouTeam.setTextColor(Utils.getColor(getActivity(),R.color.black_home_four_midle));
        putongView.setVisibility(View.INVISIBLE);
        xinshouView.setVisibility(View.INVISIBLE);

        if (position==0){
            putongTeam.setTextColor(Utils.getColor(getActivity(),R.color.org_home));
            putongView.setVisibility(View.VISIBLE);


        }else{
            xinshouView.setVisibility(View.VISIBLE);
            xinshouTeam.setTextColor(Utils.getColor(getActivity(),R.color.org_home));
        }
    }

}
