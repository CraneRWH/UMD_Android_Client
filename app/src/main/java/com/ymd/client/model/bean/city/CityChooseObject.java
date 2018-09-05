package com.ymd.client.model.bean.city;

import java.util.ArrayList;
import java.util.Map;

/**
 *	城市列表
 */
public class CityChooseObject {

	public static final int ITEM = 0;
	public static final int SECTION = 1;
	public static final int HOT = 2;
	private int type;
	private String cityName;
	private Long cityId;
	private String cityFirst;
	
	public ArrayList<Map<String,Object>> citys = new ArrayList<Map<String,Object>>();
	
	public static ArrayList<Integer> letters = new ArrayList<Integer>();
	
	public CityChooseObject(int t,String name) {
		this.type = t;
		this.cityName = name;
	}

	public CityChooseObject(int type, String cityName, Long cityId, String cityFirst) {
		this.type = type;
		this.cityName = cityName;
		this.cityId = cityId;
		this.cityFirst = cityFirst;
	}

	public void setCitys(ArrayList<Map<String,Object>> list) {
		this.citys.addAll(list);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public int getSectionPosition() {
		return sectionPosition;
	}

	public void setSectionPosition(int sectionPosition) {
		this.sectionPosition = sectionPosition;
	}

	public int getListPosition() {
		return listPosition;
	}

	public void setListPosition(int listPosition) {
		this.listPosition = listPosition;
	}

	public int sectionPosition;
	public int listPosition;

	public static int getITEM() {
		return ITEM;
	}

	public static int getSECTION() {
		return SECTION;
	}

	public static int getHOT() {
		return HOT;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getCityFirst() {
		return cityFirst;
	}

	public void setCityFirst(String cityFirst) {
		this.cityFirst = cityFirst;
	}

	public ArrayList<Map<String, Object>> getCitys() {
		return citys;
	}

	public static ArrayList<Integer> getLetters() {
		return letters;
	}

	public static void setLetters(ArrayList<Integer> letters) {
		CityChooseObject.letters = letters;
	}
}
