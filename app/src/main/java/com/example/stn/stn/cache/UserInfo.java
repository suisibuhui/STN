package com.example.stn.stn.cache;

import com.example.stn.stn.bean.CustomerInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Name: UserInfo
 * Author: xulong
 * Comment: //TODO
 * Date: 2016-08-26 17:21.
 */
public class UserInfo implements Serializable {


    /**
     * tranId : 12334
     * return_code : 0
     * return_message : 成功
     * acctId : 23917966
     * prodInfos : [{"userName":"薛余翠","userType":1,"userTypeName":"宽带用户","installAddr":"","userPropName":"普通","refundTypeName":"普通","stateName":null,"userProp":"0","refundType":"0","reorgId":"11295","stdName":"闵行区 梅陇(原曹行)(2309) 春申景城居委 锦梅路1398弄春申景城三期17号1502室","state":"正常","prodInstId":18333485404,"terminals":[{"resEquNo":"3","resEquNo2":"","resEquNo3":"","resType":"","resTypeName":"","resCode":"","resCodeName":""}]}]
     */

    private String tranId;
    private String return_code;
    private String return_message;
    private String acctId;
    private List<CustomerInfo.CustsEntity> customer;
    private List<ProdInfosBean> userInfoList;

    public List<CustomerInfo.CustsEntity> getCustomer() {
        return customer;
    }

