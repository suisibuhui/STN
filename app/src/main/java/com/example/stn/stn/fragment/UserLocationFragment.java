package com.example.stn.stn.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.stn.stn.R;
import com.example.stn.stn.cache.UserInfo;
import com.example.stn.stn.querycustomer.LocationDetailActivity;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Name: UserLocationFragment
 * Author: xulong
 * Comment: 用户定位
 * Date: 2016-08-16 14:35.
 */
public class UserLocationFragment extends BaseFragment {

    protected View rootView;
    protected TextView tvUserName;
    protected TextView tvUserId;
    protected TextView tvUserAddress;
    protected LinearLayout llUserLocate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_user_location, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(rootView);
    }



    private void initView(View rootView) {
        tvUserName = (TextView) rootView.findViewById(R.id.tv_user_name);  //客户名称
        tvUserId = (TextView) rootView.findViewById(R.id.tv_user_id);      //智能卡号/宽带用户名
        tvUserAddress = (TextView) rootView.findViewById(R.id.tv_user_address);     //地址名称

        if (locationCache.getCustomer() != null) {
            tvUserName.setText(locationCache.getCustomer().getCustomerName());
            tvUserId.setText("");//不清楚从哪来的
            tvUserAddress.setText(locationCache.getCustomer().getAddress());
        }
        if(locationCache.getUser() != null){

            UserInfo.ProdInfosBean user = locationCache.getUser();
            String address = user.getAddress();
            String cardNo = user.getCardNo();
            tvUserId.setText(cardNo);
            tvUserAddress.setText(address);
        }

        llUserLocate = (LinearLayout) rootView.findViewById(R.id.ll_locate);
        llUserLocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, LocationDetailActivity.class);
                intent.putExtra("locationBiaoShi", "isCustomer"); //跳转标识：已经客户定位了
                startActivity(intent);




            }
        });
    }




    @Override
    public void onResume() {
        super.onResume();

        if (locationCache.getCustomer() != null) {
            tvUserName.setText(locationCache.getCustomer().getCustomerName());
            tvUserId.setText("");//不清楚从哪来的
            tvUserAddress.setText(locationCache.getCustomer().getAddress());
        }
        if(locationCache.getUser() != null){

            UserInfo.ProdInfosBean user = locationCache.getUser();
            String address = user.getAddress();
            String cardNo = user.getCardNo();
            tvUserId.setText(cardNo);
            tvUserAddress.setText(address);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }



}
