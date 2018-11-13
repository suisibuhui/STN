package com.example.stn.stn.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.example.stn.stn.R;
import com.example.stn.stn.UserMainActivity;
import com.example.stn.stn.bean.CustomerInfo;
import com.example.stn.stn.cache.UserInfo;
import com.example.stn.stn.service.ServerApi;
import com.example.stn.stn.service.ServiceGenerator;
import com.example.stn.stn.utils.SystemUtils;
import com.example.stn.stn.views.TopBarView;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Name: LocationMenuResultCustomerFragment
 * Author: xulong 客户信息右边那页。
 * Comment: //TODO
 * Date: 2016-08-19 17:34.
 */
public class LocationMenuResultCustomerFragment extends BaseFragment implements View.OnClickListener {

    protected TextView tvCustomerName;
    protected TextView tvCustomerNum;
    protected TextView tvAddressName;
   // protected TextView tvFullStatus;
    protected TextView tvContactsName;
    protected TextView tvContactsAddress;
    protected TextView tvContactsMobile;
    //protected TextView tvContactsPhone;
    protected TextView tvContactsIdType;
    protected TextView tvContactsIdNum;
    protected Button btnNext;
    private View rootView;

    private Map<String, String> fieldMap;  //存放body数据
    private int position;   //所选的位置
    private Boolean isWebSuccess ;   //网络是否请求成功
    private List<CustomerInfo.CustsEntity> mMenuData;
    private ProgressDialog mProgressDialog;
    private Call<UserInfo> call;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_location_result_customer, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //用户查询进来的
        if(locationCache.getWhereForm().equals("1")){
            initFragment();
        }else{
            initData();
            initView(rootView);
        }

