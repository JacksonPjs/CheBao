package com.chebao.net;


import com.chebao.bean.AnnouncementBean;
import com.chebao.bean.AppUpdataBean;
import com.chebao.bean.BankListBean;
import com.chebao.bean.BiaoBean;
import com.chebao.bean.BorrowDetailBean;
import com.chebao.bean.CaseFlowBean;
import com.chebao.bean.CenterIndexBean;
import com.chebao.bean.CertificationBean;
import com.chebao.bean.ChagerBean;
import com.chebao.bean.ConsultationBean;
import com.chebao.bean.DepositBean;
import com.chebao.bean.DepositListBean;
import com.chebao.bean.DidibaoBean;
import com.chebao.bean.DiscountListBean;
import com.chebao.bean.FindBean;
import com.chebao.bean.ForgetPassBean;
import com.chebao.bean.HuiKuanBean;
import com.chebao.bean.ImageCodeBean;
import com.chebao.bean.InfoBean;
import com.chebao.bean.InfoMsg;
import com.chebao.bean.IntroduceBean;
import com.chebao.bean.InvestmentBean;
import com.chebao.bean.LoginBean;
import com.chebao.bean.OneBean;
import com.chebao.bean.PayBean;
import com.chebao.bean.ProductDetialBean;
import com.chebao.bean.RandomListBean;
import com.chebao.bean.RansomBean;
import com.chebao.bean.ShareNoteBean;
import com.chebao.bean.WithdrawBean;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 */
public interface NetService {


    //服务器路径
//    public static final String API_SERVER = "http://192.168.1.171:8080/jp/app/";//测试地址
//

    public static String API_SERVER = "http://www.chebaojr.com/app/";  //上线地址
    //网址路径
//    public static final String API_SERVER_Url = "http://192.168.1.171:8080/jp/";
    public static String API_SERVER_Url = "http://www.chebaojr.com/";


    //主程序地址
    public static final String API_SERVER_Main = API_SERVER_Url;
    //测试主程序地址
//    public static final String API_SERVER_Main = "http://172.18.5.252:8080/jp/";
    //图片地址
//    public static final String API_SERVER_Photo = "http://192.168.1.171:8080/jp/";
//
    public static final String API_SERVER_Photo = "http://www.chebaojr.com/";


    // 开启滑块
    @POST("startCaptcha.html")
    Observable<String> startCaptcha(@Query("noncestr") String noncestr);


    // 取短信验证码
    @POST("getPhoneCode.html")
    Observable<String> getPhoneCode(@Query("noncestr") String noncestr, @Query("cellPhone") String cellPhone);


    /**
     * 找回密码 验证手机号码
     */
    @POST("forGetPassPhone.html")
    Observable<InfoBean> forGetPassPhone(@Query("param") String param);

    /**
     * 找回密码 获取验证码
     */
    @POST("getPhoneCode2.html")
    Observable<ForgetPassBean> getPhoneCode(@Query("cellPhone") String cellPhone);

    /**
     * 找回密码-验证手机验证码
     */
    @POST("forGetPassPhone.html")
    Observable<InfoBean> reFormforGetPassCode(@Query("phone") String phone, @Query("telCode") String telCode);


    /**
     * 投标
     */
    @POST("bfpay/investAjaxBorrow.html")
    Observable<PayBean> investAjaxBorrow(@Query("Cookie") String Cookie, @Query("tradingPassword") String tradingPassword,
                                         @Query("investAmount") String investAmount, @Query("borrowId") String borrowId,
                                         @Query("coupontype") Integer coupontype,
                                         @Query("couponId") String couponId);

    /**
     * 投标
     */
    @POST("bfpay/investAjaxBorrow.html")
    Observable<PayBean> investAjaxBorrow2(@Query("Cookie") String Cookie, @Query("tradingPassword") String tradingPassword,
                                          @Query("investAmount") String investAmount, @Query("borrowId") String borrowId);


    /**
     * 6.实名验证
     */
    @POST("userPerson.html")
    Observable<CertificationBean> userPerson();


    /**
     * 4.注册
     */
    @POST("regist.html")
    Observable<InfoBean> regist(@Query("cellPhone") String cellPhone, @Query("pwd") String pwd,
                                @Query("regCode") String regCode, @Query("regReferee") String regReferee,
                                @Query("channelbs") String channel_name,@Query("source") String source
    );

