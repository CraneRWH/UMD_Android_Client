package com.ymd.client.component.widget.viewPager;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;


/**
 *
 *   作者:rongweihe
 *   日期:2018/8/20
 *   描述:    自定义ViewPager
 *   修改历史:
 *
 */
public class MyViewPager extends ViewPager {

    private ImagePagerAdapter mAdapter;

    public MyViewPager(Context context) {
        super( context);
        setOnPageChangeListener( null);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super( context, attrs);
        setOnPageChangeListener( null);
    }

    @Override
    public void setAdapter(PagerAdapter arg0) {
        mAdapter = new ImagePagerAdapter( arg0);
        super.setAdapter( mAdapter);
        setCurrentItem(1);
    }

    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        super.setOnPageChangeListener( new InnerOnPageChangeListener( listener));
    }
    
    public void toNext() {
    	int position = getCurrentItem();
    	if (position == mAdapter.getCount() - 2) {
    		setCurrentItem(1, false);
    	}
    	else {
    		setCurrentItem(position + 1, false);
    	}
    }

    private class InnerOnPageChangeListener implements OnPageChangeListener {

        private OnPageChangeListener listener;
        private int position;

        public InnerOnPageChangeListener(OnPageChangeListener listener) {
            this.listener = listener;
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            if(null != listener) {
                listener.onPageScrollStateChanged( arg0);
            }
            if(arg0 == ViewPager.SCROLL_STATE_IDLE) {
                if(position == mAdapter.getCount() - 1) {
                    setCurrentItem( 1, false);
                }
                else if(position == 0) {
                    setCurrentItem( mAdapter.getCount() - 2, false);
                }
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            if(null != listener) {
                listener.onPageScrolled( arg0, arg1, arg2);
            }
        }

        @Override
        public void onPageSelected(int arg0) {
            position = arg0;
            if(null != listener) {
                listener.onPageSelected( arg0);
            }
        }
    }

}


















