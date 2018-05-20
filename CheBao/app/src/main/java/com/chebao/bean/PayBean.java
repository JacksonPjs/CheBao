package com.chebao.bean;

/**
 * 创建日期：2018/5/18 on 17:54
 * 描述:
 * 作者:jackson Administrator
 */
public class PayBean {

    /**
     * state : {"info":"","status":"0"}
     */

    private StateBean state;
    String usableAmount;
    String info;
    String status;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsableAmount() {
        return usableAmount;
    }

    public void setUsableAmount(String usableAmount) {
        this.usableAmount = usableAmount;
    }

    public StateBean getState() {
        return state;
    }

    public void setState(StateBean state) {
        this.state = state;
    }

    public static class StateBean {
        /**
         * info : 实名成功
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



