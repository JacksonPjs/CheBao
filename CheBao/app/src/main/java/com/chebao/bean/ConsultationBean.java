package com.chebao.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 创建日期：2018/5/17 on 18:27
 * 描述:媒体报道bean
 * 作者:jackson Administrator
 */
public class ConsultationBean {


    private StateBean state;
    private List<ConsultationBean.DataBean> data;

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

    public static class StateBean implements Serializable {
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
    public static class DataBean implements Serializable {
        /**
         * data":[{"content":"6月1儿童节放5天假啊，，，哦？是吗？不存在的","createTime":1526552600000,"id":2,"src":"","srcImgPath":"","title":"6月1儿童节放5天假啊"}]
         */

        //    private int adminId;
        private long createTime;
        private int id;
        private String srcImgPath;
        private String src;

        private String content;
        private String title;

//

        public long getCreateTime() {
            return createTime;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
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

        public String getSrcImgPath() {
            return srcImgPath;
        }

        public void setSrcImgPath(String srcImgPath) {
            this.srcImgPath = srcImgPath;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

}
