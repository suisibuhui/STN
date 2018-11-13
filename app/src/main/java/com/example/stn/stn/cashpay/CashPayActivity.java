package com.example.stn.stn.cashpay;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stn.stn.BaseActivity;
import com.example.stn.stn.R;
import com.example.stn.stn.bean.Common;
import com.example.stn.stn.cache.OrderList;
import com.example.stn.stn.fragment.UserLocationFragment;
import com.example.stn.stn.service.ServerApi;
import com.example.stn.stn.service.ServiceGenerator;
import com.example.stn.stn.utils.SystemUtils;

import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 现金缴费
 * <p>
 * Created by 冬冬 on 2016/8/16.
 */
public class CashPayActivity extends BaseActivity {

    protected FrameLayout flLocation;
    private Fragment userLocatonFragment;


    private String prodInstId; //用户证号

    private List<OrderList.OfferInfosBean> listOrderList;
    private Call<Common> call;
    private ProgressDialog mProgressDialog;

    private TextView et_cashpay_orgid;
    private Spinner sp_cash_paytype;
    private EditText et_cashpay_paymoney;
    private Button btn_cash_send;
    private boolean state;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashpay);

        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setMessage("网络加载中...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                            if (mProgressDialog != null) {
                                mProgressDialog.dismiss();
                            }
                            if (call != null) {
                                call.cancel();
                                call = null;
                            }
                        }

                    }
                    return false;
                }
            });
        } else {

        }
        initData();
        initView();
    }

    /**
     * 初始化Fragment视图
     */
    private void initFragment() {

        if (userLocatonFragment == null) {
            userLocatonFragment = new UserLocationFragment();
        }

        if (!userLocatonFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_location, userLocatonFragment).commit();
        }
    }

    private void initData() {
        try {
            //解决ScrollView和ListView的冲突方法
            state = false;
            //刷新按钮
            btn_cash_send = (Button) findViewById(R.id.btn_cash_send);

            btn_cash_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击提交
                    clickButton();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void initView() {

        initFragment();
        flLocation = (FrameLayout) findViewById(R.id.fl_location);
        flLocation.setFocusable(true);
        flLocation.setFocusableInTouchMode(true);
        flLocation.requestFocus();
        flLocation.requestFocusFromTouch();

        et_cashpay_orgid = (TextView) findViewById(R.id.et_cashpay_orgid);
        et_cashpay_orgid.setText(locationCache.getOrgInfoYourChoose().getOrgname());

        sp_cash_paytype = (Spinner) findViewById(R.id.sp_cash_paytype);
        et_cashpay_paymoney = (EditText) findViewById(R.id.et_cashpay_paymoney);


    }

    private void clickButton() {


        if(TextUtils.isEmpty(et_cashpay_paymoney.getText().toString())){
            Toast.makeText(mContext,"请输入金额",Toast.LENGTH_SHORT).show();
            return;
        }


        //点击后跳转首页
        HashMap<String, String> map = new HashMap<>();
        Integer integer = Integer.valueOf(et_cashpay_paymoney.getText().toString());
        if(integer< 10){
            SystemUtils.showToast(mContext,"缴费金额必须大于最小应缴金额10元");
            return;
        }


        if(integer> 10000){
            if(state){

                map.put("trade_money",et_cashpay_paymoney.getText().toString() );
                map.put("loginName",locationCache.getOper().getUserno());
                map.put("officeId",locationCache.getOrgInfoYourChoose().getOrgid());
                try {
                    map.put("customerCode", locationCache.getCustomer().getCustomerCode());
                } catch (Exception t) {
                    map.put("customerCode", locationCache.getUser().getCustomerId());
                }

                //缴费查询
                paymoney(application.getUUID(), locationCache.getTridId(), application.getToken(), map);
            }else{
                final SweetAlertDialog dialog = new SweetAlertDialog(mContext);
                dialog.setTitleText("提示");
                dialog.setContentText("缴费金额大于1万，请认真核实");
                dialog.setConfirmText("确定");
                dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        state = true;
                        dialog.dismiss();
                    }
                });
                dialog.show();


            }
        }else{
            map.put("trade_money",et_cashpay_paymoney.getText().toString() );
            map.put("loginName",locationCache.getOper().getUserno());
            map.put("officeId",locationCache.getOrgInfoYourChoose().getOrgid());
            try {
                map.put("customerCode", locationCache.getCustomer().getCustomerCode());
            } catch (Exception t) {
                map.put("customerCode", locationCache.getUser().getCustomerId());
            }

            //缴费查询
            paymoney(application.getUUID(), locationCache.getTridId(), application.getToken(), map);
        }

    }

    /**
     * 付钱
     *
     * @param uuid
     * @param tridId
     * @param token
     */
    public void paymoney(String uuid, String tridId, String token, HashMap<String, String> map) {

        mProgressDialog.show();  //显示加载页面

        ServerApi serverApi = ServiceGenerator.createService(ServerApi.class);
        call = serverApi.cashPay(uuid, tridId, token, map);

        call.enqueue(new Callback<Common>() {
            @Override
            public void onResponse(Call<Common> call, Response<Common> response) {

                Common refreshinfo = response.body();
                if (refreshinfo.getReturn_code().equals("0")) {
                    mProgressDialog.dismiss(); //隐藏加载页面
                    state = false;
                    final SweetAlertDialog dialog = new SweetAlertDialog(mContext);
                    dialog.setTitleText("缴费成功");
                    dialog.setConfirmText("确认");
                    dialog.setContentText(et_cashpay_paymoney.getText().toString()+"元缴费成功");
                    dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            finish();
                        }
                    });
                    dialog.show();

                } else {
                    SystemUtils.showToast(mContext, refreshinfo.getReturn_message(), Gravity.CENTER);
                    mProgressDialog.dismiss();  //隐藏加载页面
                }
            }

            @Override
            public void onFailure(Call<Common> call, Throwable t) {
                SystemUtils.showToast(mContext, t.getMessage(), Gravity.CENTER);
                mProgressDialog.dismiss();   //隐藏加载页面
            }
        });
    }




}