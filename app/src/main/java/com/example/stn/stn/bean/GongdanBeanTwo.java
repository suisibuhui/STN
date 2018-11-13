package com.example.stn.stn.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 成功返回信息
 * Created by 周丽蕊 on 2016/9/18.
 */
public class GongdanBeanTwo implements Serializable {


    /**
     * tranId : r1472358184124.8
     * return_code : 0
     * return_message : 成功
     */

    private String tranId;
    private String return_code;
    private List<ServInstInfoBean> servInstInfo;
    private String return_message;

    public String getReturn_message() {
        return return_message;
    }

    public String getTranId() {
        return tranId;
    }

    public void setTranId(String tranId) {
        this.tranId = tranId;
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public List<ServInstInfoBean> getServInstInfo() {
        return servInstInfo;
    }

    public void setServInstInfo(List<ServInstInfoBean> servInstInfo) {
        this.servInstInfo = servInstInfo;
    }

    public static class ServInstInfoBean{
        private String instId;
        private String servProcessName;
        private String servInstanceCode;
        private String eventName;
        private String subscriberName ;
        private String address;
        private String wfTypeName;
        private String customerCode;
        private String statusName ;
        private String operatorName;
        private String officeOrgName;
        private String createDate;
        private String finishDate;
        private String stdGridmanName;
        private String custType ;
        private String processNodeName ;
        private String mobile  ;
        private String broadBandLoginName  ;
        private boolean state = false;

        public void setState(boolean state) {
            this.state = state;
        }

       public boolean getState(){
           return this.state;
       }

        public String getInstId() {
            return instId;
        }

        public void setInstId(String instId) {
            this.instId = instId;
        }

        public String getServProcessName() {
            return servProcessName;
        }

        public void setServProcessName(String servProcessName) {
            this.servProcessName = servProcessName;
        }

        public String getServInstanceCode() {
            return servInstanceCode;
        }

        public void setServInstanceCode(String servInstanceCode) {
            this.servInstanceCode = servInstanceCode;
        }

        public String getEventName() {
            return eventName;
        }

        public void setEventName(String eventName) {
            this.eventName = eventName;
        }

        public String getSubscriberName() {
            return subscriberName;
        }

        public void setSubscriberName(String subscriberName) {
            this.subscriberName = subscriberName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getWfTypeName() {
            return wfTypeName;
        }

        public void setWfTypeName(String wfTypeName) {
            this.wfTypeName = wfTypeName;
        }

        public String getCustomerCode() {
            return customerCode;
        }

        public void setCustomerCode(String customerCode) {
            this.customerCode = customerCode;
        }

        public String getStatusName() {
            return statusName;
        }

        public void setStatusName(String statusName) {
            this.statusName = statusName;
        }

        public String getOperatorName() {
            return operatorName;
        }

        public void setOperatorName(String operatorName) {
            this.operatorName = operatorName;
        }

        public String getOfficeOrgName() {
            return officeOrgName;
        }

        public void setOfficeOrgName(String officeOrgName) {
            this.officeOrgName = officeOrgName;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getFinishDate() {
            return finishDate;
        }

        public void setFinishDate(String finishDate) {
            this.finishDate = finishDate;
        }

        public String getStdGridmanName() {
            return stdGridmanName;
        }

        public void setStdGridmanName(String stdGridmanName) {
            this.stdGridmanName = stdGridmanName;
        }

        public String getCustType() {
            return custType;
        }

        public void setCustType(String custType) {
            this.custType = custType;
        }

        public String getProcessNodeName() {
            return processNodeName;
        }

        public void setProcessNodeName(String processNodeName) {
            this.processNodeName = processNodeName;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getBroadBandLoginName() {
            return broadBandLoginName;
        }

        public void setBroadBandLoginName(String broadBandLoginName) {
            this.broadBandLoginName = broadBandLoginName;
        }
    }

}
