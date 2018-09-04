package com.ymd.client.model.constant;

/**
 * 作者:rongweihe
 * 日期:2018/9/3  时间:20:02
 * 描述:  将所有接口以常量形式写在此处
 * 修改历史:
 */
public class URLConstant {

    /**
        注册
     */
    public static final String REGIST = "ymdConsumer/addConsumer";

    /**
     * 获取验证码
     */
    public static final String GET_PHONE_CODE = "yzm/getYzm";

    /**
     * 登录（密码登录和验证码登录）
     */
    public static final String LOGIN = "login/login";

    /**
     * 首页轮播图
     */
    public static final String UMD_PIC = "ymdConsumer/broadcastPic";

    /**
     * 首页优惠信息
     */
    public static final String UMD_UH_PIC = "ymdConsumer/preferential";


    /**
     * 综合排序
     */
    public static final String COMPREHENSIVE_MERCHANT = "ymdMerchantController/comprehensiveMerchant";


    /**
     * 附近商家（距离最近商家）
     */
    public static final String NEAR_MERCHANT = "ymdMerchantController/findMerchant";


    /**
     * 好评排列商家
     */
    public static final String PRAISE_MERCHANT = "ymdMerchantController/praiseMerchant";


    /**
     * 销量最多商家
     */
    public static final String SALES_MERCHANT = "ymdMerchantController/salesMerchant";


    /**
     * 相似商家
     */
    public static final String LIKE_MERCHANT = "ymdMerchantController/likeMerchant";


    /**
     * 上传图片接口
     */
    public static final String UP_LOAD_PICTURE= "file/upload";


    /**
     * 获取市级城市列表
     */
    public static final String QUERY_CITY_DATA= "city/list";

    /**
     * 获取县级城市列表
     */
    public static final String QUERY_COUTY_DATA= "city/cities";
}