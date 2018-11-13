package com.example.stn.stn.accountinfo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stn.stn.BaseActivity;
import com.example.stn.stn.R;
import com.example.stn.stn.adapter.MCommonAdapter;
import com.example.stn.stn.adapter.MViewHolder;
import com.example.stn.stn.bean.AccountUeryBean;
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
public class AccountInfActivity extends BaseActivity {

    protected FrameLayout flLocation;
    private Fragment userLocatonFragment;



    private Call<AccountUeryBean> call;
    private ProgressDialog mProgressDialog;
    private ListView listview;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountinfo);

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
        //initView();
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
        getData();


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

        listview = (ListView) findViewById(R.id.lv_accountinfos);

        MCommonAdapter<AccountUeryBean.AcctInfoBean.BookBean> mCommonAdapter = new MCommonAdapter<AccountUeryBean.AcctInfoBean.BookBean>(mContext, refreshinfo.getAcctInfo().get(0).getBooks(), R.layout.item_listview_accouninfo) {
            @Override
            public void convert(MViewHolder helper, AccountUeryBean.AcctInfoBean.BookBean item, int position) {

                TextView tvAccount01 = helper.getView(R.id.tv_item_account01);
                TextView tvAccount02 = helper.getView(R.id.tv_item_account02);
                TextView tvAccount03 = helper.getView(R.id.tv_item_account03);
                TextView tvAccount04 = helper.getView(R.id.tv_item_account04);
                TextView tvAccount05 = helper.getView(R.id.tv_item_account05);
                //账本科目
                tvAccount01.setText(item.getItemName());
                //账本金额
                tvAccount02.setText(item.getAmount());
                //未销账金额
                tvAccount03.setText(item.getPayAmount());
                //当月累计金额
                tvAccount04.setText(item.getFeeCycleTotal());
                //账户余额
                tvAccount05.setText(item.getFeeTotal());


            }
        };

        listview.setAdapter(mCommonAdapter);
        setListViewHeightBasedOnChildren(listview,mContext);



    }

    private void getData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("objectId",locationCache.getCustomer().getCustomerId());
        map.put("searchType","1");
        //缴费查询
        accountquery(application.getUUID(), locationCache.getTridId(), application.getToken(), map);


    }

    private AccountUeryBean refreshinfo;
    /**
     * 查询账本信息
     *
     * @param uuid
     * @param tridId
     * @param token
     */
    public void accountquery(String uuid, String tridId, String token, HashMap<String, String> map) {

        mProgressDialog.show();  //显示加载页面

        ServerApi serverApi = ServiceGenerator.createService(ServerApi.class);
        call = serverApi.accountQuery(uuid, tridId, token, map);
        call.enqueue(new Callback<AccountUeryBean>() {
            @Override
            public void onResponse(Call<AccountUeryBean> call, Response<AccountUeryBean> response) {

                refreshinfo = response.body();
                if (refreshinfo.getReturn_code().equals("0")) {
                    mProgressDialog.dismiss(); //隐藏加载页面
                    //设置listview
                    initView();
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

    //动态设置listview高度

    public static void setListViewHeightBasedOnChildren(ListView listView,Context m) {
        ListAdapter listAdapter = listView.getAdapter();
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        View view;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, null, listView);
            //宽度为屏幕宽度
            int i1 = View.MeasureSpec.makeMeasureSpec(getScreenWidth(m),
                    View.MeasureSpec.EXACTLY);
            //根据屏幕宽度计算高度
            int i2 = View.MeasureSpec.makeMeasureSpec(i1, View.MeasureSpec.UNSPECIFIED);
            view.measure(i1, i2);
            totalHeight += view.getMeasuredHeight()+20;
        }
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1))+50;
        listView.setLayoutParams(params);
    }
    public static int getScreenWidth(Context context){
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }


}