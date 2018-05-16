package com.chebao.bean;

/**
 * 创建日期：2018/5/14 on 21:14
 * 描述:
 * 作者:jackson Administrator
 */
public class ForgetPassBean {

    /**
     * {"state":{"info":"手机验证码发送成功，请及时查收手机短信！","status":"y"}}
     */

    private StateBean state;

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
        private String status;

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
    }
}
