package com.chebao.bean;

import java.math.BigDecimal;
import java.util.List;

/**
 * 投资记录
 */

public class InvestmentBean {


    /**
     * state : {"info":"取值成功","status":"0"}
     * data : [{"borrowId":239,"borrowStatus":0,"cellPhone":"13811111111","deadline":0,"deadlineType":0,"id":198,"investAmount":1000,"investTime":1483492314000,"investWay":1,"investorId":605,"isClaim":0,"numOfPeroids":0,"ordDate":1483459200000,"ordId":"20170104091154645899193","peroids":0,"realAmount":1000,"realName":"汪灶声","repayStatus":0,"repayType":0,"result":3,"transferAmount":0,"userName":"13811111111"},{"borrowId":239,"borrowStatus":0,"cellPhone":"13811111111","deadline":0,"deadlineType":0,"id":197,"investAmount":1000,"investTime":1483439508000,"investWay":1,"investorId":605,"isClaim":0,"numOfPeroids":0,"ordDate":1483372800000,"ordId":"20170103183148593131598","peroids":0,"realAmount":1000,"realName":"汪灶声","repayStatus":0,"repayType":0,"result":3,"transferAmount":0,"userName":"13811111111"},{"borrowId":239,"borrowStatus":0,"cellPhone":"13811111111","deadline":0,"deadlineType":0,"id":196,"investAmount":100,"investTime":1483439043000,"investWay":1,"investorId":605,"isClaim":0,"numOfPeroids":0,"ordDate":1483372800000,"ordId":"20170103182403233719626","peroids":0,"realAmount":100,"realName":"汪灶声","repayStatus":0,"repayType":0,"result":3,"transferAmount":0,"userName":"13811111111"},{"borrowId":239,"borrowStatus":0,"cellPhone":"13811111111","deadline":0,"deadlineType":0,"id":195,"investAmount":100,"investTime":1483438965000,"investWay":1,"investorId":605,"isClaim":0,"numOfPeroids":0,"ordDate":1483372800000,"ordId":"20170103182245241667560","peroids":0,"realAmount":100,"realName":"汪灶声","repayStatus":0,"repayType":0,"result":3,"transferAmount":0,"userName":"13811111111"},{"borrowId":239,"borrowStatus":0,"cellPhone":"13811111111","deadline":0,"deadlineType":0,"id":194,"investAmount":100,"investTime":1483438959000,"investWay":1,"investorId":605,"isClaim":0,"numOfPeroids":0,"ordDate":1483372800000,"ordId":"20170103182239797266305","peroids":0,"realAmount":100,"realName":"汪灶声","repayStatus":0,"repayType":0,"result":3,"transferAmount":0,"userName":"13811111111"},{"borrowId":236,"borrowStatus":0,"cellPhone":"13651433115","deadline":0,"deadlineType":0,"id":193,"investAmount":100,"investTime":1483438896000,"investWay":1,"investorId":569,"isClaim":0,"numOfPeroids":0,"ordDate":1483372800000,"ordId":"20170103182136792805114","peroids":0,"realAmount":100,"realName":"陈阳金","repayStatus":0,"repayType":0,"result":3,"transferAmount":0,"userName":"13651433115"},{"borrowId":236,"borrowStatus":0,"cellPhone":"13811111111","deadline":0,"deadlineType":0,"id":192,"investAmount":1300,"investTime":1483438850000,"investWay":1,"investorId":605,"isClaim":0,"numOfPeroids":0,"ordDate":1483372800000,"ordId":"20170103182050270975875","peroids":0,"realAmount":1300,"realName":"汪灶声","repayStatus":0,"repayType":0,"result":3,"transferAmount":0,"userName":"13811111111"},{"borrowId":239,"borrowStatus":0,"cellPhone":"13811111111","deadline":0,"deadlineType":0,"id":191,"investAmount":230,"investTime":1483438074000,"investWay":1,"investorId":605,"isClaim":0,"numOfPeroids":0,"ordDate":1483372800000,"ordId":"20170103180754962582802","peroids":0,"realAmount":230,"realName":"汪灶声","repayStatus":0,"repayType":0,"result":3,"transferAmount":0,"userName":"13811111111"},{"borrowId":239,"borrowStatus":0,"cellPhone":"13811111111","deadline":0,"deadlineType":0,"id":190,"investAmount":5000,"investTime":1483437804000,"investWay":1,"investorId":605,"isClaim":0,"numOfPeroids":0,"ordDate":1483372800000,"ordId":"20170103180324528754267","peroids":0,"realAmount":5000,"realName":"汪灶声","repayStatus":0,"repayType":0,"result":3,"transferAmount":0,"userName":"13811111111"},{"borrowId":236,"borrowStatus":0,"cellPhone":"13651433115","deadline":0,"deadlineType":0,"id":189,"investAmount":100,"investTime":1483435955000,"investWay":1,"investorId":569,"isClaim":0,"numOfPeroids":0,"ordDate":1483372800000,"ordId":"20170103173235389932443","peroids":0,"realAmount":100,"realName":"陈阳金","repayStatus":0,"repayType":0,"result":3,"transferAmount":0,"userName":"13651433115"}]
     * page : {"endRow":10,"firstPage":true,"hasNextPage":true,"hasPrePage":false,"lastPage":false,"limit":10,"nextPage":2,"offset":0,"page":1,"prePage":1,"slider":[1,2,3],"startRow":1,"totalCount":21,"totalPages":3}
     */
    private pageBean page;
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

