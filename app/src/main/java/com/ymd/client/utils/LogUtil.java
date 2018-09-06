package com.ymd.client.utils;

import android.util.Log;


/**
 * package:cn.school.CranePay.utils
 * 功能简介：日志工具类，有日志开关，还可以在里面设置堆栈追踪
 * 修改历史：
 */
public class LogUtil {

    private static final String TAG = "##UMD";
    private static final String FILETER_STRING = "##";
    // 是否需要打印debug，可以在application的onCreate函数里面初始化
    public static boolean isDebug = true;

    private LogUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static final String TAG_BUS = "##CranePay";

    public static void showD(String msg) {
        if (isDebug) {
            if (msg == null) {
                msg = "null";
            }
            Log.d(TAG_BUS, msg);
        }
    }

    public static void showE(String msg) {
        if (isDebug) {
            if (msg == null) {
                msg = "null";
            }
            Log.e("#################UMD   ", msg);
        }
    }

    public static void showW(String msg) {
        if (isDebug) {
            if (msg == null) {
                msg = "null";
            }
            Log.w("#################UMD   ", msg);
        }
    }

    // 下面默认tag的函数
    public static void v(String msg) {
        if (isDebug) {
            if (msg == null) {
                msg = "null";
            }
            Log.v(TAG, msg);
        }
    }

    /**
     * @param msg 实际打印的msg,是根据tr是否为null判断的,若不为null,则打印msg+", msg: "+tr.getMessage().;若为null,则不拼接,直接打印msg.
     * @param tr
     */
    public static void v(String msg, Throwable tr) {
        if (isDebug) {
            String temp = msg;
            if (tr != null) {
                temp = msg + ", msg: " + tr.getMessage();
            }
            Log.v(TAG, temp, tr);
        }
    }

    public static void d(String msg) {
        if (isDebug) {
            if (msg == null) {
                msg = "null";
            }
            Log.d(TAG, msg);
        }
    }

    /**
     * @param msg 实际打印的msg,是根据tr是否为null判断的,若不为null,则打印msg+", msg: "+tr.getMessage().;若为null,则不拼接,直接打印msg.
     * @param tr
     */
    public static void d(String msg, Throwable tr) {
        if (isDebug) {
            String temp = msg;
            if (tr != null) {
                temp = msg + ", msg: " + tr.getMessage();
            }
            Log.d(TAG, temp, tr);
        }
    }

    public static void i(String msg) {
        if (msg == null) {
            msg = "null";
        }
        Log.i(TAG, msg);
    }

    /**
     * @param msg 实际打印的msg,是根据tr是否为null判断的,若不为null,则打印msg+", msg: "+tr.getMessage().;若为null,则不拼接,直接打印msg.
     * @param tr
     */
    public static void i(String msg, Throwable tr) {
        String temp = msg;
        if (tr != null) {
            temp = msg + ", msg: " + tr.getMessage();
        }
        Log.i(TAG, temp, tr);
    }

    public static void w(String msg) {
        if (msg == null) {
            msg = "null";
        }
        Log.w(TAG, msg);
    }

    /**
     * @param msg 实际打印的msg,是根据tr是否为null判断的,若不为null,则打印msg+", msg: "+tr.getMessage().;若为null,则不拼接,直接打印msg.
     * @param tr
     */
    public static void w(String msg, Throwable tr) {
        String temp = msg;
        if (tr != null) {
            temp = msg + ", msg: " + tr.getMessage();
        }
        Log.w(TAG, temp, tr);
    }

    public static void e(String msg) {
        if (msg == null) {
            msg = "null";
        }
        Log.e(TAG, msg);
    }

    /**
     * @param msg 实际打印的msg,是根据tr是否为null判断的,若不为null,则打印msg+", msg: "+tr.getMessage().;若为null,则不拼接,直接打印msg.
     * @param tr
     */
    public static void e(String msg, Throwable tr) {
        String temp = msg;
        if (tr != null) {
            temp = msg + ", msg: " + tr.getMessage();
        }
        Log.e(TAG, temp, tr);
    }

    // 下面是传入自定义tag的函数
    public static void v(String tag, String msg) {
        if (isDebug) {
            if (msg == null) {
                msg = "null";
            }
        //    AliLogUtil.addLogs(AliLogUtil.getLog(AliLogUtil.LOG_LEVEL_VERBOSE, tag, AliLogUtil.getmyPid(), AliLogUtil.getmyTid(), AliLogUtil.getPhoneModel(), msg));
            Log.v(FILETER_STRING + tag, msg);
        }
    }

    /**
     * @param tag
     * @param msg 实际打印的msg,是根据tr是否为null判断的,若不为null,则打印msg+", msg: "+tr.getMessage().;若为null,则不拼接,直接打印msg.
     * @param tr
     */
    public static void v(String tag, String msg, Throwable tr) {
        if (isDebug) {
            String temp = msg;
            if (tr != null) {
                temp = msg + ", msg: " + tr.getMessage();
            }
        /*    AliLogUtil.addLogs(AliLogUtil.getLog(AliLogUtil.LOG_LEVEL_VERBOSE, tag, AliLogUtil.getmyPid(), AliLogUtil.getmyTid(), AliLogUtil.getPhoneModel(), msg
                    + "throwable:" + tr.getMessage() + "message:" + AliLogUtil.getThrowableMessage(tr)));*/
            Log.v(FILETER_STRING + tag, temp, tr);
        }
    }

