package com.example.stn.stn.utils.download;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * OKHttp配置文件；
 * created by xulong
 */
public class OkHttpManager {

    private static OkHttpManager sOkHttpManager;
    private OkHttpClient okHttpClient;

    private OkHttpManager() {

    }

    public static OkHttpManager getInstance() {
        if (sOkHttpManager == null) {
            sOkHttpManager = new OkHttpManager();
        }
        return sOkHttpManager;
    }


    /**
     * 配置一下OKhttpClient
     * @return
     */
    public OkHttpClient getDefaultOkHttpClient() {
        if (okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS);
            return builder.build();
        } else {
            return okHttpClient;
        }
    }




}
