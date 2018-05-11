package com.chebao.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chebao.Adapter.BiaoAdapter;
import com.chebao.Adapter.TransactionAdapter;
import com.chebao.R;
import com.chebao.widget.DividerItemDecoration;
import com.chebao.widget.dialog.TransactionDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建日期：2018/5/7 on 14:27
 * 描述:交易记录
 * 作者:jackson Administrator
 */
public class TransactionActivity extends BaseActivity {
    @Bind(R.id.type_select)
    TextView select;
    @Bind(R.id.recyclerView)
    RecyclerView publicLv;

    List<String> biaoBeenList;
    TransactionAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        biaoBeenList = new ArrayList<>();
        biaoBeenList.add("1111");
        biaoBeenList.add("1111");
        biaoBeenList.add("1111");
        biaoBeenList.add("1111");

        adapter = new TransactionAdapter(biaoBeenList, this);
//        publicLv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
//        publicLv.verticalLayoutManager(getContext())
//                .setAdapter(adapter)
//                .setOnReloadListener(this)
//                .setRecycleViewBackgroundColor(Utils.getColor(getActivity(), R.color.bg_huise))
//                .setOnRefreshAndLoadMoreListener(this);
        LinearLayoutManager linearLayoutManager;
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        publicLv.setLayoutManager(linearLayoutManager);
        publicLv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        publicLv.setAdapter(adapter);

    }


    @OnClick({R.id.type_select})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.type_select:

                TransactionDialog myDialog=new TransactionDialog(this,
                        new TransactionDialog.ItemSelectListener() {
                            @Override
                            public void selectAll() {

                            }

                            @Override
                            public void selectRecharge() {

                            }

                            @Override
                            public void selectWithdraw() {

                            }
                        });
                myDialog.show();
                break;
        }
    }
}
