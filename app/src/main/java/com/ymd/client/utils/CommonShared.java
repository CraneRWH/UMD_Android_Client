package com.ymd.client.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.ymd.client.UApplication;

/***
 * @modify 日期 作者名 修改内容
 * @Description 该类描述 CommonShared 工具类
 */
public class CommonShared {
	private static SharedPreferences sharedPreferences = UApplication.getInstance().getSharedPreferences(UApplication.getInstance().getPackageName()+"_preferences", Context.MODE_PRIVATE);
	private static SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(UApplication.getInstance());
	private static Editor editor = sharedPreferences.edit();

	public static  final String LOGIN_TOKEN = "loginToken";

	public static String getString(String key, String defaultValue) {
		return sharedPreferences.getString(key, defaultValue);
	}

	public static String getString(String key, String defValue, boolean defaultPreference) {
		if (defaultPreference) {
			return defaultSharedPreferences.getString(key, defValue);
		}
		return sharedPreferences.getString(key, defValue);
	}

	public static int getInt(String key, int defaultValue) {
		return sharedPreferences.getInt(key, defaultValue);
	}

	public static int getInt(String key, int defValue, boolean defaultPreference) {
		if (defaultPreference) {
			return defaultSharedPreferences.getInt(key, defValue);
		}
		return sharedPreferences.getInt(key, defValue);
	}

	public static boolean getBoolean(String key, boolean defaultValue) {
		return sharedPreferences.getBoolean(key, defaultValue);
	}

	public static boolean getBoolean(String key, boolean defValue, boolean defaultPreference) {
		if (defaultPreference) {
			return defaultSharedPreferences.getBoolean(key, defValue);
		}
		return sharedPreferences.getBoolean(key, defValue);
	}

	public static void setString(String key, String value) {
		editor.putString(key, value);
		editor.commit();

	}

	public static void setInt(String key, int value) {
		editor.putInt(key, value);
		editor.commit();
	}

	public static void setBoolean(String key, boolean value) {
		editor.putBoolean(key, value);
		editor.commit();
	}

	/**
	 * 清除保存的数据
	 * 
	 * @param key
	 */
	public static void remove(String key) {
		editor.remove(key);
		editor.commit();
	}

	public static boolean isContains(String key, boolean defaultPreference) {
		if (defaultPreference) {
			return defaultSharedPreferences.contains(key);
		}
		return isContains(key);
	}

	/**
	 * 判断是否包含特定KEY 的数据
	 * 
	 * @param key
	 * @return
	 */
	public static boolean isContains(String key) {
		return sharedPreferences.contains(key);
	}
}
