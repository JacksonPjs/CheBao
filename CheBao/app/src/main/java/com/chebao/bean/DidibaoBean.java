package com.chebao.bean;

/**
 * 创建日期：2018/5/14 on 11:41
 * 描述:
 * 作者:jackson Administrator
 */
public class DidibaoBean {
    private StateBean state;
    String zrsy;
    String ljsy;
    String gmze;

    private double dsbj;
    private double ydsy;

    public StateBean getState() {
        return state;
    }

    public void setState(StateBean state) {
        this.state = state;
    }

    public String getZrsy() {
        return zrsy;
    }

    public void setZrsy(String zrsy) {
        this.zrsy = zrsy;
    }

    public String getLjsy() {
        return ljsy;
    }

    public void setLjsy(String ljsy) {
        this.ljsy = ljsy;
    }

    public String getGmze() {
        return gmze;
    }

    public void setGmze(String gmze) {
        this.gmze = gmze;
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
