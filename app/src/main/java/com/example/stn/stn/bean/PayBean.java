package com.example.stn.stn.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 成功返回信息
 * Created by 周丽蕊 on 2016/9/18.
 */
public class PayBean implements Serializable {


    /**
     * tranId : r1472358184124.8
     * return_code : 0
     * return_message : 成功
     */

    private String tranId;
    private String return_code;

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

    public String getReturn_message() {
        return return_message;
    }

    public void setReturn_message(String return_message) {
        this.return_message = return_message;
    }

    public List<PaymentRecordListBean> getPaymentRecordList() {
        return paymentRecordList;
    }

    public void setPaymentRecordList(List<PaymentRecordListBean> paymentRecordList) {
        this.paymentRecordList = paymentRecordList;
    }

    private String return_message;
    private List<PaymentRecordListBean> paymentRecordList;

    public static class PaymentRecordListBean{
        private String paymentNo;
        private String amount;
        private String paymenDate;
        private String payType;

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getPaymenDate() {
            return paymenDate;
        }

        public void setPaymenDate(String paymenDate) {
            this.paymenDate = paymenDate;
        }

        public String getPaymentNo() {
            return paymentNo;
        }

        public void setPaymentNo(String paymentNo) {
            this.paymentNo = paymentNo;
        }

        private String status;


    }

}
