package com.example.stn.stn;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Gravity;

import android.view.KeyEvent;
import android.view.View;
import android.view.Window;

import android.widget.Button;
import android.widget.EditText;

import android.widget.LinearLayout;


import com.example.stn.stn.bean.Login;
import com.example.stn.stn.cache.LocationCache;
import com.example.stn.stn.service.ServerApi;
import com.example.stn.stn.service.ServiceGenerator;
import com.example.stn.stn.utils.EnCode;
import com.example.stn.stn.utils.SystemUtils;
import com.litesuits.android.log.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Name: LoginActivity
 * Author: xulong
 * Comment: //登陆界面
 * Date: 2016-07-12 18:07.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{


    private LinearLayout mInputLayout;
    private EditText etNumberJob;
    private EditText etPassword;
    private ProgressDialog mProgressDialog;
    private Button btn_send;
    private Call<Login> call;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        locationCache.setUser(null);
        locationCache.setCustomer(null);
        locationCache.setOper(null);
        locationCache.setOrderList(null);
        locationCache.setCustomerInfo(null);
        locationCache.setUserInfo(null);
        application.setUUID(getUUID());     //把UUID放到全局变量里
        locationCache.setTridId(application.getNumberJob()+System.currentTimeMillis());
        initView(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
    //获取UUID
    private String getUUID() {
        final TelephonyManager telephonyManager = (TelephonyManager)
                this.getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + telephonyManager.getDeviceId();
        tmSerial = "" + telephonyManager.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString
                (getContentResolver(),android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(),
                ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        return tmDevice;
//        return "862157030091065";
    }







    protected void initView(Bundle savedInstanceState){
        mInputLayout = (LinearLayout) findViewById(R.id.ll_input);
        etNumberJob = (EditText)findViewById(R.id.et_number_job);

       // etNumberJob.setText("3130352");
        // TODO: 2016/9/19 0019

        etPassword = (EditText)findViewById(R.id.et_password);
      // etPassword.setText("111111");
        btn_send = (Button) findViewById(R.id.btn_login);
        btn_send.setOnClickListener(this);

    }



    @Override
    public void onClick(View v)  {


//        startActivity(new Intent(mContext,UserMainActivity.class));
//        finish();

        //清缓存



        String mNumberJob = etNumberJob.getText().toString();
        String mPassword = etPassword.getText().toString();
        application.setNumberJob(mNumberJob);

        if(TextUtils.isEmpty(mNumberJob) || TextUtils.isEmpty(mPassword) ){
            SystemUtils.showToast(mContext,"账号或密码不能为空");
            return;
        }
        //网络请求 登录
        if (mProgressDialog == null ) {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setMessage("登录中...");
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
            mProgressDialog.show();
        }else{
            mProgressDialog.show();
        }
        ServerApi serverApi = ServiceGenerator.createService(ServerApi.class);
        String enCodePassword = "";
        try{
            enCodePassword = EnCode.tripleDesEncryptUrl("asiainfomboss", mPassword);
        }catch(Exception t){

        }
        //enCodePassword = mPassword;

        call = serverApi.loginStaffInfo(application.getUUID(),locationCache.getTridId(),mNumberJob,enCodePassword);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                mProgressDialog.dismiss();
                Login body = response.body();

                if(body.getReturn_code().equals("0")){


                    if(body.getOrgInfo() == null ||body.getOrgInfo().size() == 0 ){
                        SystemUtils.showToast(mContext,"无可访问组织机构");
                        return;
                    }else{
                        locationCache.setOrgIngo(body.getOrgInfo());
                        locationCache.setOper(body.getOper());
                        application.setToken(body.getToken());
                        startActivity(new Intent(mContext,UserMainActivity.class));
                        finish();
                    }


                }else{
                    SystemUtils.showToast(mContext,body.getReturn_message());
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                mProgressDialog.dismiss();
                SystemUtils.showToast(mContext,"请检查网络连接。");
            }
        });

    }


    //检验工号是否为空 : true 为空，false不为空
    private boolean checkNumberJob(String numberJob) {
        if (numberJob == null || numberJob.length() == 0) {
            SystemUtils.showToast(mContext, "请输入工号",Gravity.CENTER);
            return false;
        } else {
            return true;
        }
    }

    //检验密码是否为空
    private boolean checkPassword(String password) {
        if (password == null || password.length() == 0) {
            SystemUtils.showToast(mContext, "请输入密码",Gravity.CENTER);
            return false;
        } else {
            return true;
        }
    }

    //检验验证码是否为空
    private boolean checkCheckcode(String checkcode) {
        if (checkcode == null || checkcode.length() == 0) {
            SystemUtils.showToast(mContext, "请输入验证码",Gravity.CENTER);
            return false;
        } else {
            return true;
        }
    }


    //邮编验证
    private boolean regixCostNum(String customerName) {
        //"[1-9]\\d{5}"
        boolean matches = Pattern.matches("[1-9]\\d{5}", customerName);
        return    matches;
    }



}
