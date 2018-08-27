package com.ymd.client.component.activity.order;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 作者:rongweihe
 * 日期:2018/8/26  时间:7:52
 * 描述:
 * 修改历史:
 */
public class PageFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    private FragmentManager fm;
    public PageFragmentAdapter(FragmentManager fm,List<Fragment> fragmentList){
        super(fm);
        this.fragmentList=fragmentList;
        this.fm=fm;
    }
    @Override
    public Fragment getItem(int idx) {
        return fragmentList.get(idx%fragmentList.size());
    }
    @Override
    public int getCount() {
        return fragmentList.size();
    }
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;  //没有找到child要求重新加载
    }  }
