package com.example.stn.stn.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.stn.stn.R;
import com.example.stn.stn.bean.GridInfo;

import java.util.ArrayList;

/**
 * Name: MainContentFragment
 * Author: xulong
 * Comment: //首页内容Fragment
 * Date: 2016-07-20 17:14.
 */
public class MainContentFragment extends GridViewFragment {

    private TextView tvTitle;
    private Button btnScanPay;
    private View rootView ;
    private ArrayList<GridInfo> datas;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    @Override
    protected ArrayList<GridInfo> getData() {
        datas = new ArrayList<>();
        datas.add(new GridInfo("newBusiness","账户充值", R.drawable.t03));//有
        datas.add(new GridInfo("productOrder","产品订购", R.drawable.home_goods_order));//有
        datas.add(new GridInfo("renewPackages","暂停恢复",R.drawable.t05));//有
        datas.add(new GridInfo("repairService","缴费查询",R.drawable.t06));//you
        datas.add(new GridInfo("refreshAuthorization","工单查询",R.drawable.t07));//you
        datas.add(new GridInfo("customerDataMaintenance","工单派发",R.drawable.t09));
        datas.add(new GridInfo("orderhuilong","工单回笼",R.drawable.t13));
        datas.add(new GridInfo("billPayment","账户信息",R.drawable.t14));//you
        return datas;
    }

    private void initView() {
        tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        tvTitle.setText("快捷工作台");

    }









}
