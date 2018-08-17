package com.ymd.client.common.net;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.ymd.client.UApplication;
import com.ymd.client.model.constant.Constants;
import com.ymd.client.utils.AbMd5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @author zhl 网络请求基础数据类
 * @class com.ymaidan.client.common.net
 * @time 2018/6/23 0023 19:01
 */
public class NetUtils {
    /*
     * 测试地址
     */
    public static final String BASE_URL = "http://111.231.215.234:9310/pay-api/";

    /**
     * API版本号
     */
    public static final String VERSION = "LLY-1.0.1";

    /**
     * 平台商户号
     *
     * @return
     */
    public static String getPlatformMerchantNo() {
        if (Constants.DEBUG) {
            //测试版本地址
            return "110335550138534";
        } else {
            //正式版本地址
            //纷呈惠
            return "110336047842852";
        }
    }

    /**
     * 获取 BASE_URL 地址
     *
     * @return 网络请求地址
     */
    public static String getBaseUrl() {
        if (Constants.DEBUG) {
            //测试版本地址
            return BASE_URL;
            //return "http://192.168.10.247:8080/";
        } else {
            //正式版本地址
            return "http://39.105.158.206:8081/pay-api/";
        }
    }


    /**
     * 参数加密，加密方式[md5(取字段值，按照字段0-63的顺序连接字符串,加上密钥).toUpperCase]
     *
     * @param requests 需要加密的参数，需要按照加密顺序传递参数顺序
     * @return 加密后的值
     */
    public static String encrypte(String... requests) {
        if (requests == null || requests.length == 0) {
            return AbMd5.MD5(Constants.KEY).toUpperCase();
        } else {
            StringBuilder sb = new StringBuilder();
            for (String str : requests) {
                sb.append(str);
            }
            sb.append(Constants.KEY);
            return AbMd5.MD5(sb.toString()).toUpperCase();
        }
    }

    /**
     * 参数加密，加密方式[md5(取字段值，按照字段0-63的顺序连接字符串,加上密钥).toUpperCase]
     *
     * @param map 需要加密的参数，key为请求字段，value为请求字段值
     * @return 排序后加密后的值
     */
    public static String encrypte(Map<Integer, String> map) {
        if (map == null || map.isEmpty()) {
            return AbMd5.MD5(Constants.KEY).toUpperCase();
        }

        List<Map.Entry<Integer, String>> list = new ArrayList<>(map.entrySet());
        //针对key的大小排序
        Collections.sort(list, new Comparator<Map.Entry<Integer, String>>() {
            @Override
            public int compare(Map.Entry<Integer, String> o1,
                               Map.Entry<Integer, String> o2) {
                int flag = o1.getKey().compareTo(o2.getKey());
                if (flag == 0) {
                    return o1.getKey().compareTo(o2.getKey());
                }
                return flag;
            }
        });

        StringBuilder sb = new StringBuilder();
        //遍历list得到map里面排序后的元素
        for (Map.Entry<Integer, String> en : list) {
            sb.append(en.getValue());
        }
        //追加固定密钥
        sb.append(Constants.KEY);

        return AbMd5.MD5(sb.toString()).toUpperCase();
    }

    /**
     * 获取不同的渠道名称
     *
     * @return
     */
    public static String getChannel() {
        String channel = "";
        try {
            ApplicationInfo appInfo = UApplication.getGobalApplication().getPackageManager()
                    .getApplicationInfo(UApplication.getGobalApplication().getPackageName(),
                            PackageManager.GET_META_DATA);
            channel = appInfo.metaData.getString("CHANNEL_VALUE");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channel;
    }
}
