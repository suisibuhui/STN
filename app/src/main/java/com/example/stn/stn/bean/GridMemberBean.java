package com.example.stn.stn.bean;

import java.util.List;

/**
 * Created by ASUS on 2018/3/14.
 */

public class GridMemberBean {

    private String tranId;
    private String return_code;
    private String return_message;
    private List<OpInfoListBean> opInfoList;

    public String getReturn_message() {
        return return_message;
    }

    public String getReturn_code() {
        return return_code;
    }

    public List<OpInfoListBean> getOpInfoList() {
        return opInfoList;
    }

    public static class OpInfoListBean{
        private String opId;
        private String opName;

        public String getOpId() {
            return opId;
        }

        public String getOpName() {
            return opName;
        }

        public void setOpId(String opId) {
            this.opId = opId;
        }

        public void setOpName(String opName) {
            this.opName = opName;
        }
    }
}
