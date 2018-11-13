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

public class BusinessAcceptanceFragment extends GridViewFragment {

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
        datas.add(new GridInfo("productOrder","产品订购", R.drawable.home_goods_order));
        datas.add(new GridInfo("shuaxinshouquan","刷新授权",R.drawable.new_empower));
        datas.add(new GridInfo("renewPackages","暂停恢复",R.drawable.t05));
        datas.add(new GridInfo("newBusiness","账户充值", R.drawable.t03));
        return datas;
    }

    private void initView() {
        tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        tvTitle.setText("业务受理");
    }
}
