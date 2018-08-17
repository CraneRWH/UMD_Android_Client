package com.ymd.client.common.rx;

import com.ymd.client.common.net.ApiService;
import com.ymd.client.common.net.NetUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author zhl
 * @class com.ymaidan.client.common
 * @time 2018/6/13 0013 11:50
 * @description
 */
public class RetrofitManager {

    public static final int UOLOAD = 1;
    public static final int DOWNLOAD = 2;
    public static final int NONE = 3;

    private static final int READ_TIMEOUT = 60;//读取超时时间,单位  秒
    private static final int CONN_TIMEOUT = 12;//连接超时时间,单位  秒

    private volatile static RetrofitManager retrofitManager;
    private Retrofit retrofit;

    //无参的单利模式
    public static RetrofitManager getSingleton() {
        if (retrofitManager == null) {
            synchronized (RetrofitManager.class) {
                retrofitManager = new RetrofitManager();
            }
        }
        return retrofitManager;
    }
    //无参的构造方法
    private RetrofitManager() {
        initRetrofitManager();
    }

    //构造方法创建Retrofit实例
    private void initRetrofitManager() {
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .addInterceptor(new HttpLogInterceptor())
                //.cookieJar(new CookieJarImpl(new PersistentCookieStore(mContext)))
                .readTimeout(READ_TIMEOUT, TimeUnit.MINUTES)//设置读取时间为一分钟
                .connectTimeout(CONN_TIMEOUT, TimeUnit.SECONDS)//设置连接时间为12s
                .build();//初始化一个client,不然retrofit会自己默认添加一个

        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(NetUtils.getBaseUrl())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public ApiService Apiservice() {
        return retrofit.create(ApiService.class);
    }
}
