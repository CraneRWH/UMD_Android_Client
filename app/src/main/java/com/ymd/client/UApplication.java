package com.ymd.client;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.bumptech.glide.Glide;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.ymd.client.model.bean.User;
import com.ymd.client.model.info.LocationInfo;
import com.ymd.client.model.info.LoginInfo;
import com.ymd.client.utils.ToolUtil;
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

    private static UApplication me;

    public static UApplication getInstance() {
        return me;
    }

    static {//static 代码段可以防止内存泄露
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.white, R.color.gray_dark);//全局设置主题颜色
                return new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Translate);//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Scale);
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        // Application 初始化
        com.shell.SdkManager.initSdkManager(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initGlide(this);
        me = this;
        //初始化
        init();
    }
    private void init() {
        app = getApplicationContext();

        MultiDex.install(this);

        //dbflow数据库初始化
        FlowManager.init(this);

        LoginInfo.initInstance(getApplicationContext());
        WebUtil.initInstance(getApplicationContext());
        LocationInfo.initInstance(getApplicationContext());
        initUser();

        ToolUtil.setContext(getApplicationContext());

        UMConfigure.init(this,"5b8cd3c1f29d9853ab000013"
                ,"umeng",UMConfigure.DEVICE_TYPE_PHONE,"");

        PlatformConfig.setQQZone("101534769", "a872b37699f21f77be8e3dadcd73d1e4");
        PlatformConfig.setWeixin("wxd7ea0a0c6e613465", "b3ca6c9b45e5e763aecf00297e95db36");
        //豆瓣RENREN平台目前只能在服务器端配置
       // PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
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

    public static void initGlide(Context cxt) {
        final Context context = cxt.getApplicationContext();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //下面用到的url最好是不存在的，目的只是为了初始化Glide
                Glide.with(context).load("/sdcard/xxx.jpg").into(1, 1);
                Glide.with(context).load("http://sdcard/xxx.jpg").into(1, 1);
            }
        }, "init_glide").start();

    }

}
