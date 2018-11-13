
package com.example.stn.stn;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.stn.stn.cache.LocationCache;
import com.example.stn.stn.utils.Constant;
import com.example.stn.stn.utils.SharedPrefsUtil;
import com.example.stn.stn.MyApplication;

public abstract class BaseActivity extends AppCompatActivity {

        protected SharedPrefsUtil mSharedPrefs;
        protected Context mContext;
        protected MyApplication application;
        protected LocationCache locationCache;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mContext = this;
            application = (MyApplication) getApplication();
            locationCache = LocationCache.getInstance();

            mSharedPrefs = new SharedPrefsUtil(getApplicationContext(), Constant.SharedPrefOperInfo.SHARED_NAME);
        }




}




