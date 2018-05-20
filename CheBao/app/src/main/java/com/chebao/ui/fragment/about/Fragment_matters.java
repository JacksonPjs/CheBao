package com.chebao.ui.fragment.about;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chebao.R;

import butterknife.ButterKnife;

/**
 * 创建日期：2018/5/20 on 13:12
 * 描述:重大事项
 * 作者:jackson Administrator
 */
public class Fragment_matters  extends Fragment {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_matters, null);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


}