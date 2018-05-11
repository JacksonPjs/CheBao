package com.chebao.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chebao.Adapter.InvestmentAdapter;
import com.chebao.R;
import com.chebao.widget.DividerItemDecoration;
import com.chebao.widget.dialog.InvestmentDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建日期：2018/5/7 on 16:37
 * 描述:投资记录
 * 作者:jackson Administrator
 */
public class InvestmentActivity extends BaseActivity {
    @Bind(R.id.type_select)
    TextView select;
    @Bind(R.id.recyclerView)
    RecyclerView publicLv;
    @Bind(R.id.title)
    TextView title;

    List<String> biaoBeenList;
    InvestmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        ButterKnife.bind(this);
        title.setText("投资记录");
        initView();
    }

    private void initView() {
        biaoBeenList = new ArrayList<>();
        biaoBeenList.add("1111");
        biaoBeenList.add("1111");
        biaoBeenList.add("1111");
        biaoBeenList.add("1111");

        adapter = new InvestmentAdapter(biaoBeenList, this);
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
        switch (view.getId()) {
            case R.id.type_select:

                InvestmentDialog dialog=new InvestmentDialog(this, new InvestmentDialog.ItemSelectListener() {
                    @Override
                    public void selectAll() {

                    }

                    @Override
                    public void selectMeet() {
                        //已还清

                    }

                    @Override
                    public void selectInvite() {
                        //招标中
                    }

                    @Override
                    public void selectRepayment() {
                        //还款中
                    }
                });
                dialog.show();
                break;
        }
    }
}