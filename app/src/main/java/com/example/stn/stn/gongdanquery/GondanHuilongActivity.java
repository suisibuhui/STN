package com.example.stn.stn.gongdanquery;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.stn.stn.BaseActivity;
import com.example.stn.stn.R;
import com.example.stn.stn.adapter.MCommonAdapter;
import com.example.stn.stn.adapter.MViewHolder;
import com.example.stn.stn.bean.Common;
import com.example.stn.stn.bean.GongdanBean;
import com.example.stn.stn.bean.GridMemberBean;
import com.example.stn.stn.service.ServerApi;
import com.example.stn.stn.service.ServiceGenerator;
import com.example.stn.stn.utils.SystemUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 冬冬 on 2016/8/16.
 */
public class GondanHuilongActivity extends BaseActivity {
    //实际为派发功能

    private String myId;//工单id
    private  int myClick;//工单id
    private ListView lv_paylist;
    private GongdanBean gongdan;
    private ProgressDialog mProgressDialog;
    private HashMap<Object, Object> fieldMap;
    private Call call;
    private Call call2;
    private Call call3;
    private MCommonAdapter<GongdanBean.ServInstInfoBean> mCommonAdapter;
    private HashMap<String,String> gridMap = new HashMap<>();
    private ArrayList<String> gridList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gongdanhuilong);


        initData();
        //initView();
    }

    private int oldPostion = -1;

    private void initView() {

        lv_paylist = (ListView) findViewById(R.id.lv_paylist);
        //工单名称
//电话
//客户编号
//网络经理
//用户名称
//安装地址
//客户类型
//工单发起人
//受理营业厅
//创建时间
//宽带登录名
//工单节点名称
        mCommonAdapter = new MCommonAdapter<GongdanBean.ServInstInfoBean>(mContext,gongdan.getServInstInfo() , R.layout.item_listview_worksheet4) {
            @Override
            public void convert(MViewHolder helper, final GongdanBean.ServInstInfoBean item, final int position) {
                TextView view1 = helper.getView(R.id.tv_order_list_1);
                TextView view2 = helper.getView(R.id.tv_order_list_2);
                TextView view3 = helper.getView(R.id.tv_order_list_3);
                TextView view4 = helper.getView(R.id.tv_order_list_4);
                TextView view5 = helper.getView(R.id.tv_order_list_5);
                TextView view6 = helper.getView(R.id.tv_order_list_6);
                TextView view7 = helper.getView(R.id.tv_order_list_7);
                TextView view8 = helper.getView(R.id.tv_order_list_8);
                TextView view9 = helper.getView(R.id.tv_order_list_9);
                TextView view10 = helper.getView(R.id.tv_order_list_10);
                TextView view11 = helper.getView(R.id.tv_order_list_11);
                //TextView view12 = helper.getView(R.id.tv_order_list_12);
                LinearLayout view13 = helper.getView(R.id.ll_gongdan);
                Button btn_paifa = helper.getView(R.id.btn_paifa);

                btn_paifa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(item.btnState){

                        }else{
                            //调用派发接口 成功后 修改颜色
                            //调用接口 查操作人
                            myId = item.getInstId();
                            myClick = position;
                            getGrid();
                        }


                    }


                });

                if(item.expand) {
                    view13.setVisibility(View.VISIBLE);
                }else{
                    view13.setVisibility(View.GONE);
                }

                if(item.btnState){
                    //置灰
                    btn_paifa.setBackgroundResource(R.drawable.shape_button_grey);
                    //已派发
                    btn_paifa.setText("已派发");
                }else{
                    //不置灰
                }

                view1.setText(item.getServProcessName());//工单名称
                view2.setText(item.getMobile());//电话
                view3.setText(item.getCustomerCode());//客户编号
                view4.setText(item.getStdGridmanName());//网络经理
                view5.setText(item.getSubscriberName());//用户名称
                view6.setText(item.getAddress());//安装地址
                view7.setText(item.getCustType());//客户类型
                view8.setText(item.getOperatorName());//工单发起人
                view9.setText(item.getOfficeOrgName());//受理营业厅
                view10.setText(item.getCreateDate());//创建时间
                view11.setText(item.getBroadBandLoginName());//宽带登录名
                //view12.setText(item.getProcessNodeName());//工单节点名称
            }
        };
        lv_paylist.setAdapter(mCommonAdapter);
        lv_paylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GongdanBean.ServInstInfoBean servInstInfoBean = gongdan.getServInstInfo().get(position);
                if (oldPostion == position) {
                    if (servInstInfoBean.expand)  {
                        oldPostion = -1;
                    }
                    servInstInfoBean.expand = !servInstInfoBean.expand;
                }else{
                    oldPostion = position;
                    servInstInfoBean.expand = true;
                }

                int totalHeight = 0;
                for(int i=0;i<mCommonAdapter.getCount();i++) {
                    View viewItem = mCommonAdapter.getView(i, null, lv_paylist);//这个很重要，那个展开的item的measureHeight比其他的大
                    viewItem.measure(0, 0);
                    totalHeight += viewItem.getMeasuredHeight();
                }

                ViewGroup.LayoutParams params = lv_paylist.getLayoutParams();
                params.height = totalHeight
                        + (lv_paylist.getDividerHeight() * (lv_paylist.getCount() - 1));
                lv_paylist.setLayoutParams(params);
                mCommonAdapter.notifyDataSetChanged();
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
                            if(call2 != null){
                                call2.cancel();
                                call2 = null;
                            }
                            if(call3 != null){
                                call3.cancel();
                                call3 = null;
                            }
                        }

                    }
                    return false;
                }
            });
            //mProgressDialog.show();
        }else{
            //mProgressDialog.show();
        }
        checkEdit();

    }
    /**
     * 客户查询点击事件
     */
    private void checkEdit() {


            //判断一下 是否有权限
        fieldMap = new HashMap<>();
        fieldMap.put("objectId",locationCache.getCustomer().getCustomerCode());//工单查询
        fieldMap.put("searchType","1");//客户证号
            fieldMap.put("loginName",locationCache.getOper().getUsername());//操作员
        //fieldMap.put("instType","2");//可回笼
        fieldMap.put("instType","1");//可派发
            fieldMap.put("officeId",String.valueOf(locationCache.getOrgInfoYourChoose().getOrgid()));//受理营业厅
            mProgressDialog.show();

            gongdanQuery(application.getUUID(), locationCache.getTridId(),
                    application.getToken(), fieldMap);


    }
    /**
     * 客户查询
     */
    private void gongdanQuery(String uuid, String tridId, String token, Map fieldMap) {
        ServerApi serverApi = ServiceGenerator.createService(ServerApi.class);


        call = serverApi.gongdanQuery(uuid, tridId, token, fieldMap);

        call.enqueue(new Callback<GongdanBean>() {
            @Override
            public void onResponse(Call<GongdanBean> call, Response<GongdanBean> response) {
                GongdanBean customerInfo = response.body();
                if (customerInfo.getReturn_code().equals("0")) {
                    //设置listview
                    gongdan = customerInfo;


                    initView();
                    mProgressDialog.dismiss();

                }else if(customerInfo.getReturn_code().equals("6")||customerInfo.getReturn_code().equals("7")){

                }  else {
                    mProgressDialog.dismiss();
                    SystemUtils.showToast(getApplicationContext(), customerInfo.getReturn_message());

                }
            }

            @Override
            public void onFailure(Call<GongdanBean> call, Throwable t) {
                mProgressDialog.dismiss();
                SystemUtils.showToast(getApplicationContext(), t.getMessage());
            }
        });
    }
    private void showMyDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("登录");
        View view = getLayoutInflater().inflate(R.layout.login_layout,null);
        final Spinner sp_application_id_type = (Spinner) view.findViewById(R.id.sp_application_id_type);
        sp_application_id_type.setAdapter(new ArrayAdapter<>(mContext,
                R.layout.support_simple_spinner_dropdown_item, gridList));
        final EditText et_applicationer = (EditText) view.findViewById(R.id.et_applicationer);
        Button btn_paifa = (Button) view.findViewById(R.id.btn_paifa);
        builder.setView(view);//设置login_layout为对话提示框
        final AlertDialog alertDialog = builder.create();
        btn_paifa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取所有参数
                HashMap<String, String> stringHashMap = new HashMap<>();
                stringHashMap.put("instId",myId);//工单id
                stringHashMap.put("operatorId",String.valueOf(locationCache.getOper().getOperid()));//工单处理人
                stringHashMap.put("opId",gridMap.get(sp_application_id_type.getSelectedItem().toString()));//网格派发
                stringHashMap.put("officeId",locationCache.getOrgInfoYourChoose().getOrgid());//受理营业厅
                stringHashMap.put("note",et_applicationer.getText().toString());//备注

                //进行派发请求
                mProgressDialog.show();
                ServerApi serverApi = ServiceGenerator.createService(ServerApi.class);
                call3 = serverApi.gongdanPaifa(application.getUUID(), locationCache.getTridId(),
                        application.getToken(), stringHashMap);
                call3.enqueue(new Callback<Common>() {
                    @Override
                    public void onResponse(Call<Common> call, Response<Common> response) {
                        Common body = response.body();
                        mProgressDialog.dismiss();
                        if(body.getReturn_code().equals("0")){
                            //按钮置灰。
                            gongdan.getServInstInfo().get(myClick).btnState = true;
                            mCommonAdapter.notifyDataSetChanged();
                        }else{
                            SystemUtils.showToast(mContext,body.getReturn_message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Common> call, Throwable t) {
                        mProgressDialog.dismiss();
                    }
                });

                alertDialog.dismiss();
            }
        });

        alertDialog.show();//显示Dialog对话框
    }

    public void getGrid() {
        mProgressDialog.show();
        ServerApi serverApi = ServiceGenerator.createService(ServerApi.class);

        HashMap<String, String> map = new HashMap<>();
        map.put("instId",myId);
        call2 = serverApi.gridMemberQuery(application.getUUID(), locationCache.getTridId(),
                application.getToken(), map);
        call2.enqueue(new Callback<GridMemberBean>() {
            @Override
            public void onResponse(Call<GridMemberBean> call, Response<GridMemberBean> response) {
                mProgressDialog.dismiss();
                GridMemberBean body = response.body();
                if(body.getReturn_code().equals("0")){
                    //设置hashmap和arraylist

                    for(GridMemberBean.OpInfoListBean bean:body.getOpInfoList()){
                        gridMap.put(bean.getOpName(),bean.getOpId());
                        gridList.add(bean.getOpName());
                    }

                    //添加完毕后 显示dialog
                    showMyDialog();


                }else{
                    SystemUtils.showToast(getApplicationContext(), body.getReturn_message());
                }
            }

            @Override
            public void onFailure(Call<GridMemberBean> call, Throwable t) {
                mProgressDialog.dismiss();
                SystemUtils.showToast(getApplicationContext(), t.getMessage());
            }
        });
    }
}