package com.example.stn.stn.pauserecover;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextPaint;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stn.stn.BaseActivity;
import com.example.stn.stn.R;
import com.example.stn.stn.UserMainActivity;
import com.example.stn.stn.adapter.MCommonAdapter;
import com.example.stn.stn.bean.Common;
import com.example.stn.stn.cache.LocationCache;
import com.example.stn.stn.cache.OrderList;
import com.example.stn.stn.cache.UserInfo;
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
 * 暂停恢复
 *
 * Created by 冬冬 on 2016/8/16.
 *
 */
public class RefreshAuthorizationActivity extends BaseActivity {

    protected FrameLayout flLocation;
    private Fragment userLocatonFragment;


    private String prodInstId; //用户证号

    private List<OrderList.OfferInfosBean> listOrderList;
    private Call<Common> call;
    private ProgressDialog mProgressDialog;
    private UserInfo.ProdInfosBean userMyChoose;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refreshauthorization);

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

            prodInstId = locationCache.getUserCode();  //已定位的用户的用户证号


            //解决ScrollView和ListView的冲突方法

            //刷新按钮
            Button btn = (Button) findViewById(R.id.btn_refresh);
            btn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    clickButton();
                    Log.i("msg","按钮点击事件");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

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
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);

    }

    private void initView() {

        initFragment();
        flLocation = (FrameLayout) findViewById(R.id.fl_location);
        flLocation.setFocusable(true);
        flLocation.setFocusableInTouchMode(true);
        flLocation.requestFocus();
        flLocation.requestFocusFromTouch();
        userMyChoose = locationCache.getUser();
        //初始化加载页面控件
        TextView plan1 = (TextView) findViewById(R.id.tv_pause_plan);
        plan1.setText(userMyChoose.getPlanName());
        TextView plan2 = (TextView) findViewById(R.id.tv_pause_plan2);
        plan2.setText(userMyChoose.getResNo());
        TextView plan3 = (TextView) findViewById(R.id.tv_pause_plan3);
        plan3.setText(userMyChoose.getCardNo());
        TextView plan4 = (TextView) findViewById(R.id.tv_pause_plan4);
        plan4.setText("");
        TextView plan5 = (TextView) findViewById(R.id.tv_pause_plan5);
        plan5.setText(locationCache.getState());

    }

    private void clickButton(){

        final SweetAlertDialog dialog = new SweetAlertDialog(mContext);
        dialog.setTitleText("提示");
        dialog.setContentText("是否确定暂停恢复");
        dialog.setConfirmText("确认");
        dialog.setCancelText("取消");
        dialog.show();

        //确认按钮点击事件
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                //点击后跳转首页
                HashMap<String, String> map = new HashMap<>();
                map.put("subscriberId",userMyChoose.getSubscriberId());
                map.put("officeId",locationCache.getOrgInfoYourChoose().getOrgid());
                //刷新授权请求方法
                refresh(application.getUUID(),locationCache.getTridId(),application.getToken(),map);
                dialog.dismiss();
            }
        });
        dialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                dialog.dismiss();
            }
        });

    }

    /**
     * 刷新授权请求
     * @param uuid
     * @param tridId
     * @param token
     *
     */
    public void refresh(String uuid, String tridId, String token, HashMap<String,String> map){

          //显示加载页面
        mProgressDialog.show();
        ServerApi serverApi = ServiceGenerator.createService(ServerApi.class);
        call = serverApi.refresh(uuid,tridId,token,map);

        call.enqueue(new Callback<Common>() {
            @Override
            public void onResponse(Call<Common> call, Response<Common> response) {

                Common refreshinfo = response.body();
                if (refreshinfo.getReturn_code().equals("0")){


                        Intent intent = new Intent(mContext , UserMainActivity.class);
                        startActivity(intent);
                        finish();
                    SystemUtils.showToast(mContext,"暂停恢复成功", Gravity.CENTER );
                        mProgressDialog.dismiss(); //隐藏加载页面


                }else {
                    SystemUtils.showToast(mContext, refreshinfo.getReturn_message(), Gravity.CENTER );
                    mProgressDialog.dismiss();  //隐藏加载页面
                }
            }
            @Override
            public void onFailure(Call<Common> call, Throwable t) {
                SystemUtils.showToast(mContext,t.getMessage(), Gravity.CENTER );
                mProgressDialog.dismiss();   //隐藏加载页面
            }
        });
    }
}