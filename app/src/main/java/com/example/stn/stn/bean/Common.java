package com.example.stn.stn.bean;

import java.io.Serializable;

/**
 * 成功返回信息
 * Created by 周丽蕊 on 2016/9/18.
 */
public class Common implements Serializable {


    /**
     * tranId : r1472358184124.8
     * return_code : 0
     * return_message : 成功
     */

    private String tranId;
    private String return_code;
    private String return_message;
    private String orderNo;

    public void setOrderNo(String orderNo){
        this.orderNo = orderNo;
    }
    public String getOrderNo(){
        return this.orderNo;
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
}