    /**
     * 找回密码
     */
    @POST("updateforGetPass.html")
    Observable<InfoBean> updateforGetPass(@Query("phone") String phone, @Query("telCode") String telCode, @Query("forGetpassword") String forGetpassword);


    /**
     * /**
     * 1,登录
     */
    @POST("login.html")
    Observable<LoginBean> login(@Query("userName") String userName, @Query("pwd") String pwd);

    /**
     * /**
     * 退出登录
     */
    @POST("loginOut.html")
    Observable<InfoMsg> loginOut(@Query("token") String cookie, @Query("cellPhone") String cellPhone);


    /**
     * 2.验证手机号码是否存在
     */
    @POST("verificationNewUserPhone.html")
    Observable<InfoBean> verificationNewUserPhone(@Query("userPhone") String userPhone);


    /**
     * 获取图形验证码
     */
    @POST("getImageCode.html")
    Observable<ImageCodeBean> getImageCode(@Query("noncestr") String noncestr);

    /**
     * 获取短信验证码
     */
    @POST("getPhoneCodeByImageCode.html")
    Observable<InfoMsg> getPhoneCodeByImageCode(@Query("imgCode") String param, @Query("noncestr") String noncestr, @Query("cellPhone") String userPhone);

    /**
     * 21． 首页
     */
    @POST("index.html")
    Observable<OneBean> index();

    /**
     * 个人中心
     */
    @POST("selectUserIndex.html")
    Observable<CenterIndexBean> selectUserIndex();


    /**
     * 获取产品列表(进行中)
     */
    @POST("selectNoviceBorrowList.html")
    Observable<BiaoBean> selectNoviceBorrowList(@Query("curPage") String curPage, @Query("pageSize") String pageSize);

    /**
     * 获取产品列表(已售罄)
     */
    @POST("selectNoviceBorrowList2.html")
    Observable<BiaoBean> selectNoviceBorrowList2(@Query("curPage") String curPage, @Query("pageSize") String pageSize);

    /**
     * 产品详情
     */
    @POST("queryBorrowDetail.html")
    Observable<BorrowDetailBean> queryBorrowDetail(@Query("id") String id);

    /**
     * 产品出借记录
     */
    @POST("borrowInvestList.html")
    Observable<InvestmentBean> borrowInvestList(@Query("borrowId") String borrowId,@Query("result") String borrowStatus,
                                                @Query("curPage") String curPage, @Query("pageSize") String pageSize);

    /**
     * 产品风控意见
     */
    @POST("queryBorrowRisk.html")
    Observable<IntroduceBean> queryBorrowRisk(@Query("borrowId") String id);

    /**
     * 产品信息
     */
    @POST("queryBorrowIntroduce.html")
    Observable<IntroduceBean> queryBorrowIntroduce(@Query("id") String id);

    /**
     * 产品资质资料
     */
    @POST("queryBorrowData.html")
    Observable<ProductDetialBean> queryBorrowData(@Query("id") String id);

    /**
     * 发现
     */
    @POST("selectActivitylist.html")
    Observable<FindBean> selectActivitylist(@Query("type") String type, @Query("curPage") String curPage);

    /**
     * 6.设置交易密码
     */
    @POST("setUserPayPwd.html")
    Observable<InfoBean> setUserPayPwd(@Query("address") String address, @Query("reAddress") String reAddress);

    /**
     * 6.修改交易密码
     */
    @POST("updateUserPay.html")
    Observable<InfoBean> updateUserPay(@Query("oldpwd") String oldpwd, @Query("newpwd") String newpwd);

    /**
     * 6.修改登录密码
     */
    @POST("updateUserPass.html")
    Observable<InfoBean> updateUserPass(@Query("oldPass") String oldpwd, @Query("newPass") String newpwd);

    /**
     * 我的滴滴宝
     */
    @POST("userdidibao.html")
    Observable<DidibaoBean> userdidibao(@Query("Cookie") String Cookie);

    /**
     * 我的红包
     */
    @POST("queryTCouponAndJxList.html")
    Observable<DiscountListBean> CouponAndJxList(@Query("Cookie") String Cookie, @Query("couponStatus") String tradingPassword
            , @Query("curPage") String curPage);


