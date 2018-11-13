package com.example.stn.stn.productorder;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.TextViewCompat;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.stn.stn.BaseActivity;
import com.example.stn.stn.R;
import com.example.stn.stn.UserMainActivity;
import com.example.stn.stn.adapter.MCommonAdapter;
import com.example.stn.stn.adapter.MViewHolder;
import com.example.stn.stn.bean.Common;
import com.example.stn.stn.cache.OrderList;
import com.example.stn.stn.cache.OrderListList;
import com.example.stn.stn.cache.UserInfo;
import com.example.stn.stn.fragment.UserLocationFragment;
import com.example.stn.stn.service.ServerApi;
import com.example.stn.stn.service.ServiceGenerator;
import com.example.stn.stn.utils.CustomDialog;
import com.example.stn.stn.utils.SystemUtils;
import com.litesuits.common.assist.Check;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class ProductOrderActivity extends BaseActivity  {

    protected FrameLayout flLocation;
    private Fragment userLocatonFragment;



    private ProgressDialog mProgressDialog;
    private ListView lv_productinfo;
    private ListView lv_productinfo2;
    private List<OrderList.OfferInfosBean> prodSubscriptionInfo;//本来订购的
    private List<OrderList.OfferInfosBean> prodSubscriptionInfoForShow;
    private List<OrderList.srvPkg> promSubscriptionInfo;//本来促销的
    private LinearLayout llNext;
    private MCommonAdapter orderAdapter;//第一个listview的adapter
    private HashMap<String, OrderListList.ProductLisBean> productChoose;//选择的产品

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_order);

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
//                            if(call != null){
//                                call.cancel();
//                                call = null;
//                            }
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

        productChoose = locationCache.getProductChoose();
        try {
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


        mProgressDialog.show();
        ServerApi serverApi = ServiceGenerator.createService(ServerApi.class);
        HashMap<String, String> map = new HashMap<>();
        map.put("subscriberId",locationCache.getUser().getSubscriberId());
        Call<OrderList> orderListCall = serverApi.orderQuery(application.getUUID(), locationCache.getTridId(), application.getToken(), map);

        orderListCall.enqueue(new Callback<OrderList>() {
            @Override
            public void onResponse(Call<OrderList> call, Response<OrderList> response) {
                mProgressDialog.dismiss();
                //
                OrderList body = response.body();
                if(body!=null&&body.getProdSubscriptionInfo()!=null){
                    prodSubscriptionInfo = body.getProdSubscriptionInfo();//多弄一个参数 把自己的放进去
                    //new list for show
                    prodSubscriptionInfoForShow = new ArrayList<OrderList.OfferInfosBean>();
                    for(OrderList.OfferInfosBean bean:prodSubscriptionInfo){
                        prodSubscriptionInfoForShow.add(bean);
                        bean.setEndDate(bean.getBusiExpireDate());//失效时间
                    }
                    orderAdapter = getOrderAdapter(prodSubscriptionInfoForShow);
                    lv_productinfo.setAdapter(orderAdapter);
                    setListViewHeightBasedOnChildren(lv_productinfo,ProductOrderActivity.this);

                }

                if(body!=null){

                    promSubscriptionInfo = body.getPromSubscriptionInfo();
                    if(promSubscriptionInfo == null){
                        promSubscriptionInfo = new ArrayList<OrderList.srvPkg>();
                    }
                    ListAdapter orderAdapter = getOrderAdapter2(promSubscriptionInfo);
                    lv_productinfo2.setAdapter(orderAdapter);
                    setListViewHeightBasedOnChildren(lv_productinfo2,ProductOrderActivity.this);
                }else{

                }
            }

            @Override
            public void onFailure(Call<OrderList> call, Throwable t) {
                //

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
            totalHeight += view.getMeasuredHeight();
        }
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
    public static int getScreenWidth(Context context){
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }

    private void initView() {

        initFragment();

        flLocation = (FrameLayout) findViewById(R.id.fl_location);
        flLocation.setFocusable(true);
        flLocation.setFocusableInTouchMode(true);
        flLocation.requestFocus();
        flLocation.requestFocusFromTouch();

        lv_productinfo = (ListView) findViewById(R.id.lv_productinfo);
        lv_productinfo2 = (ListView) findViewById(R.id.lv_productinfo2);
        llNext = (LinearLayout) findViewById(R.id.lv_next);

        llNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(mContext,ProductListActivity.class),111);
            }
        });
        //设置adapter 动态设计高度 //点击事件 修改数据 adapter。notification 动态设计高度


    }

    private void clickButton(){

        final SweetAlertDialog dialog = new SweetAlertDialog(mContext);
        dialog.setTitleText("提示");
        dialog.setContentText("是否确定产品变更");
        dialog.setConfirmText("确认");
        dialog.setCancelText("取消");
        dialog.show();

        //确认按钮点击事件
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                //产品订购方法

                HashMap<String, String> map = new HashMap<>();
                map.put("officeId",locationCache.getOrgInfoYourChoose().getOrgid());
                map.put("subscriberId",locationCache.getUser().getSubscriberId());
                StringBuffer sb = new StringBuffer();
                //三个列表 一个是本来订购的 一个是本来促销的 一个是选购的
                //1
                for(OrderList.OfferInfosBean bean:prodSubscriptionInfo){

                    boolean statusForProduct = bean.getStatusForProduct();
                    if(!statusForProduct){
                        sb.append(bean.getProductCode()+"|"+             //产品编码
                                bean.getProductId()+"|" +              //产品id
                                bean.getBusiValiDate()+"|"+            //产品生效时间
                                bean.getBusiExpireDate() +"|" +            //产品失效时间
                                "2"+":"                                    //操作类型 为false 那就是删除
                        );
                    }else{
                        //为 ture 判断失效时间有没有发生变化
                       if(!bean.getEndDate().equals(bean.getBusiExpireDate())){
                           //不相同
                           sb.append(bean.getProductCode()+"|"+             //产品编码
                                   bean.getProductId()+"|" +              //产品id
                                   bean.getBusiValiDate()+"|"+            //产品生效时间
                                   bean.getBusiExpireDate() +"|" +            //产品失效时间
                                   "3"+":"                                    //操作类型 为true 时间不相同 为修改
                           );
                       }
                    }

                }
                //2
                for(OrderList.srvPkg bean:promSubscriptionInfo){

                    boolean statusForProduct = bean.getStatusForProduct();
                    if(!statusForProduct){
                        sb.append(bean.getPromCode()+"|"+             //产品编码
                                bean.getPromId()+"|" +              //产品id
                                bean.getPromBusiValiDate()+"|"+            //产品生效时间
                                bean.getPromBusiExpireDate()+"|"  +            //产品失效时间
                                "2"+":"                                    //操作类型 为false 那就是删除
                        );
                    }

                }
                //3
                for(String key: productChoose.keySet()){
                    sb.append(productChoose.get(key).getProductCode()+"|"+             //产品编码
                            productChoose.get(key).getProductId()+"|" +              //产品id
                            productChoose.get(key).getActiveDate()+"|"+            //产品生效时间
                            productChoose.get(key).getInActiveDate()+"|"  +            //产品失效时间
                            "1"+":"                                    //操作类型 新增 1
                    );
                }
                if(sb.length()!= 0 ){
                    sb.deleteCharAt(sb.length()-1);
                }else{
                    SystemUtils.showToast(mContext,"未做任何操作");
                    return;
                }

                map.put("products",sb.toString());
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

    public void refresh(String uuid,String tridid,String token,HashMap<String,String> map){
        mProgressDialog.show();
        ServerApi serverApi = ServiceGenerator.createService(ServerApi.class);
        Call<Common> orderListCall = serverApi.productChange(application.getUUID(), locationCache.getTridId(), application.getToken(), map);
        orderListCall.enqueue(new Callback<Common>() {
            @Override
            public void onResponse(Call<Common> call, Response<Common> response) {
                mProgressDialog.dismiss();
                Common body = response.body();

                if(body.getReturn_code().equals("0")){
                    final SweetAlertDialog dialog = new SweetAlertDialog(mContext);
                    dialog.setTitleText("提示");
                    dialog.setContentText("产品变更成功");
                    dialog.setConfirmText("确认");
                    dialog.setCancelable(false);
                    dialog.show();
                    dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            finish();
                        }
                    });
                }else{
                    SystemUtils.showToast(mContext,body.getReturn_message());
                }
            }

            @Override
            public void onFailure(Call<Common> call, Throwable t) {
                mProgressDialog.dismiss();
            }
        });



    }

    /**
     * 获取订购信息的adapter
     * @return
     */
    private MCommonAdapter getOrderAdapter(final List<OrderList.OfferInfosBean> orderName ) {
        final MCommonAdapter orderAdapter = new MCommonAdapter<OrderList.OfferInfosBean>(mContext, orderName,
                R.layout.item_listview_productorder) {
            @Override
            public void convert(MViewHolder helper, final OrderList.OfferInfosBean item, final int position) {
                TextView tvPackageName = helper.getView(R.id.tv_product_name);
                TextView tvPackageTime = helper.getView(R.id.tv_product_time);
                tvPackageTime.setText(item.getBusiValiDate()+"  至  "+item.getBusiExpireDate());
                final CheckBox cbState = helper.getView(R.id.cb_productorder);
                if(item.getStatusForProduct()){
                    //
                    cbState.setChecked(true);
                }else{
                    cbState.setChecked(false);
                }
                LinearLayout productLL = helper.getView(R.id.ll_productorder);
                LinearLayout changeTime = helper.getView(R.id.lv_next);
                // tvPackageName.setText();
                tvPackageName.setText(item.getProductName());//名字
                //tvPackageTime.setText();//根据时间设置。
//                changeTime.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        SystemUtils.showToast(mContext,"点击了修改时间");
//                        //保存一下修改时间。
//                    }
//                });
                changeTime.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {

                            CustomDialog.Builder builder1 = new CustomDialog.Builder(ProductOrderActivity.this);
                            View view = View.inflate(ProductOrderActivity.this, R.layout.time_choose, null);   //加载时间选择器布局文件
                            final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);

                            builder1.setContentView(view);

                            Calendar cal = Calendar.getInstance();
                            cal.setTimeInMillis(System.currentTimeMillis());
//                datePicker.setMaxDate(System.currentTimeMillis());
//                datePicker.setMinDate(System.currentTimeMillis()-24*60*60*1000*7);
                            datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), null);

                            builder1.setTitle("开始日期");
                            builder1.setPositiveButton("确  定", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    StringBuffer sb1 = new StringBuffer();
                                    sb1.append(String.format("%d-%02d-%02d",
                                            datePicker.getYear(),
                                            datePicker.getMonth() + 1,
                                            datePicker.getDayOfMonth()));
                                    sb1.append("");
                                    String time = sb1.toString();
                                    String productId = item.getProductId();
                                    if(productChoose.containsKey(productId)){
                                        //如果包含这个id 删除
                                        prodSubscriptionInfoForShow.get(position).setBusiExpireDate(time);
                                        OrderListList.ProductLisBean productLisBean = productChoose.get(productId);
                                        productLisBean.setInActiveDate(time);
                                        productChoose.put(productLisBean.getProductId(),productLisBean);
                                        locationCache.setProductChoose(productChoose);
                                    }else{
                                        prodSubscriptionInfoForShow.get(position).setBusiExpireDate(time);
                                        //不包含这个id 修改状态
                                        prodSubscriptionInfo.get(position).setBusiExpireDate(time);
                                    }
                                    notifyDataSetChanged();
                                    dialog.cancel();

                                }
                            });
                            builder1.setNegativeButton("取 消", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            Dialog dialog = builder1.create();
                            dialog.show();


                        }
                        return true;
                    }
                });
