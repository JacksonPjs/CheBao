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

    public static final int DEFAULT_MAX_VALUE = 10000;
    public static final int DEFAULT_VALUE = 50;

    public static final int DEFAULT_HINT_SIZE = 15;
    public static final int DEFAULT_UNIT_SIZE = 30;
    public static final int DEFAULT_VALUE_SIZE = 15;

    public static final int DEFAULT_ARC_WIDTH = 15;

    public static final int DEFAULT_WAVE_HEIGHT = 40;


    public static final int INVEST_SELL_OUT=1;
    public static final int INVESTING=0;

    /**
     * 项目item
     * @param context
     * @return
     */
    public static final int MAIN_ITEM_HOME=0;
    public static final int MAIN_ITEM_INVEST=1;
    public static final int MAIN_ITEM_FIND=2;
    public static final int MAIN_ITEM_MINE=3;


    public static final String PAY_NO_PAYPSW="7";//支付返回没有设置交易密码
    public static final String PAY_NO_SHIMING="5";//支付返回没有实名
    public static final String PAY_NO_MONEY="53";//支付返回余额不足
    public static final String PAY_NO_BANK="6";//没有绑定银行卡
    public static final String STATUS_SUCCESS="0";//返回成功状态
    public static final String STATUS_NO_LOGIN="99";//返回未登录

    public static final int PAY_NO_PAYPSW_INTENT=2;//支付返回没有设置交易密码跳转flag
    public static final int PAY_NO_MONEY_INTENT=1;//余额不足
    public static final int PAY_NO_BANK_INTENT=3;//没有绑卡
    public static final int PAY_NO_SHIMING_INTENT=4;//没有实名

    public static final int STATUS_INTENT_TIME=1000;//跳转等待时间ms


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
