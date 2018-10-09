package com.ymd.client.model.bean.homePage;

import java.io.Serializable;
import java.util.List;

/**
 * 商户信息
 */
public class MerchantInfoEntity implements Serializable {
    private Long id;
    /**
     * 门店名称
     */
    private String name;
    /**
     * 门店地址
     */
    private String address;
    /**
     * 门店的联系电话
     */
    private String merTel;
    /**
     * 商店地址经度
     */
    private String longitude;
    /**
     * 商店地址纬度
     */
    private String latitude;

    /**
     * 是否提供发票
     */
    private Integer invoice;
    /**
     * 折扣
     */
    private String discount;
    /**
     * 城市
     */
    private String city;
    /**
     * 区县
     */
    private String county;
    /**
    距离
     */
    private String distance;
    /**
    距离
     */
    private String score;

    /**
     * 文件
     */
    private List<FileInfo> file;

    /**
     * 人均消费
     */
    private String consumption;

    /**
     * 营业开始时间
     */
    private String startBusinessTime;

    /**
     * 营业结束时间
     */
    private String endBusinessTime;
    //门头照
    private String photoUrl;

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getConsumption() {
        return consumption;
    }

    public void setConsumption(String consumption) {
        this.consumption = consumption;
    }

    public String getStartBusinessTime() {
        return startBusinessTime;
    }

    public void setStartBusinessTime(String startBusinessTime) {
        this.startBusinessTime = startBusinessTime;
    }

    public String getEndBusinessTime() {
        return endBusinessTime;
    }

    public void setEndBusinessTime(String endBusinessTime) {
        this.endBusinessTime = endBusinessTime;
    }

    public List<FileInfo> getFile() {
        return file;
    }

    public void setFile(List<FileInfo> file) {
        this.file = file;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMerTel() {
        return merTel;
    }

    public void setMerTel(String merTel) {
        this.merTel = merTel;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Integer getInvoice() {
        return invoice;
    }

    public void setInvoice(Integer invoice) {
        this.invoice = invoice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }


}
