package com.chebao.bean;

import java.util.List;

/**
 * 创建日期：2018/5/31 on 11:54
 * 描述:
 * 作者:jackson Administrator
 */
public class DepositListBean {
    private StateBean state;
    private List<DataBean> data;

    public StateBean getState() {
        return state;
    }

    public void setState(StateBean state) {
        this.state = state;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class StateBean {


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

    public static class DataBean {

        int borrowId;
        int id;
        long investTime;
        int investmoney;
        int locktime;
        int type;
        int userId;


        public int getBorrowId() {
            return borrowId;
        }

        public void setBorrowId(int borrowId) {
            this.borrowId = borrowId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getInvestTime() {
            return investTime;
        }

        public void setInvestTime(long investTime) {
            this.investTime = investTime;
        }

        public int getInvestmoney() {
            return investmoney;
        }

        public void setInvestmoney(int investmoney) {
            this.investmoney = investmoney;
        }

        public int getLocktime() {
            return locktime;
        }

        public void setLocktime(int locktime) {
            this.locktime = locktime;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
