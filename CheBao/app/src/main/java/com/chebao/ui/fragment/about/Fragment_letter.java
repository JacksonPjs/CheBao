package com.chebao.ui.fragment.about;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chebao.R;
import com.chebao.ui.activity.PicturesLookActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建日期：2018/5/20 on 13:10
 * 描述: 法人承诺函
 * 作者:jackson Administrator
 */
public class Fragment_letter extends Fragment {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_letter, null);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.letter})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.letter:
                intent = new Intent(getActivity(), PicturesLookActivity.class);
                intent.putExtra("url",R.mipmap.bg_letter);
                startActivity(intent);
                break;


        }
    }


}
