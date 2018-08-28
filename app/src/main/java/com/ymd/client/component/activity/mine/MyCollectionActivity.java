package com.ymd.client.component.activity.mine;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.component.widget.icindicator.FragmentContainerHelper;
import com.ymd.client.component.widget.icindicator.MagicIndicator;
import com.ymd.client.component.widget.icindicator.ViewPagerHelper;
import com.ymd.client.component.widget.icindicator.buildins.UIUtil;
import com.ymd.client.component.widget.icindicator.buildins.commonnavigator.CommonNavigator;
import com.ymd.client.component.widget.icindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import com.ymd.client.component.widget.icindicator.buildins.commonnavigator.abs.IPagerIndicator;
import com.ymd.client.component.widget.icindicator.buildins.commonnavigator.abs.IPagerTitleView;
import com.ymd.client.component.widget.icindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import com.ymd.client.component.widget.icindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import com.ymd.client.component.widget.icindicator.buildins.commonnavigator.titles.SimplePagerTitleView;
import com.ymd.client.utils.StatusBarUtils;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的-我的收藏
 */
public class MyCollectionActivity extends BaseActivity {

    @BindView(R.id.base_title)
    TextView mTxtTitle;

    @BindView(R.id.my_collection_indicator)
    MagicIndicator mIndicator;

    @BindView(R.id.my_collection_viewpager)
    ViewPager mViewPager;

    private static final String[] CHANNELS = new String[]{" 全部 ", " 美食 ", " 酒店 ", " 爱车 ", " 电影 "};
    private List<String> mDataList = Arrays.asList(CHANNELS);
    private MyColPagerAdapter mPagerAdapter = new MyColPagerAdapter(mDataList);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);
        ButterKnife.bind(this);
        setStatusBar(R.color.white);
        mTxtTitle.setText(getResources().getString(R.string.fragment_my_collection));

        mViewPager.setAdapter(mPagerAdapter);

        initMagicIndicator();
    }

    @OnClick(R.id.base_back)
    void back() {
        finish();
    }

    @Override
    protected void setStatusBar(int resourcesId) {
        super.setStatusBar(resourcesId);
        StatusBarUtils.StatusBarLightMode(this, true);
    }

    private void initMagicIndicator() {
        if(CHANNELS.length > 5){
            LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,UIUtil.dip2px(this, 42));
            mIndicator.setLayoutParams(params);
        }else{
            LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,UIUtil.dip2px(this, 42));
            params.gravity = Gravity.CENTER_HORIZONTAL;
            mIndicator.setLayoutParams(params);
        }
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.text_gray_dark));
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.bg_header));
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index,false);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                if(CHANNELS.length > 5){
                    linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                }else{
                    linePagerIndicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                    linePagerIndicator.setLineWidth(UIUtil.dip2px(context, 48));
                }

                linePagerIndicator.setColors(getResources().getColor(R.color.bg_header));
                return linePagerIndicator;
            }
        });

        mIndicator.setNavigator(commonNavigator);
        if(CHANNELS.length > 5){
            ViewPagerHelper.bind(mIndicator, mViewPager);
        }else{
            LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
            titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            titleContainer.setDividerDrawable(new ColorDrawable() {
                @Override
                public int getIntrinsicWidth() {
                    return UIUtil.dip2px(MyCollectionActivity.this, 15);
                }
            });

            final FragmentContainerHelper fragmentContainerHelper = new FragmentContainerHelper(mIndicator);
            fragmentContainerHelper.setInterpolator(new OvershootInterpolator(2.0f));
            fragmentContainerHelper.setDuration(300);
            mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    fragmentContainerHelper.handlePageSelected(position);
                }
            });
        }
    }

    /**
     * Created by hackware on 2016/9/10.
     */
    public static class MyColPagerAdapter extends PagerAdapter {
        private List<String> mDataList;

        public MyColPagerAdapter(List<String> dataList) {
            mDataList = dataList;
        }

        @Override
        public int getCount() {
            return mDataList == null ? 0 : mDataList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TextView textView = new TextView(container.getContext());
            textView.setText(mDataList.get(position));
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(24);
            container.addView(textView);
            return textView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            TextView textView = (TextView) object;
            String text = textView.getText().toString();
            int index = mDataList.indexOf(text);
            if (index >= 0) {
                return index;
            }
            return POSITION_NONE;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mDataList.get(position);
        }
    }
}
