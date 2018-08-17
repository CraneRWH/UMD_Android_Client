package com.ymd.client.model.bean;

import java.io.Serializable;

/**
 * @author zhl
 * @class com.ymd.client.model.bean
 * @time 2018/8/16 0016 14:15
 * @description
 */
public class ResponseBean implements Serializable {

    public String code;//返回错误码

    public String msg;//错误信息
}
