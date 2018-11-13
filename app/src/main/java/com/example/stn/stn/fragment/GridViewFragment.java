package com.example.stn.stn.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.stn.stn.R;
import com.example.stn.stn.accountinfo.AccountInfActivity;
import com.example.stn.stn.adapter.GridViewMainAdapter;
import com.example.stn.stn.adapter.ViewHolder;
import com.example.stn.stn.bean.GridInfo;
import com.example.stn.stn.cache.UserInfo;
import com.example.stn.stn.cashpay.CashPayActivity;
import com.example.stn.stn.customeranduserinfo.CustomerActivity;
import com.example.stn.stn.customeranduserinfo.UserInfoActivity;
import com.example.stn.stn.gongdanquery.GondanHuilongActivity;
import com.example.stn.stn.gongdanquery.GondanHuilongForTrueActivity;
import com.example.stn.stn.gongdanquery.GongdanQueryActivity;
import com.example.stn.stn.pauserecover.RefreshAuthorizationActivity;
import com.example.stn.stn.pauserecover.ShuaxinshouquanActivity;
import com.example.stn.stn.payquery.PayQueryActivity;
import com.example.stn.stn.productorder.ProductOrderActivity;
import com.example.stn.stn.querycustomer.LocationDetailActivity;
import com.example.stn.stn.utils.CommonDialog;
import com.example.stn.stn.utils.SystemUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Name: GridViewFragment
 * Author: xulong
 * Comment: //TODO
 * Date: 2016-08-12 01:02.
 */
