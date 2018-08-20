package com.ymd.client.component.widget.viewPager;

import android.database.DataSetObserver;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;


/**
 *
 *   作者:rongweihe
 *   日期:2018/8/20
 *   描述:    图片选项卡的adapter
 *   修改历史:
 *
 */
class ImagePagerAdapter extends PagerAdapter {

    private PagerAdapter adapter;

    public ImagePagerAdapter(PagerAdapter adapter) {
        this.adapter = adapter;
        adapter.registerDataSetObserver( new DataSetObserver() {

            @Override
            public void onChanged() {
                notifyDataSetChanged();
            }

            @Override
            public void onInvalidated() {
                notifyDataSetChanged();
            }

        });
    }

    @Override
    public int getCount() {
        return adapter.getCount() + 2;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return adapter.isViewFromObject( arg0, arg1);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if(position == 0) {
            position = adapter.getCount() - 1;
        }
        else if(position == adapter.getCount() + 1) {
            position = 0;
        }
        else {
            position -= 1;
        }
        return adapter.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        adapter.destroyItem( container, position, object);
    }

}







