package com.ymd.client.model.info;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ymd.client.component.event.CityShowEvent;
import com.ymd.client.component.widget.dialog.MyDialog;
import com.ymd.client.model.bean.city.CityEntity;
import com.ymd.client.model.bean.city.LocationInfoEntity;
import com.ymd.client.model.constant.URLConstant;
import com.ymd.client.utils.AlertUtil;
import com.ymd.client.utils.CommonShared;
import com.ymd.client.utils.ToolUtil;
import com.ymd.client.web.WebUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

/**
 * 定位城市的类
 * @author rongweihe
 *
 */
public class LocationInfo implements java.io.Serializable{

	public final static String LOCATION_INFO_SETTING = "locationInfo";
	public final static String CITYS_INFO_SETTING = "citysInfo";
	public final static String CITY_CHOOSE_SETTING = "cityChooseInfo";
	private static LocationInfo instance = null;
	private static LocationInfoEntity locationInfo = new LocationInfoEntity();
	private static CityEntity chooseCity = new CityEntity();
	private static List<CityEntity> allCitys;
	private Context applicationContext;

	public static String locationStr = "";

	private LocationInfo(Context context) {
		this.applicationContext = context;
	}
	public static void initInstance(Context context) {
		if (instance == null) {
			instance = new LocationInfo(context);
		}
		resetCitysData();
		setLocationInfoData();
		setChooseCityInfo();
	}

	private static void resetCitysData() {
		String cityInfo = CommonShared.getString(CITYS_INFO_SETTING, "");

		if (cityInfo != null && cityInfo.length() > 0 ) {
			allCitys = new Gson().fromJson(cityInfo, new TypeToken<List<CityEntity>>(){}.getType());
		}
	}

	public void refreshCitiesData() {
		resetCitysData();
	}

	private static void setChooseCityInfo() {
		String cityInfo = CommonShared.getString(CITY_CHOOSE_SETTING, "");
		if (cityInfo != null && cityInfo.length() > 0 ) {
			chooseCity = new Gson().fromJson(cityInfo, CityEntity.class);
		} else {
			chooseCity.setCityID(0);
			chooseCity.setCityName("北京市");
		}
	}

	@SuppressWarnings("unchecked")
	private static void setLocationInfoData() {
		String cityInfo = CommonShared.getString(LOCATION_INFO_SETTING, "");

		if (cityInfo != null && cityInfo.length() > 0 ) {
			locationInfo = new Gson().fromJson(cityInfo, LocationInfoEntity.class);
		}
		//	locationInfo.setCity("济南市");
	}

	private LocationInfoEntity getCityInfo(String cityInfo) {
		LocationInfoEntity cityValue = new LocationInfoEntity();
		if (cityInfo != null && cityInfo.length() > 0 ) {
			try {

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return cityValue;
	}
	public void isLocationCity(Context context) {
		try {
			if (chooseCity!= null && !locationInfo.getCity().contains(chooseCity.getCityName())) {
				AlertUtil.DialogMessage(context, locationInfo.getCity(), "定位城市与选择城市不符!是否使用定位城市?", 3, 0.8, "使用", "不使用",
						new MyDialog.SureListener() {

							@Override
							public void onSureListener() {
								CityEntity cityEntity = new CityEntity();
								cityEntity.setCityID(ToolUtil.changeLong(locationInfo.getCityCode()));
								cityEntity.setCityName(locationInfo.getCity());
								LocationInfo.chooseCity = cityEntity;
								CommonShared.setString(CITY_CHOOSE_SETTING, new Gson().toJson(chooseCity));
								locationChangeChooseCity();
							}
						}, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setLocationInfo(LocationInfoEntity info) {
		locationInfo = info;
		CommonShared.setString(LOCATION_INFO_SETTING, new Gson().toJson(info));
		if (ToolUtil.changeInteger(chooseCity.getCityID()) == 0)
			locationChangeChooseCity();
	}

	public void saveLocationInfo() {


		CommonShared.setString(LOCATION_INFO_SETTING, new Gson().toJson(locationInfo));
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

	public LocationInfoEntity getLocationInfo() {
		return locationInfo;
	}

	public CityEntity getChooseCity() {
		return chooseCity;
	}

	public void setChooseCity(CityEntity chooseCity) {
		LocationInfo.chooseCity = chooseCity;
		CommonShared.setString(CITY_CHOOSE_SETTING, new Gson().toJson(chooseCity));
	/*	if (changeListener!= null) {
			changeListener.onChange(chooseCity);
		}*/
		EventBus.getDefault().post(new CityShowEvent(true));
	}

	public List<CityEntity> getAllCitys() {
		return allCitys;
	}

	public void setAllCitys(List<CityEntity> allCitys) {
		LocationInfo.allCitys = allCitys;
	}

	private void locationChangeChooseCity() {
		for (CityEntity item : allCitys) {
			if (item.getCityName().contains(locationInfo.getCity()) || locationInfo.getCity().contains(item.getCityName())) {
				chooseCity = item;
				chooseCity.setCountyName(locationInfo.getCounty());
				CommonShared.setString(CITY_CHOOSE_SETTING, new Gson().toJson(chooseCity));
				/*	if (changeListener != null) {
						changeListener.onChange(chooseCity);
					}*/
				EventBus.getDefault().post(new CityShowEvent(true));
				break;
			}
		}
	}

//	private OnCityChangeListener changeListener;

/*	public OnCityChangeListener getChangeListener() {
		return changeListener;
	}

	public void setChangeListener(OnCityChangeListener changeListener) {
		this.changeListener = changeListener;
	}*/

	public interface OnCityChangeListener {
		void onChange(CityEntity cityEntity);
	}
}
