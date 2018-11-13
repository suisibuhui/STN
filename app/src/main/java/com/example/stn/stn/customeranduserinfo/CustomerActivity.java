package com.example.stn.stn.customeranduserinfo;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stn.stn.BaseActivity;
import com.example.stn.stn.R;
import com.example.stn.stn.bean.CustomerInfo;
import com.example.stn.stn.utils.SystemUtils;

import java.util.Map;

/**
 * Name: CustomerOlderActivity
 * Author: xulong
 * Comment: //已有客户：预约新装第一个页面 【这个页面要调：m039业务校验接口】
 * Date: 2016-08-09 18:04.
 */
public class CustomerActivity extends BaseActivity implements View.OnClickListener {


    private TextView tvCustomerName;
    private TextView tvCustomerNum;
    private TextView tvAddressName;
    private TextView tvContactsName;
    private TextView tvContactsAddress;
    private TextView tvContactsMobile;
    private TextView tvContactsIdNum;
    private TextView tvContactsIdType;
    private Button btnNext;
    private CustomerInfo.CustsEntity customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_customer__older);
        initData();
        initView();

    }

    private void initData() {
                     //获取缓存的客户定位信息
        customer = locationCache.getCustomer();
    }

    private void initView() {
        //客户名称
        tvCustomerName = (TextView)findViewById(R.id.tv_customer_name);

        //业务能力验证
        //  tvBusinessAbility = (TextView) rootView.findViewById(R.id.tv_businessAbility);

        //客户编号
        tvCustomerNum = (TextView)findViewById(R.id.tv_customer_num);

        //住宅地址名称
        tvAddressName = (TextView)findViewById(R.id.tv_address_name);

        //整转状态
        //tvFullStatus = (TextView) rootView.findViewById(R.id.tv_full_status);

        //联系人名字
        tvContactsName = (TextView)findViewById(R.id.tv_contacts_name);

        //联系人地址
        tvContactsAddress = (TextView)findViewById(R.id.tv_contacts_address);

        //联系人手机
        tvContactsMobile = (TextView) findViewById(R.id.tv_contacts_mobile);

        //联系人电话
        //tvContactsPhone = (TextView) rootView.findViewById(R.id.tv_contacts_phone);

        //联系人证件类型
        tvContactsIdType = (TextView)findViewById(R.id.tv_contacts_id_type);

        //联系人证件号码
        tvContactsIdNum = (TextView) findViewById(R.id.tv_contacts_id_num);

        btnNext = (Button)findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvCustomerName.setText(customer.getCustomerName());
        tvCustomerNum.setText(String.valueOf(customer.getCustomerCode()));
        tvAddressName.setText(customer.getAddress());
        tvContactsName.setText(customer.getContactName());
        tvContactsAddress.setText(customer.getAddress());

        String mobile = customer.getMobile();
        if(TextUtils.isEmpty(mobile)){
            //为空
            tvContactsMobile.setText(customer.getPhone1());
        }else{
            //不为空
            tvContactsMobile.setText(mobile);
        }
        // tvContactsPhone.setText(String.valueOf(locationCache.getCustomer().getContacts().get(0).getTel1()));
        tvContactsIdType.setText(SystemUtils.getIdType(customer.getIdentityType()));
        tvContactsIdNum.setText(customer.getIdentityNo());

    }


    @Override
    public void onClick(View v) {

    }
}
