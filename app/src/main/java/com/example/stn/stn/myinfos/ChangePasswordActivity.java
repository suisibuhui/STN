package com.example.stn.stn.myinfos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.stn.stn.BaseActivity;
import com.example.stn.stn.LoginActivity;
import com.example.stn.stn.MainActivity;
import com.example.stn.stn.R;
import com.example.stn.stn.UserMainActivity;
import com.example.stn.stn.bean.Common;
import com.example.stn.stn.service.ServerApi;
import com.example.stn.stn.service.ServiceGenerator;
import com.example.stn.stn.utils.EnCode;
import com.example.stn.stn.utils.SystemUtils;

import java.net.URLEncoder;
import java.util.Map;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 修改密码页面
 * Created by 周丽蕊 on 2016/9/21.
 */
public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener {

    private String jobNum;
    private TextView tv_job_num;   //登录工号
    private EditText et_password;   // 原密码
    private EditText et_new_password1;//新密码
    private EditText et_new_password2;// 新密码重输
    private Button btn_password_change;//修改密码按钮

    private Map<String, String> fieldMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_password_change);

        initData();
        initView();
    }

    private void initData() {

        jobNum = locationCache.getOper().getUserno();  //获取登陆工号


    }

    private void initView() {

        tv_job_num = (TextView) findViewById(R.id.tv_job_num);
        tv_job_num.setText(jobNum); //自动填充登陆工号

        et_password = (EditText) findViewById(R.id.et_password);  // 原密码
        et_new_password1 = (EditText) findViewById(R.id.et_new_password1);  //新密码
        et_new_password2 = (EditText) findViewById(R.id.et_new_password2);  // 新密码重输

        btn_password_change = (Button) findViewById(R.id.btn_password_change);//修改密码的确定按钮
        btn_password_change.setOnClickListener(this);
    }


    /**
     * 确定按钮点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_password_change:
                submit();
                break;
        }
    }

    /**
     * 表单提交
     */
    private void submit() {
        // validate
        String password = et_password.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            //如果原密码为空，提示信息
            SystemUtils.showToast(mContext,  "请输入原密码", Gravity.CENTER);
            return;
        }

        String password1 = et_new_password1.getText().toString().trim();
        if (TextUtils.isEmpty(password1)) {
            //如果新密码为空，提示信息
            SystemUtils.showToast(mContext,  "请输入新密码", Gravity.CENTER);
            return;
        }

//        //先判断是否为空
//        if(!TextUtils.isEmpty(password1)){
//            if(!regixPassword(password1)){
//                //新密码输入错误
//                SystemUtils.showToast(mContext,  "新密码要求：6 - 12位字符、需要包含大写字母、小写字母、数字", Gravity.CENTER);
//                return;
//
//            }
//        }

        String password2 = et_new_password2.getText().toString().trim();
        if (TextUtils.isEmpty(password2)) {
            //如果新密码重输为空，提示信息
            SystemUtils.showToast(mContext, "请再次输入新密码", Gravity.CENTER);
            return;
        }

        //先判断是否为空
      //  if(!TextUtils.isEmpty(password2)){
//            if(!regixPassword(password2)){
//                //新密码重输输入错误
//                SystemUtils.showToast(mContext,  "重输新密码要求：6 - 12位字符、需要包含大写字母、小写字母、数字", Gravity.CENTER);
//                return;
//            }
      //  }

        if (! password1.equals(password2)){
            //如果两次输入的密码不一致，提示信息
            SystemUtils.showToast(mContext, "两次输入的密码不一致", Gravity.CENTER);
            return;
        }

        // TODO validate success, do something

        String etPassword = null;
        String etPassword1 = null;
        //密码加密
        try {
            etPassword  = EnCode.tripleDesEncryptUrl2("asiainfomboss", et_password.getText().toString());
            etPassword1 = EnCode.tripleDesEncryptUrl2("asiainfomboss",et_new_password1.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        fieldMap = new ArrayMap<>();
        try{
            fieldMap.put("oldPassword", etPassword);
            fieldMap.put("newPassword",etPassword1);
            fieldMap.put("encodeType","0");
        }catch (Exception t){

        }


        Log.i("msg","原密码：" + etPassword);
        Log.i("msg","新密码：" + etPassword1);

        if (SystemUtils.checkNull(password) && SystemUtils.checkNull(password1) && SystemUtils.checkNull(password2)){
            //如果输入框都不为空，进行修改密码请求
            changePassword(application.getUUID(),application.getNumberJob()+System.currentTimeMillis(),application.getToken(),fieldMap);
        }
    }

    /**
     * 修改密码请求
     * @param uuid
     * @param tridId
     * @param token
     * @param fieldMap
     */
    public void changePassword(String uuid, String tridId, String token, Map fieldMap){

        ServerApi serverApi = ServiceGenerator.createService(ServerApi.class);
        Call<Common> call = serverApi.password(uuid , tridId , token , fieldMap);
        call.enqueue(new Callback<Common>() {
            @Override
            public void onResponse(Call<Common> call, Response<Common> response) {
                Common changeInfo = response.body();
                if (changeInfo.getReturn_code().equals("0")){
                    //请求成功,跳转到登陆页面
                    SystemUtils.showToast(mContext,"密码修改成功");
                    Intent intent = new Intent(mContext , LoginActivity.class);
                    startActivity(intent);
                    finish();
                    try{
                        UserMainActivity.instance.finish();
                        locationCache = null;
                    }catch(Exception t){

                    }

                }else {
                    //请求失败，提示失败信息
                    SystemUtils.showToast(mContext, changeInfo.getReturn_message(), Gravity.CENTER );
                }
            }

            @Override
            public void onFailure(Call<Common> call, Throwable t) {
                SystemUtils.showToast(mContext , t.getMessage(), Gravity.CENTER );
            }
        });
    }

    /**
     * 正则表达式，判断密码 :6 - 12位字符、需要包含大写字母、小写字母、数字
     */
    //
    private boolean regixPassword(String str){
        boolean matches = Pattern.matches("^(?!^\\\\d+$)(?!^[a-zA-Z]+$)(?!^[a-z0-9]+$)(?!^[A-Z0-9]+$)[a-zA-Z0-9]{6,12}$", str);
        return    matches;
    }

}
