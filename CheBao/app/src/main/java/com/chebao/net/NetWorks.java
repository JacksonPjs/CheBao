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
import com.chebao.bean.WithdrawBean;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 */
public class NetWorks extends RetrofitUtils {
    protected static final NetService service = getRetrofit().create(NetService.class);

    /**
     * 版本更新
     *
     * @param observer
     */
    public static void appCurrentVersion(Subscriber<AppUpdataBean> observer) {
        setSubscribe(service.appCurrentVersion("1"), observer);
    }
    /**
     * 开启验证码
     *
     * @param observer
     */
    public static void startCaptcha(String noncestr, Subscriber<String> observer) {
        setSubscribe(service.startCaptcha(noncestr), observer);
    }


    /**
     * 取短信验证码
     *
     * @param observer
     */
    public static void getPhoneCode(String noncestr, String cellPhone, Subscriber<String> observer) {
        setSubscribe(service.getPhoneCode(noncestr, cellPhone), observer);
    }


    /**
     * 2.验证手机号码是否存在
     *
     * @param observer
     */
    public static void verificationNewUserPhone(String userPhone, Subscriber<InfoBean> observer) {
        setSubscribe(service.verificationNewUserPhone(userPhone), observer);
    }

    /**
     * 2.验证手机号码是否存在
     *
     * @param observer
     */
    public static void forGetPassPhone(String param, Subscriber<InfoBean> observer) {
        setSubscribe(service.forGetPassPhone(param), observer);
    }

    /**
     * 找回密码-获取验证码
     *
     * @param observer
     */
    public static void getPhoneCode(String cellPhone, Subscriber<ForgetPassBean> observer) {
        setSubscribe(service.getPhoneCode(cellPhone), observer);
    }
    /**
     * 找回密码-验证手机验证码
     *
     * @param observer
     */
    public static void reFormforGetPassCode(String phone,String telCode, Subscriber<InfoBean> observer) {
        setSubscribe(service.reFormforGetPassCode(phone,telCode), observer);
    }
    /**
     * 获取图形验证码
     *
     * @param observer
     */
    public static void getImageCode(String param, Subscriber<ImageCodeBean> observer) {
        setSubscribe(service.getImageCode(param), observer);
    }

    /**
     * 获取短信验证码
     *
     * @param phoneNum
     * @param noncestr
     * @param observer
     */
    public static void getPhoneCodeByImageCode(String phoneNum, String noncestr, String param, Subscriber<InfoMsg> observer) {
        setSubscribe(service.getPhoneCodeByImageCode(param, noncestr, phoneNum), observer);
    }

    /**
     * 注册
     *
     * @param observer
     */
    public static void regist(String cellPhone, String pwd, String regCode, String regReferee,String channel_name,String source , Subscriber<InfoBean> observer) {
        setSubscribe(service.regist(cellPhone, pwd, regCode, regReferee,channel_name,source ), observer);
    }

    /**
     * 忘记密码
     *
     * @param observer
     */
    public static void updateforGetPass(String phone, String telCode, String forGetpassword,Subscriber<InfoBean> observer) {
        setSubscribe(service.updateforGetPass(phone, telCode, forGetpassword), observer);
    }

    /**
     * 首页
     *
     * @param observer
     */
    public static void index(Subscriber<OneBean> observer) {
        setSubscribe(service.index(), observer);
    }

    /**
     * 登录
     *
     * @param observer
     */
    public static void login(String cellPhone, String pwd, Subscriber<LoginBean> observer) {
        setSubscribe(service.login(cellPhone, pwd), observer);
    }
    /**
     * 退出登录
     *
     * @param observer
     */
    public static void loginOut(String Cookie,String cellPhone, Subscriber<InfoMsg> observer) {
        setSubscribe(service.loginOut(Cookie, cellPhone), observer);
    }
    /**
     * 个人中心
     *
     * @param observer
     */
    public static void selectUserIndex(Subscriber<CenterIndexBean> observer) {
        setSubscribe(service.selectUserIndex(), observer);
    }

    /**
     * 获取产品列表
     *
     * @param observer
     */
    public static void selectBorrowListApp(String page, String pagesize, Subscriber<BiaoBean> observer) {
        setSubscribe(service.selectNoviceBorrowList(page, pagesize), observer);
    }


