package com.example.stn.stn.myinfos;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.stn.stn.BaseActivity;
import com.example.stn.stn.R;
import com.example.stn.stn.UserMainActivity;
import com.example.stn.stn.bean.Login;

import java.util.ArrayList;
import java.util.List;


/**
 * Name: MimeDetailActivity
 * Author: xulong
 * Comment: //TODO
 * Date: 2016-08-12 03:45.
 */
public class MimeDetailActivity extends BaseActivity implements View.OnClickListener {


    protected TextView tvMimeName;
    protected TextView tvLoginJobnum;
    protected TextView tvOrganizationname;
    protected TextView tvFingureNum;

    protected RelativeLayout rlCustomerMemo;
    protected RelativeLayout rlHistoryNotice;
    protected TextView passwordLogin;

    private String loginName;//登录名称
    private String loginNum; //登录工号
    private String orgname; //所述组织

    private TextView remember_memo;  //客户备忘录的小红圈
    private TextView remember_notice; //历史公告的小红圈


    private String number; //提醒数量
    private FrameLayout fl_progressconstruction;
    private TextView xiaxian;
    private TextView tvOrganizationname2;
    private int yourChoice;
    private RelativeLayout passwordChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_mime_detail);



    }


    private void initData() {

        Login.OperBean  oper = locationCache.getOper();
        ArrayList<Login.OperBean2> orgIngo = (ArrayList<Login.OperBean2>) locationCache.getOrgIngo();

        Login.OperBean2 orgInfoYourChoose = locationCache.getOrgInfoYourChoose();

        if(oper == null){
            tvMimeName.setText("");
            tvLoginJobnum.setText("");
            tvOrganizationname.setText("");
        }else{
            tvMimeName.setText(oper.getUsername());
            tvLoginJobnum.setText("    "+oper.getUserno());
            tvOrganizationname.setText("    "+oper.getOrgname());
        }
       if(orgInfoYourChoose == null){
           tvOrganizationname2.setText("");
       }else{
           tvOrganizationname2.setText(orgInfoYourChoose.getOrgname());
       }



        tvOrganizationname2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void showDialog(){
        final List<Login.OperBean2> orgIngo = locationCache.getOrgIngo();
        String[] items = new String[orgIngo.size()];
        Login.OperBean2 orgInfoYourChoose = locationCache.getOrgInfoYourChoose();
        if(orgInfoYourChoose == null){
            orgInfoYourChoose = new Login.OperBean2();
        }
        for (int i = 0;i<orgIngo.size();i++){
            items[i] = orgIngo.get(i).getOrgname();
            if(orgIngo.get(i).getOrgname().equals(orgInfoYourChoose.getOrgname())){
                yourChoice = i;
            }
        }


        //判断默认选项


        final AlertDialog.Builder singleChoiceDialog =
                new AlertDialog.Builder(MimeDetailActivity.this);
        singleChoiceDialog.setTitle("请选择受理组织");
        //singleChoiceDialog.setCancelable(false);
        // 第二个参数是默认选项，此处设置为0
        singleChoiceDialog.setSingleChoiceItems(items, yourChoice,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        yourChoice = which;
                    }
                });
        singleChoiceDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (yourChoice != -1) {
                            //设置一下
                            Login.OperBean2 operBean2 = orgIngo.get(yourChoice);
                            locationCache.setOrgInfoYourChoose(operBean2);
                            tvOrganizationname2.setText(operBean2.getOrgname());
                        }
                    }
                });
        singleChoiceDialog.show();
    }

    private void initView() {



        /**
         * 登录人员部分
         */

        tvMimeName = (TextView) findViewById(R.id.tv_mime_name);
        tvLoginJobnum = (TextView) findViewById(R.id.tv_login_jobnum);//工号
        tvOrganizationname = (TextView) findViewById(R.id.tv_organizationname);
        tvOrganizationname2 = (TextView) findViewById(R.id.tv_organizationname2);

        //客户备忘录的小红圈
        remember_memo = (TextView) findViewById(R.id.remember_memo);
        remember_memo.setVisibility(View.GONE); //隐藏小红圈


        //历史公告的小红圈
        remember_notice = (TextView) findViewById(R.id.remember_notice);


        passwordChange = (RelativeLayout) findViewById(R.id.rl_password_login);
        passwordChange.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        initView();
        initData();



        super.onResume();

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch(id){
            case R.id.rl_password_login:
                //密码修改
                startActivity(new Intent(mContext,ChangePasswordActivity.class));
                finish();
                break;

        }

    }
}
