package com.chebao.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chebao.Adapter.BuyNotesAdapter;
import com.chebao.R;
import com.chebao.bean.IntroduceBean;
import com.chebao.bean.InvestmentBean;
import com.chebao.bean.TPersonBorrower;
import com.chebao.net.NetWorks;
import com.chebao.utils.T1changerString;
import com.chebao.widget.DividerItemDecoration;
import com.pvj.xlibrary.loadinglayout.LoadingLayout;
import com.pvj.xlibrary.loadinglayout.Utils;
import com.pvj.xlibrary.loadingrecyclerview.LoadMoreRecyclerLoadingLayout;
import com.pvj.xlibrary.log.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/3/10.
 * 产品介绍列表
 */

public class Fragemt_Explain extends Fragment {
    @Bind(R.id.layout_contiant)
    LoadingLayout layoutContiant;
    @Bind(R.id.collateralinfos)
    TextView collateralInfos;
    @Bind(R.id.introductioninfos)
    TextView introductioninfos;
    @Bind(R.id.guaranteetype)
    TextView guaranteetype;
    @Bind(R.id.borrowername)
    TextView borrowername;
//    @Bind(R.id.age)
//    TextView age;
//    @Bind(R.id.maritalstatus)
//    TextView maritalstatus;
//    @Bind(R.id.address)
//    TextView address;

    int page = 1;
    int pagesize = 10;


    String id;
    int borrowStatus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        id = bundle.getString("id");
        borrowStatus = bundle.getInt("borrowStatus");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_explan, null);
        ButterKnife.bind(this, rootView);
        initView();
        net(0, 0);
        return rootView;
    }

    /**
     * @param stype     是刷新 还是加载  0是刷新  1是加载
     * @param inrefresh 第几次刷新下的加载
     */
    private void net(final int stype, final int inrefresh) {
        NetWorks.queryBorrowIntroduce(id, new Subscriber<IntroduceBean>() {
            @Override
            public void onStart() {
                layoutContiant.setStatus(LoadingLayout.Loading);
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                layoutContiant.setStatus(LoadingLayout.Error);
                Logger.json(e.toString());
            }

            @Override
            public void onNext(IntroduceBean b) {

                if (b.getState().getStatus() == 0) {
                    layoutContiant.setStatus(LoadingLayout.Success);
                    IntroduceBean.DataBean dataBean=b.getData();

                    Spanned text = Html.fromHtml(dataBean.getCollateralInfos());
                    collateralInfos.setText(text);
                    Spanned introductioninfostext = Html.fromHtml(dataBean.getIntroductionInfos());
                    introductioninfos.setText(introductioninfostext);

                    Spanned riskControlInfos  = Html.fromHtml(dataBean.getRiskControlInfos());
                    borrowername.setText(riskControlInfos);

                    guaranteetype.setText(dataBean.getGuaranteetype()+"");
//                    TPersonBorrower tPersonBorrower = b.gettPersonBorrower();
//                    borrowername.setText("借款人:" + tPersonBorrower.getBorrowerName() + "");
//                    age.setText("年龄:" + tPersonBorrower.getAge() + "");
//                    maritalstatus.setText("婚姻状况:" + T1changerString.MaritalStatusChager(tPersonBorrower.getMaritalStatus()) + "");
//                    address.setText("籍贯信息:" + tPersonBorrower.getHouseRegisteProvince() + tPersonBorrower.getHouseRegisteCity() + "");

                } else {
                    layoutContiant.setStatus(LoadingLayout.Empty);
                }

            }
        });
    }

    private void initView() {


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}