        TopBarView topBarView = (TopBarView) getActivity().findViewById(R.id.topbar);
        topBarView.setOnLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();

            }
        });


    }

    private void initData() {
        if (mProgressDialog == null ) {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setMessage("网络加载中...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                            if(mProgressDialog != null){
                                mProgressDialog.dismiss();
                            }
                            if(call != null){
                                call.cancel();
                                call = null;
                            }
                        }

                    }
                    return false;
                }
            });
        }else{

        }
        //客户信息列表
        mMenuData = locationCache.getCustomerInfo().getCustomer();
        locationCache.setCustomer(mMenuData.get(0));
        position = 0;
        fieldMap = new ArrayMap<>();
        fieldMap.put("objectId", locationCache.getCustomer().getCustomerCode());
        fieldMap.put("searchType","1");//证件号码
        fieldMap.put("officeId",locationCache.getOrgInfoYourChoose().getOrgid());
        fieldMap.put("identityType", locationCache.getCustomer().getIdentityType());
        isWebSuccess = false;


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_next) {
            //保存选定的客户名称
            locationCache.setCustomerName(tvCustomerName.getText().toString());
            //保存选定的地址名称
            locationCache.setLocationAddress(tvAddressName.getText().toString());
            //保存选定的客户信息
            locationCache.setCustomer(mMenuData.get(position));


                mProgressDialog.show();
                ServerApi serverApi = ServiceGenerator.createService(ServerApi.class);
                call = serverApi.userQuery(application.getUUID(),locationCache.getTridId(),
                    application.getToken(), fieldMap );
                call.enqueue(new Callback<UserInfo>() {
                    @Override
                    public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                        UserInfo userInfo = response.body();
                        mProgressDialog.dismiss();
                        if (userInfo.getReturn_code().equals("0")) {
                            if(userInfo.getProdInfos().size() == 0){
                                Intent intent = new Intent(mContext, UserMainActivity.class);
                                intent.putExtra("businessName" ,"");
                            }else{

                                locationCache.setUserInfo(userInfo);  //缓存用户列表

                                initFragment();
                            }

                        }else{
                            SystemUtils.showToast(mContext,userInfo.getReturn_message());
                            locationCache.setUserInfo(null);
                            locationCache.setUser(null);
                            Intent intent = new Intent(mContext, UserMainActivity.class);
                            mContext.startActivity(intent);
                        }
                    }
                    @Override
                    public void onFailure(Call<UserInfo> call, Throwable t) {
                        mProgressDialog.dismiss();
                    }
                });


        }
    }


    private void initView(View rootView) {

        //客户名称
        tvCustomerName = (TextView) rootView.findViewById(R.id.tv_customer_name);

        //业务能力验证
      //  tvBusinessAbility = (TextView) rootView.findViewById(R.id.tv_businessAbility);

        //客户编号
        tvCustomerNum = (TextView) rootView.findViewById(R.id.tv_customer_num);

        //住宅地址名称
        tvAddressName = (TextView) rootView.findViewById(R.id.tv_address_name);

        //整转状态
        //tvFullStatus = (TextView) rootView.findViewById(R.id.tv_full_status);

        //联系人名字
        tvContactsName = (TextView) rootView.findViewById(R.id.tv_contacts_name);

        //联系人地址
        tvContactsAddress = (TextView) rootView.findViewById(R.id.tv_contacts_address);

        //联系人手机
        tvContactsMobile = (TextView) rootView.findViewById(R.id.tv_contacts_mobile);

        //联系人电话
        //tvContactsPhone = (TextView) rootView.findViewById(R.id.tv_contacts_phone);

        //联系人证件类型
        tvContactsIdType = (TextView) rootView.findViewById(R.id.tv_contacts_id_type);

        //联系人证件号码
        tvContactsIdNum = (TextView) rootView.findViewById(R.id.tv_contacts_id_num);

        btnNext = (Button) rootView.findViewById(R.id.btn_next);
        btnNext.setOnClickListener(LocationMenuResultCustomerFragment.this);

        CustomerInfo.CustsEntity customer = locationCache.getCustomerInfo().getCustomer().get(0);
        tvCustomerName.setText(customer.getCustomerName());
        tvCustomerNum.setText(String.valueOf(customer.getCustomerCode()));
        tvAddressName.setText(customer.getAddress());
       // tvFullStatus.setText(locationCache.getCustomer().getTransferFlagName());
        //设置业务能力。
       // tvBusinessAbility.setText(locationCache.getCustomer().getCapValue());
        //里面有没
        //判断 长度

//        if(locationCache.getCustomer().getContacts().size() == 0){
//            //长度为0 不做任何操作
//        }else{
//            tvContactsName.setText(locationCache.getCustomer().getContacts().get(0).getContName());
//            //
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

//        }

    }

    /**
     * 跳转到用户Fragment
     */
    private void initFragment() {
        //跳转用户菜单
        Fragment locationMenuLeftUserFragment = LocationMenuLeftUserFragment.newInstance(locationCache.getCustomer());
        FragmentManager fragmentManagerMenu = getFragmentManager();
        FragmentTransaction transactionMenu = fragmentManagerMenu.beginTransaction();
        transactionMenu.replace(R.id.fl_location_menu, locationMenuLeftUserFragment, "menuUserFragment");
        transactionMenu.addToBackStack("menuUserFragment");
        transactionMenu.commit();

        //跳转用户Fragment
        Fragment locationMenuResultUserFragment = new LocationMenuResultUserFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fl_location_content, locationMenuResultUserFragment, "resultUserFragment");
        transaction.addToBackStack("resultUserFragment");
        transaction.commit();
    }






    /**
     * 供Activity回调的填充函数
     */
    public void initText (String customerName, String customerId, String customerAdd,
                           String contactName, String contactAdd,
                          String contactMobile, String contactIdType,
                          String contactIdNum ,String custAddrId,String stdAddrId, int position,String phone) {
        tvCustomerName.setText(customerName);
        tvCustomerNum.setText(customerId);
        tvAddressName.setText(customerAdd);
        //tvFullStatus.setText(customerStatus);
        tvContactsName.setText(contactName);
        tvContactsAddress.setText(contactAdd);
        if(TextUtils.isEmpty(contactMobile)){
            tvContactsMobile.setText(phone);
        }else{
            tvContactsMobile.setText(contactMobile);
        }

        //tvContactsPhone.setText(contactPhoe);
        tvContactsIdType.setText(SystemUtils.getIdType(contactIdType));
        tvContactsIdNum.setText(contactIdNum);
        this.position = position;

        fieldMap = new ArrayMap<>();

        fieldMap.put("objectId", locationCache.getCustomer().getIdentityNo());
        fieldMap.put("searchType","9");//证件号码
        fieldMap.put("officeId",locationCache.getOrgInfoYourChoose().getOrgid());
        fieldMap.put("identityType", locationCache.getCustomer().getIdentityType());

    }


    @Override
    public void onResume() {
        super.onResume();
        if(locationCache.getWhereForm().equals("1")){
            getActivity().finish();
        }else{
            if (isWebSuccess) {
                initFragment();
            }
        }

    }
}