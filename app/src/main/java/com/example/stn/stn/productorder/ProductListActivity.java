package com.example.stn.stn.productorder;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.stn.stn.BaseActivity;
import com.example.stn.stn.R;
import com.example.stn.stn.adapter.MCommonAdapter;
import com.example.stn.stn.adapter.MViewHolder;
import com.example.stn.stn.bean.PayBean;
import com.example.stn.stn.cache.OrderList;
import com.example.stn.stn.cache.OrderListList;
import com.example.stn.stn.service.ServerApi;
import com.example.stn.stn.service.ServiceGenerator;
import com.example.stn.stn.utils.SystemUtils;
import com.example.stn.stn.views.RefreshableListView;
import com.example.stn.stn.views.SearchView;
import com.example.stn.stn.views.TopBarView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 冬冬 on 2016/8/16.
 */
public class ProductListActivity extends BaseActivity {


    private RefreshableListView listview;
    private ProgressDialog mProgressDialog;
    //private MCommonAdapter orderAdapter;//apapter
    private MyAdapter orderAdapter;//apapter
    private List<OrderListList.ProductLisBean> productLis = new ArrayList<>();//adapter中的list
    private List<OrderListList.ProductLisBean> productLisAll = new ArrayList<>();//总List
    private HashMap<String,OrderListList.ProductLisBean> productChoose;//前面id
    private String s1;//输入的字符串
    private TopBarView topBar;
    private MyAdapter myAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productlist);

        productChoose = locationCache.getProductChoose();
        initView();
        initData();
        new HashMap<String,String>();

    }

    private void initView() {

        SearchView viewById = (SearchView) findViewById(R.id.sv_search);
        viewById.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //输入之后 做一个检索
                s1 = s.toString();
                s1 = s1.toUpperCase();
                productLis.clear();
                filter();
            }
        });
        listview = (RefreshableListView) findViewById(R.id.lv_productlist);
        listview.setEnables(false, true);
        listview.setOnRefreshListener(new RefreshableListView.OnRefreshListener() {
            @Override
            public void onRefreshing() {
                //下拉
            }
            @Override
            public void onLoading() {
                //上拉
                //SystemUtils.showToast(mContext,"上拉刷新一次");

                getMoreProduct();


            }
        });

        topBar = (TopBarView) findViewById(R.id.tbv_productorder);
        topBar.setOnRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击了右边的
                locationCache.setProductChoose(productChoose);
                finish();

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
        mProgressDialog.show();
        ServerApi serverApi = ServiceGenerator.createService(ServerApi.class);
        HashMap<String, String> map = new HashMap<>();
        map.put("subscriberCode",locationCache.getUser().getSubscriberCode());
        map.put("officeId",locationCache.getOrgInfoYourChoose().getOrgid());
        map.put("queryIndex","15");
        Call<OrderListList> orderListListCall = serverApi.orderQueryList(application.getUUID(), locationCache.getTridId(), application.getToken(), map);

        orderListListCall.enqueue(new Callback<OrderListList>() {
            @Override
            public void onResponse(Call<OrderListList> call, Response<OrderListList> response) {
                OrderListList body = response.body();
                mProgressDialog.dismiss();
                if(body!=null && body.getProductLis()!=null){
                    productLisAll.addAll(body.getProductLis());
                    filter();
                    //获取Adapter 设置
                   // orderAdapter = getOrderAdapter(productLis);
                    orderAdapter = new MyAdapter();
                    listview.setAdapter(orderAdapter);
                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                           // SystemUtils.showToast(mContext,"position="+position);
                            CheckBox viewById = (CheckBox) view.findViewById(R.id.cb_product_prom);
                            if (viewById.isChecked()) {
                                viewById.setChecked(false);
                            } else {
                                viewById.setChecked(true);
                            }
                            boolean checked = viewById.isChecked();
                            //根据position 和check的状态 把选择的套餐保存起来，或删除。
                            if(checked){
                                //为true 放进去
                                String productId = productLis.get(position - 1).getProductId();
                                if(productChoose.containsKey(productId)){
                                    //包含
                                }else{
                                    //不包含
                                    productChoose.put(productLis.get(position-1).getProductId(),productLis.get(position-1));
                                }
                            }else{
                                //为false
                                try{
                                    productChoose.remove(productLis.get(position-1).getProductId());
                                }catch(Exception t){
                                }

                            }


                        }
                    });
                }else{
                    SystemUtils.showToast(mContext,body.getReturn_message());
                }

            }

            @Override
            public void onFailure(Call<OrderListList> call, Throwable t) {

            }
        });


    }

    private int num = 1;
    private void getMoreProduct(){

        mProgressDialog.show();
        ServerApi serverApi = ServiceGenerator.createService(ServerApi.class);
        HashMap<String, String> map = new HashMap<>();
        map.put("subscriberCode",locationCache.getUser().getSubscriberCode());
        map.put("officeId",locationCache.getOrgInfoYourChoose().getOrgid());
        map.put("queryIndex",(num++)*15+"");
        Call<OrderListList> orderListListCall = serverApi.orderQueryList(application.getUUID(), locationCache.getTridId(), application.getToken(), map);
        orderListListCall.enqueue(new Callback<OrderListList>() {
            @Override
            public void onResponse(Call<OrderListList> call, Response<OrderListList> response) {
                OrderListList body = response.body();
                mProgressDialog.dismiss();
                if(body!=null &&body.getProductLis()!=null&&body.getProductLis().size()!=0){
                    List<OrderListList.ProductLisBean> productLismore = body.getProductLis();
                    productLisAll.addAll(productLismore);
                    filter();
                    //新的数据 加进去

                    //更新数据
                }else{
                    SystemUtils.showToast(mContext,body.getReturn_message());
                    listview.onRefreshComplete();
                }
                listview.onRefreshComplete();

            }

            @Override
            public void onFailure(Call<OrderListList> call, Throwable t) {

            }
        });
        listview.onRefreshComplete();
    }

    //输入后 调用一次 下拉时 调用一次
    private void filter(){

        try{
            if(TextUtils.isEmpty(s1)){
                productLis.addAll(productLisAll);
            }else{
                //不是空的
                for(OrderListList.ProductLisBean bean:productLisAll){
                    if(bean.getProdNameShortSpell().contains(s1)){
                        //过滤一下
                        productLis.add(bean);
                    }
                }
            }
            orderAdapter.notifyDataSetChanged();
            listview.onRefreshComplete();
        }catch(Exception t){

        }

    }
    /**
     * 获取订购信息的adapter
     * @return
     */
    private MCommonAdapter getOrderAdapter(final List<OrderListList.ProductLisBean> orderName ) {
        MCommonAdapter orderAdapter = new MCommonAdapter<OrderListList.ProductLisBean>(mContext, orderName,
                R.layout.item_listview_productorder3) {
            @Override
            public void convert(MViewHolder helper, OrderListList.ProductLisBean item, int position) {
                TextView tvPackageName = helper.getView(R.id.tv_product_name);
                final CheckBox cbState = helper.getView(R.id.cb_product_prom);
                LinearLayout productLL = helper.getView(R.id.ll_productchoose);
                tvPackageName.setText(item.getProductName());//名字
                if(productChoose.containsKey(item.getProductId())){
                    cbState.setChecked(true);
                }else{
                    cbState.setChecked(false);
                }
//                productLL.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        cbState.setChecked(!cbState.isChecked());
//                    }
//                });

            }
        };
        return orderAdapter;
    }
    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return productLis.size();
        }

        @Override
        public OrderListList.ProductLisBean getItem(int position) {
            return productLis.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_listview_productorder3, parent, false);
            }
            TextView tvPackageName = (TextView) convertView.findViewById(R.id.tv_product_name);
            final CheckBox cbState = (CheckBox) convertView.findViewById(R.id.cb_product_prom);
            tvPackageName.setText(getItem(position).getProductName());//名字
            if(productChoose.containsKey(getItem(position).getProductId())){
                cbState.setChecked(true);
            }else{
                cbState.setChecked(false);
            }
            cbState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            cbState.setFocusable(false);
            cbState.setClickable(false);
            return convertView;

        }
    }
}