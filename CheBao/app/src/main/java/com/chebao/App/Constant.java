package com.chebao.App;

import android.content.Context;
import android.view.WindowManager;

/**
 * 创建日期：2018/5/4 on 10:18
 * 描述:
 * 作者:jackson Administrator
 */
public class Constant {


    public static final boolean ANTI_ALIAS = true;

    public static final int DEFAULT_SIZE = 150;
    public static final int DEFAULT_START_ANGLE = 270;
    public static final int DEFAULT_SWEEP_ANGLE = 360;

    public static final int DEFAULT_ANIM_TIME = 1000;

    public static final int DEFAULT_MAX_VALUE = 100;
    public static final int DEFAULT_VALUE = 50;

    public static final int DEFAULT_HINT_SIZE = 15;
    public static final int DEFAULT_UNIT_SIZE = 30;
    public static final int DEFAULT_VALUE_SIZE = 15;

    public static final int DEFAULT_ARC_WIDTH = 15;

    public static final int DEFAULT_WAVE_HEIGHT = 40;


    public static final int INVEST_SELL_OUT=1;
    public static final int INVESTING=0;

    public static final boolean IsDebug=true;//线上,测试flag(发版本改false)
    public static final boolean IsCeshi=false;//切换网络地址的flag,线上false,测试地址为true


    /**
     * 获取屏幕分辨率
     * @param context
     * @return
     */
    public static int[] getScreenDispaly(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();// 手机屏幕的宽度
        int height = windowManager.getDefaultDisplay().getHeight();// 手机屏幕的高度
        int result[] = { width, height };
        return result;
    }

    //*/ 手势密码点的状态
    public static final int POINT_STATE_NORMAL = 0; // 正常状态

    public static final int POINT_STATE_SELECTED = 1; // 按下状态

    public static final int POINT_STATE_WRONG = 2; // 错误状态
    //*/

    public static  String[] BorrowStatus={"申请中","即将开标", "招标中", "满标中", "还款中", "已还款", "借款失败", "复审失败", "流标中", "复审中", "已流标"};

}
