package com.ymd.client.model.info;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ymd.client.model.bean.LocationValueObject;
import com.ymd.client.utils.CommonShared;
import com.ymd.client.utils.ToolUtil;

/**
 * 定位城市的类
 * @author rongweihe
 *
 */
public class LocationInfo implements java.io.Serializable{

	public final static String LOCATION_INFO_SETTING = "locationInfo";
	private static LocationInfo instance = null;
	private static LocationValueObject locationInfo = new LocationValueObject();
	private Context applicationContext;

	public static String locationStr = "";

	private LocationInfo(Context context) {
		this.applicationContext = context;
	}
	public static void initInstance(Context context) {
		if (instance == null) {
			instance = new LocationInfo(context);
		}

		setLocationInfoData();
	}

	@SuppressWarnings("unchecked")
	private static void setLocationInfoData() {
		String cityInfo = CommonShared.getString(LOCATION_INFO_SETTING, "");
		locationInfo.setCity("北京");
		if (cityInfo != null && cityInfo.length() > 0 ) {
			try {
				Map<String,Object> allValue = new Gson().fromJson(cityInfo, new TypeToken<HashMap<String,Object>>(){}.getType());
				if (allValue.containsKey("result")) {
					Map<String,Object> locationValue = (Map<String, Object>) allValue.get("result");
					locationInfo.setCityCode(ToolUtil.changeString(locationValue.get("cityCode")));
					locationInfo.setAddress(ToolUtil.changeString(locationValue.get("formatted_address")));

					Map<String,Object> location = (Map<String, Object>) locationValue.get("location");
					locationInfo.setJD(ToolUtil.changeDouble(location.get("lng")));
					locationInfo.setWD(ToolUtil.changeDouble(location.get("lat")));

					Map<String,Object> address = (Map<String, Object>) locationValue.get("addressComponent");
					locationInfo.setCity(ToolUtil.changeString(address.get("city")));
					locationInfo.setProvince(ToolUtil.changeString(address.get("province")));
					locationInfo.setTown(ToolUtil.changeString(address.get("district")));
					locationInfo.setCounty(ToolUtil.changeString(address.get("street")));
					locationInfo.setDirection(ToolUtil.changeString(address.get("direction")));
					locationInfo.setDistance(ToolUtil.changeString(address.get("distance")));
				}
				else {
					locationInfo.setCityCode(ToolUtil.changeString(allValue.get("cityCode")));
					locationInfo.setAddress(ToolUtil.changeString(allValue.get("formatted_address")));
					locationInfo.setChooseCity(ToolUtil.changeString(allValue.get("chooseCity")));

					locationInfo.setJD(ToolUtil.changeDouble(allValue.get("JD")));
					locationInfo.setWD(ToolUtil.changeDouble(allValue.get("WD")));

					locationInfo.setCity(ToolUtil.changeString(allValue.get("city")));
					locationInfo.setProvince(ToolUtil.changeString(allValue.get("province")));
					locationInfo.setTown(ToolUtil.changeString(allValue.get("district")));
					locationInfo.setCounty(ToolUtil.changeString(allValue.get("street")));
					locationInfo.setDirection(ToolUtil.changeString(allValue.get("direction")));
					locationInfo.setDistance(ToolUtil.changeString(allValue.get("distance")));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//	locationInfo.setCity("济南市");
	}

	private LocationValueObject getCityInfo(String cityInfo) {
		LocationValueObject cityValue = new LocationValueObject();
		if (cityInfo != null && cityInfo.length() > 0 ) {
			try {
				Map<String,Object> allValue = new Gson().fromJson(cityInfo, new TypeToken<HashMap<String,Object>>(){}.getType());
				if (allValue.containsKey("result")) {
					Map<String,Object> locationValue = (Map<String, Object>) allValue.get("result");
					cityValue.setCityCode(ToolUtil.changeString(locationValue.get("cityCode")));
					cityValue.setAddress(ToolUtil.changeString(locationValue.get("formatted_address")));

					Map<String,Object> location = (Map<String, Object>) locationValue.get("location");
					cityValue.setJD(ToolUtil.changeDouble(location.get("lng")));
					cityValue.setWD(ToolUtil.changeDouble(location.get("lat")));

					Map<String,Object> address = (Map<String, Object>) locationValue.get("addressComponent");
					cityValue.setCity(ToolUtil.changeString(address.get("city")));
					cityValue.setProvince(ToolUtil.changeString(address.get("province")));
					cityValue.setTown(ToolUtil.changeString(address.get("district")));
					cityValue.setCounty(ToolUtil.changeString(address.get("street")));
					cityValue.setDirection(ToolUtil.changeString(address.get("direction")));
					cityValue.setDistance(ToolUtil.changeString(address.get("distance")));
				}
				else {
					cityValue.setCityCode(ToolUtil.changeString(allValue.get("cityCode")));
					cityValue.setAddress(ToolUtil.changeString(allValue.get("formatted_address")));
					locationInfo.setChooseCity(ToolUtil.changeString(allValue.get("chooseCity")));

					cityValue.setJD(ToolUtil.changeDouble(allValue.get("JD")));
					cityValue.setWD(ToolUtil.changeDouble(allValue.get("WD")));

					cityValue.setCity(ToolUtil.changeString(allValue.get("city")));
					cityValue.setProvince(ToolUtil.changeString(allValue.get("province")));
					cityValue.setTown(ToolUtil.changeString(allValue.get("district")));
					cityValue.setCounty(ToolUtil.changeString(allValue.get("street")));
					cityValue.setDirection(ToolUtil.changeString(allValue.get("direction")));
					cityValue.setDistance(ToolUtil.changeString(allValue.get("distance")));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return cityValue;
	}
/*
	public void isLocationCity() {
		LocationValueObject city = getCityInfo(locationStr);
		if (!locationInfo.getCity().contains(locationInfo.getChooseCity())) {
			AlertUtil.DialogMessage(applicationContext, city.getCity(), "定位城市与选择城市不符!是否使用定位城市?", 3 ,0.8,"使用", "不使用",
					new SureListener() {

						@Override
						public void onSureListener() {
							locationInfo.setChooseCity(locationInfo.getCity());
							saveLocationInfo();
							//		SettingInfo.getInstance().addString(applicationContext, LocationInfo.LOCATION_INFO_SETTING, locationStr);
							initInstance(applicationContext);
						}
					},null);
		}
	}*/

	public void saveLocationInfo() {

		Map<String,Object> address = new HashMap<String, Object>();
		address.put("city", locationInfo.getCity());
		address.put("province", locationInfo.getProvince());
		address.put("district", locationInfo.getTown());
		address.put("street", locationInfo.getCounty());
		address.put("direction", locationInfo.getDirection());
		address.put("distance", locationInfo.getDistance());

		address.put("JD", locationInfo.getJD());
		address.put("WD", locationInfo.getWD());
		address.put("chooseCity", locationInfo.getChooseCity());

		address.put("cityCode", locationInfo.getCityCode());
		address.put("formatted_address", locationInfo.getAddress());

		CommonShared.setString(LOCATION_INFO_SETTING, new Gson().toJson(address));
	}

	public boolean isEmpty() {
		if (CommonShared.getString(LOCATION_INFO_SETTING, "").equals("")) {
			return true;
		}
		if (locationInfo.getCity() == null || locationInfo.getCity().trim().length() == 0) {
			return true;
		}
		return false;
	}

	public static LocationInfo getInstance() {
		return instance;
	}

	public LocationValueObject getLocationInfo() {
		return locationInfo;
	}

}
