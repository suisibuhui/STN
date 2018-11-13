package com.example.stn.stn.customeranduserinfo;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.stn.stn.BaseActivity;
import com.example.stn.stn.R;
import com.example.stn.stn.adapter.MCommonAdapter;
import com.example.stn.stn.adapter.MViewHolder;
import com.example.stn.stn.bean.CustomerInfo;
import com.example.stn.stn.cache.OrderList;
import com.example.stn.stn.cache.UserInfo;
import com.example.stn.stn.fragment.LocationMenuResultUserFragment;
import com.example.stn.stn.querycustomer.LocationPackageDetailActivity;
import com.example.stn.stn.utils.SystemUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Name: CustomerOlderActivity
 * Author: xulong
 * Comment: //已有客户：预约新装第一个页面 【这个页面要调：m039业务校验接口】
 * Date: 2016-08-09 18:04.
 */
public class UserInfoActivity extends BaseActivity implements View.OnClickListener {


    private TextView zhinengkaNum;
    private TextView jidingheNum;
    private TextView tv_jidinghecard;
    private TextView tv_jidinghecard2;
    private TextView tv_pause_plan2;
    private TextView tv_pause_plan;
    private ListView lvOrderInfo;
    private ListView lv_not_buniess_list;
    private Button btnNext;
    private List<OrderList.OfferInfosBean> prodSubscriptionInfo;
    private List<OrderList.srvPkg> promSubscriptionInfo;
    private UserInfo.ProdInfosBean userMyChoose;
    private TextView tv_login_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_userinfo_user);
        initData();
        initView();

    }


    private void initData() {

        userMyChoose = locationCache.getUser();
        prodSubscriptionInfo = locationCache.getProdSubscriptionInfo();
        promSubscriptionInfo = locationCache.getPromSubscriptionInfo();
    }

    private void initView() {
        zhinengkaNum = (TextView)findViewById(R.id.tv_zhinengkanum);
        jidingheNum = (TextView) findViewById(R.id.tv_jidingheNum);
        tv_jidinghecard = (TextView)findViewById(R.id.tv_jidinghecard);
        tv_jidinghecard2 = (TextView)findViewById(R.id.tv_jidinghecard2);


        tv_pause_plan2 = (TextView)findViewById(R.id.tv_pause_plan2);
        tv_pause_plan = (TextView) findViewById(R.id.tv_pause_plan);//营销计划
        String state = SystemUtils.getState(userMyChoose.getSubscriberStatus());

        tv_login_name = (TextView)findViewById(R.id.tv_login_name);//营销计划

        tv_login_name.setText(userMyChoose.getbLoginName());
        //dongdong
        //tv_pause_plan2.setText(userMyChoose.get);
        //智能卡 和 机顶盒

        tv_pause_plan2.setText(state);
        locationCache.setState(state);
        tv_pause_plan.setText(userMyChoose.getPlanName());
        zhinengkaNum.setText(userMyChoose.getCardNo());
        jidingheNum.setText(userMyChoose.getResNo());

        tv_jidinghecard.setText(userMyChoose.getResType());
        tv_jidinghecard2.setText(userMyChoose.getCardType());

        //可以
        lvOrderInfo = (ListView)findViewById(R.id.lv_order_info);

        //未办结订单 可以
        lv_not_buniess_list = (ListView)findViewById(R.id.lv_not_buniess_list);

        btnNext = (Button)findViewById(R.id.btn_next);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //订单信息的单击事件
        lvOrderInfo.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        lvOrderInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, LocationPackageDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("from","0");
                bundle.putString("packageName",prodSubscriptionInfo.get(position).getProductName());
                bundle.putString("effectDate", String.valueOf(prodSubscriptionInfo.get(position).getBusiValiDate()));
                bundle.putString("predictUseDate",String.valueOf(prodSubscriptionInfo.get(position).getBusiExpireDate()) );
                bundle.putString("status",String.valueOf(prodSubscriptionInfo.get(position).getStatus()));
                bundle.putString("machineStatus", prodSubscriptionInfo.get(position).getStopStatus());
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
        if(!(promSubscriptionInfo == null)){
            ListAdapter orderAdapter2 = getOrderAdapter2(promSubscriptionInfo);
            lv_not_buniess_list.setAdapter(orderAdapter2);
            setListViewHeightBasedOnChildren(lv_not_buniess_list,mContext);
        }else{
            ListAdapter orderAdapter2 = getOrderAdapter2(new ArrayList< OrderList.srvPkg >());
            lv_not_buniess_list.setAdapter(orderAdapter2);
            setListViewHeightBasedOnChildren(lv_not_buniess_list,mContext);
        }
        if(!(prodSubscriptionInfo == null)){
            ListAdapter orderAdapter = getOrderAdapter(prodSubscriptionInfo);
            lvOrderInfo.setAdapter(orderAdapter);
            setListViewHeightBasedOnChildren(lvOrderInfo,mContext);
        }else{
            ListAdapter orderAdapter = getOrderAdapter(new ArrayList<OrderList.OfferInfosBean>());
            lvOrderInfo.setAdapter(orderAdapter);
            setListViewHeightBasedOnChildren(lvOrderInfo,mContext);
        }
        //订单信息的单击事件
        lv_not_buniess_list.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        lv_not_buniess_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, LocationPackageDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("from","1");
                bundle.putString("packageName",promSubscriptionInfo.get(position).getPromName());
                bundle.putString("effectDate", String.valueOf(promSubscriptionInfo.get(position).getPromBusiValiDate()));
                bundle.putString("predictUseDate",String.valueOf(promSubscriptionInfo.get(position).getPromBusiExpireDate()) );
//                bundle.putString("status",String.valueOf(prodSubscriptionInfo.get(position).getStatus()));
//                bundle.putString("machineStatus", prodSubscriptionInfo.get(position).getStopStatus());
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

    }


    @Override
    public void onClick(View v) {

    }
    /**
     * 获取订购信息的adapter
     * @return
     */
    private ListAdapter getOrderAdapter2(final List<OrderList.srvPkg> orderName ) {
        ListAdapter orderAdapter = new MCommonAdapter<OrderList.srvPkg>(mContext, orderName,
                R.layout.item_listview_location_order_info) {
            @Override
            public void convert(MViewHolder helper, OrderList.srvPkg item, int position) {
                TextView tvPackageName = helper.getView(R.id.tv_package_name);
                // tvPackageName.setText();
                tvPackageName.setText(item.getPromName());
            }
        };
        return orderAdapter;
    }
    /**
     * 获取订购信息的adapter
     * @return
     */
    private ListAdapter getOrderAdapter(final List<OrderList.OfferInfosBean> orderName ) {
        ListAdapter orderAdapter = new MCommonAdapter<OrderList.OfferInfosBean>(mContext, orderName,
                R.layout.item_listview_location_order_info) {
            @Override
            public void convert(MViewHolder helper, OrderList.OfferInfosBean item, int position) {
                TextView tvPackageName = helper.getView(R.id.tv_package_name);
                // tvPackageName.setText();
                tvPackageName.setText(item.getProductName());
            }
        };
        return orderAdapter;
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
