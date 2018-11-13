package com.example.stn.stn.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stn.stn.R;
import com.example.stn.stn.bean.CustomerInfo;

import java.util.List;


/**
 * Name: LocationCustomerAdapter
 * Author: xulong
 * Comment: //客户定位菜单适配器
 * Date: 2016-08-29 12:25.
 */
public class LocationMenuCustomerAdapter extends CommonAdapter<CustomerInfo.CustsEntity>{

    public LocationMenuCustomerAdapter(Context context, List<CustomerInfo.CustsEntity> mDatas) {
        super(context, mDatas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.get(mContext, convertView,
                parent, R.layout.item_listview_location_menu_customer, position);

        CustomerInfo.CustsEntity custsBean = getItem(position);
        TextView tvLocationCustomerName = viewHolder.getView(R.id.tv_location_customer_name);
        tvLocationCustomerName.setText(custsBean.getCustomerCode());
        TextView tvLocationCustomerAddress = viewHolder.getView(R.id.tv_location_customer_address);
        tvLocationCustomerAddress.setText(custsBean.getCustomerName());
        if (position == 0) {
            viewHolder.getConvertView().setBackgroundColor(Color.parseColor("#E96E04"));
        } else {
            viewHolder.getConvertView().setBackgroundColor(0);
        }
        return viewHolder.getConvertView();
    }
}
