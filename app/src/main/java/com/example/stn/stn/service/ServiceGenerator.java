package com.example.stn.stn.service;

import com.litesuits.android.log.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Name: ServiceGenerator
 * Author: xulong
 * Comment: 获取服务器对象
 * Date: 2016-07-12 15:25.
 */

public class ServiceGenerator {
    //测试
    //  private static final String SERVER_BASE_URL9 = "http://1.82.229.158:8081/mboss/";//公司测试地址
    //正式
  // private static final String SERVER_BASE_URL9 = "http://113.143.102.126:8090/mboss/";
   private static final String SERVER_BASE_URL9 = "http://103.232.146.86:8090/mboss/";
    static final  HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger(){
        @Override
        public void log(String message) {
            Log.i("RetrofitLog","retrofitBack = "+message);
        }
    });
    //右边地址去、
    static OkHttpClient.Builder httpBuilder  =new OkHttpClient.Builder();

    static OkHttpClient         client      =httpBuilder.readTimeout(3, TimeUnit.MINUTES)
            .connectTimeout(3, TimeUnit.MINUTES).writeTimeout(3, TimeUnit.MINUTES).addInterceptor(loggingInterceptor) //设置超时
            .build();
    private static Retrofit.Builder mBuilder = new Retrofit.Builder()
            .baseUrl(SERVER_BASE_URL9).client(client)
            .addConverterFactory(GsonConverterFactory.create());

    //用于受理单页面处
    private static Retrofit.Builder mBuilder1 = new Retrofit.Builder()
            .baseUrl(SERVER_BASE_URL9).client(client);

    /**
     * 生成服务器对象
     *
     * @param serviceClass
     * @param <S>
     * @return
     */
    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = mBuilder.build();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return retrofit.create(serviceClass);
    }

    /**
     * 生成服务器对象：受理单页面
     *
     * @param serviceClass
     * @param <S>
     * @return
     */
    public static <S> S createService1(Class<S> serviceClass) {
        Retrofit retrofit = mBuilder1.build();

        return retrofit.create(serviceClass);
    }






}
