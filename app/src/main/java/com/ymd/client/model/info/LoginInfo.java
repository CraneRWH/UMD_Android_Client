package com.ymd.client.model.info;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.ymd.client.component.event.LoginEvent;
import com.ymd.client.model.bean.user.UserObject;
import com.ymd.client.utils.CommonShared;
import com.ymd.client.utils.ToolUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * 包名:cn.school.schoolbus.base.info
 * 类名:
 * 时间:2018/4/24 0024Time:11:26
 * 作者:荣维鹤
 * 功能简介:    登录人员的信息
 * 修改历史:
 */
public class LoginInfo {

    public static final String LOGIN_INFO = "loginInfo";	//登录用户的信息
    private static LoginInfo instance = null;
    private static UserObject loginInfo = new UserObject();
    private static Context applicationContext;
    private LoginInfo(Context context) {
        applicationContext = context;
    }
    public static synchronized LoginInfo getInstance() {
        return instance;
    }

    public static boolean isLogin = false;
    /**
     * 程序启动时初始化登录人员信息
     * @param context
     */
    public static void initInstance(Context context) {
        if (instance == null) {
            instance = new LoginInfo(context);
        }
        getDataHandler.sendEmptyMessage(0);
    }

    private static Handler getDataHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            resetLoginInfo();
        }
    };

    /**
     * 获取存储中的登录人员信息
     */
    private static void resetLoginInfo() {
        String loginInfoStr = CommonShared.getString(LOGIN_INFO,"");
        if (loginInfoStr != null && loginInfoStr.length() > 0) {
            setLoginInfo(loginInfoStr);
        }
    }

    /**
     * 解析登录人员信息
     * @param loginInfoStr
     */
    public static void setLoginInfo(String loginInfoStr) {
        if (ToolUtil.changeString(loginInfoStr) != null && ToolUtil.changeString(loginInfoStr).length() > 0) {
            loginInfo = new Gson().fromJson(loginInfoStr, UserObject.class);
            CommonShared.setString(LoginInfo.LOGIN_INFO, loginInfoStr);
            isLogin = true;
        } else {
            return;
        }
    }

    /**
     * 退出登录时清空用户信息
     */
    public static void exitLogin() {
        loginInfo = null;
        CommonShared.setString(LoginInfo.LOGIN_INFO, "");
        isLogin = false;
        EventBus.getDefault().post(new LoginEvent(false));
    }

    public UserObject getLoginInfo() {
        return loginInfo;
    }
}
