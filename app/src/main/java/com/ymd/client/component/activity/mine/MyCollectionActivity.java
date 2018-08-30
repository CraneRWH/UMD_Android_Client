package com.ymd.client.component.activity.mine;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.component.activity.mine.collection.CommonCollectionFragment;
import com.ymd.client.component.widget.icindicator.FragmentContainerHelper;
import com.ymd.client.component.widget.icindicator.MagicIndicator;
import com.ymd.client.component.widget.icindicator.buildins.UIUtil;
import com.ymd.client.component.widget.icindicator.buildins.commonnavigator.CommonNavigator;
import com.ymd.client.component.widget.icindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import com.ymd.client.component.widget.icindicator.buildins.commonnavigator.abs.IPagerIndicator;
import com.ymd.client.component.widget.icindicator.buildins.commonnavigator.abs.IPagerTitleView;
import com.ymd.client.component.widget.icindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import com.ymd.client.component.widget.icindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import com.ymd.client.component.widget.icindicator.buildins.commonnavigator.titles.SimplePagerTitleView;
import com.ymd.client.utils.StatusBarUtils;

import java.util.ArrayList;
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
    TextView mTxtTitle;//标题

    @BindView(R.id.my_collection_indicator)
    MagicIndicator mIndicator;//顶部tab

    @BindView(R.id.my_collection_fragment_container)
    ViewPager mViewPager;//content

    private List<Fragment> mFragments = new ArrayList<>();
    private FragmentContainerHelper mFragmentContainerHelper;// = new FragmentContainerHelper();

    private static final String[] CHANNELS = new String[]{"全部", "美食", "酒店", "爱车", "电影", "美食", "酒店", "爱车", "电影"};
    //private static final String[] CHANNELS = new String[]{"全部", "美食", "酒店", "爱车", "电影"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);
        ButterKnife.bind(this);
        setStatusBar(R.color.white);
        mTxtTitle.setText(getResources().getString(R.string.fragment_my_collection));

        mFragmentContainerHelper = new FragmentContainerHelper();

        initFragments();
        initMagicIndicator();

        mFragmentContainerHelper.handlePageSelected(0, false);
        mViewPager.setCurrentItem(0);
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


    /**
     * 初始化Fragment,ViewAdapter
     */
    private void initFragments() {
        for (int i = 0; i < CHANNELS.length; i++) {
            CommonCollectionFragment fragment = new CommonCollectionFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position", i);
            fragment.setArguments(bundle);
            mFragments.add(fragment);
        }

        mViewPager.setAdapter(new FragmentStatePagerAdapter(this.getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mFragmentContainerHelper.handlePageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 初始化顶部tab
     */
    private void initMagicIndicator() {
        if (CHANNELS.length > 5) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, UIUtil.dip2px(this, 42));
            mIndicator.setLayoutParams(params);
        } else {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, UIUtil.dip2px(this, 42));
            params.gravity = Gravity.CENTER_HORIZONTAL;
            mIndicator.setLayoutParams(params);
        }
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return CHANNELS.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.text_gray_dark));
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.bg_header));
                simplePagerTitleView.setText(Arrays.asList(CHANNELS).get(index));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mFragmentContainerHelper.handlePageSelected(index);
                        mViewPager.setCurrentItem(index, false);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                if (CHANNELS.length > 5) {
                    linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                } else {
                    linePagerIndicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                    linePagerIndicator.setLineWidth(UIUtil.dip2px(context, 40));
                }

                linePagerIndicator.setColors(getResources().getColor(R.color.bg_header));
                return linePagerIndicator;
            }
        });
        mIndicator.setNavigator(commonNavigator);
        mFragmentContainerHelper.attachMagicIndicator(mIndicator);

        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return UIUtil.dip2px(MyCollectionActivity.this, 8);
            }
        });

        mFragmentContainerHelper.setInterpolator(new OvershootInterpolator(2.0f));
        mFragmentContainerHelper.setDuration(300);
    }
}
