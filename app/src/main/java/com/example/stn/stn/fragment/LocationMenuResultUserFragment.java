package com.example.stn.stn.fragment;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.stn.stn.R;
import com.example.stn.stn.UserMainActivity;
import com.example.stn.stn.adapter.MCommonAdapter;
import com.example.stn.stn.adapter.MViewHolder;
import com.example.stn.stn.cache.OrderList;
import com.example.stn.stn.cache.UserInfo;
import com.example.stn.stn.querycustomer.LocationPackageDetailActivity;
import com.example.stn.stn.service.ServerApi;
import com.example.stn.stn.service.ServiceGenerator;
import com.example.stn.stn.utils.SystemUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Name: LocationMenuResultUserFragment
 * Author: xulong
 * Comment: //TODO
 * Date: 2016-08-19 17:33.
 */
public class LocationMenuResultUserFragment extends BaseFragment implements View.OnClickListener {

    protected ListView lvOrderInfo;
    protected Button btnNext;
    private View rootView;
    private String prodInstId; //用户证号
    //private List<UserInfo.ProdInfosBean.TerminalsBean> listTerminals; //资源信息数据源
    //private List<UserInfo.ProdInfosBean.ProdUnOrder> prodUnOrderList; //未办结订单
    private List<OrderList.OfferInfosBean> orderName; //订购信息名称数据源

    private List<UserInfo.ProdInfosBean> mMenuUserData;
    private boolean mHandledPress = false;
    private int position;  //所选具体用户



    //用来回调回退栈监听
    protected BackHandlerInterface backHandlerInterface;
    private ListView lv_not_buniess_list;
    private ProgressDialog mProgressDialog;
    private TextView zhinengkaNum;
    private TextView jidingheNum;
    private UserInfo.ProdInfosBean userMyChoose;
    private TextView tv_pause_plan2;
    private TextView tv_jidinghecard;
    private TextView tv_jidinghecard2;
    private TextView tv_pause_plan;
    private TextView tv_login_name;
    private List<OrderList.OfferInfosBean> prodSubscriptionInfo;
    private List<OrderList.srvPkg> promSubscriptionInfo;

    public interface BackHandlerInterface {
        public void setSelectedFragment(LocationMenuResultUserFragment backHandledFragment);

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(getActivity() instanceof BackHandlerInterface)) {
                 throw new ClassCastException("Hosting activity must implement BackHandlerInterface");
             } else {
                backHandlerInterface = (BackHandlerInterface) getActivity();
             }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_location_result_user, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.position = 0;
        //默认加载初始数据

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


        mMenuUserData = locationCache.getUserInfo().getProdInfos();

        if (mMenuUserData == null ) {


            getActivity().finish();
            return;
        }
        //用户证号
        prodInstId = String.valueOf(mMenuUserData.get(0).getSubscriberId());

        initData();
        initView(rootView);
        HashMap<String, String> map = new HashMap<>();
        map.put("subscriberId",prodInstId);

