package com.chebao.net;




import com.chebao.bean.BiaoBean;
import com.chebao.bean.BorrowDetailBean;
import com.chebao.bean.CenterIndexBean;
import com.chebao.bean.ImageCodeBean;
import com.chebao.bean.InfoBean;
import com.chebao.bean.InvestmentBean;
import com.chebao.bean.LoginBean;
import com.chebao.bean.OneBean;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 */
public class NetWorks extends RetrofitUtils {
    protected static final NetService service = getRetrofit().create(NetService.class);




    /**
     * 开启验证码
     *
     * @param observer
     */
    public static void startCaptcha(String noncestr , Subscriber<String> observer) {
        setSubscribe(service.startCaptcha(noncestr), observer);
    }


    /**
     * 取短信验证码
     *
     * @param observer
     */
    public static void getPhoneCode(String noncestr , String cellPhone , Subscriber<String> observer) {
        setSubscribe(service.getPhoneCode(noncestr,cellPhone), observer);
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
    public static void getPhoneCodeByImageCode(String phoneNum, String noncestr, String param, Subscriber<InfoBean> observer) {
        setSubscribe(service.getPhoneCodeByImageCode(param,noncestr,phoneNum), observer);
    }
    /**
     * 注册
     *
     * @param observer
     */
    public static void regist(String cellPhone, String pwd, String regCode, String regReferee, Subscriber<InfoBean> observer) {
        setSubscribe(service.regist(cellPhone, pwd, regCode, regReferee), observer);
    }

    /**
     * 忘记密码
     *
     * @param observer
     */
    public static void updateforGetPass(String phone, String telCode, String forGetpassword, Subscriber<InfoBean> observer) {
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
    public static void borrowInvestList(String id,String result, String page, String pagesize,
                                        Subscriber<InvestmentBean> observer) {
        setSubscribe(service.borrowInvestList(id,result, page, pagesize), observer);
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
