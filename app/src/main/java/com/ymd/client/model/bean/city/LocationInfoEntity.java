package com.ymd.client.model.bean.city;

/**
 * 包名:com.ymd.client.model.bean.city
 * 类名:
 * 时间:2018/9/5 0005Time:16:34
 * 作者:荣维鹤
 * 功能简介:    定位信息
 * 修改历史:
 */
public class LocationInfoEntity {

    private String country;  //国家
    private double longitude;   //经度
    private double latitude;    //纬度
    private double accuracy;    //精度
    private double speed;   //速度
    private String province;    //省
    private String city;    //市
    private String cityCode;    //城市编号
    private String county;    //区
    private String adCode;      //区域码
    private String address;     //地址

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getAdCode() {
        return adCode;
    }

    public void setAdCode(String adCode) {
        this.adCode = adCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