    public static void d(String tag, String msg) {
        if (isDebug) {
            if (msg == null) {
                msg = "null";
            }
        //    AliLogUtil.addLogs(AliLogUtil.getLog(AliLogUtil.LOG_LEVEL_DEBUG, tag, AliLogUtil.getmyPid(), AliLogUtil.getmyTid(), AliLogUtil.getPhoneModel(), msg));
            Log.d(FILETER_STRING + tag, msg);
        }
    }

    /**
     * @param tag
     * @param msg 实际打印的msg,是根据tr是否为null判断的,若不为null,则打印msg+", msg: "+tr.getMessage().;若为null,则不拼接,直接打印msg.
     * @param tr
     */
    public static void d(String tag, String msg, Throwable tr) {
        if (isDebug) {
            String temp = msg;
            if (tr != null) {
                temp = msg + ", msg: " + tr.getMessage();
            }
        /*    AliLogUtil.addLogs(AliLogUtil.getLog(AliLogUtil.LOG_LEVEL_DEBUG, tag, AliLogUtil.getmyPid(), AliLogUtil.getmyTid(), AliLogUtil.getPhoneModel(), msg
                    + "throwable:" + tr.getMessage() + "message:" + AliLogUtil.getThrowableMessage(tr)));*/
            Log.d(FILETER_STRING + tag, temp, tr);
        }
    }

    public static void i(String tag, String msg) {
        if (msg == null) {
            msg = "null";
        }
    /*    AliLogUtil.addLogs(AliLogUtil.getLog(AliLogUtil.LOG_LEVEL_INFO, tag, AliLogUtil.getmyPid(), AliLogUtil.getmyTid(), AliLogUtil.getPhoneModel(), msg));*/
        Log.i(FILETER_STRING + tag, msg);
    }

    /**
     * @param tag
     * @param msg 实际打印的msg,是根据tr是否为null判断的,若不为null,则打印msg+", msg: "+tr.getMessage().;若为null,则不拼接,直接打印msg.
     * @param tr
     */
    public static void i(String tag, String msg, Throwable tr) {
        String temp = msg;
        if (tr != null) {
            temp = msg + ", msg: " + tr.getMessage();
        }
    /*    AliLogUtil.addLogs(AliLogUtil.getLog(AliLogUtil.LOG_LEVEL_INFO, tag, AliLogUtil.getmyPid(), AliLogUtil.getmyTid(), AliLogUtil.getPhoneModel(), msg
                + "throwable:" + tr.getMessage() + "message:" + AliLogUtil.getThrowableMessage(tr)));*/
        Log.i(FILETER_STRING + tag, temp, tr);
    }

    public static void w(String tag, String msg) {
        if (msg == null) {
            msg = "null";
        }
    /*    AliLogUtil.addLogs(AliLogUtil.getLog(AliLogUtil.LOG_LEVEL_WARN, tag, AliLogUtil.getmyPid(), AliLogUtil.getmyTid(), AliLogUtil.getPhoneModel(), msg));*/
        Log.w(FILETER_STRING + tag, msg);
    }

    /**
     * @param tag
     * @param msg 实际打印的msg,是根据tr是否为null判断的,若不为null,则打印msg+", msg: "+tr.getMessage().;若为null,则不拼接,直接打印msg.
     * @param tr
     */
    public static void w(String tag, String msg, Throwable tr) {
        String temp = msg;
        if (tr != null) {
            temp = msg + ", msg: " + tr.getMessage();
        }
    /*    AliLogUtil.addLogs(AliLogUtil.getLog(AliLogUtil.LOG_LEVEL_WARN, tag, AliLogUtil.getmyPid(), AliLogUtil.getmyTid(), AliLogUtil.getPhoneModel(), msg
                + "throwable:" + tr.getMessage() + "message:" + AliLogUtil.getThrowableMessage(tr)));*/
        Log.w(FILETER_STRING + tag, temp, tr);
    }

    public static void e(String tag, String msg) {
        if (msg == null) {
            msg = "null";
        }
    /*    AliLogUtil.addLogs(AliLogUtil.getLog(AliLogUtil.LOG_LEVEL_ERROR, tag, AliLogUtil.getmyPid(), AliLogUtil.getmyTid(), AliLogUtil.getPhoneModel(), msg));*/
        Log.e(FILETER_STRING + tag, msg);
    }

    /**
     * @param tag
     * @param msg 实际打印的msg,是根据tr是否为null判断的,若不为null,则打印msg+", msg: "+tr.getMessage().;若为null,则不拼接,直接打印msg.
     * @param tr
     */
    public static void e(String tag, String msg, Throwable tr) {
        String temp = msg;
        if (tr != null) {
            temp = msg + ", msg: " + tr.getMessage();
        }
    /*    AliLogUtil.addLogs(AliLogUtil.getLog(AliLogUtil.LOG_LEVEL_ERROR, tag, AliLogUtil.getmyPid(), AliLogUtil.getmyTid(), AliLogUtil.getPhoneModel(), msg
                + "throwable:" + tr.getMessage() + "message:" + AliLogUtil.getThrowableMessage(tr)));*/
        Log.e(FILETER_STRING + tag, temp, tr);
    }
}