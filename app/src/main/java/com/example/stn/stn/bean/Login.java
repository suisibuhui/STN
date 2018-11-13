package com.example.stn.stn.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Name: Login
 * Author: xulong
 * Comment: //登录信息实例
 * Date: 2016-07-15 10:46.
 */
public class Login implements Serializable {


    /**
     * tranId : 12344444
     * return_code : 0
     * return_message : 成功
     * oper : {"operid":"88270","userno":"1","username":"曹立斌","phoneno":"18751559275","orgid":null,"orgname":"信息技术部","funcauth":"","userid":null,"channelid":null}
     * token : 6a2b571727a4aca0ea6557fb33ff49fe
     * expire : 10
     * isNewPassport:1:是 0：不是
     */

    private String tranId;
    private String return_code;
    private String return_message;
    private OperBean oper;
    private List<OperBean2> orgInfo;
    private String token;
    private int expire;



    public List<OperBean2> getOrgInfo() {
        return orgInfo;
    }

    public void setOrgInfo(List<OperBean2> orgInfo) {
        this.orgInfo = orgInfo;
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

    public void setOper(OperBean oper) {
        this.oper = oper;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setExpire(int expire) {
        this.expire = expire;
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

    public OperBean getOper() {
        return oper;
    }

    public String getToken() {
        return token;
    }

    public int getExpire() {
        return expire;
    }

    public static class OperBean2 {
        private String orgId;
        private String orgName;

        public void setOrgid(String orgid) {
            this.orgId = orgid;
        }

        public void setOrgname(String orgname) {
            this.orgName = orgname;
        }

        public String getOrgid() {
            return orgId;
        }

        public String getOrgname() {
            return orgName;
        }
    }
    public static class OperBean {
        /**
         * operid : 88270
         * userno : 1
         * username : 曹立斌
         * phoneno : 18751559275
         * orgid : null
         * orgname : 信息技术部
         * funcauth :
         * userid : null
         * channelid : null
         */


        private String operid;
        private String userno;
        private String username;
        private String phoneno;
        private Object orgid;
        private String orgname;
        private String funcauth;
        private Object userid;
        private Object channelid;

        public void setOperid(String operid) {
            this.operid = operid;
        }

        public void setUserno(String userno) {
            this.userno = userno;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setPhoneno(String phoneno) {
            this.phoneno = phoneno;
        }

        public void setOrgid(Object orgid) {
            this.orgid = orgid;
        }

        public void setOrgname(String orgname) {
            this.orgname = orgname;
        }

        public void setFuncauth(String funcauth) {
            this.funcauth = funcauth;
        }

        public void setUserid(Object userid) {
            this.userid = userid;
        }

        public void setChannelid(Object channelid) {
            this.channelid = channelid;
        }

        public String getOperid() {
            return operid;
        }

        public String getUserno() {
            return userno;
        }

        public String getUsername() {
            return username;
        }

        public String getPhoneno() {
            return phoneno;
        }

        public Object getOrgid() {
            return orgid;
        }

        public String getOrgname() {
            return orgname;
        }

        public String getFuncauth() {
            return funcauth;
        }

        public Object getUserid() {
            return userid;
        }

        public Object getChannelid() {
            return channelid;
        }
    }
}
