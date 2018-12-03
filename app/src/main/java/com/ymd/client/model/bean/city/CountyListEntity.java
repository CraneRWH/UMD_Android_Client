package com.ymd.client.model.bean.city;

import java.util.List;

/**
 * 作者:rongweihe
 * 日期:2018/12/3  时间:21:28
 * 描述:
 * 修改历史:
 */
public class CountyListEntity {
    private Long cityId;
    private List<CityEntity> counties;

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public List<CityEntity> getCounties() {
        return counties;
    }

    public void setCounties(List<CityEntity> counties) {
        this.counties = counties;
    }
}
