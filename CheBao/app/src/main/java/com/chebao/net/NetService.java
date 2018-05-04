package com.chebao.net;




import com.chebao.bean.BorrowDetailBean;
import com.chebao.bean.CertificationBean;
import com.chebao.bean.InfoBean;
import com.chebao.bean.LoginBean;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 */
public interface NetService {
    //服务器路径
//    public static final String API_SERVER = "http://172.18.5.252:8080/jp/app/";//测试地址


    public static final String API_SERVER = "http://www.enduo168.com/app/";  //上线地址
    //网址路径
    public static final String API_SERVER_Url = "http://192.168.1.196:8080/jp/";


//    public static final String API_SERVER_Url = "http://www.enduo168.com/";

    //主程序地址
    public static final String API_SERVER_Main = API_SERVER_Url;
    //测试主程序地址
//    public static final String API_SERVER_Main = "http://172.18.5.252:8080/jp/";
    //图片地址
    // public static final String API_SERVER_Photo = "http://192.168.1.196:8080/";



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
     * 投标
     */
    @POST("bfpay/investAjaxBorrow.html")
    Observable<String> investAjaxBorrow(@Query("tradingPassword") String tradingPassword, @Query("investAmount") String investAmount, @Query("borrowId") String borrowId);


    /**
     * 6.实名验证
     */
    @POST("userPerson.html")
    Observable<CertificationBean> userPerson();




    /**
     * 4.注册
     */
    @POST("regist.html")
    Observable<InfoBean> regist(@Query("cellPhone") String cellPhone, @Query("pwd") String pwd, @Query("regCode") String regCode, @Query("regReferee") String regReferee);

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
     * 2.验证手机号码是否存在
     */
    @POST("verificationNewUserPhone.html")
    Observable<InfoBean> verificationNewUserPhone(@Query("userPhone") String userPhone);


}