    public pageBean getPage() {
        return page;
    }

    public void setPage(pageBean page) {
        this.page = page;
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
         * borrowId : 239
         * borrowStatus : 0
         * cellPhone : 13811111111
         * deadline : 0
         * deadlineType : 0
         * id : 198
         * investAmount : 1000.0
         * investTime : 1483492314000
         * investWay : 1
         * investorId : 605
         * isClaim : 0
         * numOfPeroids : 0
         * ordDate : 1483459200000
         * ordId : 20170104091154645899193
         * peroids : 0
         * realAmount : 1000.0
         * realName : 汪灶声
         * repayStatus : 0
         * repayType : 0
         * result : 3
         * transferAmount : 0.0
         * userName : 13811111111
         */
        /** 订单号 **/
        private String ordId;

        /** 订单时间 **/
        private long ordDate;

        /** 借款ID **/
        private Long borrowId;

        /** 投资人Id **/
        private Long investorId;

        /** 投资时间 **/
        private long investTime;

        /** 实际投资额度 **/
        private BigDecimal realAmount;

        /** 投资金额 **/
        private BigDecimal investAmount;

        /** 已转让金额 **/
        private BigDecimal transferAmount;

        /** 是否投资成功（0处理中，1成功，2，投标失败） **/
        private Integer result;

        /**投资方式（1手动投资，2自动投资，3app投资，4微信投资）**/
        private Integer investWay;
        /** 债权Id **/
        private Long claimId;

        /** 是否转让债权（0否，1是） **/
        private Integer isClaim;

        /** 投资备注  **/
        private String remarks;
        private Long goodsId;
        private BigDecimal profit;

        private Long groupId;

        private Long xjqId;

        private BigDecimal xjqmoney;

        private Long jxqId;

        private BigDecimal jxqmoney;
        /*交易密码*/
        private String tradingPassword;
        /*使用现金券json*/
        private String couponId;


        private String userName;

        private String borrowTitle;

        private int borrowStatus;

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

        public String getOrdId() {
            return ordId;
        }

        public void setOrdId(String ordId) {
            this.ordId = ordId;
        }

        public long getOrdDate() {
            return ordDate;
        }

        public void setOrdDate(long ordDate) {
            this.ordDate = ordDate;
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

        public BigDecimal getRealAmount() {
            return realAmount;
        }

        public void setRealAmount(BigDecimal realAmount) {
            this.realAmount = realAmount;
        }

        public BigDecimal getInvestAmount() {
            return investAmount;
        }

        public void setInvestAmount(BigDecimal investAmount) {
            this.investAmount = investAmount;
        }

        public BigDecimal getTransferAmount() {
            return transferAmount;
        }

        public void setTransferAmount(BigDecimal transferAmount) {
            this.transferAmount = transferAmount;
        }

        public Integer getResult() {
            return result;
        }

        public void setResult(Integer result) {
            this.result = result;
        }

        public Integer getInvestWay() {
            return investWay;
        }

        public void setInvestWay(Integer investWay) {
            this.investWay = investWay;
        }

        public Long getClaimId() {
            return claimId;
        }

        public void setClaimId(Long claimId) {
            this.claimId = claimId;
        }

        public Integer getIsClaim() {
            return isClaim;
        }

        public void setIsClaim(Integer isClaim) {
            this.isClaim = isClaim;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public Long getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(Long goodsId) {
            this.goodsId = goodsId;
        }

        public BigDecimal getProfit() {
            return profit;
        }

        public void setProfit(BigDecimal profit) {
            this.profit = profit;
        }

        public Long getGroupId() {
            return groupId;
        }

        public void setGroupId(Long groupId) {
            this.groupId = groupId;
        }

        public Long getXjqId() {
            return xjqId;
        }

        public void setXjqId(Long xjqId) {
            this.xjqId = xjqId;
        }

        public BigDecimal getXjqmoney() {
            return xjqmoney;
        }

        public void setXjqmoney(BigDecimal xjqmoney) {
            this.xjqmoney = xjqmoney;
        }

        public Long getJxqId() {
            return jxqId;
        }

        public void setJxqId(Long jxqId) {
            this.jxqId = jxqId;
        }

        public BigDecimal getJxqmoney() {
            return jxqmoney;
        }

        public void setJxqmoney(BigDecimal jxqmoney) {
            this.jxqmoney = jxqmoney;
        }

        public String getTradingPassword() {
            return tradingPassword;
        }

        public void setTradingPassword(String tradingPassword) {
            this.tradingPassword = tradingPassword;
        }

        public String getCouponId() {
            return couponId;
        }

        public void setCouponId(String couponId) {
            this.couponId = couponId;
        }

        public long getInvestTime() {
            return investTime;
        }

        public void setInvestTime(long investTime) {
            this.investTime = investTime;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }

    public static class pageBean {
        /**
         * "page":{"endRow":3,"firstPage":true,"hasNextPage":false,"hasPrePage":false,"lastPage":true,"limit":10,"nextPage":1,"offset":0,"page":1,"prePage":1,
         * "slider":[1],"startRow":1,"totalCount":3,"totalPages":1}}
         */

        private boolean hasNextPage;

        public boolean isHasNextPage() {
            return hasNextPage;
        }

        public void setHasNextPage(boolean hasNextPage) {
            this.hasNextPage = hasNextPage;
        }
    }
}
