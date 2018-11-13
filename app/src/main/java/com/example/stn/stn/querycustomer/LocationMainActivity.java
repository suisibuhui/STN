package com.example.stn.stn.querycustomer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;


import com.example.stn.stn.BaseActivity;
import com.example.stn.stn.R;
import com.example.stn.stn.cache.UserInfo;
import com.example.stn.stn.fragment.LocationMenuLeftCustomerFragment;
import com.example.stn.stn.fragment.LocationMenuLeftUserFragment;
import com.example.stn.stn.fragment.LocationMenuResultCustomerFragment;
import com.example.stn.stn.fragment.LocationMenuResultUserFragment;
import com.example.stn.stn.views.TopBarView;

import java.util.List;

/**
 * Name: LocationMainActivity
 * Author: xulong
 * Comment: //TODO
 * Date: 2016-08-19 17:12.
 */
public class LocationMainActivity extends BaseActivity implements LocationMenuLeftCustomerFragment.interfaceCustomerFragment,
                                                                  LocationMenuLeftUserFragment.interfaceUserFragment,
                                                                  LocationMenuResultUserFragment.BackHandlerInterface {

    protected Fragment locationMenuLeftCustomerFragment, locationMenuResultCustomerFragment,
            locationMenuLeftUserFragment, locationMenuResultUserFragment;
    protected TopBarView topbar;
    private LocationMenuResultUserFragment selectedFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_location_main);
        initData();
        initView();
    }


    private void initData() {
        initCustomerFragment();

    }


    private void initView() {
        topbar = (TopBarView) findViewById(R.id.topbar);
        topbar.setOnRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //进入页面前 判断一下权限
                //2017.0509 权限控制

                    intent.setClass(LocationMainActivity.this, LocationDetailActivity.class);
                    LocationMainActivity.this.startActivity(intent);
                    LocationMainActivity.this.finish();


            }
        });
    }


    //初始化填充主内容：客户定位
    private void initCustomerFragment() {
        if (locationMenuResultCustomerFragment == null) {
            locationMenuResultCustomerFragment = new LocationMenuResultCustomerFragment();
        }

        if (!locationMenuResultCustomerFragment.isAdded()) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fl_location_content, locationMenuResultCustomerFragment, "resultCustomerFragment");
            transaction.commit();
        }

        if (locationMenuLeftCustomerFragment == null) {
            locationMenuLeftCustomerFragment = new LocationMenuLeftCustomerFragment();
        }

        if (!locationMenuLeftCustomerFragment.isAdded()) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fl_location_menu, locationMenuLeftCustomerFragment, "menuCustomerFragment");
            transaction.commit();
        }
    }


    //初始化填充主内容：用户定位
    private void initUserFragment() {
        if (locationMenuResultUserFragment == null) {
            locationMenuResultUserFragment = new LocationMenuResultUserFragment();
        }

        if (!locationMenuResultUserFragment.isAdded()) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fl_location_content, locationMenuResultUserFragment, "resultUserFragment");
            transaction.commit();
        }

        if (locationMenuLeftUserFragment == null) {
            locationMenuLeftUserFragment = new LocationMenuLeftUserFragment();
        }

        if (!locationMenuLeftUserFragment.isAdded()) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fl_location_menu, locationMenuLeftUserFragment, "menuUserFragment");
            transaction.commit();
        }
    }


    /**
     * 客户参数回调
     */
    @Override
    public void initResultCustomerFragment(String customerName, String customerId, String customerAdd, String customerStatus,
                                           String contactName, String contactAdd, String contactMobile,
                                           String contactPhoe, String contactIdType, String contactIdNum,String custAddrId,String stdAddrId, int position) {
        FragmentManager manager = getSupportFragmentManager();
        LocationMenuResultCustomerFragment locationMenuResultCustomerFragment = (LocationMenuResultCustomerFragment) manager.findFragmentByTag("resultCustomerFragment");
        locationMenuResultCustomerFragment.initText(customerName, customerId, customerAdd,
                contactName, contactAdd, contactMobile,contactIdType, contactIdNum, custAddrId, stdAddrId, position,contactPhoe);
    }



    /**
     * 用户参数回调 ：调用用户定位左边菜单Fragment的接口里的回调方法
     * @param listTerminals
     * @param prodInstId
     */
    @Override
    public void initResultUserFragment(List<UserInfo.ProdInfosBean.TerminalsBean> listTerminals, String prodInstId, int position,
                                       List<UserInfo.ProdInfosBean.ProdUnOrder> prodUnOrderList) {
        FragmentManager manager = getSupportFragmentManager();
        LocationMenuResultUserFragment locationMenuResultUserFragment = (LocationMenuResultUserFragment) manager.findFragmentByTag("resultUserFragment");
        //根据用户定位左边选择的用户更新用户定位右边Fragment的内容
        locationMenuResultUserFragment.initText(listTerminals, prodInstId, position,prodUnOrderList);
    }


    @Override
    public void setSelectedFragment(LocationMenuResultUserFragment backHandledFragment) {
        this.selectedFragment = backHandledFragment;
    }

    @Override
    public void onBackPressed() {
        if (selectedFragment == null || !selectedFragment.onBackPressed()) {
            super.onBackPressed();
        }
    }










}
