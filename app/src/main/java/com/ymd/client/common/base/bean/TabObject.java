package com.ymd.client.common.base.bean;

/**
 * 作者:rongweihe
 * 日期:2018/8/26  时间:10:50
 * 描述:  选项卡类
 * 修改历史:
 */
public class TabObject {
    private String name;
    private int position;

    public TabObject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
