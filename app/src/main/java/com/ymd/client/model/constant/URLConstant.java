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


    /**
     * 获取今日推荐的商家美食
     */
    public static final String RECOMMEND_NICE_MERCHANT= "ymdConsumer/recommendation";

    /**
     * 修改更新用户信息
     */
    public static final String UPDATE_USER_INFO = "ymdConsumer/updateConsumer";

    /**
     * 获取首页的功能列表
     */
    public static final String QUERY_HOME_FUNCTIONS = "ymdIndustry/list";

    /**
     * 美食类别
     */
    public static final String QUERY_FOOD_TYPE_FUNCTIONS = "ymdConsumer/foodClasses";

    /**
     *  修改登录密码
     */
    public static final String CHANGE_LOGIN_PASSWORD = "ymdConsumer/updatePassword";

    /**
     *  修改手机号
     */
    public static final String CHANGE_LOGIN_PHONE = "ymdConsumer/updatePhone";

    /**
     *  获取商家的商品分类
     */
    public static final String MERCHANT_GOOD_TYPE = "HyGoodsController/rangeGoodsList";


    /**
     *  获取商家的所有商品
     */
    public static final String MERCHANT_GOOD_LIST = "HyGoodsController/goodsList";

    /**
     *  获取商家的评价
     */
    public static final String MERCHANT_EVALUATION_LIST = "Evaluation/evaluationList";


    /**
     *  获取商家的相关资质照片等
     */
    public static final String MERCHANT_PHOTO_FILE_LIST = "ymdConsumer/merchantFile";


    /**
     *  收藏商家
     */
    public static final String MERCHANT_COLLECTION_ADD = "ymdConsumer/merchantCollectionAdd";

    /**
     *  取消收藏商家
     */
    public static final String MERCHANT_COLLECTION_DEL = "ymdConsumer/merchantCollectionDel";

    /**
     *  商家详情
     */
    public static final String MERCHANT_DETAIL_INFO = "ymdMerchantController/merchantDetails";

    /**
     *  个人收藏列表
     */
    public static final String MERCHANT_COLLECTION_LIST= "ymdConsumer/merchantCollectionList";

    /**
     *  投诉
     */
    public static final String MERCHANT_COMPLAINT= "ymdConsumer/complaints";

    /**
     *  订单评论
     */
    public static final String MERCHANT_ADD_EVALUATION= "Evaluation/addEvaluation";

    /**
     * 商户推荐的商品
     */
    public static final String MERCHANT_RECOMMEND_GOODS = "HyGoodsController/collectionGoodsList";

    /**
     * 获取商户的包房列表
     */
    public static final String MERCHANT_ROOM_LIST = "ymdMerchantController/roomsList";

    /**
     * 获取订单列表
     */
    public static final String ORDER_LIST = "ymdOrder/orderList";

    /**
     * 生成订单
     */
    public static final String CREATE_ORDER = "ymdOrder/addOrder";

    /**
     * 优惠买单生成订单
     */
    public static final String CREATE_U_ORDER = "ymdOrder/yhmdAddOrder";

    /**
     * 取消订单
     */
    public static final String CANCLE_ORDER = "ymdConsumer/ConsumerRefuseOrder";

    /**
     * 获取订单详情
     */
    public static final String ORDER_DETAIL = "ymdOrder/orderDetails";

    /**
     * 生成订单预约
     */
    public static final String ORDER_APPOINTMENT = "ymdOrder/orderAppointment";


    /**
     * 获取我的U币
     */
    public static final String QUEYR_U_LIST = "ymdUCurrency/uBInfo";

    /**
     * 获取我的U币
     */
    public static final String QUEYR_U_NUM = "ymdConsumer/consumerUNumber";

    /**
     * 获取热门搜索
     */
    public static final String QUEYR_HOT_SEARCH = "ymdMerchantController/popularSearch";

    /**
     * 获取支付信息
     */
    public static final String ORDER_PAY_INFO = "ymdOrder/orderQuery";

    /**
     * 支付信息
     */
    public static final String ORDER_PAY = "ymdOrder/pay";

    /**
     * 获取个人的评论列表
     */
    public static final String QUERY_COMMENT_LIST = "Evaluation/commentsList";

    /**
     * 获取最新版本号
     */
    public static final String VERSION_NEW = "file/appVersion";

    /**
     * 删除订单
     */
    public static final String DELETE_ORDER = "ymdOrder/deleteOrder";

    /**
     * 获取会员列表
     */
    public static final String QUERY_MEMBER_LIST = "hyMember/memberList";
    /**
     * 开通会员接口
     */
    public static final String OPEN_MEMBER = "hyMember/openMember";

}
