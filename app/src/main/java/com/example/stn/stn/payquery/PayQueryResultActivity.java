package com.example.stn.stn.payquery;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.stn.stn.BaseActivity;
import com.example.stn.stn.R;
import com.example.stn.stn.adapter.MCommonAdapter;
import com.example.stn.stn.adapter.MViewHolder;
import com.example.stn.stn.bean.PayBean;
import com.example.stn.stn.utils.SystemUtils;

/**
 * Created by 冬冬 on 2016/8/16.
 */
public class PayQueryResultActivity extends BaseActivity {


    private PayBean payInfos;
    private ListView lv_paylist;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payqueryresult);


        initData();
        initView();
    }

    private void initView() {

        lv_paylist = (ListView) findViewById(R.id.lv_paylist);
        MCommonAdapter<PayBean.PaymentRecordListBean> mCommonAdapter = new MCommonAdapter<PayBean.PaymentRecordListBean>(mContext, payInfos.getPaymentRecordList(), R.layout.item_payresult) {
            @Override
            public void convert(MViewHolder helper, PayBean.PaymentRecordListBean item, int position) {
                TextView view1 = helper.getView(R.id.tv_payresult_1);
                TextView view2 = helper.getView(R.id.tv_payresult_2);
                TextView view3 = helper.getView(R.id.tv_payresult_3);
                TextView view4 = helper.getView(R.id.tv_payresult_4);
                view1.setText(item.getPaymentNo());//编号
                view2.setText(item.getAmount());//金额
                view3.setText(item.getPaymenDate());//时间
                view4.setText(SystemUtils.getPayType(item.getPayType()));//续费方式
            }
        };
        lv_paylist.setAdapter(mCommonAdapter);
    }

    private void initData() {
        payInfos = locationCache.getPayInfos();
    }


}