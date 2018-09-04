package com.ymd.client.model.bean;

/**
 * 定位信息类
 * @author rongweihe
 *
 */
public class LocationValueObject {

	private double JD; // 经度
	private double WD; // 纬度
	private String address; // 组合后的地址

	private String province; // 省份
	private String city; // 市
	private String town; // 县区一级
	private String county; // 街道、村

	private String direction;
	private String distance; // 距离

	private String cityCode; // 城市编号

	private String chooseCity;	//选择的城市，或者原来定位的城市

	public double getJD() {
		return JD;
	}

	public void setJD(double jD) {
		JD = jD;
	}

	public double getWD() {
		return WD;
	}

	public void setWD(double wD) {
		WD = wD;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getChooseCity() {
		return chooseCity;
	}

	public void setChooseCity(String chooseCity) {
		this.chooseCity = chooseCity;
	}

}