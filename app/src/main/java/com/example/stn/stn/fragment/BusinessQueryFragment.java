package com.example.stn.stn.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.example.stn.stn.R;
import com.example.stn.stn.bean.GridInfo;

import java.util.ArrayList;

/**
 * Name: BusinessQueryFragment
 * Author: xulong
 * Comment: //TODO
 * Date: 2016-07-21 16:43.
 */
public class BusinessQueryFragment extends GridViewFragment {

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
        datas.add(new GridInfo("refreshAuthorization","工单查询",R.drawable.t07));
        datas.add(new GridInfo("repairService","缴费查询",R.drawable.t06));
        datas.add(new GridInfo("customerInfo","客户信息",R.drawable.customer_information));
        datas.add(new GridInfo("handleQuery","受理查询",R.drawable.handle_check));
        datas.add(new GridInfo("userInfo","用户信息",R.drawable.user_infor));
        datas.add(new GridInfo("billQuery","账单查询", R.drawable.bill_check));
        datas.add(new GridInfo("billPayment","账户信息",R.drawable.t14));
        return datas;
    }

    private void initView() {
        tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        tvTitle.setText("业务查询");


    }
}
