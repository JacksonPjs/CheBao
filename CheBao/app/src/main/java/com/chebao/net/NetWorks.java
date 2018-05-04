package com.chebao.net;




import com.chebao.bean.InfoBean;

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
