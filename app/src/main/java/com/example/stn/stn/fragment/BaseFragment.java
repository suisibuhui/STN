package com.example.stn.stn.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.stn.stn.MyApplication;
import com.example.stn.stn.cache.LocationCache;
import com.example.stn.stn.utils.Constant;
import com.example.stn.stn.utils.SharedPrefsUtil;


/**
 * Name: BaseFragment
 * Author: xulong
 * Comment: //TODO
 * Date: 2016-07-22 08:27.
 */
public class BaseFragment extends Fragment {
    protected Context mContext;
    protected SharedPrefsUtil mSharedPrefs;
    protected LocationCache locationCache;
    protected MyApplication application;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mSharedPrefs = new SharedPrefsUtil(getActivity(), Constant.SharedPrefrence.SHARED_NAME);
        locationCache = LocationCache.getInstance();
        application = (MyApplication)mContext.getApplicationContext();
    }
}
