package com.chebao;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;


import com.chebao.ui.activity.BaseActivity;
import com.chebao.ui.fragment.Fragment_Find;
import com.chebao.ui.fragment.Fragment_Home;
import com.chebao.ui.fragment.Fragment_Invest;
import com.chebao.ui.fragment.Fragment_Mine;
import com.pvj.xlibrary.loadinglayout.Utils;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {
    private ImageView[] imagebuttons;
    private TextView[] textviews;
    private int index=0;
    private FragmentManager fragmentManager;
    private Fragment currentFragment = new Fragment();
    private List<Fragment> fragments = new ArrayList<>();

    private int currentIndex = 0;
    //当前显示的fragment
    private static final String CURRENT_FRAGMENT = "STATE_FRAGMENT_SHOW";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTabView(savedInstanceState);


    }
    private void initTabView(Bundle savedInstanceState) {
        imagebuttons = new ImageView[4];
        imagebuttons[0] = (ImageView) findViewById(R.id.ib_weixin);
        imagebuttons[1] = (ImageView) findViewById(R.id.ib_contact_list);
        imagebuttons[2] = (ImageView) findViewById(R.id.ib_find);
        imagebuttons[3] = (ImageView) findViewById(R.id.ib_profile);

        imagebuttons[0].setSelected(true);
        textviews = new TextView[4];
        textviews[0] = (TextView) findViewById(R.id.tv_weixin);
        textviews[1] = (TextView) findViewById(R.id.tv_contact_list);
        textviews[2] = (TextView) findViewById(R.id.tv_find);
        textviews[3] = (TextView) findViewById(R.id.tv_profile);
        textviews[0].setTextColor(Utils.getColor(this, R.color.org_home));
        fragmentManager= getSupportFragmentManager();

        if (savedInstanceState != null) { // “内存重启”时调用

            //获取“内存重启”时保存的索引下标
            currentIndex = savedInstanceState.getInt(CURRENT_FRAGMENT,0);

            //注意，添加顺序要跟下面添加的顺序一样！！！！
            fragments.removeAll(fragments);
            fragments.add(fragmentManager.findFragmentByTag(0+""));
            fragments.add(fragmentManager.findFragmentByTag(1+""));
            fragments.add(fragmentManager.findFragmentByTag(2+""));
            fragments.add(fragmentManager.findFragmentByTag(3+""));

            //恢复fragment页面
            restoreFragment();


        }else{      //正常启动时调用

            fragments.add(new Fragment_Home());
            fragments.add(new Fragment_Invest());
            fragments.add(new Fragment_Find());
            fragments.add(new Fragment_Mine());

            showFragment();
        }



    }
    public void onTabClicked(View view) {

//        if (!(Boolean) SharedPreferencesUtils.getParam(this, "islogin", false)) {
//            if (view.getId() == R.id.re_find) {
//                Intent intent = new Intent(this, LoginActivity.class);
//                startActivity(intent);
//                return;
//            }
//        }

        switch (view.getId()) {
            case R.id.re_weixin:
                currentIndex = 0;
                break;
            case R.id.re_contact_list:
                currentIndex = 1;
                break;
            case R.id.re_find:
                currentIndex = 2;

                break;
            case R.id.re_profile:
                currentIndex = 3;

                break;
        }
        showFragment();


    }
    /**
     * 使用show() hide()切换页面
     * 显示fragment
     */
    private void showFragment(){

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        //如果之前没有添加过
        if(!fragments.get(currentIndex).isAdded()){
            transaction
                    .hide(currentFragment)
                    .add(R.id.fragment_container,fragments.get(currentIndex),""+currentIndex);  //第三个参数为添加当前的fragment时绑定一个tag

        }else{
            transaction
                    .hide(currentFragment)
                    .show(fragments.get(currentIndex));
        }

        currentFragment = fragments.get(currentIndex);
        imagebuttons[index].setSelected(false);
        // 把当前tab设为选中状态
        imagebuttons[currentIndex].setSelected(true);
        textviews[index].setTextColor(0xFF999999);

        textviews[currentIndex].setTextColor(Utils.getColor(this, R.color.org_home));
        index=currentIndex;
        transaction.commit();


    }

    /**
     * 恢复fragment
     */
    private void restoreFragment(){


        FragmentTransaction mBeginTreansaction = fragmentManager.beginTransaction();

        for (int i = 0; i < fragments.size(); i++) {

            if(i == currentIndex){
                mBeginTreansaction.show(fragments.get(i));
            }else{
                mBeginTreansaction.hide(fragments.get(i));
            }

        }

        mBeginTreansaction.commit();

        //把当前显示的fragment记录下来
        currentFragment = fragments.get(currentIndex);

    }

    /**
     * 当系统要回收Fragment时，我们告诉系统：不要再保存Fragment。相当于用户回到app的时候，我们就当用户是第一次打开app（因为很长时间没有操作了）。
     *
     * @param outState
     * @param outPersistentState
     */
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        //“内存重启”时保存当前的fragment名字
        outState.putInt(CURRENT_FRAGMENT,currentIndex);
         super.onSaveInstanceState(outState, outPersistentState);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EToast.reset();
    }


}

