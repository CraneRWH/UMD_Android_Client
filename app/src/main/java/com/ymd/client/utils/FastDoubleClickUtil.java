package com.ymd.client.utils;



/**
 * 作者:rongweihe
 * 日期:2018/8/24
 * 描述:    防止短时间内多次点击
 * 修改历史:
 */
public class FastDoubleClickUtil {

	private static long lastClickTime;
	
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if ( 0 < timeD && timeD < 300) {  
            return true;  
        }  
        lastClickTime = time;  
        return false;  
    }
}