public abstract class GridViewFragment extends BaseFragment {
    protected View rootView;
    protected GridView gvContentQuery;
    private AlertDialog mDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main_content, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initGridView();
    }

    protected abstract ArrayList<GridInfo> getData();

    private void initGridView() {
        gvContentQuery = (GridView) rootView.findViewById(R.id.gv_content_query);
        gvContentQuery.setAdapter(new GridViewMainAdapter<GridInfo>(mContext, getData()) {
            @Override
            public void convert(ViewHolder helper, GridInfo item) {
                ImageView imageView = helper.getView(R.id.item_iv_icon);
                imageView.setImageDrawable(getResources().getDrawable(item.getIcon()));
                TextView textView = helper.getView(R.id.item_tv_icontext);
                textView.setText(item.getLabel());
            }
        });
        gvContentQuery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GridInfo info = (GridInfo) gvContentQuery.getItemAtPosition(position);
                String funcauth = locationCache.getOper().getFuncauth();
                String[] split = funcauth.split(",");
                switch (info.getAction()) {
                case "renewPackages":        //暂停恢复    需要定位到用户
                    if(Arrays.asList(split).contains("5")) {
                    setTridId();
                    if (locationCache.getUser() == null) {   //如果用户没有定位，就跳转到用户定位页面

                        Intent intent = new Intent(mContext, LocationDetailActivity.class);
                        startActivity(intent);
                        SystemUtils.showToast(mContext, "请进行用户定位！", Gravity.CENTER);

                    } else {                    //如果用户已经定位，就跳转到用户确认页面
                        UserInfo.ProdInfosBean user = locationCache.getUser();
                        if(user.getSubscriberStatus().equals("3")){
                            //是3
                            Intent intent = new Intent(mContext, RefreshAuthorizationActivity.class);
                            intent.putExtra("name", "pause");
                            startActivity(intent);
                        }else{
                            //不是3
                            SystemUtils.showToast(mContext,"用户状态非暂停状态，不能操作");
                        }

                    }
                    }else{
                        Toast.makeText(mContext,"无权限",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case "repairService":        //缴费查询    需要定位到用户
                    if(Arrays.asList(split).contains("3")) {



                        setTridId();
                       if (locationCache.getUser() == null) {   //如果用户没有定位，就跳转到用户定位页面
                           Intent intent = new Intent(mContext, LocationDetailActivity.class);
                           startActivity(intent);
                            SystemUtils.showToast(mContext, "请进行用户定位！", Gravity.CENTER);
                        } else {                    //如果用户已经定位，就跳转到用户确认页面
                            Intent intent = new Intent(mContext, PayQueryActivity.class);
                            startActivity(intent);
                        }
                    }else{
                        Toast.makeText(mContext,"无权限",Toast.LENGTH_SHORT).show();
                    }
                        break;
                case "newBusiness":        //账户充值    需要定位到用户
                    if(Arrays.asList(split).contains("7")) {


                        setTridId();
                        if (locationCache.getUser() == null) {   //如果用户没有定位，就跳转到用户定位页面
                            Intent intent = new Intent(mContext, LocationDetailActivity.class);
                            startActivity(intent);
                            SystemUtils.showToast(mContext, "请进行用户定位！", Gravity.CENTER);
                        } else {                    //如果用户已经定位，就跳转到用户确认页面
                            Intent intent = new Intent(mContext, CashPayActivity.class);
                            startActivity(intent);
                        }
                    }else{
                        Toast.makeText(mContext,"无权限",Toast.LENGTH_SHORT).show();
                    }
                        break;

                    case "billPayment":        //账户查询    需要定位到用户
                        if(Arrays.asList(split).contains("8")) {
                            setTridId();
                            if (locationCache.getUser() == null) {   //如果用户没有定位，就跳转到用户定位页面
                                Intent intent = new Intent(mContext, LocationDetailActivity.class);
                                startActivity(intent);
                                SystemUtils.showToast(mContext, "请进行用户定位！", Gravity.CENTER);
                            } else {                    //如果用户已经定位，就跳转到用户确认页面
                                Intent intent = new Intent(mContext, AccountInfActivity.class);
                                startActivity(intent);
                            }
                        }else{
                            Toast.makeText(mContext,"无权限",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "refreshAuthorization":        //工单查询    需要定位到用户
                        if(Arrays.asList(split).contains("4")) {
                            setTridId();
                            if (locationCache.getUser() == null) {   //如果用户没有定位，就跳转到用户定位页面
                                Intent intent = new Intent(mContext, LocationDetailActivity.class);
                                startActivity(intent);
                                SystemUtils.showToast(mContext, "请进行用户定位！", Gravity.CENTER);
                            } else {                    //如果用户已经定位，就跳转到用户确认页面
                                Intent intent = new Intent(mContext, GongdanQueryActivity.class);
                                startActivity(intent);
                            }
                        }else{
                            Toast.makeText(mContext,"无权限",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "orderhuilong":        //工单回笼    需要定位到用户
//                        if(Arrays.asList(split).contains("6")) {
//                            setTridId();
//                            if (locationCache.getUser() == null) {   //如果用户没有定位，就跳转到用户定位页面
//                                Intent intent = new Intent(mContext, LocationDetailActivity.class);
//                                startActivity(intent);
//                                SystemUtils.showToast(mContext, "请进行用户定位！", Gravity.CENTER);
//                            } else {                    //如果用户已经定位，就跳转到用户确认页面
//                                Intent intent = new Intent(mContext, GondanHuilongForTrueActivity.class);
//                                startActivity(intent);
//                            }
//                        }else{
//                            Toast.makeText(mContext,"无权限",Toast.LENGTH_SHORT).show();
//                        }
                        CommonDialog commonDialog = new CommonDialog(mContext,"功能尚未上线");
                        commonDialog.show();
                        break;
                    case "customerDataMaintenance":        //工单派发    需要定位到用户
//                        if(Arrays.asList(split).contains("6")) {
//                            setTridId();
//                            if (locationCache.getUser() == null) {   //如果用户没有定位，就跳转到用户定位页面
//                                Intent intent = new Intent(mContext, LocationDetailActivity.class);
//                                startActivity(intent);
//                                SystemUtils.showToast(mContext, "请进行用户定位！", Gravity.CENTER);
//                            } else {                    //如果用户已经定位，就跳转到用户确认页面
//                                Intent intent = new Intent(mContext, GondanHuilongActivity.class);
//                                startActivity(intent);
//                            }
//                        }else{
//                            Toast.makeText(mContext,"无权限",Toast.LENGTH_SHORT).show();
//                        }
                        CommonDialog commonDialog2 = new CommonDialog(mContext,"功能尚未上线");
                        commonDialog2.show();
                        break;

                    case "productOrder":        //产品订购
//                        //初始化已经选购的
//                        locationCache.setProductChoose(null);
//                        if(Arrays.asList(split).contains("6")) {
//                            setTridId();
//                            if (locationCache.getUser() == null) {   //如果用户没有定位，就跳转到用户定位页面
//                                Intent intent = new Intent(mContext, LocationDetailActivity.class);
//                                startActivity(intent);
//                                SystemUtils.showToast(mContext, "请进行用户定位！", Gravity.CENTER);
//                            } else {                    //如果用户已经定位，就跳转到用户确认页面
//                                Intent intent = new Intent(mContext, ProductOrderActivity.class);
//                                startActivity(intent);
//                            }
//                        }else{
//                            Toast.makeText(mContext,"无权限",Toast.LENGTH_SHORT).show();
//                        }
                        CommonDialog commonDialog3 = new CommonDialog(mContext,"功能尚未上线");
                        commonDialog3.show();
                        break;

                    case "customerInfo":        //客户信息    需要定位到用户
                        if(Arrays.asList(split).contains("1")) {


                            setTridId();
                            if (locationCache.getUser() == null) {   //如果用户没有定位，就跳转到用户定位页面
                                Intent intent = new Intent(mContext, LocationDetailActivity.class);
                                startActivity(intent);
                                SystemUtils.showToast(mContext, "请进行用户定位！", Gravity.CENTER);
                            } else {                    //如果用户已经定位，就跳转到用户确认页面
                                Intent intent = new Intent(mContext, CustomerActivity.class);
                                startActivity(intent);
                            }
                        }else{
                            Toast.makeText(mContext,"无权限",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "userInfo":        //用户信息    需要定位到用户
                        if(Arrays.asList(split).contains("2")) {


                            setTridId();
                            if (locationCache.getUser() == null) {   //如果用户没有定位，就跳转到用户定位页面
                                Intent intent = new Intent(mContext, LocationDetailActivity.class);
                                startActivity(intent);
                                SystemUtils.showToast(mContext, "请进行用户定位！", Gravity.CENTER);
                            } else {                    //如果用户已经定位，就跳转到用户确认页面
                                Intent intent = new Intent(mContext, UserInfoActivity.class);
                                startActivity(intent);
                            }
                        } else{
                    Toast.makeText(mContext,"无权限",Toast.LENGTH_SHORT).show();
                }
                        break;
                    case "shuaxinshouquan":        //刷新授权
                        if(Arrays.asList(split).contains("9")) {
                            setTridId();
                            if (locationCache.getUser() == null) {   //如果用户没有定位，就跳转到用户定位页面

                                Intent intent = new Intent(mContext, LocationDetailActivity.class);
                                startActivity(intent);
                                SystemUtils.showToast(mContext, "请进行用户定位！", Gravity.CENTER);

                            } else {                    //如果用户已经定位，就跳转到用户确认页面
                                UserInfo.ProdInfosBean user = locationCache.getUser();
//                                if(user.getSubscriberStatus().equals("3")){
//                                    //是3
                                    Intent intent = new Intent(mContext, ShuaxinshouquanActivity.class);
                                    intent.putExtra("name", "pause");
                                    startActivity(intent);
//                                }else{
//                                    //不是3
//                                    SystemUtils.showToast(mContext,"用户状态非暂停状态，不能操作");
//                                }

                            }
                        }else{
                            Toast.makeText(mContext,"无权限",Toast.LENGTH_SHORT).show();
                        }
                        break;

                    default:
                        CommonDialog commonDialog4 = new CommonDialog(mContext,"功能尚未开发");
                        //commonDialog4.setText("sdf");
                        commonDialog4.show();

                            break;
                }




            }
        });

    }


    /**
     * 设置业务TridId
     */
    private void setTridId() {
        locationCache.setTridId(application.getNumberJob()+System.currentTimeMillis());
    }



}
