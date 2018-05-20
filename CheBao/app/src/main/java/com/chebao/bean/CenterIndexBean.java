package com.chebao.bean;

/**
 * 个人中心
 * Created by Administrator on 2017/1/5.
 */

public class CenterIndexBean {

    /**
     * state : {"info":"取值成功","status":"0"}
     * tPerson : true
     * payPwd : true
     * email : false
     * tBankCardlist : true
     * images : images/user/user-img-01.png
     * userName : 13811111111
     * usableAmount : 9999400.0
     * total1 : 1.0E7
     */

    private StateBean state;

    private double usableAmount;
    private double total1;
    private double total2;
    private double total3;

    public double getTotal3() {
        return total3;
    }

    public void setTotal3(double total3) {
        this.total3 = total3;
    }

    public double getTotal2() {
        return total2;
    }

    public void setTotal2(double total2) {
        this.total2 = total2;
    }

    public StateBean getState() {
        return state;
    }

    public void setState(StateBean state) {
        this.state = state;
    }








    public double getUsableAmount() {
        return usableAmount;
    }

    public void setUsableAmount(double usableAmount) {
        this.usableAmount = usableAmount;
    }

    public double getTotal1() {
        return total1;
    }

    public void setTotal1(double total1) {
        this.total1 = total1;
    }

    public static class StateBean {
        /**
         * info : 取值成功
         * status : 0
         */

        private String info;
        private int status;

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
