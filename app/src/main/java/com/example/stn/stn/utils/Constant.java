package com.example.stn.stn.utils;

/**
 * Name: Constant
 * Author: xulong
 * Comment: //TODO
 * Date: 2016-07-18 00:45.
 */
public class Constant {

    public static final String APP_NAME = "mboss";
    public final int LAYOUT_INDEX = 0;
    public final int CHECKBOX_INDEX = 100;
    /**
     * 登录信息
     */
    public static class SharedPrefOperInfo {
        public static final String SHARED_NAME = "sharedoperinfo";
        //登录令牌
        public static final String TOKEN = "token";
        //登录超时时间
        public static final String EXPIRE = "expire";
        //业务员标识
        public static final String OPERID = "operid";
        //员工号
        public static final String USERNO = "userno";
        //业务员名称
        public static final String USERNAME = "username";
        //手机号
        public static final String PHONENO = "phoneno";
        //所属组织
        public static final String BELONGORG = "belongorg";
        //权限列表，逗号分割,如：1,2,3,6,13
        public static final String FUNCAUTH = "funcauth";
        //设备推送标识（暂不用）
        public static final String USERID = "userid";
        //设备推送标识（暂不用）
        public static final String CHEANNELID = "channelid";

        //业务员信息。
        public static final String OPER = "oper";

    }

    public static class Time {
        public static final long SECOND = 1000;
        public static final long MINUTE = 60 * SECOND;
        public static final long HOUR = 60 * MINUTE;
        public static final long DAY = 24 * HOUR;
    }

    /**
     * 公告信息
     */
    public static class Affiches {
        // 公告标识
        public static final String AFFID = "affid";
        // 公告主题
        public static final String ADDTHEME = "afftheme";
        // 公告内容
        public static final String CONTENT = "content";
        // 发布时间
        public static final String CREATETIME = "createtime";
        // 阅读状态， 1-未读， 2-已读
        public static final String STATE = "state";

        public static final String AFFICHES = "affiches";

    }

    /**
     * 备忘信息
     */
    public static class Memos {
        // 客户标识
        public static final String CUSTID = "custid";
        // 客户姓名
        public static final String CUSTNAME = "custname";
        // 联系电话
        public static final String PHONENO = "phoneno";
        // 申请地址
        public static final String ADDRESS = "address";
        // 意向产品
        public static final String OFFER = "offer";
        // 提醒时间,格式： yyyy/MM/dd HH:mm
        public static final String REMINDTIME = "remindtime";

        public static final String MEMOS = "memos";

    }

    public static class SharedPrefrence {
        public static final String SHARED_NAME = "Mboss";
        public static final String LOGGED = "logged";
        public static final String TOKEN = "token";
        public static final String TEL = "tel";
    }

    /**
     * startActivity的结果码
     */
    public static class ResultCode {
        public static final int ADD_CUSTOMER_ADDRESS = 5100;
    }


}
