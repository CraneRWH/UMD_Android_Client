package com.ymd.client.common.base.service;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.Manifest;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ymd.client.model.info.LocationInfo;
import com.ymd.client.utils.CommonShared;
import com.ymd.client.utils.LogUtil;
import com.ymd.client.utils.ToastUtil;
import com.ymd.client.utils.ToolUtil;

/**
 * @author 荣维鹤
 * @desc 定位服务
 *
 */
public class AndroidLocationService extends Service implements LocationListener {

	private LocationManager locationManager;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		InitLocation();
	}

	private void initLocationManage() {
		LocationManager locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		// 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
		boolean gps = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		// 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
		boolean network = locationManager
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		if (network) {
			if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
				// TODO: Consider calling
				//    ActivityCompat#requestPermissions
				// here to request the missing permissions, and then overriding
				//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
				//                                          int[] grantResults)
				// to handle the case where the user grants the permission. See the documentation
				// for ActivityCompat#requestPermissions for more details.
				return;
			}
			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, 0, 0, this);
		} else if (gps) {
			locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 0, 0, this);
		}
		else {
			LogUtil.showD("无法定位");
			ToastUtil.ToastMessage(getApplicationContext(),"定位失败",ToastUtil.WRONG);
		//	AlertUtil.FailDialog(this, "定位失败");
		}
	}

	private void InitLocation() {

		LocationClient mLocationClient = new LocationClient(
				this.getApplicationContext()); // 声明LocationClient类
		MyLocationListener myListener = new MyLocationListener();
		mLocationClient.registerLocationListener(myListener); // 注册监听函数

		LocationClientOption mOption = new LocationClientOption();
		// int span=1000;
		// option.setScanSpan(span);
		mOption.setLocationMode(LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		mOption.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
		mOption.setScanSpan(3000);//可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
		mOption.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
		mOption.setIsNeedLocationDescribe(true);//可选，设置是否需要地址描述
		mOption.setNeedDeviceDirect(false);//可选，设置是否需要设备方向结果
		mOption.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		mOption.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
		mOption.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		mOption.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		mOption.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
		mOption.setOpenGps(true);//可选，默认false，设置是否开启Gps定位
		mOption.setIsNeedAltitude(true);//可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用

		mLocationClient.setLocOption(mOption);
		if(!mLocationClient.isStarted()){
			mLocationClient.start();
		}else{
			mLocationClient.requestLocation();
		}
	}

	private class MyLocationListener extends BDAbstractLocationListener {

		@Override
		public void onReceiveLocation(BDLocation blocation) {
			System.out.println("WIFI定位   " + blocation.getAddrStr() + "  " + blocation.getCity()
					+ "  " + blocation.getCountry() + "  " + blocation.getLocType()
					+ "  " + blocation.getProvince() );
			if (blocation == null) {
				initLocationManage();
			}
			else {
				Address address = new Address(null);
				// GetAddr(location.getLatitude(),location.getLongitude());
				address.setLocality(blocation.getCity());
				System.out.println("定位失败  " + blocation.getCity());
				address.setCountryName(blocation.getCountry());
				address.setLatitude(blocation.getLatitude());
				address.setLongitude(blocation.getLongitude());
				Message msg = Message.obtain(successHandler);
				msg.obj = address;
				successHandler.sendMessage(msg);
			}
		}

	}


	@Override
	public boolean stopService(Intent name) {
		return super.stopService(name);
	}

	@Override
	public void onLocationChanged(Location location) {
		// Log.d(TAG, "Get the current position \n" + location);
		if (location != null) {
			reverseGeocode(location);
		} else {
			System.out.println("未获得location");
		}
		// start(location);

		// 如果只是需要定位一次，这里就移除监听，停掉服务。如果要进行实时定位，可以在退出应用或者其他时刻停掉定位服务。
		locationManager.removeUpdates(this);
		stopSelf();
	}

	public void reverseGeocode(final Location location) {
		new Thread() {
			public void run() {
				try {
					updateWithNewLocation(location);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	public Handler successHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			final Address address = (Address) msg.obj;
			try {
				// System.out.println(address);
				LocationInfo.initInstance(getApplicationContext());
				Map<String, Object> cityResult = new Gson().fromJson(CommonShared.getString(
								LocationInfo.LOCATION_INFO_SETTING, ""), new TypeToken<Map<String,Object>>(){}.getType());
				cityResult.put("city", address.getLocality());
				for (int i = 0; address.getAddressLine(i) != null; i++) {
					String addressLine = address.getAddressLine(i);
					cityResult.put("formatted_address", addressLine);
				}
				//	cityResult.put("province", address.getAdminArea());
				if (address.getLatitude() != 0 && address.getLongitude() != 0) {
					cityResult.put("JD", address.getLongitude());
					cityResult.put("WD", address.getLatitude());
				}
				String chooseName = LocationInfo.getInstance()
						.getLocationInfo().getChooseCity()/*
				 * ToolUtil.changeString(city
				 * .get("city"))
				 */;
				if (chooseName != null && chooseName.trim().length() > 0) {
					cityResult.put("chooseCity", chooseName);
				} else {
					cityResult.put("chooseCity", address.getLocality());
				}
				String localName = address.getLocality();
				//	if (LocationInfo.getInstance().isEmpty()) {
				// 保存定位城市并初始化定位信息
				CommonShared.setString(
						LocationInfo.LOCATION_INFO_SETTING,
						new Gson().toJson(cityResult));
				LocationInfo.initInstance(getApplicationContext());
				/*} else
						 * if
						 * (!localityName.contains(LocationInfo.getInstance().
						 * getLocationInfo().getCity()))
						 {
					LocationInfo.getInstance().getLocationInfo()
							.setCity(chooseName);
					LocationInfo.getInstance().saveLocationInfo();
					LocationInfo.initInstance(getApplicationContext());
				}*/

				Intent intent = new Intent();
				intent.setAction("locationAction");
				intent.putExtra("NAME", localName);
				sendBroadcast(intent);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	/**
	 * 更新location
	 *
	 * @param location
	 * @return cityName
	 */
	private void updateWithNewLocation(final Location location) throws Exception{
		System.out.println("location  " + location);
		double lat = 0;
		double lng = 0;
		Geocoder geocoder = new Geocoder(this);
		List<Address> addList = null;
		if (location != null) {
			lat = location.getLatitude();
			lng = location.getLongitude();
		} else {
			LogUtil.showD("无法获取地理信息");
		}

		try {
			addList = geocoder.getFromLocation(lat, lng, 5); // 解析经纬度
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (!addList.isEmpty()) {
			System.out.println("定位成功");
			Address address = addList.get(0);
			address.setLatitude(location.getLatitude());
			address.setLongitude(location.getLongitude());
			Message msg = Message.obtain(successHandler);
			msg.obj = address;
			successHandler.sendMessage(msg);
		} else {
			failHandler.sendEmptyMessage(0);
			/*Address address = new Address(null);
			address.setLocality("北京");
			System.out.println("定位失败  " + address.getLocality());
			address.setLatitude(location.getLatitude());
			address.setLongitude(location.getLongitude());
			Message msg = Message.obtain(successHandler);
			msg.obj = address;
			successHandler.sendMessage(msg);*/
		}
	}

	private Handler failHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			InitLocation();
		}
	};
}