//                cbState.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                });
//                cbState.setClickable(false);
                cbState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Log.i("dongdong","按钮变化了"+position);

                        boolean checked = cbState.isChecked();

                            String productId = item.getProductId();
                            if(productChoose.containsKey(productId)){

                                if(position >=prodSubscriptionInfo.size()){
                                    //如果包含这个id 删除 
                                    prodSubscriptionInfoForShow.remove(position);
                                    productChoose.remove(productId);
                                    notifyDataSetChanged();
                                    setListViewHeightBasedOnChildren(lv_productinfo,ProductOrderActivity.this);
                                }else{
                                    //不包含这个id 修改状态
                                    prodSubscriptionInfo.get(position).setStatusforproduct(cbState.isChecked());
                                }

                            }else{
                                //不包含这个id 修改状态
                                prodSubscriptionInfo.get(position).setStatusforproduct(cbState.isChecked());
                            }

                    }
                });
                productLL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cbState.setChecked(!cbState.isChecked());
//                        //状态发生了修改
//                       // item.setStatusforproduct(cbState.isChecked());
//                        String productId = item.getProductId();
//
//
//                        if(productChoose.containsKey(productId)){
//                            //如果包含这个id 删除
//                            prodSubscriptionInfoForShow.remove(position);
//                            productChoose.remove(productId);
//                            notifyDataSetChanged();
//                            setListViewHeightBasedOnChildren(lv_productinfo,ProductOrderActivity.this);
//                        }else{
//                            //不包含这个id 修改状态
//                            prodSubscriptionInfo.get(position).setStatusforproduct(cbState.isChecked());
//                        }

                    }
                });



            }
        };
        return orderAdapter;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        productChoose = locationCache.getProductChoose();

        prodSubscriptionInfoForShow.clear();
        for(OrderList.OfferInfosBean bean:prodSubscriptionInfo){
            prodSubscriptionInfoForShow.add(bean);
        }
        //数据放进去咯。
        for(String key: productChoose.keySet()){
            OrderListList.ProductLisBean bean = productChoose.get(key);
            OrderList.OfferInfosBean offerInfosBean = new OrderList.OfferInfosBean();
            offerInfosBean.setBusiExpireDate(bean.getInActiveDate());//失效
            offerInfosBean.setBusiValiDate(bean.getActiveDate());//生效
            offerInfosBean.setProductId(bean.getProductId());//id
            offerInfosBean.setProductCode(bean.getProductCode());//code
            offerInfosBean.setProductName(bean.getProductName());
            prodSubscriptionInfoForShow.add(offerInfosBean);
        }

        //数据改变
        orderAdapter.notifyDataSetChanged();
        //返回 重新计算高度
        setListViewHeightBasedOnChildren(lv_productinfo,mContext);

    }

    /**
     * 获取订购信息的adapter
     * @return
     * @param orderName
     */
    private ListAdapter getOrderAdapter2(final List<OrderList.srvPkg> orderName ) {
        ListAdapter orderAdapter = new MCommonAdapter<OrderList.srvPkg>(mContext, orderName,
                R.layout.item_listview_productorder2) {
            @Override
            public void convert(MViewHolder helper, OrderList.srvPkg item, final int position) {
                TextView tvPackageName = helper.getView(R.id.tv_product_name);
                TextView tvPackageTime = helper.getView(R.id.tv_product_time);
                final CheckBox cbState = helper.getView(R.id.cb_productorder);
                LinearLayout productLL = helper.getView(R.id.ll_productorder);
                tvPackageTime.setText(item.getPromBusiValiDate()+"  至  "+item.getPromBusiExpireDate());
                tvPackageName.setText(item.getPromName());//名字
                cbState.setChecked(item.getStatusForProduct());
                cbState.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                cbState.setClickable(false);
//                cbState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        cbState.setChecked(true);
//                    }
//                });
//                productLL.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        cbState.setChecked(!cbState.isChecked());
//                        //设置一下状态
//                        promSubscriptionInfo.get(position).setStatusForProduct(cbState.isChecked());
//                    }
//                });

            }
        };
        return orderAdapter;
    }


}