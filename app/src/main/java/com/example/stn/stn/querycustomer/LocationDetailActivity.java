package com.example.stn.stn.querycustomer;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.stn.stn.BaseActivity;
import com.example.stn.stn.R;
import com.example.stn.stn.bean.CustomerInfo;
import com.example.stn.stn.cache.UserInfo;
import com.example.stn.stn.service.ServerApi;
import com.example.stn.stn.service.ServiceGenerator;
import com.example.stn.stn.utils.SystemUtils;
import com.example.stn.stn.views.TopBarView;
import com.example.stn.stn.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Name: LocationDetailActivity
 * Author: xulong
 * Comment: //定位详情页
 * Date: 2016-08-12 03:49.
 */
public class LocationDetailActivity extends BaseActivity implements View.OnClickListener,
        View.OnFocusChangeListener {

    protected EditText etUserMobile;
    protected EditText etJidingheNum;
    protected EditText etZhinengkaNum;
    protected EditText etKuandaiNum;
    protected EditText etIdcarNum;
    protected Spinner spIdType;
    protected EditText etRegularNum;
    protected Button btnCustomerQuery;
    protected Button btnUserQuery;
    protected TopBarView topbar;
    //设置一个变量。用来判断焦点的位置
    private int whichOneOnFocus;//为1 为2 为3




    private HashMap<String, String> fieldMap;  //存放body数据
    private ArrayMap<String, String> spinnerMap;  //存放枚举值
    private ArrayList<String> spinnerList;       //下拉框数据源
    private String toastString;                        //网络返回信息
    private ProgressDialog mProgressDialog;
    private Call call;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_location_detail);
        //创建一个进度条
        //网络请求 登录
        if (mProgressDialog == null ) {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setMessage("网络加载中...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                            if(mProgressDialog != null){
                                mProgressDialog.dismiss();
                            }
                            if(call != null){
                                call.cancel();
                                call = null;
                            }
                        }

                    }
                    return false;
                }
            });
            //mProgressDialog.show();
        }else{
            //mProgressDialog.show();
        }


        initData();
        initView();


    }


    private void initData() {

        spinnerMap = new ArrayMap<>();
    }

    /**
     * 加个判断防止多次点击bug
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        if (SystemUtils.isFastClick()) {
            return;
        } else {
            switch (view.getId()) {
                case R.id.btn_customer_query:

                        checkEdit();


                    break;
                case R.id.btn_user_query:

                        checkEdit2();


                    break;
                default:
                    break;
            }
        }
    }



    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.et_user_mobile:
                if (hasFocus) {
                    //修改状态常量
                    whichOneOnFocus = 1;
                    topbar.setRightVisibility(View.VISIBLE);
                } else {
                    topbar.setRightVisibility(View.GONE);
                }
                break;
            case R.id.et_jidinghe_num:
                if (hasFocus) {

                    //修改状态常量
                    whichOneOnFocus = 2;
                    topbar.setRightVisibility(View.VISIBLE);
                } else {

                    topbar.setRightVisibility(View.GONE);
                }
                break;
            case R.id.et_zhinengka_num:
                if (hasFocus) {
                    whichOneOnFocus = 3;
                    topbar.setRightVisibility(View.VISIBLE);
                } else {
                    topbar.setRightVisibility(View.GONE);
                }
                break;
            case R.id.et_kuandai_num:
                if (hasFocus) {
                    whichOneOnFocus = 4;
                    topbar.setRightVisibility(View.VISIBLE);
                } else {
                    topbar.setRightVisibility(View.GONE);
                }
                break;
            case R.id.et_idcard_num:
                if (hasFocus) {
                    whichOneOnFocus = 5;
                    topbar.setRightVisibility(View.VISIBLE);
                } else {
                    topbar.setRightVisibility(View.GONE);
                }
                break;
            default:
                topbar.setRightVisibility(View.GONE);
                break;
        }
    }

    private void initView() {
        //移动电话
        etUserMobile = (EditText) findViewById(R.id.et_user_mobile);
        //机顶盒号
        etJidingheNum = (EditText) findViewById(R.id.et_jidinghe_num);
        //智能卡号
        etZhinengkaNum = (EditText) findViewById(R.id.et_zhinengka_num);
        //宽带登录名
        etKuandaiNum = (EditText) findViewById(R.id.et_kuandai_num);
        //身份证件号码
        etIdcarNum = (EditText) findViewById(R.id.et_idcard_num);
        //智能卡
        //住宅地址名称
        //证件号码
        //证件类型

        //固定号码

        //客户查询
        btnCustomerQuery = (Button) findViewById(R.id.btn_customer_query);
        btnCustomerQuery.setOnClickListener(LocationDetailActivity.this);
        //用户查询
        btnUserQuery = (Button) findViewById(R.id.btn_user_query);
        btnUserQuery.setOnClickListener(LocationDetailActivity.this);
        //顶部栏
        topbar = (TopBarView) findViewById(R.id.topbar);
        topbar.setOnRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //***********************************开启Activity**********************//
               Intent openCameraIntent = new Intent(LocationDetailActivity.this,CaptureActivity.class);
               startActivityForResult(openCameraIntent, 0);
            }
        });
        topbar.setRightVisibility(View.GONE);
        //设置扫码控件的焦点改变事件
        etIdcarNum.setOnFocusChangeListener(this);
        etKuandaiNum.setOnFocusChangeListener(this);
        etZhinengkaNum.setOnFocusChangeListener(this);
        etJidingheNum.setOnFocusChangeListener(this);
        etUserMobile.setOnFocusChangeListener(this);

        //证件号码
        //证件类型
        //证件类型下面的分割线



    }

    //返回扫码结果。
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            //获取到Result 根据不同的位置。填入不同的位置
            if(whichOneOnFocus == 1){
                //点击的是第一个 et_box_top
                etUserMobile.setText(scanResult);
                return;
            }
            if(whichOneOnFocus == 2){
                //点击的是第二个 et_eoc
                etJidingheNum.setText(scanResult);
                return;
            }
            if(whichOneOnFocus == 3){
                //点击的是第三个 etCmmac
                etZhinengkaNum.setText(scanResult);
                return;
            } if(whichOneOnFocus == 4){
                //点击的是第三个 etCmmac
                etKuandaiNum.setText(scanResult);
                return;
            }
            if(whichOneOnFocus == 5){
                //点击的是第三个 etCmmac
                etIdcarNum.setText(scanResult);
                return;
            }
        }
    }

    /**
     * 客户查询点击事件
     */
    private void checkEdit() {
        if (
                SystemUtils.checkNull(etUserMobile.getText().toString()) |
                        SystemUtils.checkNull(etJidingheNum.getText().toString()) |
                        SystemUtils.checkNull(etZhinengkaNum.getText().toString()) |
                        SystemUtils.checkNull(etKuandaiNum.getText().toString()) |
                        SystemUtils.checkNull(etIdcarNum.getText().toString())) {


            if (SystemUtils.checkNull(etUserMobile.getText().toString())) {
                fieldMap = new HashMap<>();
                fieldMap.put("queryType", "1");
                fieldMap.put("queryValue", etUserMobile.getText().toString());
            } else if (SystemUtils.checkNull(etJidingheNum.getText().toString())) {
                fieldMap = new HashMap<>();
                fieldMap.put("queryType", "5");
                fieldMap.put("queryValue", etJidingheNum.getText().toString());
            } else if (SystemUtils.checkNull(etZhinengkaNum.getText().toString())) {
                fieldMap = new HashMap<>();
                fieldMap.put("queryType", "7");
                fieldMap.put("queryValue", etZhinengkaNum.getText().toString());
            } else if (SystemUtils.checkNull(etKuandaiNum.getText().toString())) {
                fieldMap = new HashMap<>();
                fieldMap.put("queryType", "8");
                fieldMap.put("queryValue", etKuandaiNum.getText().toString());
            } else if (SystemUtils.checkNull(etIdcarNum.getText().toString())) {
                fieldMap = new HashMap<>();
                fieldMap.put("queryType", "9");
                fieldMap.put("queryValue", etIdcarNum.getText().toString());
            }
            //判断一下 是否有权限
            fieldMap.put("certType","11");//证件类型
            fieldMap.put("officeId",String.valueOf(locationCache.getOrgInfoYourChoose().getOrgid()));//受理营业厅

                    mProgressDialog.show();

                    customerQuery(application.getUUID(), locationCache.getTridId(),
                            application.getToken(), fieldMap);





        } else {
            SweetAlertDialog dialog = new SweetAlertDialog(this);
            dialog.setTitleText("提示");
            dialog.setContentText("至少填写一项");
            dialog.setConfirmText("确认");
            dialog.show();
        }
    }
    /**
     * 用户查询点击事件
     */
    private void checkEdit2() {
        if (
                SystemUtils.checkNull(etUserMobile.getText().toString()) |
                        SystemUtils.checkNull(etJidingheNum.getText().toString()) |
                        SystemUtils.checkNull(etZhinengkaNum.getText().toString()) |
                        SystemUtils.checkNull(etKuandaiNum.getText().toString()) |
                        SystemUtils.checkNull(etIdcarNum.getText().toString())) {


            if (SystemUtils.checkNull(etUserMobile.getText().toString())) {
                fieldMap = new HashMap<>();
                fieldMap.put("searchType", "1");
                fieldMap.put("objectId", etUserMobile.getText().toString());
            } else if (SystemUtils.checkNull(etJidingheNum.getText().toString())) {
                fieldMap = new HashMap<>();
                fieldMap.put("searchType", "5");
                fieldMap.put("objectId", etJidingheNum.getText().toString());
            } else if (SystemUtils.checkNull(etZhinengkaNum.getText().toString())) {
                fieldMap = new HashMap<>();
                fieldMap.put("searchType", "7");
                fieldMap.put("objectId", etZhinengkaNum.getText().toString());
            } else if (SystemUtils.checkNull(etKuandaiNum.getText().toString())) {
                fieldMap = new HashMap<>();
                fieldMap.put("searchType", "8");
                fieldMap.put("objectId", etKuandaiNum.getText().toString());
            } else if (SystemUtils.checkNull(etIdcarNum.getText().toString())) {
                fieldMap = new HashMap<>();
                fieldMap.put("searchType", "9");
                fieldMap.put("objectId", etIdcarNum.getText().toString());
            }
            //判断一下 是否有权限
            fieldMap.put("identityType","11");//证件类型
            fieldMap.put("officeId",String.valueOf(locationCache.getOrgInfoYourChoose().getOrgid()));//受理营业厅

            mProgressDialog.show();

            userQuery(application.getUUID(), locationCache.getTridId(),
                    application.getToken(), fieldMap);





        } else {
            SweetAlertDialog dialog = new SweetAlertDialog(this);
            dialog.setTitleText("提示");
            dialog.setContentText("至少填写一项");
            dialog.setConfirmText("确认");
            dialog.show();
        }
    }




    /**
     * 客户查询
     */
    private void customerQuery(String uuid, String tridId, String token, Map fieldMap) {
        ServerApi serverApi = ServiceGenerator.createService(ServerApi.class);


         call = serverApi.customerQuery(uuid, tridId, token, fieldMap);

        call.enqueue(new Callback<CustomerInfo>() {
            @Override
            public void onResponse(Call<CustomerInfo> call, Response<CustomerInfo> response) {
                CustomerInfo customerInfo = response.body();
                if (customerInfo.getReturn_code().equals("0")) {

                    //请求成功后，将请求到的客户列表对象：1)缓存起来
                    // 2)跳转并传值下一LocationMainActivity页面
                    locationCache.setCustomerInfo(customerInfo);
                    locationCache.setWhereForm("0");//1用户 0 客户
                    Intent intent = new Intent(LocationDetailActivity.this, LocationMainActivity.class);
                    intent.putExtra("customerInfo", customerInfo);
                    startActivity(intent);
                   mProgressDialog.dismiss();

                }else if(customerInfo.getReturn_code().equals("6")||customerInfo.getReturn_code().equals("7")){

                }  else {
                    mProgressDialog.dismiss();
                    SystemUtils.showToast(getApplicationContext(), customerInfo.getReturn_message());

                }
            }

            @Override
            public void onFailure(Call<CustomerInfo> call, Throwable t) {
               mProgressDialog.dismiss();
                SystemUtils.showToast(getApplicationContext(), t.getMessage());
            }
        });
    }

    /**
     * 用户查询
     */
    private void userQuery(String uuid, String tridId, String token, Map fieldMap) {
        ServerApi serverApi = ServiceGenerator.createService(ServerApi.class);


        call = serverApi.userQuery(uuid, tridId, token, fieldMap);

        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                UserInfo userInfo = response.body();
                if (userInfo.getReturn_code().equals("0")) {

                    //请求成功后，将请求到的客户列表对象：1)缓存起来
                    // 2)跳转并传值下一LocationMainActivity页面
                    locationCache.setUserInfo(userInfo);
                    CustomerInfo.CustsEntity custsEntity1 = userInfo.getCustomer().get(0);
                    locationCache.setWhereForm("1");//1用户 0 客户
                    locationCache.setCustomer(custsEntity1);
                    Intent intent = new Intent(LocationDetailActivity.this, LocationMainActivity.class);
                    startActivity(intent);
                    mProgressDialog.dismiss();

                }else if(userInfo.getReturn_code().equals("6")||userInfo.getReturn_code().equals("7")){

                }  else {
                    mProgressDialog.dismiss();
                    SystemUtils.showToast(getApplicationContext(), userInfo.getReturn_message());

                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                mProgressDialog.dismiss();
                SystemUtils.showToast(getApplicationContext(), t.getMessage());
            }
        });
    }



}
