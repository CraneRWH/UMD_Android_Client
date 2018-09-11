package com.ymd.client.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
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
		return -1;
	}

	public static long changeLong(Object object) {
		if (object != null && object.toString().length() > 0 ) {
			try {
				return Long.parseLong(object.toString().trim());
			} catch(Exception e){
				LogUtil.d(object + "不能转化为long型");
			}
		}
		return -1l;
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

}