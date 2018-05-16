package com.chebao.bean;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * 创建日期：2018/5/14 on 16:42
 * 描述:
 * 作者:jackson Administrator
 */
public class HuiKuanBean {




    private StateBean state;
    private ArrayList<DataBean> data;
    String dyds;
    String zds;

    public String getDyds() {
        return dyds;
    }

    public void setDyds(String dyds) {
        this.dyds = dyds;
    }

    public String getZds() {
        return zds;
    }

    public void setZds(String zds) {
        this.zds = zds;
    }

    public StateBean getState() {
        return state;
    }

    public void setState(StateBean state) {
        this.state = state;
    }

    public ArrayList<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
        this.data = data;
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

    public static class DataBean {
        /****/
        private Long id;

        /** 订单号 **/
        private String ordId;

        /** 投资ID **/
        private Long investId;

        /** 借款ID **/
        private Long borrowId;

        /** 投资人ID **/
        private Long investorId;

        /** 预定还款日期 **/
        private long repayDate;

        /** 实际还款时间 **/
        private long realRepayTime;

        /** 应还本金 **/
        private BigDecimal capitalAmount;

        /** 应还收益 **/
        private BigDecimal profitAmount;

        /** 剩余本金 **/
        private BigDecimal remainCapitalAmount;

        /** 剩余利息 **/
        private BigDecimal remainProfitAmount;

        /** 居间费用 **/
        private BigDecimal feeAmount;

        /** 还款状态（1为未还款，2为已还款） **/
        private Integer repayStatus;

        /** 总期数 **/
        private Integer peroids;

        /** 第几期 **/
        private Integer numOfPeriods;

        /** 创建时间 **/
        private long createTime;

        private String thirdUserId;
        /**是否参与债转0未1已**/
        private Integer ynclaim;

        private String borrowTitle;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getOrdId() {
            return ordId;
        }

        public void setOrdId(String ordId) {
            this.ordId = ordId;
        }

        public Long getInvestId() {
            return investId;
        }

        public void setInvestId(Long investId) {
            this.investId = investId;
        }

        public Long getBorrowId() {
            return borrowId;
        }

        public void setBorrowId(Long borrowId) {
            this.borrowId = borrowId;
        }

        public Long getInvestorId() {
            return investorId;
        }

        public void setInvestorId(Long investorId) {
            this.investorId = investorId;
        }

        public long getRepayDate() {
            return repayDate;
        }

        public void setRepayDate(long repayDate) {
            this.repayDate = repayDate;
        }

        public long getRealRepayTime() {
            return realRepayTime;
        }

        public void setRealRepayTime(long realRepayTime) {
            this.realRepayTime = realRepayTime;
        }

        public BigDecimal getCapitalAmount() {
            return capitalAmount;
        }

        public void setCapitalAmount(BigDecimal capitalAmount) {
            this.capitalAmount = capitalAmount;
        }

        public BigDecimal getProfitAmount() {
            return profitAmount;
        }

        public void setProfitAmount(BigDecimal profitAmount) {
            this.profitAmount = profitAmount;
        }

        public BigDecimal getRemainCapitalAmount() {
            return remainCapitalAmount;
        }

        public void setRemainCapitalAmount(BigDecimal remainCapitalAmount) {
            this.remainCapitalAmount = remainCapitalAmount;
        }

        public BigDecimal getRemainProfitAmount() {
            return remainProfitAmount;
        }

        public void setRemainProfitAmount(BigDecimal remainProfitAmount) {
            this.remainProfitAmount = remainProfitAmount;
        }

        public BigDecimal getFeeAmount() {
            return feeAmount;
        }

        public void setFeeAmount(BigDecimal feeAmount) {
            this.feeAmount = feeAmount;
        }

        public Integer getRepayStatus() {
            return repayStatus;
        }

        public void setRepayStatus(Integer repayStatus) {
            this.repayStatus = repayStatus;
        }

        public Integer getPeroids() {
            return peroids;
        }

        public void setPeroids(Integer peroids) {
            this.peroids = peroids;
        }

        public Integer getNumOfPeriods() {
            return numOfPeriods;
        }

        public void setNumOfPeriods(Integer numOfPeriods) {
            this.numOfPeriods = numOfPeriods;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getThirdUserId() {
            return thirdUserId;
        }

        public void setThirdUserId(String thirdUserId) {
            this.thirdUserId = thirdUserId;
        }

        public Integer getYnclaim() {
            return ynclaim;
        }

        public void setYnclaim(Integer ynclaim) {
            this.ynclaim = ynclaim;
        }

        public String getBorrowTitle() {
            return borrowTitle;
        }

        public void setBorrowTitle(String borrowTitle) {
            this.borrowTitle = borrowTitle;
        }
    }
}
