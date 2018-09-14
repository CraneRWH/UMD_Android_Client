package com.ymd.client.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.ymd.client.model.info.LocationInfo;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.UUID;

/**
 * 自定义的工具类
 * @author 荣维鹤
 *
 */
public class ToolUtil {
	public static Context context;

	public static void setContext(Context c) {
		context = c;
	}


	private static final double EARTH_RADIUS = 6378.137;//地球半径
	public static double rad(double d)
	{
		return d * Math.PI / 180.0;
	}
	public static double GetDistance(double WD, double JD)
	{
		double radLat1 = rad(WD);
		double radLat2 = rad(LocationInfo.getInstance().getLocationInfo().getLatitude());
		double a = radLat1 - radLat2;
		double b = rad(JD) - rad(LocationInfo.getInstance().getLocationInfo().getLongitude());

		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
				Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	public static String Distance(double WD, double JD) {
		System.out.println("WD  " + WD);
		System.out.println("JD  " + JD);
		System.out.println("WDs  " + LocationInfo.getInstance().getLocationInfo().getLatitude());
		System.out.println("JDs  " + LocationInfo.getInstance().getLocationInfo().getLongitude());
		if (LocationInfo.getInstance().getLocationInfo().getLatitude()==0 && LocationInfo.getInstance().getLocationInfo().getLongitude() == 0) {
			return "0";
		}
		double a, b, R;
		R = 6378137; // 地球半径
		WD = WD * Math.PI / 180.0;
		double WD2;
		WD2 = LocationInfo.getInstance().getLocationInfo().getLatitude() * Math.PI / 180.0;
		a = WD - WD2;
		b = (JD - LocationInfo.getInstance().getLocationInfo().getLongitude()) * Math.PI / 180.0;
		double d;
		double sa2, sb2;
		sa2 = Math.sin(a / 2.0);
		sb2 = Math.sin(b / 2.0);
		d = 2
				* R
				* Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(WD)
				* Math.cos(LocationInfo.getInstance().getLocationInfo().getLatitude()) * sb2 * sb2));
		return changeString(d);
	}
	//获取当前时间的自定义格式
	public static String getCurrentTime(String format) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
		String currentTime = sdf.format(date);
		return currentTime;
	}

	//获取当前时间的格式为 yyyy-MM-dd HH:mm:ss
	public static String getCurrentTime() {
		return getCurrentTime("yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 将数据转化为字符串
	 * @param object
	 * @return
	 */
	public static String changeString(Object object) {
		if (object != null && !object.equals("null")) {
			return object.toString();
		}
		return "";
	}

	public static int changeInteger(Object object) {
		if (object != null && object.toString().length() > 0 ) {
			try {
				return Integer.parseInt(object.toString().trim());
			} catch(Exception e){
				LogUtil.d(object + "不能转化为int型");
			}
		}
		return 0;
	}

	public static long changeLong(Object object) {
		if (object != null && object.toString().length() > 0 ) {
			try {
				return Long.parseLong(object.toString().trim());
			} catch(Exception e){
				LogUtil.d(object + "不能转化为long型");
			}
		}
		return 0L;
	}

	public static double changeDouble(Object object) {
		if (object != null) {
			try {
				return Double.parseDouble(object.toString());
			} catch(Exception e){
				LogUtil.d(object + "不能转化为double型");
			}
		}
		return 0D;
	}

	public static float changeFloat(Object object) {
		if (object != null) {
			try {
				return Float.parseFloat(object.toString());
			} catch(Exception e){
				LogUtil.d(object + "不能转化为float型");
			}
		}
		return 0f;
	}

	public static boolean changeBoolean(Object object) {
		if ( object != null ) {
			try {
				if (object.toString().equals("0")) {
					return false;
				} else if (object.toString().equals("1")) {
					return true;
				}
				return Boolean.parseBoolean(object.toString());
			} catch(Exception e) {
				LogUtil.d(object + "不能转化为boolean型");
			}
		}

		return false;
	}

	public static String getUUID(){
		String temp = UUID.randomUUID().toString();
		return temp.replaceAll("-", "");
	}
/*
	public static Handler toLoginHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Intent intent = new Intent();
			intent.setAction("com.mnet.TO_LOGIN");
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
		}
	};*/

	/**
	 * 将生日转换为年龄
	 * @param birthDay
	 * @return
	 */
	public static int changeBirthDay2Age(Object birthDay) {
		SimpleDateFormat yearFormat = new SimpleDateFormat(
				"yyyy");
		try {
			if (null != birthDay) {
				Date bDate = yearFormat.parse(birthDay.toString());
				int startYear = Integer
						.parseInt(yearFormat.format(bDate));
				int nowYear = Integer.parseInt(yearFormat.format(new Date()));
				return nowYear - startYear;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 将生日转换为年龄
	 * @param sex
	 * @return
	 */
	public static String change2Sex(Object sex) {
		if (sex == null || sex.equals("null")) {
			return "未知";
		}
		if(changeInteger(sex) == 0) {
			return "男";
		} else if (changeInteger(sex) == 1) {
			return "女";
		} else {
			return "未知";
		}
	}

	/**
	 * 修改事件格式
	 * @param f
	 * @param date
	 * @return
	 */
	public static String dateFormat(String f, Object date) {
		try {

			SimpleDateFormat formatDate = new SimpleDateFormat(f);
			SimpleDateFormat lsdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date departTime = lsdFormat.parse(date.toString());
			return formatDate.format(departTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 修改事件格式,"hh:mm"
	 * @param date
	 * @return
	 */
	public static String dateFormat(Object date) {
		return dateFormat("hh:mm", date);
	}

	/**
	 * 修改事件格式,"hh:mm"
	 * @param time
	 * @return
	 */
	public static String timeFormat(String time) {
		if (time == null || time.equals("null")) {
			return "";
		}
		if (time.length() >=5) {
			return time.substring(0, 5);
		} else {
			return  time;
		}
	}

	/**
	 * 修改日期格式为“MM/dd”
	 * @param date
	 * @return
	 */
	public static String stringDateFormat(Object date) {
		if (date == null || date.equals("")) {
			return "";
		}
		String dateStr = changeString(date);
		String date1 = dateStr.substring(5,dateStr.length());
		return date1.replace("-","/");
	}

	/**
	 * 获取当前ip地址
	 * @return
	 */
	@SuppressLint("LongLogTag")
	public static String getLocalIpAddress()
	{
		try
		{
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();)
			{
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();)
				{
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress())
					{
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		}
		catch (SocketException ex)
		{
			Log.e("WifiPreference IpAddress", ex.toString());
		}
		return null;
	}

	/**
	 * 包含大小写字母及数字且在6-12位
	 * 是否包含
	 *
	 * @param str
	 * @return
	 */
	public static boolean isLetterDigit(String str) {
		boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
		boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
		for (int i = 0; i < str.length(); i++) {
			if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
				isDigit = true;
			} else if (Character.isLetter(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
				isLetter = true;
			}
		}
		String regex = "^[a-zA-Z0-9]{6,12}$";
		boolean isRight = isDigit && isLetter && str.matches(regex);
		return isRight;
	}

	public static String double2Point(double data) {
		DecimalFormat df = new DecimalFormat("#.00");
		return df.format(data);
	}

}