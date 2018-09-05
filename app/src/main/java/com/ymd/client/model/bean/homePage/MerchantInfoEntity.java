package com.ymd.client.model.bean.homePage;


import java.util.List;

public class MerchantInfoEntity {
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
    /*
    距离
     */
    private String distance;
    /*
    距离
     */
    private String score;

    //文件
    private List<FileInfoEntity> file;

    public List<FileInfoEntity> getFile() {
        return file;
    }

    public void setFile(List<FileInfoEntity> file) {
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
