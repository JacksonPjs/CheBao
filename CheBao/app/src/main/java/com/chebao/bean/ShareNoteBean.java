package com.chebao.bean;

import java.math.BigDecimal;
import java.util.List;

/**
 * 创建日期：2018/6/28 on 11:39
 * 描述:
 * 作者:jackson Administrator
 */
public class ShareNoteBean {
    private StateBean state;
    private List<DataBean> data;
    private BigDecimal sumtjjl;
    private  int count;

    public BigDecimal getSumtjjl() {
        return sumtjjl;
    }

    public void setSumtjjl(BigDecimal sumtjjl) {
        this.sumtjjl = sumtjjl;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public StateBean getState() {
        return state;
    }

    public void setState(StateBean state) {
        this.state = state;
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


        private BigDecimal awardAmount;
        private int id;
        private String cellPhone;
        private long  createTime;

        public BigDecimal getAwardAmount() {
            return awardAmount;
        }

        public void setAwardAmount(BigDecimal awardAmount) {
            this.awardAmount = awardAmount;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCellPhone() {
            return cellPhone;
        }

        public void setCellPhone(String cellPhone) {
            this.cellPhone = cellPhone;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }
    }
}
