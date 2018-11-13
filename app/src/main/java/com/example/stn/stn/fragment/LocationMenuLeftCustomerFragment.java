package com.example.stn.stn.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.stn.stn.R;
import com.example.stn.stn.adapter.LocationMenuCustomerAdapter;
import com.example.stn.stn.bean.CustomerInfo;

import java.util.List;


/**
 * Name: LocationMenuLeftFragment
 * Author: xulong
 * Comment: //客户信息左边那页。
 * Date: 2016-08-19 14:57.
 */
public class LocationMenuLeftCustomerFragment extends BaseFragment {

    protected ListView lvLocationCustomerName;
    protected Button btnAddressAdd;
    private View rootView;
    protected ListAdapter adapter;                  //菜单适配器
    private View v;
    private CustomerInfo.CustsEntity customerSelect;  //所选择的客户
    private List<CustomerInfo.CustsEntity> mMenuData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_location_menu_customer, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(locationCache.getWhereForm().equals("1")){

        }else{
            initData();
            initView(rootView);
        }

    }

    private void initData() {
        mMenuData = locationCache.getCustomerInfo().getCustomer();

    }

    private void initView(View rootView) {
        getMenuLeftAdapter();
        lvLocationCustomerName = (ListView) rootView.findViewById(R.id.lv_location_customer_name);
        lvLocationCustomerName.setAdapter(adapter);
        lvLocationCustomerName.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        lvLocationCustomerName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String customerName = mMenuData.get(position).getCustomerName();
                String customerId = String.valueOf(mMenuData.get(position).getCustomerCode());
                String customerAdd = mMenuData.get(position).getAddress();
                String customerStatus = ""; //整转状态
                //长度为0 不做任何操作
                String contactName = mMenuData.get(position).getContactName();
                String contactAdd = mMenuData.get(position).getAddress();
                String contactMobile = mMenuData.get(position).getMobile();
                String contactPhoe = mMenuData.get(position).getPhone1();

                String contactIdNum =mMenuData.get(position).getIdentityNo();
                String contactIdType = mMenuData.get(position).getIdentityType();
                String custAddrId = mMenuData.get(position).getAddress();
                String stdAddrId = "";

                //所选择的客户
                customerSelect = mMenuData.get(position);

                mInterface.initResultCustomerFragment(customerName, customerId, customerAdd, customerStatus,
                        contactName, contactAdd, contactMobile, contactPhoe, contactIdType, contactIdNum, custAddrId, stdAddrId, position);

                if (v != null) {
                    v.setBackgroundColor(0);
                } else {
                    View defaultView = lvLocationCustomerName.getChildAt(0);
                    defaultView.setBackgroundColor(0);
                }
                view.setBackgroundColor(Color.parseColor("#E96E04"));
                v = view;
            }
        });
        setListViewHeightBasedOnChildren(lvLocationCustomerName);     //计算listView

        /****************新增地址按钮************************/


    }

    //获取左侧滑菜单的adapter
    private ListAdapter getMenuLeftAdapter() {
        adapter = new LocationMenuCustomerAdapter(mContext, mMenuData);
        return adapter;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mInterface = (interfaceCustomerFragment) context;
        } catch (Exception e) {
            throw new ClassCastException(context.toString() + "must implement interfaceCustomerFragment");
        }
    }

    /**
     * 定义接口及回调方法
     */
    private interfaceCustomerFragment mInterface;

    public interface interfaceCustomerFragment {
        void initResultCustomerFragment(String customerName, String customerId, String customerAdd,
                                        String customerStatus, String contactName, String contactAdd,
                                        String contactMobile, String contactPhoe, String contactIdType,
                                        String contactIdNum, String custAddrId, String stdAddrId, int position);
    }

    //接受回传信息
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        Log.i("luojipanduan","进入onActiviyResult");
//
//        if (data != null) {
//            CustomerInfo customerInfo = (CustomerInfo)data.getSerializableExtra("addCustomerAddress");
//            List<CustomerInfo.CustsEntity> custsEntitiesAdd = customerInfo.getCusts();
//            mMenuData.clear();
//            mMenuData.addAll(custsEntitiesAdd);
//            ((LocationMenuCustomerAdapter)lvLocationCustomerName.getAdapter()).notifyDataSetChanged();
//        }
//        setListViewHeightBasedOnChildren(lvLocationCustomerName);     //计算listView
    }


    /**
     * 解决ScrollView和ListView的冲突方法
     * @param listView
     */
    public void setListViewHeightBasedOnChildren(ListView listView){

        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1)) + 100;
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }


}
