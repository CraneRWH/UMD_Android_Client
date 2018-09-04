package com.ymd.client.model.constant;

/**
 * @author zhl
 * @class com.ymd.client.model.constant
 * @time 2018/8/16 0016 14:15
 * @description
 */
public class Constants {

    public static final boolean DEBUG = false;//测试true,正式false

    //文件根目录
    public final static String ROOT_PATH = "ymd";
    public final static String IMAGE_PATH = "/temp";

    public static final String KEY = "21E4ACD4CD5D4619B063F40C5A454F7D";//密钥

    public static final int REQUEST_CODE_CAMERA = 0x01;
    public static final int REQUEST_CODE_STORE = 0x02;

    //我要合作拍照
    public static final int REQUEST_LICENSE = 0x10;//营业执照
    public static final int REQUEST_ID_X = 0x11;//身份证正面
    public static final int REQUEST_ID_Y = 0x12;//身份证反面
    public static final int REQUEST_HYGIENE = 0x13;//卫生许可证
    public static final int REQUEST_OTHER1 = 0x14;//其他证
    public static final int REQUEST_OTHER2 = 0x15;//其他证

    public static final String GES_KEY = "GES_KEY";

    public static final String LOCATION_ACTION = "locationAction";
}
