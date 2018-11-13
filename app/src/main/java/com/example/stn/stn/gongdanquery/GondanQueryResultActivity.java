package com.example.stn.stn.gongdanquery;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.stn.stn.BaseActivity;
import com.example.stn.stn.R;
import com.example.stn.stn.adapter.MCommonAdapter;
import com.example.stn.stn.adapter.MViewHolder;
import com.example.stn.stn.bean.GongdanBean;
import com.example.stn.stn.bean.PayBean;
import com.example.stn.stn.utils.SystemUtils;

/**
 * Created by 冬冬 on 2016/8/16.
 */
public class GondanQueryResultActivity extends BaseActivity {


    private ListView lv_paylist;
    private GongdanBean gongdan;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gongdanqueryresult);


        initData();
        initView();
    }

    private void initView() {

        lv_paylist = (ListView) findViewById(R.id.lv_paylist);
        lv_paylist.setDividerHeight(0);
        MCommonAdapter<GongdanBean.ServInstInfoBean> mCommonAdapter = new MCommonAdapter<GongdanBean.ServInstInfoBean>(mContext,gongdan.getServInstInfo() , R.layout.item_listview_worksheet3) {
            @Override
            public void convert(MViewHolder helper, GongdanBean.ServInstInfoBean item, int position) {
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
                TextView view12 = helper.getView(R.id.tv_order_list_12);
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
                view12.setText(item.getProcessNodeName());//工单节点名称
            }
        };
        lv_paylist.setAdapter(mCommonAdapter);
    }

    private void initData() {
        gongdan = locationCache.getGongdan();
    }


}