package com.example.stn.stn.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.example.stn.stn.R;
import com.example.stn.stn.bean.CustomerInfo;
import com.example.stn.stn.cache.UserInfo;

import java.util.List;

/**
 * Name: LocationMenuLeftUserFragment
 * Author: xulong
 * Comment: //TODO
 * Date: 2016-08-26 10:19.
 */
public class LocationMenuLeftUserFragment extends BaseFragment {

    protected TextView tvLocationCustomerName;
    protected ListView lvLocationUserName;
    protected TextView tvLocationCustomerStdAddress;
    private View rootView;

    private List<UserInfo.ProdInfosBean> mMenuUserData;
    private View v;
    private int count = 0;
    private MyAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_location_menu_user, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //默认加载初始数据
        mMenuUserData = locationCache.getUserInfo().getProdInfos();
//        if (mMenuUserData == null) {
//            //进入前 判断一下权限
//            //进入页面前 判断一下权限
//            //2017.0509 权限控制
//            String funcauth = LoginCache.getInstance().getOperBean().getFuncauth();
//            String[] split = funcauth.split(",");
//            if(Arrays.asList(split).contains("19")) {
//                Intent intent = new Intent(mContext, LocationDetailActivity.class);
//                startActivity(intent);
//                getActivity().finish();
//                return;
//            }else{
//                Toast.makeText(mContext,"当前工号无权限",Toast.LENGTH_SHORT).show();
//            }
//
//
//        }
        initData();
        initView(rootView);

    }

    private void initData() {
        //客户定位页面获取到的用户信息列表
              mMenuUserData = locationCache.getUserInfo().getProdInfos();

    }


    private void initView(View rootView) {
        getMenuLeftAdapter();
//        tvLocationCustomerName = (TextView) rootView.findViewById(R.id.tv_location_customer_name);
//        tvLocationCustomerStdAddress = (TextView) rootView.findViewById(R.id.tv_location_customer_stdAddress);
//        //获取上一个Fragment传递过来的参数
//        if (getArguments() != null) {
//            String customerName = getArguments().getString("customerName");
//            String stdAddrShortName = getArguments().getString("stdAddrShortName");
//            tvLocationCustomerName.setText(customerName);
//            tvLocationCustomerStdAddress.setText(stdAddrShortName);
//        }

        //用户定位页的左边用户菜单listView
        lvLocationUserName = (ListView) rootView.findViewById(R.id.lv_location_user_name);
        lvLocationUserName.setAdapter(adapter);
        lvLocationUserName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击用户菜单后，把所选择的用户菜单位置保存起来。
                locationCache.setWhichOneClick(position);

                //保存点击的用户
                locationCache.setUserMyChoose(locationCache.getUserInfo().getProdInfos().get(position));

                //客户定位接口的用户信息列表里的设备列表
              List<UserInfo.ProdInfosBean.TerminalsBean> listTerminals = null;
              List<UserInfo.ProdInfosBean.ProdUnOrder> prodUnOrderList = null;
                //用户证号
                  String prodInstId = null;
                //XL 0927 : 用于传参给右边用户Fragment的回调方法【回调】
                 mInterface.initResultUserFragment(listTerminals, prodInstId, position,prodUnOrderList);


//                if (v != null) {
//                    v.setBackgroundColor(0);
//                } else {
//                    View defaultView = lvLocationUserName.getChildAt(0);
//                    defaultView.setBackgroundColor(0);
//                }
//                view.setBackgroundColor(Color.parseColor("#E96E04"));
//                v = view;
                adapter.notifyDataSetChanged();
                count = position;
            }
        });

    }


    /**
     * 获取左侧滑菜单的adapter
     *
     * @return
     */
    private ListAdapter getMenuLeftAdapter() {
        adapter = new MyAdapter();

        return adapter;
    }


    /**
     * 用来接收上一个Fragment传过来的参数
     *
     * @param
     * @return
     */
    public static LocationMenuLeftUserFragment newInstance(CustomerInfo.CustsEntity custsEntity) {
        LocationMenuLeftUserFragment locationMenuLeftUserFragment = new LocationMenuLeftUserFragment();
        Bundle bundle = new Bundle();
//        bundle.putString("customerName", custsEntity.getCustomerName());
//        bundle.putString("stdAddrShortName", custsEntity.getAddress());
        locationMenuLeftUserFragment.setArguments(bundle);
        return locationMenuLeftUserFragment;
    }

    /**
     * 定义接口及回调方法
     */
    private interfaceUserFragment mInterface;

    public interface interfaceUserFragment {


       void initResultUserFragment(List<UserInfo.ProdInfosBean.TerminalsBean> listTerminals, String prodInstId, int position,
                                   List<UserInfo.ProdInfosBean.ProdUnOrder> prodUnOrderList);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mInterface = (interfaceUserFragment) context;
        } catch (Exception e) {
            throw new ClassCastException(context.toString() + "must implement interfaceUserFragment");
        }
    }

    class MyAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return mMenuUserData.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null){
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_listview_location_menu_user, parent, false);
            }
            if(count == position){
                convertView.setBackgroundColor(Color.parseColor("#E96E04"));
            }else{
                convertView.setBackgroundColor(0);
            }
            TextView tvLocationUserName = (TextView) convertView.findViewById(R.id.tv_location_menu_user);
            tvLocationUserName.setText("   "+String.valueOf(mMenuUserData.get(position).getSubscriberCode()));
            TextView tvLocationMenuUserType = (TextView) convertView.findViewById(R.id.tv_location_menu_user_type);
            tvLocationMenuUserType.setText("   "+mMenuUserData.get(position).getSubscriberName());


            return convertView;
        }
    }

}
