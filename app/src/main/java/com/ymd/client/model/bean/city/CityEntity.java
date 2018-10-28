package com.ymd.client.model.bean.city;

/**
 * 包名:com.ymd.client.model.bean.city
 * 类名:
 * 时间:2018/9/5 0005Time:14:11
 * 作者:荣维鹤
 * 功能简介:
 * 修改历史:
 */
public class CityEntity {
    private long cityID;
    private String cityName;
    private String cityFirst;   //首字母

    private String countyName;
    private long countyCode;

    public long getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(long countyCode) {
        this.countyCode = countyCode;
    }

    public long getCityID() {
        return cityID;
    }

    public void setCityID(long cityID) {
        this.cityID = cityID;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityFirst() {
        return cityFirst;
    }

    public void setCityFirst(String cityFirst) {
        this.cityFirst = cityFirst;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }
}
