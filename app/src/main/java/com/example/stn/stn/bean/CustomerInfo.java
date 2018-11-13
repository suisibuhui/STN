package com.example.stn.stn.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Name: CustomerInfo
 * Author: xulong
 * Comment: //TODO
 * Date: 2016-08-23 19:01.
 */
public class CustomerInfo implements Serializable {


    /**
     * tranId : yl_test021475898563
     * return_code : 0
     * return_message : 成功
     * custs : [{"custName":"1223","custId":27014977,"custType":"1","custLevel":"1","custState":"2","custProp":"0","certType":"6","certNo":"1","certExpireDate":"20171130","certValidDate":"20161026","custPasswd":"","custOccupation":"个人","custCertAddr":"还在","remark":"","companyName":"1A","companyAddr":"1B","custAddrId":"25524085","stdAddrName":"虹口区 凉城(0907) 锦三 车站北路625弄21号2153室","stdAddrId":"62595382","stdAddrShortName":"虹口区 凉城","custTypeName":"公众客户","custLevelName":"VIP-1","custStateName":"正常","certTypeName":"台湾通行证","custPropName":null,"contacts":[{"contId":"25281286","contName":"周丽蕊","tell1":"65645645","tell2":"45645655","mobile1":15952236569,"mobile2":"","contAddr":"22822460","postCode":"","certType":"6","certTypeName":"台湾通行证","certNo":"","transferFlag":null,"transferFlagName":null,"defaultType":"4","fax":"","email":""},{"contId":"25281286","contName":"周丽蕊","tell1":"65645645","tell2":"45645655","mobile1":15952236569,"mobile2":"","contAddr":"22822460","postCode":"","certType":"6","certTypeName":"台湾通行证","certNo":"","transferFlag":null,"transferFlagName":null,"defaultType":"4","fax":"","email":""},{"contId":"25281286","contName":"周丽蕊","tell1":"65645645","tell2":"45645655","mobile1":15952236569,"mobile2":"","contAddr":"22822460","postCode":"","certType":"6","certTypeName":"台湾通行证","certNo":"","transferFlag":null,"transferFlagName":null,"defaultType":"4","fax":"","email":""},{"contId":"25281286","contName":"周丽蕊","tell1":"65645645","tell2":"45645655","mobile1":15952236569,"mobile2":"","contAddr":"22822460","postCode":"","certType":"6","certTypeName":"台湾通行证","certNo":"","transferFlag":null,"transferFlagName":null,"defaultType":"4","fax":"","email":""},{"contId":"25281286","contName":"周丽蕊","tell1":"65645645","tell2":"45645655","mobile1":15952236569,"mobile2":"","contAddr":"22822460","postCode":"","certType":"6","certTypeName":"台湾通行证","certNo":"","transferFlag":null,"transferFlagName":null,"defaultType":"4","fax":"","email":""},{"contId":"25281286","contName":"周丽蕊","tell1":"65645645","tell2":"45645655","mobile1":15952236569,"mobile2":"","contAddr":"22822460","postCode":"","certType":"6","certTypeName":"台湾通行证","certNo":"","transferFlag":null,"transferFlagName":null,"defaultType":"4","fax":"","email":""},{"contId":"25281286","contName":"周丽蕊","tell1":"65645645","tell2":"45645655","mobile1":15952236569,"mobile2":"","contAddr":"22822460","postCode":"","certType":"6","certTypeName":"台湾通行证","certNo":"","transferFlag":null,"transferFlagName":null,"defaultType":"4","fax":"","email":""},{"contId":"25281286","contName":"周丽蕊","tell1":"65645645","tell2":"45645655","mobile1":15952236569,"mobile2":"","contAddr":"22822460","postCode":"","certType":"6","certTypeName":"台湾通行证","certNo":"","transferFlag":null,"transferFlagName":null,"defaultType":"4","fax":"","email":""},{"contId":"25281286","contName":"周丽蕊","tell1":"65645645","tell2":"45645655","mobile1":15952236569,"mobile2":"","contAddr":"22822460","postCode":"","certType":"6","certTypeName":"台湾通行证","certNo":"","transferFlag":null,"transferFlagName":null,"defaultType":"4","fax":"","email":""},{"contId":"25281286","contName":"周丽蕊","tell1":"65645645","tell2":"45645655","mobile1":15952236569,"mobile2":"","contAddr":"22822460","postCode":"","certType":"6","certTypeName":"台湾通行证","certNo":"","transferFlag":null,"transferFlagName":null,"defaultType":"4","fax":"","email":""},{"contId":"25281286","contName":"周丽蕊","tell1":"65645645","tell2":"45645655","mobile1":15952236569,"mobile2":"","contAddr":"22822460","postCode":"","certType":"6","certTypeName":"台湾通行证","certNo":"","transferFlag":null,"transferFlagName":null,"defaultType":"4","fax":"","email":""},{"contId":"25281286","contName":"周丽蕊","tell1":"65645645","tell2":"45645655","mobile1":15952236569,"mobile2":"","contAddr":"22822460","postCode":"","certType":"6","certTypeName":"台湾通行证","certNo":"","transferFlag":null,"transferFlagName":null,"defaultType":"4","fax":"","email":""}]}]
     */

    private String tranId;
    private String return_code;
    private String return_message;
    private List<CustsEntity> customer;
    private String orderNo;

    public void setOrderNo(String orderNo){
        this.orderNo = orderNo;
    }
    public String getOrderNo(){
        return orderNo;
    }

    public void setTranId(String tranId) {
        this.tranId = tranId;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public void setReturn_message(String return_message) {
        this.return_message = return_message;
    }

    public void setCustomer(List<CustsEntity> customer) {
        this.customer = customer;
    }

    public String getTranId() {
        return tranId;
    }

    public String getReturn_code() {
        return return_code;
    }

    public String getReturn_message() {
        return return_message;
    }

    public List<CustsEntity> getCustomer() {
        return customer;
    }

    public static class CustsEntity implements Serializable {
        private String customerId;
        private String customerCode;
        private String customerName;
        private String contactName;
        private String corpOrgCode;
        private String recOrgname;
        private String createDate;
        private String identityType;
        private String identityNo;
        private String phone1;
        private String mobile;
        private String address;
        private String areaCode;
        private String settleCode;

        public String getCorpOrgName() {
            return recOrgname;
        }

        public void setCorpOrgName(String corpOrgName) {
            this.recOrgname = corpOrgName;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getCustomerCode() {
            return customerCode;
        }

        public void setCustomerCode(String customerCode) {
            this.customerCode = customerCode;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getContactName() {
            return contactName;
        }

        public void setContactName(String contactName) {
            this.contactName = contactName;
        }

        public String getCorpOrgCode() {
            return corpOrgCode;
        }

        public void setCorpOrgCode(String corpOrgCode) {
            this.corpOrgCode = corpOrgCode;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getIdentityType() {
            return identityType;
        }

        public void setIdentityType(String identityType) {
            this.identityType = identityType;
        }

        public String getIdentityNo() {
            return identityNo;
        }

        public void setIdentityNo(String identityNo) {
            this.identityNo = identityNo;
        }

        public String getPhone1() {
            return phone1;
        }

        public void setPhone1(String phone1) {
            this.phone1 = phone1;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public String getSettleCode() {
            return settleCode;
        }

        public void setSettleCode(String settleCode) {
            this.settleCode = settleCode;
        }
    }
}
