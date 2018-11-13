package com.example.stn.stn.querycustomer;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.stn.stn.BaseActivity;
import com.example.stn.stn.R;
import com.example.stn.stn.adapter.MCommonAdapter;
import com.example.stn.stn.adapter.MViewHolder;
import com.example.stn.stn.cache.OrderList;
import com.example.stn.stn.utils.SystemUtils;

import java.util.List;

/**
 * Name: LocationPackageDetailActivity
 * Author: xulong
 * Comment: 用户定位的定位结果之订购信息套餐详情
 * Date: 2016-08-19 17:24.
 */
public class LocationPackageDetailActivity extends BaseActivity {

    protected TextView tvPackageName;
    protected TextView tvEffectDate;
    protected TextView tvPredictUseDate;
    protected TextView tvStatus;
    protected TextView tvMachineStatus;
    private LinearLayout llPackageInfo;
    private LinearLayout llPackageInfo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_location_package_detail);
        initView();
        initData();
    }

    private void initData() {

        //套餐名称
        tvPackageName.setText(getIntent().getStringExtra("packageName"));
        //生效日期
        tvEffectDate.setText(getIntent().getStringExtra("effectDate"));
        //失效日期
        tvPredictUseDate.setText(getIntent().getStringExtra("predictUseDate"));
        String from = getIntent().getStringExtra("from");
        if(from.equals("0")){
            //状态
            tvStatus.setText(SystemUtils.getPackageState(getIntent().getStringExtra("status")));
            //停开机状态
            tvMachineStatus.setText(SystemUtils.getOpenCloseState(getIntent().getStringExtra("machineStatus")));
        }else{
            //1
            llPackageInfo.setVisibility(View.GONE);
            llPackageInfo2.setVisibility(View.GONE);
        }



    }

    private void initView() {
        tvPackageName = (TextView) findViewById(R.id.tv_packagelist_01);
        tvEffectDate = (TextView) findViewById(R.id.tv_packagelist_02);
        tvPredictUseDate = (TextView) findViewById(R.id.tv_packagelist_03);
        tvStatus = (TextView) findViewById(R.id.tv_packagelist_04);
        tvMachineStatus = (TextView) findViewById(R.id.tv_packagelist_05);
        llPackageInfo = (LinearLayout) findViewById(R.id.ll_packageinfos_01);
        llPackageInfo2 = (LinearLayout) findViewById(R.id.ll_packageinfos_02);

    }



}
