package com.ymd.client.component.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 作者:rongweihe
 * 日期:2018/8/25  时间:7:54
 * 描述:
 * 修改历史:
 */
public class AppFragmentPageAdapter extends FragmentPagerAdapter {
    public List<Fragment> mFragmentList;

    public AppFragmentPageAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        mFragmentList=fragmentList;
    }

    @Override
    public Fragment getItem(int position) {

        return mFragmentList==null?null:mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList==null?0:mFragmentList.size();
    }
}
