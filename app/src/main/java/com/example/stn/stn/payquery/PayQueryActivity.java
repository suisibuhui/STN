package com.example.stn.stn.payquery;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.stn.stn.BaseActivity;
import com.example.stn.stn.R;
import com.example.stn.stn.UserMainActivity;
import com.example.stn.stn.bean.AccountUeryBean;
import com.example.stn.stn.bean.Common;
import com.example.stn.stn.bean.PayBean;
import com.example.stn.stn.cache.OrderList;
import com.example.stn.stn.cache.UserInfo;
import com.example.stn.stn.fragment.UserLocationFragment;
import com.example.stn.stn.service.ServerApi;
import com.example.stn.stn.service.ServiceGenerator;
import com.example.stn.stn.utils.CustomDialog;
import com.example.stn.stn.utils.SystemUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 暂停恢复
 *
 * Created by 冬冬 on 2016/8/16.
 *
 */
public class PayQueryActivity extends BaseActivity implements View.OnTouchListener {

    protected FrameLayout flLocation;
    private Fragment userLocatonFragment;


    private String prodInstId; //用户证号

    private List<OrderList.OfferInfosBean> listOrderList;
    private Call<PayBean> call;
    private Call<AccountUeryBean> call1;
    private ProgressDialog mProgressDialog;
    private TextView etStartTime;
    private TextView etEndTime;
    private StringBuffer time1;//开始时间
    private String time1111;//开始时间
    private StringBuffer time2;//结束时间
    private String time2222;//结束时间
    private int ownTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payquery);

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

            //刷新按钮
            Button btn = (Button) findViewById(R.id.btn_refresh);
            btn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v){


                    if(TextUtils.isEmpty(time1111)){
                        SystemUtils.showToast(mContext,"请输入开始时间");
                        return;
                    }
                    if(TextUtils.isEmpty(time2222)){
                        SystemUtils.showToast(mContext,"请输入结束时间");
                        return;
                    }
                    HashMap<String, String> map = new HashMap<>();
                    map.put("objectId",locationCache.getCustomer().getCustomerId());
                    map.put("searchType","1");
                    //缴费查询
                    accountquery(application.getUUID(), locationCache.getTridId(), application.getToken(), map);


                    Log.i("msg","按钮点击事件");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 查询账本信息
     *
     * @param uuid
     * @param tridId
     * @param token
     */
    private AccountUeryBean refreshinfo;
    public void accountquery(String uuid, String tridId, String token, HashMap<String, String> map) {

        mProgressDialog.show();  //显示加载页面

        ServerApi serverApi = ServiceGenerator.createService(ServerApi.class);

        call1 = serverApi.accountQuery(uuid, tridId, token, map);
        call1.enqueue(new Callback<AccountUeryBean>() {
            @Override
            public void onResponse(Call<AccountUeryBean> call, Response<AccountUeryBean> response) {

                refreshinfo = response.body();
                if (refreshinfo.getReturn_code().equals("0")) {
                  //mProgressDialog.dismiss(); //隐藏加载页面
                  //设置listview
                  //缴费查询
                    clickButton();
                } else {
                    SystemUtils.showToast(mContext, refreshinfo.getReturn_message(), Gravity.CENTER);
                    mProgressDialog.dismiss();  //隐藏加载页面
                }
            }

            @Override
            public void onFailure(Call<AccountUeryBean> call, Throwable t) {
                SystemUtils.showToast(mContext, t.getMessage(), Gravity.CENTER);
                mProgressDialog.dismiss();   //隐藏加载页面
            }
        });


    }

    private void initView() {

        initFragment();
        flLocation = (FrameLayout) findViewById(R.id.fl_location);
        flLocation.setFocusable(true);
        flLocation.setFocusableInTouchMode(true);
        flLocation.requestFocus();
        flLocation.requestFocusFromTouch();

        etStartTime = (TextView) this.findViewById(R.id.time_search); //开始时间控件
        etEndTime = (TextView) this.findViewById(R.id.time_search2);  //结束时间控件

        etStartTime.setOnTouchListener(this);
        etEndTime.setOnTouchListener(this);


    }

    private void clickButton(){
                //点击后跳转首页
                HashMap<String, String> map = new HashMap<>();
                map.put("beginDate",time1111);
                map.put("endDate",time2222);
        try{
            map.put("custId",refreshinfo.getAcctInfo().get(0).getAccountId());
        }catch(Exception t){
            map.put("custId","");
        }

                //缴费查询
                refresh(application.getUUID(),locationCache.getTridId(),application.getToken(),map);

//
//            }
//        });
//        dialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
//            @Override
//            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                dialog.dismiss();
//            }
//        });

    }

    /**
     * 刷新授权请求
     * @param uuid
     * @param tridId
     * @param token
     *
     */
    public void refresh(String uuid, String tridId, String token, HashMap<String,String> map){

        mProgressDialog.show();  //显示加载页面

        ServerApi serverApi = ServiceGenerator.createService(ServerApi.class);
        call = serverApi.payQuery(uuid,tridId,token,map);

        call.enqueue(new Callback<PayBean>() {
            @Override
            public void onResponse(Call<PayBean> call, Response<PayBean> response) {

                PayBean refreshinfo = response.body();
                if (refreshinfo.getReturn_code().equals("0")){

                    if(refreshinfo.getPaymentRecordList() == null || refreshinfo.getPaymentRecordList().size() == 0){
                        SystemUtils.showToast(mContext,"内容为空");
                    }else{
                        locationCache.setPayInfos(refreshinfo);
                        Intent intent = new Intent(mContext , PayQueryResultActivity.class);
                        startActivity(intent);
                    }
                    //SystemUtils.showToast(mContext,"刷新授权成功", Gravity.CENTER );
                        mProgressDialog.dismiss(); //隐藏加载页面
                }else {
                    SystemUtils.showToast(mContext, refreshinfo.getReturn_message(), Gravity.CENTER );
                    mProgressDialog.dismiss();  //隐藏加载页面
                }
            }
            @Override
            public void onFailure(Call<PayBean> call, Throwable t) {
                SystemUtils.showToast(mContext,t.getMessage(), Gravity.CENTER );
                mProgressDialog.dismiss();   //隐藏加载页面
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            if (v.getId() == R.id.time_search) {//点击了开始时间控件
                CustomDialog.Builder builder1 = new CustomDialog.Builder(this);
                View view = View.inflate(this, R.layout.time_choose, null);   //加载时间选择器布局文件
                final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);

                builder1.setContentView(view);

                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                cal.add(Calendar.DATE, -1);
                datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), null);
                etStartTime.onTouchEvent(event);
                builder1.setTitle("开始时间");
                builder1.setPositiveButton("确  定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        StringBuffer sb1 = new StringBuffer();
                        sb1.append(String.format("%d%02d%02d",
                                datePicker.getYear(),
                                datePicker.getMonth() + 1,
                                datePicker.getDayOfMonth()));
                        sb1.append("");
                        int month1 = datePicker.getMonth() + 1;
                        int year = datePicker.getYear();
                        int dayOfMonth = datePicker.getDayOfMonth();

                        time1111 = datePicker.getYear()+"-"+month1+"-"+datePicker.getDayOfMonth();
                        etEndTime.requestFocus();

                        dialog.cancel();

                        String a = sb1.toString();

                        int i = (int)Double.parseDouble(a);


                        Log.i("msg" , String.valueOf(i - ownTime));

//                        /**
//                         * 判断开始时间不能早于当前时间
//                         */
//                        if( (i - ownTime) > 0){
//                            SweetAlertDialog dialog1 = new SweetAlertDialog(PayQueryActivity.this);
//                            dialog1.setTitleText("提示");
//                            dialog1.setContentText("开始时间不能大于当前时间");
//                            dialog1.setConfirmText("确认");
//                            dialog1.show();
//                        }else{
                            etStartTime.setText(sb1);
                            time1 = sb1;
                        //}
                    }
                });
                builder1.setNegativeButton("取 消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                Dialog dialog = builder1.create();
                dialog.show();

            } else if (v.getId() == R.id.time_search2) { //点击了结束时间控件

                try {
                    CustomDialog.Builder builder2 = new CustomDialog.Builder(this);
                    View view = View.inflate(this, R.layout.time_choose, null);
                    final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);

                    builder2.setContentView(view);

                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(System.currentTimeMillis());
                    datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), null);

                    etEndTime.onTouchEvent(event);
                    builder2.setTitle("结束时间");
                    builder2.setPositiveButton("确  定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            StringBuffer sb2 = new StringBuffer();
                            sb2.append(String.format("%d%02d%02d",
                                    datePicker.getYear(),
                                    datePicker.getMonth() + 1,
                                    datePicker.getDayOfMonth()));
                            sb2.append("");
                            int month1 = datePicker.getMonth() + 1;
                            time2222 = datePicker.getYear()+"-"+month1+"-"+datePicker.getDayOfMonth();
                            Log.i("msg" , sb2.toString());
                            time2 = sb2;


                            String tm1 = "";
                            int b = 0;

                            if (time1 == null){
                                tm1 = "";
                            }else {
                                tm1 = time1.toString();
                                b = (int)Double.parseDouble(tm1);
                            }

                            String tm2 = time2.toString();
                            Log.i("msg",String.valueOf(tm1.compareTo(tm2)));


                            int c = (int)Double.parseDouble(tm2);


                            if (time1 == null){ //开始时间为空
                                /**
                                 * 判断结束时间不能早于当前时间
                                 */
//                                if( (c - ownTime) > 0 ){
//                                    SweetAlertDialog dialog1 = new SweetAlertDialog(PayQueryActivity.this);
//                                    dialog1.setTitleText("提示");
//                                    dialog1.setContentText("结束时间不能大于当前时间");
//                                    dialog1.setConfirmText("确认");
//                                    dialog1.show();
//                                }else {
                                    etEndTime.setText(sb2);
                                //}
                            }else{ //开始时间不为空
                                /**
                                 * 判断结束时间不能早于当前时间
                                 */
//                                if( (c - ownTime) > 0 ){
//                                    SweetAlertDialog dialog1 = new SweetAlertDialog(PayQueryActivity.this);
//                                    dialog1.setTitleText("提示");
//                                    dialog1.setContentText("结束时间不能大于当前时间");
//                                    dialog1.setConfirmText("确认");
//                                    dialog1.show();
//                                }else

                                if( (b - c) > 0 ){
                                    /**
                                     * 判断结束时间不能早于开始时间
                                     */
                                    SweetAlertDialog dialog1 = new SweetAlertDialog(PayQueryActivity.this);
                                    dialog1.setTitleText("提示");
                                    dialog1.setContentText("结束时间不能小于开始时间");
                                    dialog1.setConfirmText("确认");
                                    dialog1.show();
                                }else{
                                    etEndTime.setText(sb2);
                                }
                            }

                            dialog.cancel();
                        }
                    });
                    builder2.setNegativeButton("取 消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    Dialog dialog = builder2.create();
                    dialog.show();
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        }
        return true;
    }
}