    public void setCustomer(List<CustomerInfo.CustsEntity> customer) {
        this.customer = customer;
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

    public void setAcctId(String acctId) {
        this.acctId = acctId;
    }

    public void setProdInfos(List<ProdInfosBean> prodInfos) {
        this.userInfoList = prodInfos;
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

    public String getAcctId() {
        return acctId;
    }

    public List<ProdInfosBean> getProdInfos() {
        return userInfoList;
    }

    public static class ProdInfosBean {

        private String subscriberId;
        private String planName;

        public String getPlanName() {
            return planName;
        }

        public void setPlanName(String planName) {
            planName = planName;
        }

        private String subscriberCode;
        private String subscriberName;
        private String customerId;
        private String resNo;
        private String cardNo;
        private String subFlag;
        private String subscriberStatus;
        private String contactName;
        private String phone1;
        private String mobile;
        private String familyLock;
        private String bLoginName;
        private String address;
        private String resType;
        private String cardType;

        public String getResType() {
            return resType;
        }

        public String getCardType() {
            return cardType;
        }

        public String getSubscriberId() {
            return subscriberId;
        }

        public void setSubscriberId(String subscriberId) {
            this.subscriberId = subscriberId;
        }

        public String getSubscriberCode() {
            return subscriberCode;
        }

        public void setSubscriberCode(String subscriberCode) {
            this.subscriberCode = subscriberCode;
        }

        public String getSubscriberName() {
            return subscriberName;
        }

        public void setSubscriberName(String subscriberName) {
            this.subscriberName = subscriberName;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getResNo() {
            return resNo;
        }

        public void setResNo(String resNo) {
            this.resNo = resNo;
        }

        public String getCardNo() {
            return cardNo;
        }

        public void setCardNo(String cardNo) {
            this.cardNo = cardNo;
        }

        public String getSubFlag() {
            return subFlag;
        }

        public void setSubFlag(String subFlag) {
            this.subFlag = subFlag;
        }

        public String getSubscriberStatus() {
            return subscriberStatus;
        }

        public void setSubscriberStatus(String subscriberStatus) {
            this.subscriberStatus = subscriberStatus;
        }

        public String getContactName() {
            return contactName;
        }

        public void setContactName(String contactName) {
            this.contactName = contactName;
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

        public String getFamilyLock() {
            return familyLock;
        }

        public void setFamilyLock(String familyLock) {
            this.familyLock = familyLock;
        }

        public String getbLoginName() {
            return bLoginName;
        }

        public void setbLoginName(String bLoginName) {
            this.bLoginName = bLoginName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }



        public static class TerminalsBean {
            /**
             * resEquNo : 3
             * resEquNo2 :
             * resEquNo3 :
             * resType :
             * resTypeName :
             * resCode :
             * resCodeName :
             */

            private String resEquNo;
            private String resEquNo2;
            private String resEquNo3;
            private String resType;
            private String resTypeName;
            private String resCode;
            private String resCodeName;
            private String resPurp;//用途
            private String resPurpName;//用途的名字

            public void setResEquNo(String resEquNo) {
                this.resEquNo = resEquNo;
            }

            public void setResEquNo2(String resEquNo2) {
                this.resEquNo2 = resEquNo2;
            }

            public void setResEquNo3(String resEquNo3) {
                this.resEquNo3 = resEquNo3;
            }

            public void setResType(String resType) {
                this.resType = resType;
            }

            public void setResTypeName(String resTypeName) {
                this.resTypeName = resTypeName;
            }

            public void setResCode(String resCode) {
                this.resCode = resCode;
            }

            public void setResCodeName(String resCodeName) {
                this.resCodeName = resCodeName;
            }

            public String getResEquNo() {
                return resEquNo;
            }
            public String getResPurp() {
                return resPurp;
            }

            public void setResPurp(String resPurp) {
                this.resPurp = resPurp;
            }

            public void setResPurpName(String resPurpName) {
                this.resPurpName = resPurpName;
            }

            public String getResPurpName() {
                return resPurpName;
            }
            public String getResEquNo2() {
                return resEquNo2;
            }

            public String getResEquNo3() {
                return resEquNo3;
            }

            public String getResType() {
                return resType;
            }

            public String getResTypeName() {
                return resTypeName;
            }

            public String getResCode() {
                return resCode;
            }

            public String getResCodeName() {
                return resCodeName;
            }
        }
        public static class ProdUnOrder {


            private String custOrderId;
            private String businessId;
            private String businessName;
            private String outlineFlag;
            private String createDate;
            private String opId;
            private String opName;
            private String ownOrgId;//用途
            private String ownOrgIdName;//用途的名字

            public String getCustOrderId() {
                return custOrderId;
            }

            public void setCustOrderId(String custOrderId) {
                this.custOrderId = custOrderId;
            }

            public String getBusinessId() {
                return businessId;
            }

            public void setBusinessId(String businessId) {
                this.businessId = businessId;
            }

            public String getBusinessName() {
                return businessName;
            }

            public void setBusinessName(String businessName) {
                this.businessName = businessName;
            }

            public String getOutlineFlag() {
                return outlineFlag;
            }

            public void setOutlineFlag(String outlineFlag) {
                this.outlineFlag = outlineFlag;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getOpId() {
                return opId;
            }

            public void setOpId(String opId) {
                this.opId = opId;
            }

            public String getOpName() {
                return opName;
            }

            public void setOpName(String opName) {
                this.opName = opName;
            }

            public String getOwnOrgId() {
                return ownOrgId;
            }

            public void setOwnOrgId(String ownOrgId) {
                this.ownOrgId = ownOrgId;
            }

            public String getOwnOrgIdName() {
                return ownOrgIdName;
            }

            public void setOwnOrgIdName(String ownOrgIdName) {
                this.ownOrgIdName = ownOrgIdName;
            }

            public String getOrgId() {
                return orgId;
            }

            public void setOrgId(String orgId) {
                this.orgId = orgId;
            }

            public String getOrgIdName() {
                return orgIdName;
            }

            public void setOrgIdName(String orgIdName) {
                this.orgIdName = orgIdName;
            }

            public String getValidType() {
                return validType;
            }

            public void setValidType(String validType) {
                this.validType = validType;
            }

            public String getValidDate() {
                return validDate;
            }

            public void setValidDate(String validDate) {
                this.validDate = validDate;
            }

            public String getDocumentNum() {
                return documentNum;
            }

            public void setDocumentNum(String documentNum) {
                this.documentNum = documentNum;
            }

            private String orgId;//用途的名字
            private String orgIdName;//用途的名字
            private String validType;//用途的名字
            private String validDate;//用途的名字
            private String documentNum;//用途的名字


        }

    }
}
