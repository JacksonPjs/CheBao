package com.chebao.ui.fragment.about;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chebao.R;
import com.chebao.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建日期：2018/5/19 on 18:43
 * 描述:
 * 作者:jackson Administrator
 */
public class FragmentAbout extends Fragment {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    //fragment存储器
    private List<Fragment> mFragments;
    //tab条目中的内容
    private String[] titles = {"关于我们", "法人承诺函", "组织信息", "审计报告", "风控管理", "重大事项", "其他信息"};
    //    private String[]urls = {
//            "file:///android_asset/about.html",
//            "file:///android_asset/letter.html",
//            "file:///android_asset/organization.html",
//            "file:///android_asset/audit.html",
//            "file:///android_asset/management.html",
//            "file:///android_asset/matters.html",
//            "file:///android_asset/other.html"};
    private FixedPagerAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        mTabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        initData();
        return view;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mAdapter = new FixedPagerAdapter(getChildFragmentManager());
        mAdapter.setTitles(titles);//标题
        mFragments = new ArrayList<>();



        mFragments.add(new FragmentAboutUs());
        mFragments.add(new Fragment_letter());
        mFragments.add(new Fragment_organization());
        mFragments.add(new Fragment_audit());
        mFragments.add(new Fragment_management());
        mFragments.add(new Fragment_matters());
        mFragments.add(new FragmentOther());


        //把要显示的fragment集合传给adapter
        mAdapter.setFragments(mFragments);
        /**
         * 设置tablayout的模式
         *  模式一：MODE_SCROLLABLE  可以滑动，显示很多个tab中的几个展示出来
         *  模式二：MODE_FIXED Fixed tabs display all tabs concurrently   展示出所有的，适合三四个tab
         */
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //给viewPager设置适配器
        mViewPager.setAdapter(mAdapter);
        //TabLayout绑定ViewPager
        mTabLayout.setupWithViewPager(mViewPager);

    }


}