        orderQuery(application.getUUID(), locationCache.getTridId(), application.getToken(),map);
       mProgressDialog.show();
    }


    @Override
    public void onStart() {
        super.onStart();
        backHandlerInterface.setSelectedFragment(this);
    }


    private void initData() {


        //userMyChoose = locationCache.getUserMyChoose();


            userMyChoose = locationCache.getUserInfo().getProdInfos().get(0);
            locationCache.setUserMyChoose(locationCache.getUserInfo().getProdInfos().get(0));

//        this.position = 0;
//        //默认加载初始数据
//        mMenuUserData = locationCache.getUserInfo().getProdInfos();
//        if (mMenuUserData == null ) {
//            Intent intent = new Intent(mContext, LocationDetailActivity.class);
//            startActivity(intent);
//            getActivity().finish();
//            return;
//        }
//        //用户证号
//        prodInstId = String.valueOf(mMenuUserData.get(0).getProdInstId());
//        listTerminals = mMenuUserData.get(0).getTerminals();

    }

    private void initView(View rootView) {

        zhinengkaNum = (TextView) rootView.findViewById(R.id.tv_zhinengkanum);
        jidingheNum = (TextView) rootView.findViewById(R.id.tv_jidingheNum);
        tv_jidinghecard = (TextView) rootView.findViewById(R.id.tv_jidinghecard);
        tv_jidinghecard2 = (TextView) rootView.findViewById(R.id.tv_jidinghecard2);


        tv_pause_plan2 = (TextView) rootView.findViewById(R.id.tv_pause_plan2);
        tv_pause_plan = (TextView) rootView.findViewById(R.id.tv_pause_plan);//营销计划
        tv_login_name = (TextView) rootView.findViewById(R.id.tv_login_name);//营销计划

        tv_login_name.setText(userMyChoose.getbLoginName());

        String state = SystemUtils.getState(userMyChoose.getSubscriberStatus());

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
        lvOrderInfo = (ListView) rootView.findViewById(R.id.lv_order_info);

        //未办结订单 可以
        lv_not_buniess_list = (ListView) rootView.findViewById(R.id.lv_not_buniess_list);

        btnNext = (Button) rootView.findViewById(R.id.btn_next);

        btnNext.setOnClickListener(LocationMenuResultUserFragment.this);

        //订单信息的单击事件
        lvOrderInfo.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        lvOrderInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), LocationPackageDetailActivity.class);
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
        //订单信息的单击事件
        lv_not_buniess_list.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        lv_not_buniess_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), LocationPackageDetailActivity.class);
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
     * 获取资源信息的adapter
     * @return
     */
    private ListAdapter getprodUnOrderAdapter(final List<UserInfo.ProdInfosBean.ProdUnOrder> list) {
        ListAdapter resourceAdapter = new MCommonAdapter<UserInfo.ProdInfosBean.ProdUnOrder>(mContext, list,
                R.layout.item_listview_location_resource_info2) {
            @Override
            public void convert(MViewHolder helper, UserInfo.ProdInfosBean.ProdUnOrder item, int position) {
                //暂时不显示
//                TextView tvResourceType = helper.getView(R.id.tv_resource_type);
//                tvResourceType.setText(list.get(position).getResTypeName());
                //客户订单编号
                TextView tvResourceCode = helper.getView(R.id.tv_resource_code);
                tvResourceCode.setText(list.get(position).getCustOrderId());
                //业务类型
                TextView tvResourceNum = helper.getView(R.id.tv_resource_num);
                tvResourceNum.setText(list.get(position).getBusinessName());
            }
        };
        return resourceAdapter;
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_next) {


            //保存获取的订购信息 和促销信息


            locationCache.setProdSubscriptionInfo(prodSubscriptionInfo);
            locationCache.setPromSubscriptionInfo(promSubscriptionInfo);

            locationCache.setUserCode(prodInstId);
            locationCache.setUser(mMenuUserData.get(position));

            startActivity(new Intent(mContext, UserMainActivity.class));


        }
    }



    /**
     * 订购信息查询
     */
    private void
    orderQuery(String uuid, String tridId, String token, HashMap<String,String> map) {
        mProgressDialog.show();
        ServerApi serverApi = ServiceGenerator.createService(ServerApi.class);
        Call<OrderList> call = serverApi.orderQuery(uuid,tridId,token,map);
        call.enqueue(new Callback<OrderList>() {
            @Override
            public void onResponse(Call<OrderList> call, Response<OrderList> response) {

                try{
                    mProgressDialog.dismiss();
                    OrderList orderList = response.body();
                    if (orderList.getReturn_code().equals("0")) {
                        //locationCache.setOrderList(orderList);
                        //orderName = orderList.getOfferInfos();
                        //设置Adapter
                        //dongdong
                        prodSubscriptionInfo = orderList.getProdSubscriptionInfo();
                        promSubscriptionInfo = orderList.getPromSubscriptionInfo();

                        if(!(prodSubscriptionInfo == null)){
                            ListAdapter orderAdapter = getOrderAdapter(prodSubscriptionInfo);
                            lvOrderInfo.setAdapter(orderAdapter);
                            setListViewHeightBasedOnChildren(lvOrderInfo,mContext);
                        }else{
                            ListAdapter orderAdapter = getOrderAdapter(new ArrayList<OrderList.OfferInfosBean>());
                            lvOrderInfo.setAdapter(orderAdapter);
                            setListViewHeightBasedOnChildren(lvOrderInfo,mContext);
                        }

                        if(!(promSubscriptionInfo == null)){
                            ListAdapter orderAdapter2 = getOrderAdapter2(promSubscriptionInfo);
                            lv_not_buniess_list.setAdapter(orderAdapter2);
                            setListViewHeightBasedOnChildren(lv_not_buniess_list,mContext);
                        }else{
                            ListAdapter orderAdapter2 = getOrderAdapter2(new ArrayList< OrderList.srvPkg >());
                            lv_not_buniess_list.setAdapter(orderAdapter2);
                            setListViewHeightBasedOnChildren(lv_not_buniess_list,mContext);
                        }
                        mProgressDialog.dismiss();
                    } else {

                        SystemUtils.showToast(mContext,orderList.getReturn_message());
                        mProgressDialog.dismiss();

                    }
                }catch(Exception t){

                }

            }

            @Override
            public void onFailure(Call<OrderList> call, Throwable t) {

                mProgressDialog.dismiss();
                SystemUtils.showToast(mContext,t.getMessage());

            }
        });


    }


    //dongdong
    //回调方法(LocationMainActivity里)根据用户定位左边选择的用户更新用户定位右边Fragment的内容
    public void initText (List<UserInfo.ProdInfosBean.TerminalsBean> listTerminals, String prodInstId, int position,
                          List<UserInfo.ProdInfosBean.ProdUnOrder> prodUnOrderList) {
       // this.listTerminals = listTerminals;
       // this.prodUnOrderList = prodUnOrderList;
        this.position = position;
        userMyChoose = locationCache.getUserMyChoose();
        String state = SystemUtils.getState(userMyChoose.getSubscriberStatus());
        tv_pause_plan2.setText(state);
        locationCache.setState(state);
        tv_pause_plan.setText(userMyChoose.getPlanName());
        zhinengkaNum.setText(userMyChoose.getCardNo());
        jidingheNum.setText(userMyChoose.getResNo());

        tv_jidinghecard.setText(userMyChoose.getResType());
        tv_jidinghecard2.setText(userMyChoose.getCardType());
        //dongdng
        HashMap<String, String> map = new HashMap<>();
        map.put("subscriberId",locationCache.getUserMyChoose().getSubscriberId());
        //订购信息查询接口
        orderQuery(application.getUUID(), locationCache.getTridId(), application.getToken(),map);
        mProgressDialog.show();
    }



    //回退监听事件
    public boolean onBackPressed () {
        if (!mHandledPress) {
            getFragmentManager().popBackStack("menuUserFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            mHandledPress = true;
            return true;
        }
        return false;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!(orderName == null)) {
            lvOrderInfo.setAdapter(getOrderAdapter(orderName));
        }



        //dongdong
//        //设置listview
//        if (!(prodUnOrderList == null)) {
//            lv_not_buniess_list.setVisibility(View.VISIBLE);
//            lv_not_buniess_list.setAdapter(getprodUnOrderAdapter(prodUnOrderList));
//        }else{
//            //为null
//            lv_not_buniess_list.setVisibility(View.GONE);
//        }
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
