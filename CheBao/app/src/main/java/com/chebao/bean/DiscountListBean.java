package com.chebao.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/9.
 */

public class DiscountListBean {
    private StateBean state;
    private ArrayList<DisData> data;

    public ArrayList<DisData> getData() {
        return data;
    }

    public void setData(ArrayList<DisData> data) {
        this.data = data;
    }

    public StateBean getState() {
        return state;
    }

    public void setState(StateBean state) {
        this.state = state;
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

        @Override
        public String toString() {
            return "StateBean{" +
                    "info='" + info + '\'' +
                    ", status=" + status +
                    '}';
        }
    }

    public static class DisData implements Serializable{

        /****/
        private Long id;

        /**用户id**/
        private Long userId;

        /**礼券名称**/
        private String couponName;

        /**礼券金额**/

        /**创建时间**/
        private long createTime;

        /**备注**/
        private String couponRemarks;

        /**礼券类型（1.红包 2:推荐奖励）**/
        private Integer couponType;

        /**礼券状态（1，未领取，2，未使用，3，已使用，4，未领取过期，5未使用过期）**/
        private Integer couponStatus;

        /**过期时间**/
        private long expirationDate;

        /**券号**/
        private String couponNum;
        /**
         * 用户电话号码
         */
        private String cellPhone;
        /**
         * 用户名
         */
        private String userName;

        private BigDecimal useMinMoney;//使用最低额度
        private Integer useqx;//使用最低日期
        /**礼券金额**/
        private Double couponAmount;
        int rqlx;

        public int getRqlx() {
            return rqlx;
        }

        public void setRqlx(int rqlx) {
            this.rqlx = rqlx;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getCouponName() {
            return couponName;
        }

        public void setCouponName(String couponName) {
            this.couponName = couponName;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getCouponRemarks() {
            return couponRemarks;
        }

        public void setCouponRemarks(String couponRemarks) {
            this.couponRemarks = couponRemarks;
        }

        public Integer getCouponType() {
            return couponType;
        }

        public void setCouponType(Integer couponType) {
            this.couponType = couponType;
        }

        public Integer getCouponStatus() {
            return couponStatus;
        }

        public void setCouponStatus(Integer couponStatus) {
            this.couponStatus = couponStatus;
        }

        public long getExpirationDate() {
            return expirationDate;
        }

        public void setExpirationDate(long expirationDate) {
            this.expirationDate = expirationDate;
        }

        public String getCouponNum() {
            return couponNum;
        }

        public void setCouponNum(String couponNum) {
            this.couponNum = couponNum;
        }

        public String getCellPhone() {
            return cellPhone;
        }

        public void setCellPhone(String cellPhone) {
            this.cellPhone = cellPhone;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public BigDecimal getUseMinMoney() {
            return useMinMoney;
        }

        public void setUseMinMoney(BigDecimal useMinMoney) {
            this.useMinMoney = useMinMoney;
        }

        public Integer getUseqx() {
            return useqx;
        }

        public void setUseqx(Integer useqx) {
            this.useqx = useqx;
        }

        public Double getCouponAmount() {
            return couponAmount;
        }

        public void setCouponAmount(Double couponAmount) {
            this.couponAmount = couponAmount;
        }
    }
}
