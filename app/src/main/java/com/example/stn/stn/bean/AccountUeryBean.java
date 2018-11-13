package com.example.stn.stn.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 成功返回信息
 * Created by 周丽蕊 on 2016/9/18.
 */
public class AccountUeryBean implements Serializable {


    /**
     * tranId : r1472358184124.8
     * return_code : 0
     * return_message : 成功
     */

    private String tranId;
    private String return_code;
    private List<AcctInfoBean> acctInfo;
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

    public List<AcctInfoBean> getAcctInfo() {
        return acctInfo;
    }

    public void setAcctInfo(List<AcctInfoBean> acctInfo) {
        this.acctInfo = acctInfo;
    }

    public static class AcctInfoBean{
        private String accountId;
        private String accountCode;
        private String accountName;
        private String pointBalace;
        private String accountStatus;
        private String paymentMethod;
        private String minNeedAmount;
        private String creditId;
        private String accountCredit;
        private String creditCanSet;
        private List<BookBean> books;

        public String getAccountId() {
            return accountId;
        }

        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }

        public String getAccountCode() {
            return accountCode;
        }

        public void setAccountCode(String accountCode) {
            this.accountCode = accountCode;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public String getPointBalace() {
            return pointBalace;
        }

        public void setPointBalace(String pointBalace) {
            this.pointBalace = pointBalace;
        }

        public String getAccountStatus() {
            return accountStatus;
        }

        public void setAccountStatus(String accountStatus) {
            this.accountStatus = accountStatus;
        }

        public String getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public String getMinNeedAmount() {
            return minNeedAmount;
        }

        public void setMinNeedAmount(String minNeedAmount) {
            this.minNeedAmount = minNeedAmount;
        }

        public String getCreditId() {
            return creditId;
        }

        public void setCreditId(String creditId) {
            this.creditId = creditId;
        }

        public String getAccountCredit() {
            return accountCredit;
        }

        public void setAccountCredit(String accountCredit) {
            this.accountCredit = accountCredit;
        }

        public String getCreditCanSet() {
            return creditCanSet;
        }

        public void setCreditCanSet(String creditCanSet) {
            this.creditCanSet = creditCanSet;
        }

        public List<BookBean> getBooks() {
            return books;
        }

        public void setBooks(List<BookBean> books) {
            this.books = books;
        }

        public static class BookBean{
            private String itemName;
            private String amount;
            private String payAmount;
            private String feeCycleTotal;
            private String feeDetailTotal;
            private String feeTotal;

            public String getItemName() {
                return itemName;
            }

            public void setItemName(String itemName) {
                this.itemName = itemName;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getPayAmount() {
                return payAmount;
            }

            public void setPayAmount(String payAmount) {
                this.payAmount = payAmount;
            }

            public String getFeeCycleTotal() {
                return feeCycleTotal;
            }

            public void setFeeCycleTotal(String feeCycleTotal) {
                this.feeCycleTotal = feeCycleTotal;
            }

            public String getFeeDetailTotal() {
                return feeDetailTotal;
            }

            public void setFeeDetailTotal(String feeDetailTotal) {
                this.feeDetailTotal = feeDetailTotal;
            }

            public String getFeeTotal() {
                return feeTotal;
            }

            public void setFeeTotal(String feeTotal) {
                this.feeTotal = feeTotal;
            }
        }

    }

}
