package com.ymd.client.model.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * @author zhl
 * @class com.ymd.client
 * @time 2018/6/13 0013 15:59
 * @description 数据库初始化类
 */
@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public final class AppDatabase {

    //数据库名称
    public static final String NAME = "ymd";
    //数据库版本号
    public static final int VERSION = 100;
}
