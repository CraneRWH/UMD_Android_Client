package com.ymd.client.model.bean;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.ymd.client.model.db.AppDatabase;

import java.io.Serializable;

/**
 * @author zhl
 * @class com.ymd.client.model.bean
 * @time 2018/8/16 0016 14:38
 * @description
 */
@Table(database = AppDatabase.class)
public class User extends BaseModel implements Serializable {

    @PrimaryKey(autoincrement = true)
    public Long id;

    @Column
    public String name;
}
