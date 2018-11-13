package com.example.stn.stn.service;


import com.example.stn.stn.bean.AccountUeryBean;
import com.example.stn.stn.bean.Common;
import com.example.stn.stn.bean.CustomerInfo;
import com.example.stn.stn.bean.GongdanBean;
import com.example.stn.stn.bean.GridMemberBean;
import com.example.stn.stn.bean.Login;
import com.example.stn.stn.bean.PayBean;
import com.example.stn.stn.cache.OrderList;
import com.example.stn.stn.cache.OrderListList;
import com.example.stn.stn.cache.UserInfo;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Name: ServiceApi
 * Author: xulong
 * Comment: 定义retrofit请求接口，即程序中都需要什么请求操作
 * Date: 2016-07-12 15:25.
 */
public interface ServerApi {
    /**
     * 业务员登录
     *
     * @param
     */
    @GET("{uuid}/{tranId}/m002/{jobnum}/{password}/0")
    Call<Login> loginStaffInfo(@Path("uuid") String uuid,
                               @Path("tranId") String tranId,
                               @Path("jobnum") String jobnum,
                               @Path("password") String password);
    /**
     * 客户查询
     */
    @FormUrlEncoded
    @POST("{uuid}/{tranId}/m012/{token}")
    Call<CustomerInfo> customerQuery (@Path("uuid") String uuid,
                                      @Path("tranId") String tranId,
                                      @Path("token") String token,
                                      @FieldMap Map<String, String> fieldMap);

    /**
     * 客户查询
     */
    @FormUrlEncoded
    @POST("{uuid}/{tranId}/m014/{token}")
    Call<UserInfo> userDQuery (@Path("uuid") String uuid,
                                      @Path("tranId") String tranId,
                                      @Path("token") String token,
                                      @FieldMap Map<String, String> fieldMap);

    /**
     * 客户定位
     */
    @FormUrlEncoded
    @POST("{uuid}/{tranId}/m013/{token}")
    Call<UserInfo> userQuery (@Path("uuid") String uuid,
                              @Path("tranId") String tranId,
                              @Path("token") String token,
                              @FieldMap Map<String, String> fieldMap);
    /**
     * 订购查询
     */
    @FormUrlEncoded
    @POST("{uuid}/{tranId}/m099/{token}")
    Call<OrderList> orderQuery (@Path("uuid") String uuid,
                                @Path("tranId") String tranId,
                                @Path("token") String token,
                                @FieldMap Map<String, String> fieldMap);

    /**
     * 产品变更
     */
    @FormUrlEncoded
    @POST("{uuid}/{tranId}/m101/{token}")
    Call<Common> productChange (@Path("uuid") String uuid,
                                @Path("tranId") String tranId,
                                @Path("token") String token,
                                @FieldMap Map<String, String> fieldMap);

    /**
     * 订购查询
     */
    @FormUrlEncoded
    @POST("{uuid}/{tranId}/m100/{token}")
    Call<OrderListList> orderQueryList (@Path("uuid") String uuid,
                                        @Path("tranId") String tranId,
                                        @Path("token") String token,
                                        @FieldMap Map<String, String> fieldMap);
    /**
     * 刷新授权
     *
     * @param
     */
    @FormUrlEncoded
    @POST("{uuid}/{tranId}/m049/{token}")
    Call<Common> refresh (@Path("uuid") String uuid,
                          @Path("tranId") String tranId,
                          @Path("token") String token,
                          @FieldMap Map<String, String> fieldMap);

    /**
     * 缴费查询
     *
     * @param
     */
    @FormUrlEncoded
    @POST("{uuid}/{tranId}/m086/{token}")
    Call<PayBean> payQuery (@Path("uuid") String uuid,
                            @Path("tranId") String tranId,
                            @Path("token") String token,
                            @FieldMap Map<String, String> fieldMap);

    /**
     * 现金缴费
     *
     * @param
     */
    @FormUrlEncoded
    @POST("{uuid}/{tranId}/m055/{token}")
    Call<Common> cashPay (@Path("uuid") String uuid,
                            @Path("tranId") String tranId,
                            @Path("token") String token,
                            @FieldMap Map<String, String> fieldMap);

    /**
     * 账户查询
     *
     * @param
     */
    @FormUrlEncoded
    @POST("{uuid}/{tranId}/m095/{token}")
    Call<AccountUeryBean> accountQuery (@Path("uuid") String uuid,
                                        @Path("tranId") String tranId,
                                        @Path("token") String token,
                                        @FieldMap Map<String, String> fieldMap);
    /**
     * 暂停恢复
     *
     * @param
     */
    @GET("{uuid}/{tranId}/m048/{token}/{subscriberCode}")
    Call<Common> shuaxinshouquan(@Path("uuid") String uuid,
                               @Path("tranId") String tranId,
                                 @Path("token") String token,
                               @Path("subscriberCode") String subscriberCode);

    /**
     * 账户查询
     *
     * @param
     */
    @FormUrlEncoded
    @POST("{uuid}/{tranId}/m019/{token}")
    Call<GongdanBean> gongdanQuery (@Path("uuid") String uuid,
                                    @Path("tranId") String tranId,
                                    @Path("token") String token,
                                    @FieldMap Map<String, String> fieldMap);

    /**
     * 1.1.11网格成员查询
     */
    @FormUrlEncoded
    @POST("{uuid}/{tranId}/m087/{token}")
    Call<GridMemberBean> gridMemberQuery (@Path("uuid") String uuid,
                                          @Path("tranId") String tranId,
                                          @Path("token") String token,
                                          @FieldMap Map<String, String> fieldMap);

    /**
     * 1⼯单派发接⼝
     */
    @FormUrlEncoded
    @POST("{uuid}/{tranId}/m070/{token}/1")
    Call<Common> gongdanPaifa (@Path("uuid") String uuid,
                                          @Path("tranId") String tranId,
                                          @Path("token") String token,
                                          @FieldMap Map<String, String> fieldMap);
    /**
     * 1⼯单回退接⼝
     */
    @FormUrlEncoded
    @POST("{uuid}/{tranId}/m070/{token}/2")
    Call<Common> gongdanHuitui (@Path("uuid") String uuid,
                               @Path("tranId") String tranId,
                               @Path("token") String token,
                               @FieldMap Map<String, String> fieldMap);

    /**
     * 1⼯单回笼接⼝
     */
    @FormUrlEncoded
    @POST("{uuid}/{tranId}/m070/{token}/3")
    Call<Common> gongdanHuilong (@Path("uuid") String uuid,
                                @Path("tranId") String tranId,
                                @Path("token") String token,
                                @FieldMap Map<String, String> fieldMap);

    /**
     * 1⼯单回笼接⼝
     */
    @FormUrlEncoded
    @POST("{uuid}/{tranId}/m051/{token}")
    Call<Common> password(@Path("uuid") String uuid,
                          @Path("tranId") String tranId,
                          @Path("token") String token,
                          @FieldMap Map<String, String> fieldMap);
}
