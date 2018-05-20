package com.chebao.bean;

/**
 * 创建日期：2018/5/17 on 16:56
 * 描述:
 * 作者:jackson Administrator
 */
public class RansomBean  {

    /**
     * state : {"info":"实名成功","status":"0"}
     */
    double kshed;
    private StateBean state;

    public StateBean getState() {
        return state;
    }

    public void setState(StateBean state) {
        this.state = state;
    }

    public double getKshed() {
        return kshed;
    }

    public void setKshed(double kshed) {
        this.kshed = kshed;
    }

    public static class StateBean {


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
