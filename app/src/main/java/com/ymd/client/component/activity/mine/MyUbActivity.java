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
import com.ymd.client.component.activity.mine.ub.UbInFragment;
import com.ymd.client.component.activity.mine.ub.UbOutFragment;
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
import com.ymd.client.utils.ScreenUtil;
import com.ymd.client.utils.StatusBarUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的-我的U币
 */
public class MyUbActivity extends BaseActivity {
    @BindView(R.id.base_title)
    TextView mTxtTitle;

    @BindView(R.id.my_ub_viewpager)
    ViewPager mViewPager;

    protected ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();

    @BindView(R.id.my_ub_indicator)
    MagicIndicator mIndicator;//顶部tab

    private List<Fragment> mFragments = new ArrayList<>();
    private FragmentContainerHelper mFragmentContainerHelper;// = new FragmentContainerHelper();
    private static final String[] CHANNELS = new String[]{"收入", "支出"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ub);
        ButterKnife.bind(this);
        setStatusBar(R.color.white);
        mTxtTitle.setText(getResources().getString(R.string.fragment_my_ub));

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

    private void initFragments() {
        mFragments = new ArrayList<>();
        mFragments.add(new UbInFragment());
        mFragments.add(new UbOutFragment());

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
                simplePagerTitleView.setWidth((ScreenUtil.getScreenWidthPix(context) - ScreenUtil.dip2px(context, 60)) / 2);
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
                linePagerIndicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                linePagerIndicator.setLineWidth(UIUtil.dip2px(context, 80));
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
                return UIUtil.dip2px(MyUbActivity.this, 20);
            }
        });

        mFragmentContainerHelper.setInterpolator(new OvershootInterpolator(2.0f));
        mFragmentContainerHelper.setDuration(300);
    }

}