    /**
     * 获取产品列表(已售罄)
     *
     * @param observer
     */
    public static void selectBorrowListApp2(String page, String pagesize, Subscriber<BiaoBean> observer) {
        setSubscribe(service.selectNoviceBorrowList2(page, pagesize), observer);
    }

    /**
     * 产品详情
     *
     * @param observer
     */
    public static void queryBorrowDetail(String id, Subscriber<BorrowDetailBean> observer) {
        setSubscribe(service.queryBorrowDetail(id), observer);
    }

    /**
     * 投资记录
     *
     * @param observer
     */
    public static void borrowInvestList(String id,String borrowStatus, String page, String pagesize,
                                        Subscriber<InvestmentBean> observer) {
        setSubscribe(service.borrowInvestList(id,borrowStatus, page, pagesize), observer);
    }


    /**
     * 风控师意见
     *
     * @param observer
     */
    public static void queryBorrowRisk(String id, Subscriber<IntroduceBean> observer) {
        setSubscribe(service.queryBorrowRisk(id), observer);
    }

    /**
     * 产品信息
     *
     * @param observer
     */
    public static void queryBorrowIntroduce(String id, Subscriber<IntroduceBean> observer) {
        setSubscribe(service.queryBorrowIntroduce(id), observer);
    }

    /**
     * 产品资质资料
     *
     * @param observer
     */
    public static void queryBorrowData(String id, Subscriber<ProductDetialBean> observer) {
        setSubscribe(service.queryBorrowData(id), observer);
    }

    /**
     * 发现
     *
     * @param observer
     */
    public static void selectActivitylist(String type, String curPage, Subscriber<FindBean> observer) {
        setSubscribe(service.selectActivitylist(type, curPage), observer);
    }

    /**
     * 设置交易密码
     *
     * @param observer
     */
    public static void setUserPayPwd(String address, String readdress, Subscriber<InfoBean> observer) {
        setSubscribe(service.setUserPayPwd(address, readdress), observer);
    }

    /**
     * 修改支付密码
     *
     * @param observer
     */
    public static void updateUserPay(String address, String readdress, Subscriber<InfoBean> observer) {
        setSubscribe(service.setUserPayPwd(address, readdress), observer);
    }
    /**
     * 修改登录密码
     *
     * @param observer
     */
    public static void updateUserPass(String address, String readdress, Subscriber<InfoBean> observer) {
        setSubscribe(service.updateUserPass(address, readdress), observer);
    }
    /**
     * 获取实名信息
     *
     * @param observer
     */
    public static void userPerson(Subscriber<CertificationBean> observer) {
        setSubscribe(service.userPerson(), observer);
    }
    /**
     * 实名认证
     *
     * @param observer
     */
    public static void fysmrz(String Cookie,String name,String idCard,Subscriber<CertificationBean> observer) {
        setSubscribe(service.fysmrz(Cookie,name,idCard), observer);
    }

    /**
     * 银行卡列表
     *
     * @param observer
     */
    public static void selectBankCard(Subscriber<BankListBean> observer) {
        setSubscribe(service.selectBankCard(), observer);
    }
    /**
     *
     *
     * @param observer
     */
    public static void userdidibao(String Cookie,Subscriber<DidibaoBean> observer) {
        setSubscribe(service.userdidibao(Cookie), observer);
    }

    /**
     *优惠券列表
     *
     * @param observer
     */
    public static void CouponAndJxList(String Cookie, String type ,String curPage, Subscriber<DiscountListBean> observer) {
        setSubscribe(service.CouponAndJxList(Cookie, type,curPage),observer);
    }

    /**
     *优惠券列表
     *
     * @param observer
     */
    public static void userreturnrecord(String Cookie,String curPage,String repayStatus, Subscriber<HuiKuanBean> observer) {
        setSubscribe(service.userreturnrecord( Cookie,curPage, repayStatus),observer);
    }

