package com.example.stn.stn.cache;

import java.io.Serializable;
import java.util.List;

/**
 * Name: OrderList
 * Author: xulong
 * Comment: //订购信息查询
 * Date: 2016-08-23 19:22.
 */
public class OrderListList implements Serializable {


    /**
     * tranId : 12344444
     * return_code : 0
     * return_message : 成功
     * offerInfos : [{"offerId":800500000377,"offerName":"◎交互应用基础包","validType":"1","validDate":"2014-08-05 12:45:08","acceptDate":"2014-08-05 12:45:08","expireDate":"2050-12-31 12:00:00","state":"1","corOrgId":11629,"corOrgName":"闸北分公司","exprieFollowState":3,"nextOfferId":800500000350,"nextOfferName":"标清点播回看-100元/年","offerType":"2","soState":"","remark":"","estTime":"2016-09-12 23:59:59","crdExpTime":"","creatDate":"2014-08-05 12:45:08","isCanUseSettle":1,"offerTypeName":"","validTypeName":"当前有效","stateName":"正常","soStateName":"","expireFollowStateName":"顺延续订"},{"offerId":800500000350,"offerName":"标清点播回看-100元/年","validType":"1","validDate":"2014-08-05 12:45:08","acceptDate":"2014-08-05 12:45:08","expireDate":"2050-12-31 12:00:00","state":"1","corOrgId":11629,"corOrgName":"闸北分公司","exprieFollowState":3,"nextOfferId":800500000350,"nextOfferName":"标清点播回看-100元/年","offerType":"2","soState":"","remark":"","estTime":"2016-09-12 23:59:59","crdExpTime":"","creatDate":"2014-08-05 12:45:08","isCanUseSettle":1,"offerTypeName":"","validTypeName":"当前有效","stateName":"正常","soStateName":"","expireFollowStateName":"顺延续订"}]
     */

    private String tranId;
    private String return_code;
    private String return_message;
    private List<ProductLisBean> productList;//用户订购

    public List<ProductLisBean> getProductLis() {
        return productList;
    }

    public void setProductLis(List<ProductLisBean> productLis) {
        productList = productLis;
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


    public static class ProductLisBean implements Serializable{
        private String productId;
        private String productCode;
        private String productName;
        private String prodNameShortSpell;
        private String activeDate;
        private String inActiveDate;

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProdNameShortSpell() {
            return prodNameShortSpell;
        }

        public void setProdNameShortSpell(String prodNameShortSpell) {
            this.prodNameShortSpell = prodNameShortSpell;
        }

        public String getActiveDate() {
            return activeDate;
        }

        public void setActiveDate(String activeDate) {
            this.activeDate = activeDate;
        }

        public String getInActiveDate() {
            return inActiveDate;
        }

        public void setInActiveDate(String inActiveDate) {
            this.inActiveDate = inActiveDate;
        }
    }


}
