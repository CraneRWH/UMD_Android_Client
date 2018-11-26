package com.ymd.client.common.base.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 支付信息
 */
public class PayOrderResult {

    private String bg_bank_code;
    private String bg_bank_message;
    private String order_date;
    private String order_id;
    private String pay_info;
    private String pay_url;
    private String platform_seq_id;
    private String resp_code;
    private String resp_desc;
    private String token_id;

    public String getBg_bank_code() {
        return bg_bank_code;
    }

    public void setBg_bank_code(String bg_bank_code) {
        this.bg_bank_code = bg_bank_code;
    }

    public String getBg_bank_message() {
        return bg_bank_message;
    }

    public void setBg_bank_message(String bg_bank_message) {
        this.bg_bank_message = bg_bank_message;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getPay_info() {
        return pay_info;
    }

    public void setPay_info(String pay_info) {
        this.pay_info = pay_info;
    }

    public String getPay_url() {
        return pay_url;
    }

    public void setPay_url(String pay_url) {
        this.pay_url = pay_url;
    }

    public String getPlatform_seq_id() {
        return platform_seq_id;
    }

    public void setPlatform_seq_id(String platform_seq_id) {
        this.platform_seq_id = platform_seq_id;
    }

    public String getResp_code() {
        return resp_code;
    }

    public void setResp_code(String resp_code) {
        this.resp_code = resp_code;
    }

    public String getResp_desc() {
        return resp_desc;
    }

    public void setResp_desc(String resp_desc) {
        this.resp_desc = resp_desc;
    }

    public String getToken_id() {
        return token_id;
    }

    public void setToken_id(String token_id) {
        this.token_id = token_id;
    }
}
