package com.chebao.bean;

import java.util.List;

/**
 * 创建日期：2018/5/11 on 20:57
 * 描述:
 * 作者:jackson Administrator
 */
public class FindBean {


    private StateBean state;
    private pageBean page;

    private List<FindBean.DataBean> list;

    public StateBean getState() {
        return state;
    }

    public void setState(StateBean state) {
        this.state = state;
    }



    public List<DataBean> getData() {
        return list;
    }

    public void setData(List<DataBean> data) {
        this.list = data;
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

    public static class DataBean {
       private String activityBS;
       private String activityImg;
       private String activityMsg;
       private String activityName;
       private String activitySrc;
       private int activityType;
        private String activityWapSrc;
        private long activityendtime;
        private long activitystarttime;
        private long createTime;
        private int id;
        private  int source;

        public String getActivityImg() {
            return activityImg;
        }

        public void setActivityImg(String activityImg) {
            this.activityImg = activityImg;
        }

        public String getActivityBS() {
            return activityBS;
        }

        public void setActivityBS(String activityBS) {
            this.activityBS = activityBS;
        }

        public String getActivityMsg() {
            return activityMsg;
        }

        public void setActivityMsg(String activityMsg) {
            this.activityMsg = activityMsg;
        }

        public String getActivityName() {
            return activityName;
        }

        public void setActivityName(String activityName) {
            this.activityName = activityName;
        }

        public String getActivitySrc() {
            return activitySrc;
        }

        public void setActivitySrc(String activitySrc) {
            this.activitySrc = activitySrc;
        }

        public int getActivityType() {
            return activityType;
        }

        public void setActivityType(int activityType) {
            this.activityType = activityType;
        }

        public String getActivityWapSrc() {
            return activityWapSrc;
        }

        public void setActivityWapSrc(String activityWapSrc) {
            this.activityWapSrc = activityWapSrc;
        }

        public long getActivityendtime() {
            return activityendtime;
        }

        public void setActivityendtime(long activityendtime) {
            this.activityendtime = activityendtime;
        }

        public long getActivitystarttime() {
            return activitystarttime;
        }

        public void setActivitystarttime(long activitystarttime) {
            this.activitystarttime = activitystarttime;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }
    }
}
