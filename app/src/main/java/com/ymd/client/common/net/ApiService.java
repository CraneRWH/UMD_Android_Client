package com.ymd.client.common.net;

import com.ymd.client.model.bean.ResponseBean;
import io.reactivex.Flowable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author zhl
 * @class com.ymaidan.client.common
 * @time 2018/6/13 0013 10:26
 * @description 所有接口的入口
 */
public interface ApiService {

    /**
     * @param phone        手机号
     * @param password     密码
     * @param version      版本号
     * @param interface_no 接口编号
     * @param sign         签名
     * @return
     */
    @POST("request.app")
    Flowable<ResponseBean> login(@Query("1") String phone, @Query("8") String password,
                                 @Query("59") String version, @Query("3") String interface_no,
                                 @Query("64") String sign);

}