    /**
     * 交易记录
     *
     * @param observer
     */
    public static void moneyFlow(String curPage, String funtype, Subscriber<CaseFlowBean> observer) {
        setSubscribe(service.moneyFlow(curPage, funtype), observer);
    } /**

     /**
     * 交易记录(全部)
     *
     * @param observer
     */
    public static void moneyFlowAll(String curPage, Subscriber<CaseFlowBean> observer) {
        setSubscribe(service.moneyFlowAll(curPage), observer);
    } /**

     * 交易记录
     *
     * @param observer
     */
    public static void selectInvestListing(String curPage, String borrowStatus, Subscriber<InvestmentBean> observer) {
        setSubscribe(service.selectInvestListing(curPage,borrowStatus), observer);
    }
/**
        * 交易记录(全部)
     *
             * @param observer
     */
    public static void selectInvestListingAll(String curPage, Subscriber<InvestmentBean> observer) {
        setSubscribe(service.selectInvestListingAll(curPage), observer);
    }
    /**
     * 充值
     *
     * @param observer
     */
    public static void wxpay(String payway, String rechargeAmount, Subscriber<ChagerBean> observer) {
        setSubscribe(service.wxpay( rechargeAmount), observer);
    }
    /**
     * 申请提现
     *
     * @param observer
     */
    public static void userWithdraw(String cookie,Subscriber<WithdrawBean> observer) {
        setSubscribe(service.userWithdraw(cookie), observer);
    }
    /**
     * 提现
     *
     * @param observer
     */
    public static void tongLianUserWithdraw(String withdrawAmount, String pwd,Subscriber<InfoBean> observer) {
        setSubscribe(service.tongLianUserWithdraw(withdrawAmount, pwd), observer);
    }/**
     * 赎回准备
     *
     * @param observer
     */
    public static void didiRedeemInfo(Subscriber<RansomBean> observer) {
        setSubscribe(service.didiRedeemInfo(), observer);
    }

    /**
     * 平台公告
     *
     * @param observer
     */
    public static void noticeList( Subscriber<AnnouncementBean> observer) {
        setSubscribe(service.noticeList(), observer);
    }
 /**
     * 媒体报道
     *
     * @param observer
     */
    public static void consultationPageApp( Subscriber<ConsultationBean> observer) {
        setSubscribe(service.consultationPageApp(), observer);
    }
    /**
     * 公告详情
     *
     * @param observer
     */
    public static void showNotice(String noticeId, Subscriber<AnnouncementBean> observer) {
        setSubscribe(service.showNotice(noticeId), observer);
    }
    /**
     * 公告详情
     *
     * @param observer
     */
    public static void consultationApp(String id, Subscriber<InfoBean> observer) {
        setSubscribe(service.consultationApp(id), observer);
    }

    /**
     * 公告详情
     *
     * @param observer
     */
    public static void ajaxgetuseryhq(String couponAmount,String useqx,String rqlx, Subscriber<DiscountListBean> observer) {
        setSubscribe(service.ajaxgetuseryhq(couponAmount,useqx,rqlx), observer);
    }
    /**
     * 投标
     *
     * @param observer
     */
    public static void investAjaxBorrow(String Cookie,String tradingPassword,String investAmount,String borrowId,int coupontype
                                        ,String couponId,Subscriber<PayBean> observer) {
        setSubscribe(service.investAjaxBorrow(Cookie,tradingPassword,investAmount,borrowId,coupontype,couponId), observer);
    }
    /**
     * 投标没有卡券
     *
     * @param observer
     */
    public static void investAjaxBorrow2(String Cookie,String tradingPassword,String investAmount,String borrowId,Subscriber<PayBean> observer) {
        setSubscribe(service.investAjaxBorrow2(Cookie,tradingPassword,investAmount,borrowId), observer);
    }/**
     * 投标没有卡券
     *
     * @param observer
     */
    public static void applicationForm(String investAmount,String tradingPassword,Subscriber<DepositBean> observer) {
        setSubscribe(service.applicationForm(investAmount,tradingPassword), observer);
    }

    /**
     *
     *
     * @param observer
     */
    public static void didiPurchaseInfo(Subscriber<InfoBean> observer) {
        setSubscribe(service.didiPurchaseInfo(), observer);
    }
    /**
     *
     *赎回记录
     * @param observer
     */
    public static void appRedemptionRecord(String Cookie,String page,Subscriber<RandomListBean> observer) {
        setSubscribe(service.appRedemptionRecord(Cookie,page), observer);
    }

    /**
     *存入记录
     *
     * @param observer
     */
    public static void appInvestHqRecord(String Cookie,String page,Subscriber<DepositListBean> observer) {
        setSubscribe(service.appInvestHqRecord(Cookie,page), observer);
    }

    /**
     * 插入观察者-泛型
     *
     * @param observable
     * @param observer
     * @param <T>
     */
    public static <T> void setSubscribe(Observable<T> observable, Subscriber<T> observer) {

        observable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


}
