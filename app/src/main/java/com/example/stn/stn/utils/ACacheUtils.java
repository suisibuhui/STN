package com.example.stn.stn.utils;

import android.content.Context;


import com.example.stn.stn.cache.ACache;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Name: ACacheUtils
 * Author: xulong
 * Comment: ACache缓存工具类
 * Date: 2016-07-17 22:11.
 */
public class ACacheUtils {

    private ACache mACahe;
    private Context mContext;

    public ACacheUtils(Context context) {
        mContext = context;
        mACahe = ACache.get(mContext.getApplicationContext());
    }


    /**
     * 保存字符串
     * @param key
     * @param value
     */
    public void setStringSP (String key,String value){
        mACahe.put(key,value);
    }

    public void setStringSP(String key, String jsonObject, int saveTime){
        mACahe.put(key,jsonObject,saveTime);
    }

    /**
     * 取字符串
     * @param key
     * @return
     */
    public String getStringSP(String key){
        return mACahe.getAsString(key);
    }

    /**
     * 存JSONObject
     * @param key
     * @param jsonObject
     */
    public void setJSONObjectSP(String key, JSONObject jsonObject){
        mACahe.put(key,jsonObject);
    }

    public void setJSONObjectSP(String key, JSONObject jsonObject, int saveTime){
        mACahe.put(key,jsonObject,saveTime);
    }

    /**
     * 取JSONObject
     * @param key
     * @return
     */
    public JSONObject getJSONObjectSP(String key){
        return mACahe.getAsJSONObject(key);
    }

    /**
     * 存JSONArray
     * @param key
     * @param jsonArray
     */
    public void setJSONArraySP(String key, JSONArray jsonArray){
        mACahe.put(key,jsonArray);
    }

    public void setJSONArraySP(String key, JSONObject jsonArray, int saveTime){
        mACahe.put(key,jsonArray,saveTime);
    }

    /**
     * 取JSONArray
     * @param key
     * @return
     */
    public JSONArray getJSONArraySP(String key){
        return mACahe.getAsJSONArray(key);
    }

    /**
     * 移除某个key
     * @param key
     * @return
     */
    public Boolean removeSP(String key){
        return mACahe.remove(key);
    }

    /**
     * 清空所有数据
     */
    public void clearAll(){
        mACahe.clear();
    }

}
