package com.example.stn.stn.cache;



import com.example.stn.stn.bean.CustomerInfo;
import com.example.stn.stn.bean.GongdanBean;
import com.example.stn.stn.bean.Login;
import com.example.stn.stn.bean.PayBean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Name: LocationCache
 * Author: xulong
 * Comment: //缓存
 * Date: 2016-08-23 20:18.
 */
public class LocationCache implements Serializable {
    private static LocationCache instance;
    private String tridId;         //用户定位的tridID

    public String getTridId() {
        return tridId;
    }

    public void setTridId(String tridId) {
        this.tridId = tridId;
    }
    public static LocationCache getInstance() {
        if (instance == null) {
            instance = new LocationCache();
        }
        return instance;
    }

    private List<Login.OperBean2> orgIngo;

    public void setOrgIngo(List<Login.OperBean2> orgIngo) {
        this.orgIngo = orgIngo;
    }

    public List<Login.OperBean2> getOrgIngo() {
        return orgIngo;
    }

    private Login.OperBean oper;

    public Login.OperBean getOper() {
        return oper;
    }

    public void setOper(Login.OperBean oper) {
        this.oper = oper;
    }

    private CustomerInfo customerInfo;
    public void setCustomerInfo(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    private CustomerInfo.CustsEntity customer;

    public CustomerInfo.CustsEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerInfo.CustsEntity customer) {
        this.customer = customer;
    }

    private String customerName;
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    private String locationAddress;
    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    private UserInfo userInfo;
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    private int whichOneClick;
    public void setWhichOneClick(int whichOneClick) {
        this.whichOneClick = whichOneClick;
    }

    public int getWhichOneClick() {
        return whichOneClick;
    }

    private String userCode;
    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserCode() {
        return userCode;
    }

    private UserInfo.ProdInfosBean user;
    public void setUser(UserInfo.ProdInfosBean user) {
        this.user = user;
    }

    public UserInfo.ProdInfosBean getUser() {
        return user;
    }

    private OrderList orderList;
    public void setOrderList(OrderList orderList) {
        this.orderList = orderList;
    }

    public OrderList getOrderList() {
        return orderList;
    }

    private List<OrderList.srvPkg> srvPkgList;
    public void setSrvPkgList(List<OrderList.srvPkg> srvPkgList) {
        this.srvPkgList = srvPkgList;
    }

    public List<OrderList.srvPkg> getSrvPkgList() {
        return srvPkgList;
    }

    private String state;
    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    private UserInfo.ProdInfosBean userMyChoose;

    public void setUserMyChoose(UserInfo.ProdInfosBean userMyChoose) {
        this.userMyChoose = userMyChoose;
    }

    public UserInfo.ProdInfosBean getUserMyChoose() {
        return userMyChoose;
    }

    private String whereForm;

    public String getWhereForm() {
        return whereForm;
    }

    public void setWhereForm(String whereForm) {
        this.whereForm = whereForm;
    }

    private PayBean payInfos;
    public void setPayInfos(PayBean payInfos) {
        this.payInfos = payInfos;
    }

    public PayBean getPayInfos() {
        return payInfos;
    }

    private Login.OperBean2 orgInfoYourChoose;

    public Login.OperBean2 getOrgInfoYourChoose() {
        return orgInfoYourChoose;
    }

    public void setOrgInfoYourChoose(Login.OperBean2 orgInfoYourChoose) {
        this.orgInfoYourChoose = orgInfoYourChoose;
    }

    private List<OrderList.OfferInfosBean> prodSubscriptionInfo;
    public void setProdSubscriptionInfo(List<OrderList.OfferInfosBean> prodSubscriptionInfo) {
        this.prodSubscriptionInfo = prodSubscriptionInfo;
    }

    public List<OrderList.OfferInfosBean> getProdSubscriptionInfo() {
        return prodSubscriptionInfo;
    }

    private List<OrderList.srvPkg> promSubscriptionInfo;
    public void setPromSubscriptionInfo(List<OrderList.srvPkg> promSubscriptionInfo) {
        this.promSubscriptionInfo = promSubscriptionInfo;
    }

    public List<OrderList.srvPkg> getPromSubscriptionInfo() {
        return promSubscriptionInfo;
    }

    private GongdanBean gongdan;
    public void setGongdan(GongdanBean gongdan) {
        this.gongdan = gongdan;
    }

    public GongdanBean getGongdan() {
        return gongdan;
    }

    private HashMap<String,OrderListList.ProductLisBean> productChoose;//选择的套餐
    public void setProductChoose(HashMap<String,OrderListList.ProductLisBean> productChoose) {
        this.productChoose = productChoose;
    }

    public HashMap<String, OrderListList.ProductLisBean> getProductChoose() {
        if(productChoose == null){
            productChoose = new HashMap<>();
        }
        return productChoose;
    }
}
