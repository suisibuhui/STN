package com.example.stn.stn.cache;

import java.io.Serializable;
import java.util.List;

/**
 * Name: OrderList
 * Author: xulong
 * Comment: //订购信息查询
 * Date: 2016-08-23 19:22.
 */
public class OrderList implements Serializable {


    /**
     * tranId : 12344444
     * return_code : 0
     * return_message : 成功
     * offerInfos : [{"offerId":800500000377,"offerName":"◎交互应用基础包","validType":"1","validDate":"2014-08-05 12:45:08","acceptDate":"2014-08-05 12:45:08","expireDate":"2050-12-31 12:00:00","state":"1","corOrgId":11629,"corOrgName":"闸北分公司","exprieFollowState":3,"nextOfferId":800500000350,"nextOfferName":"标清点播回看-100元/年","offerType":"2","soState":"","remark":"","estTime":"2016-09-12 23:59:59","crdExpTime":"","creatDate":"2014-08-05 12:45:08","isCanUseSettle":1,"offerTypeName":"","validTypeName":"当前有效","stateName":"正常","soStateName":"","expireFollowStateName":"顺延续订"},{"offerId":800500000350,"offerName":"标清点播回看-100元/年","validType":"1","validDate":"2014-08-05 12:45:08","acceptDate":"2014-08-05 12:45:08","expireDate":"2050-12-31 12:00:00","state":"1","corOrgId":11629,"corOrgName":"闸北分公司","exprieFollowState":3,"nextOfferId":800500000350,"nextOfferName":"标清点播回看-100元/年","offerType":"2","soState":"","remark":"","estTime":"2016-09-12 23:59:59","crdExpTime":"","creatDate":"2014-08-05 12:45:08","isCanUseSettle":1,"offerTypeName":"","validTypeName":"当前有效","stateName":"正常","soStateName":"","expireFollowStateName":"顺延续订"}]
     */

    private String tranId;
    private String return_code;
    private String return_message;
    private List<OfferInfosBean> prodSubscriptionInfo;//用户订购
    private List<srvPkg> promSubscriptionInfo;//促销订购
    private List<srvPkg> promSubscription;//促销订购2

    public List<srvPkg> getPromSubscription() {
        return promSubscription;
    }

    public void setPromSubscription(List<srvPkg> promSubscription) {
        this.promSubscription = promSubscription;
    }

    public List<OfferInfosBean> getProdSubscriptionInfo() {
        return prodSubscriptionInfo;
    }

    public void setProdSubscriptionInfo(List<OfferInfosBean> prodSubscriptionInfo) {
        this.prodSubscriptionInfo = prodSubscriptionInfo;
    }

    public List<srvPkg> getPromSubscriptionInfo() {
        return promSubscriptionInfo;
    }

    public void setPromSubscriptionInfo(List<srvPkg> promSubscriptionInfo) {
        this.promSubscriptionInfo = promSubscriptionInfo;
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


    public String getTranId() {
        return tranId;
    }

    public String getReturn_code() {
        return return_code;
    }

    public String getReturn_message() {
        return return_message;
    }


    public static class OfferInfosBean implements Serializable{
        private String productId;
        private String productCode;
        private String prodSubscriptionId;
        private String productName;
        private String createDate;
        private String busiValidDate;
        private String busiExpriDate;
        private String stopStatus;
        private String status;
        private boolean statusforproduct = true;
        private String endDate;

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setStatusforproduct(boolean statusforproduct) {
            this.statusforproduct = statusforproduct;
        }

        public boolean getStatusForProduct(){
            return statusforproduct;
        }
        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public String getProductCode() {
            return productCode;
        }


        public String getBusiValiDate() {
            return busiValidDate;
        }

        public void setBusiValiDate(String busiValiDate) {
            this.busiValidDate = busiValiDate;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProdSubscriptionId() {
            return prodSubscriptionId;
        }

        public void setProdSubscriptionId(String prodSubscriptionId) {
            this.prodSubscriptionId = prodSubscriptionId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getBusiExpireDate() {
            return busiExpriDate;
        }

        public void setBusiExpireDate(String busiExpireDate) {
            this.busiExpriDate = busiExpireDate;
        }

        public String getStopStatus() {
            return stopStatus;
        }

        public void setStopStatus(String stopStatus) {
            this.stopStatus = stopStatus;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class srvPkg {
        private String promId;
        private String promCode;
        private String promName;
        private String promSubscriptionId;
        private String promBusiValidDate;
        private String promBusiExpirDate;
        private String promCreateDate;
        private boolean statusForProduct = true;
        public boolean getStatusForProduct(){
            return statusForProduct;
        }

        public void setStatusForProduct(boolean statusForProduct) {
            this.statusForProduct = statusForProduct;
        }

        public String getPromCode() {
            return promCode;
        }

        public String getPromName() {
            return promName;
        }

        public void setPromName(String promName) {
            this.promName = promName;
        }

        public String getPromId() {
            return promId;
        }

        public void setPromId(String promId) {
            this.promId = promId;
        }

        public String getPromSubscriptionId() {
            return promSubscriptionId;
        }

        public void setPromSubscriptionId(String promSubscriptionId) {
            this.promSubscriptionId = promSubscriptionId;
        }

        public String getPromBusiValiDate() {
            return promBusiValidDate;
        }

        public void setPromBusiValiDate(String promBusiValiDate) {
            this.promBusiValidDate = promBusiValiDate;
        }

        public String getPromBusiExpireDate() {
            return promBusiExpirDate;
        }

        public void setPromBusiExpireDate(String promBusiExpireDate) {
            this.promBusiExpirDate = promBusiExpireDate;
        }

        public String getPromCreateDate() {
            return promCreateDate;
        }

        public void setPromCreateDate(String promCreateDate) {
            this.promCreateDate = promCreateDate;
        }
    }
}
