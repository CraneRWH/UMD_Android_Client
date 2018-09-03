package com.ymd.client;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.ymd.client.model.bean.User;
import com.ymd.client.web.WebUtil;

import java.util.List;

//                       _oo0oo_
//                      o8888888o
//                      88" . "88
//                      (| -_- |)
//                      0\  =  /0
//                    ___/`---'\___
//                  .' \\|     |// '.
//                 / \\|||  :  |||// \
//                / _||||| -:- |||||- \
//               |   | \\\  -  /// |   |
//               | \_|  ''\---/''  |_/ |
//               \  .-\__  '-'  ___/-. /
//             ___'. .'  /--.--\  `. .'___
//          ."" '<  `.___\_<|>_/___.' >' "".
//         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//         \  \ `_.   \_ __\ /__ _/   .-` /  /
//     =====`-.____`.___ \_____/___.-`___.-'=====
//                       `=---='
//
//
//     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//
//               佛祖保佑         永无BUG
/**
 * @author zhl
 * @class com.ymd.client
 * @time 2018/8/16 0016 10:53
 * @description
 */
public class UApplication extends MultiDexApplication {

    private static Context app;
    public static User user = null;
    public static boolean islogin = false;

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化
        init();
    }

    private void init() {
        app = getApplicationContext();

        MultiDex.install(this);

        //dbflow数据库初始化
        FlowManager.init(this);

        WebUtil.initInstance(getApplicationContext());
        initUser();
    }

    private void initUser() {
        //同步检索
        try {
            List<User> users = SQLite.select().from(User.class)
                    .queryList();
            if (users == null || users.size() == 0) {
                islogin = false;
            } else {
                islogin = true;
                this.user = users.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            islogin = false;
        }
    }

    /**
     * 获取context
     *
     * @return
     */
    public static Context getGobalApplication() {
        return app;
    }
}
