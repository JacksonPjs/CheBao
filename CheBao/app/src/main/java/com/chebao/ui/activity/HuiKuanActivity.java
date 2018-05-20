package com.chebao.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chebao.Adapter.TransactionAdapter;
import com.chebao.Adapter.ViewPagerFramentAdapter;
import com.chebao.R;
import com.chebao.bean.HuiKuanBean;
import com.chebao.bean.IntroduceBean;
import com.chebao.net.NetWorks;
import com.chebao.ui.fragment.Fragment_Find_End;
import com.chebao.ui.fragment.Fragment_Find_Start;
import com.chebao.ui.fragment.Fragment_HuiKuan_Start;
import com.chebao.ui.fragment.Fragment_Huikuan_End;
import com.chebao.widget.DividerItemDecoration;
import com.chebao.widget.dialog.TransactionDialog;
import com.pvj.xlibrary.loadinglayout.LoadingLayout;
import com.pvj.xlibrary.loadinglayout.Utils;
import com.pvj.xlibrary.log.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

import static com.chebao.utils.edncodeUtils.getCookie;

/**
 * 创建日期：2018/5/7 on 16:35
 * 描述:
 * 作者:jackson Administrator
 */
public class HuiKuanActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @Bind(R.id.biao_viewpager)
    ViewPager biaoViewpager;
    @Bind(R.id.title)
    TextView title;

    @Bind(R.id.putong_team)
    TextView putongTeam;  @Bind(R.id.allmoney)
    TextView allmoney;
    @Bind(R.id.xinshou_team)
    TextView xinshouTeam;
 @Bind(R.id.mmoney)
    TextView mmoney;

    @Bind(R.id.putong_view)
    ImageView putongView;
    @Bind(R.id.xinshou_view)
    ImageView xinshouView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huikuan);
        ButterKnife.bind(this);
        title.setText("回款查询");
        initView();
        net();
    }

    private void initView() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new Fragment_HuiKuan_Start());
        fragmentList.add(new Fragment_Huikuan_End());
        ViewPagerFramentAdapter adapter = new ViewPagerFramentAdapter(getSupportFragmentManager(), fragmentList);
        biaoViewpager.setAdapter(adapter);
        biaoViewpager.setOnPageChangeListener(this);

    }
    /**
     *
     */
    private void net() {
        NetWorks.userreturnrecord(getCookie(),1+"","1", new Subscriber<HuiKuanBean>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Logger.json(e.toString());
            }

            @Override
            public void onNext(HuiKuanBean b) {

                if (b.getState().getStatus() == 0) {
                    allmoney.setText(b.getZds()+"");
                    mmoney.setText(b.getDyds()+"");


                } else {
                }

            }
        });
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
        if (position == 0) {
            putongTeam.setTextColor(Utils.getColor(this, R.color.org_home));
            xinshouTeam.setTextColor(Utils.getColor(this, R.color.black_home_four_midle));
            putongView.setVisibility(View.VISIBLE);
            xinshouView.setVisibility(View.GONE);


        } else {
            putongView.setVisibility(View.GONE);
            xinshouView.setVisibility(View.VISIBLE);
            putongTeam.setTextColor(Utils.getColor(this, R.color.black_home_four_midle));
            xinshouTeam.setTextColor(Utils.getColor(this, R.color.org_home));
        }
    }
}