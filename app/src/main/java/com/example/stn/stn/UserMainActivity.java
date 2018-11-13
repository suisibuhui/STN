package com.example.stn.stn;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;


import com.example.stn.stn.bean.Login;
import com.example.stn.stn.cache.ACache;
import com.example.stn.stn.fragment.BusinessAcceptanceFragment;
import com.example.stn.stn.fragment.BusinessQueryFragment;
import com.example.stn.stn.fragment.MainContentFragment;
import com.example.stn.stn.fragment.UserLocationFragment;
import com.example.stn.stn.myinfos.MimeDetailActivity;
import com.example.stn.stn.views.TopBarView;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Name: MainActivity
 * Author: 王冬冬
 * Comment: //首页
 * Date: 2016-07-13 23:50.
 */
public class UserMainActivity extends BaseActivity  {

    private TopBarView mTopBarView ;
    private RadioGroup mRadioGroup;
    public static UserMainActivity instance ;
    private int count = 0;



    private String state;  //查询类型： 1-到期提醒，2-指定id查询， 3-全部
    private String target; //查询内容：到期提醒传0



    private Fragment userLocatonFragment, mainContentFragment, businessAcceptanceFragment, businessQueryFragment,
            currentFragment;
    private FrameLayout progressbar;
    private ACache aCache;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        aCache = ACache.get(mContext);
        instance = this;

        showDialog();
        state = "1";
        target = "0";
        initView();

    }


    int yourChoice;
    private void showDialog(){

        final List<Login.OperBean2> orgIngo = locationCache.getOrgIngo();
         String[] items = new String[orgIngo.size()];
        for (int i = 0;i<orgIngo.size();i++){
            items[i] = orgIngo.get(i).getOrgname();
        }

        yourChoice = 0;
        final AlertDialog.Builder singleChoiceDialog =
                new AlertDialog.Builder(UserMainActivity.this);
        singleChoiceDialog.setTitle("请选择受理组织");
        singleChoiceDialog.setCancelable(false);

        // 第二个参数是默认选项，此处设置为0
        singleChoiceDialog.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        yourChoice = which;
                    }
                });
        singleChoiceDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (yourChoice != -1) {
                            //设置一下
                            Login.OperBean2 operBean2 = orgIngo.get(yourChoice);

                            locationCache.setOrgInfoYourChoose(operBean2);

                        }
                    }
                });
        singleChoiceDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }






    /**
     * 初始化Fragment视图
     */
    private void initFragment() {

        if (mainContentFragment == null) {
            mainContentFragment = new MainContentFragment();
        }

        if (userLocatonFragment == null) {
            userLocatonFragment = new UserLocationFragment();
        }

        if (!mainContentFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_content, mainContentFragment).commit();
            // 记录当前Fragment
            currentFragment = mainContentFragment;
        }

        if (!userLocatonFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fl_location, userLocatonFragment).commit();
        }
    }


    /**
     * 初始化视图
     */
    protected void initView() {

        mTopBarView = (TopBarView)findViewById(R.id.topbar);
        mTopBarView.setOnLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入我的。
                startActivity(new Intent(UserMainActivity.this, MimeDetailActivity.class));


            }
        });
        mTopBarView.setOnRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SweetAlertDialog dialog = new SweetAlertDialog(UserMainActivity.this);
                dialog.setTitleText("提示");
                dialog.setContentText("是否确定退出？");
                dialog.setConfirmText("确定");
                dialog.setCancelText("取消");
                dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        //退出
                        finish();
                        startActivity(new Intent(mContext,LoginActivity.class));
                    }
                });
                dialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });


        mRadioGroup = (RadioGroup)findViewById(R.id.rg_homepage);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbtn_home_page:
                        if(mainContentFragment == null) {
                            mainContentFragment = new MainContentFragment();
                        }
                        addOrShowFragment(mainContentFragment, "mainContentFragment");
                        mTopBarView.setTitleText("首页");
                        break;
                    case R.id.rbtn_business_acceptance:
                        if(businessAcceptanceFragment == null) {
                            businessAcceptanceFragment = new BusinessAcceptanceFragment();
                        }
                        addOrShowFragment(businessAcceptanceFragment,"businessAcceptanceFragment");
                        mTopBarView.setTitleText("业务受理");
                        break;
                    case R.id.rbtn_business_query:
                        if(businessQueryFragment == null){
                            businessQueryFragment = new BusinessQueryFragment();
                        }
                        addOrShowFragment(businessQueryFragment,"businessQueryFragment");
                        mTopBarView.setTitleText("业务查询");
                        break;
                }
            }
        });
        initFragment();
    }

    /**
     * 切换Fragment
     * @param fragment
     * @param tag
     */
    public void addOrShowFragment(Fragment fragment, String tag ) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (currentFragment == fragment)
            return;

        if (!fragment.isAdded()) {
            transaction.hide(currentFragment)
                    .add(R.id.fl_content, fragment,tag).commit();
        } else {
            transaction.hide(currentFragment).show(fragment).commit();
        }
        currentFragment = fragment;
    }










}