    /**
     * 回款记录
     */
    @POST("userreturnrecord.html")
    Observable<HuiKuanBean> userreturnrecord(@Query("Cookie") String Cookie, @Query("curPage") String curPage, @Query("repayStatus") String repayStatus);

    /**
     * 交易记录
     */
    @POST("moneyFlow.html")
    Observable<CaseFlowBean> moneyFlow(@Query("curPage") String curPage, @Query("fundType") String fundType);

    /**
     * 交易记录(全部)
     */
    @POST("moneyFlow.html")
    Observable<CaseFlowBean> moneyFlowAll(@Query("curPage") String curPage);

    /**
     * 投资记录
     */
    @POST("selectInvestListing.html")
    Observable<InvestmentBean> selectInvestListing(@Query("curPage") String curPage, @Query("borrowStatus") String borrowStatus);

    /**
     * 投资记录(全部)
     */
    @POST("selectInvestListing.html")
    Observable<InvestmentBean> selectInvestListingAll(@Query("curPage") String curPage);

    /**
     * 实名认证
     */
    @POST("fysmrz.html")
    Observable<CertificationBean> fysmrz(@Query("Cookie") String Cookie, @Query("name") String name, @Query("idCard") String idCard);

    /**
     * 充值
     */
    @POST("fyPay.html")
    Observable<ChagerBean> wxpay(@Query("rechargeAmount") String rechargeAmount);

    /**
     * 银行卡列表
     */
    @POST("selectBankCard.html")
    Observable<BankListBean> selectBankCard();

    /**
     * 提现准备
     *
     * @return
     */
    @POST("userWithdraw.html")
    Observable<WithdrawBean> userWithdraw(@Query("Cookie") String Cookie);

    /**
     * 提现申请
     *
     * @return
     */
    @POST("tongLianUserWithdraw.html")
    Observable<InfoBean> tongLianUserWithdraw(@Query("withdrawAmount") String withdrawAmount,
                                              @Query("pwd") String pwd);

    /**
     * 赎回
     *
     * @return
     */
    @POST("didiRedeemInfo.html")
    Observable<RansomBean> didiRedeemInfo();

    /**
     * 平台公告
     */
    @POST("noticeList.html")
    Observable<AnnouncementBean> noticeList();

    /**
     * 媒体报道
     */
    @POST("consultationPageApp.html")
    Observable<ConsultationBean> consultationPageApp();

    /**
     * 平台公告
     *
     * @return
     */
    @POST("showNotice.html")
    Observable<AnnouncementBean> showNotice(@Query("noticeId") String noticeId);

    /**
     * 媒体报道详情
     *
     * @return
     */
    @POST("consultationApp.html")
    Observable<InfoBean> consultationApp(@Query("id") String id);

    /**
     * 我的卡券
     *
     * @return
     */
    @POST("ajaxgetuseryhq.html")
    Observable<DiscountListBean> ajaxgetuseryhq(@Query("couponAmount") String couponAmount, @Query("useqx") String useqx, @Query("rqlx") String rqlx);

    /**
     * 存入
     *
     * @return
     */
    @POST("applicationForm.html")
    Observable<DepositBean> applicationForm(@Query("investmoney") String investmoney, @Query("tradingPassword") String tradingPassword);

    /**
     * 存入准备
     *
     * @return
     */
    @POST("didiPurchaseInfo.html")
    Observable<InfoBean> didiPurchaseInfo();

    /**
     * 赎回记录
     *
     * @return
     */
    @POST("appRedemptionRecord.html")
    Observable<RandomListBean> appRedemptionRecord( @Query("Cookie") String Cookie, @Query("curPage") String curPage);

    /**
     * 存入记录
     *
     * @return
     */
    @POST("appInvestHqRecord.html")
    Observable<DepositListBean> appInvestHqRecord(@Query("Cookie") String Cookie, @Query("curPage") String curPage);

    /**
     * 邀请记录
     *
     * @return
     */
    @POST("selectReferee.html")
    Observable<ShareNoteBean> selectReferee(@Query("Cookie") String Cookie, @Query("curPage") String curPage);

    /**
     * 版本升级
     */
    @POST("appCurrentVersion.html")
    Observable<AppUpdataBean> appCurrentVersion(@Query("phoneType") String phoneType);
}
