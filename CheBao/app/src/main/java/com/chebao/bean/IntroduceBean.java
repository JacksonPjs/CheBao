package com.chebao.bean;

/**
 * Created by Administrator on 2016/12/30.
 */

public class IntroduceBean {

    /**
     * state : {"info":"取值成功","status":"0"}
     */

    private StateBean state;
    private DataBean data;
    private TPersonBorrower tPersonBorrower;

    public StateBean getState() {
        return state;
    }

    public void setState(StateBean state) {
        this.state = state;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public TPersonBorrower gettPersonBorrower() {
        return tPersonBorrower;
    }

    public void settPersonBorrower(TPersonBorrower tPersonBorrower) {
        this.tPersonBorrower = tPersonBorrower;
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

    public static class DataBean {
        /**
         * annualRate : 10.0
         * borrowAmount : 100000.0
         * borrowStatus : 3
         * borrowTitle : ooioioioioi
         * borrowType : 1
         * createTime : 1483068079000
         * deadline : 2
         * deadlineType : 2
         * hasBorrowAmount : 0.0
         * id : 233
         * investStartTime : 1483069500000
         * mayCast : 100000.0
         * minInvestAmount : 100.0
         * progress : 0.0
         * raisingPeriod : 3
         * remainTime : 0
         * repayType : 1
         * fullTime : 1482128616000
         * repayDate : 1487520000000
         */

        private double annualRate;
        private double borrowAmount;
        private String borrowNo;
        private int borrowStatus;
        private String borrowTitle;
        private int borrowType;
        private int borrowerId;
        private int clockAmount;
        private String collateralInfos;
        private String introductionInfos;
        private String guaranteetype;
        private String riskControlInfos;


        private long createTime;
        private int createid;
        private int deadline;
        private int deadlineType;


        private double hasBorrowAmount;
        private int id;
        private long investStartTime;
        //     private double mayCast;
        private double minInvestAmount;
        //     private int raisingPeriod;
        private int remainTime;
        private int repayType;
        //    private long fullTime;
        //    private long repayDate;


        public String getRiskControlInfos() {
            return riskControlInfos;
        }

        public void setRiskControlInfos(String riskControlInfos) {
            this.riskControlInfos = riskControlInfos;
        }

        public String getGuaranteetype() {
            return guaranteetype;
        }

        public void setGuaranteetype(String guaranteetype) {
            this.guaranteetype = guaranteetype;
        }

        public String getIntroductionInfos() {
            return introductionInfos;
        }

        public void setIntroductionInfos(String introductionInfos) {
            this.introductionInfos = introductionInfos;
        }

        public String getBorrowNo() {
            return borrowNo;
        }

        public void setBorrowNo(String borrowNo) {
            this.borrowNo = borrowNo;
        }

        public int getBorrowerId() {
            return borrowerId;
        }

        public void setBorrowerId(int borrowerId) {
            this.borrowerId = borrowerId;
        }

        public int getClockAmount() {
            return clockAmount;
        }

        public void setClockAmount(int clockAmount) {
            this.clockAmount = clockAmount;
        }

        public String getCollateralInfos() {
            return collateralInfos;
        }

        public void setCollateralInfos(String collateralInfos) {
            this.collateralInfos = collateralInfos;
        }

        public int getCreateid() {
            return createid;
        }

        public void setCreateid(int createid) {
            this.createid = createid;
        }

        public int getRemainTime() {
            return remainTime;
        }

        public void setRemainTime(int remainTime) {
            this.remainTime = remainTime;
        }

        public int getRepayType() {
            return repayType;
        }

        public void setRepayType(int repayType) {
            this.repayType = repayType;
        }


        public double getAnnualRate() {
            return annualRate;
        }

        public void setAnnualRate(double annualRate) {
            this.annualRate = annualRate;
        }

        public double getBorrowAmount() {
            return borrowAmount;
        }

        public void setBorrowAmount(double borrowAmount) {
            this.borrowAmount = borrowAmount;
        }

        public int getBorrowStatus() {
            return borrowStatus;
        }

        public void setBorrowStatus(int borrowStatus) {
            this.borrowStatus = borrowStatus;
        }

        public String getBorrowTitle() {
            return borrowTitle;
        }

        public void setBorrowTitle(String borrowTitle) {
            this.borrowTitle = borrowTitle;
        }

        public int getBorrowType() {
            return borrowType;
        }

        public void setBorrowType(int borrowType) {
            this.borrowType = borrowType;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getDeadline() {
            return deadline;
        }

        public void setDeadline(int deadline) {
            this.deadline = deadline;
        }

        public int getDeadlineType() {
            return deadlineType;
        }

        public void setDeadlineType(int deadlineType) {
            this.deadlineType = deadlineType;
        }

        public double getHasBorrowAmount() {
            return hasBorrowAmount;
        }

        public void setHasBorrowAmount(double hasBorrowAmount) {
            this.hasBorrowAmount = hasBorrowAmount;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getInvestStartTime() {
            return investStartTime;
        }

        public void setInvestStartTime(long investStartTime) {
            this.investStartTime = investStartTime;
        }

//        public double getMayCast() {
//            return mayCast;
//        }
//
//        public void setMayCast(double mayCast) {
//            this.mayCast = mayCast;
//        }

        public double getMinInvestAmount() {
            return minInvestAmount;
        }

        public void setMinInvestAmount(double minInvestAmount) {
            this.minInvestAmount = minInvestAmount;
        }

//        public double getProgress() {
//            return progress;
//        }
//
//        public void setProgress(double progress) {
//            this.progress = progress;
//        }

//        public int getRaisingPeriod() {
//            return raisingPeriod;
//        }
//
//        public void setRaisingPeriod(int raisingPeriod) {
//            this.raisingPeriod = raisingPeriod;
//        }

//        public int getRemainTime() {
//            return remainTime;
//        }
//
//        public void setRemainTime(int remainTime) {
//            this.remainTime = remainTime;
//        }
//
//        public int getRepayType() {
//            return repayType;
//        }
//
//        public void setRepayType(int repayType) {
//            this.repayType = repayType;
//        }

//        public long getFullTime() {
//            return fullTime;
//        }
//
//        public void setFullTime(long fullTime) {
//            this.fullTime = fullTime;
//        }
//
//        public long getRepayDate() {
//            return repayDate;
//        }
//
//        public void setRepayDate(long repayDate) {
//            this.repayDate = repayDate;
//        }
    }
}
