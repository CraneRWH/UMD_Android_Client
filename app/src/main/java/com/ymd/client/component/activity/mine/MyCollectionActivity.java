package com.ymd.client.component.activity.mine;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.common.base.OnUMDItemClickListener;
import com.ymd.client.component.adapter.UbFragmentAdapter;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private static final String[] CHANNELS = new String[]{"全部", "美食", "酒店", "爱车", "电影", "美食", "酒店", "爱车", "电影"};
    //private static final String[] CHANNELS = new String[]{"全部", "美食", "酒店", "爱车", "电影"};
    private List<String> mDataList = Arrays.asList(CHANNELS);
    private MyColPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);
        ButterKnife.bind(this);
        setStatusBar(R.color.white);
        mTxtTitle.setText(getResources().getString(R.string.fragment_my_collection));

        mPagerAdapter = new MyColPagerAdapter(this, mDataList);
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
//        if(CHANNELS.length > 5){
//            ViewPagerHelper.bind(mIndicator, mViewPager);
//        }else{
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return UIUtil.dip2px(MyCollectionActivity.this, 8);
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
        //  }
    }

    /**
     * Created by hackware on 2016/9/10.
     */
    public static class MyColPagerAdapter extends PagerAdapter {
        private List<String> mDataList;
        Context mContext;

        public MyColPagerAdapter(Context context, List<String> dataList) {
            this.mContext = context;
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
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.view_my_collection, null, false);
            initView(view.findViewById(R.id.view_my_collection));
            container.addView(view);
            return view;
        }

        private void initView(RecyclerView recyclerView) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            recyclerView.setLayoutManager(layoutManager);
            UbFragmentAdapter adapter = new UbFragmentAdapter(getDataList(), mContext);
            adapter.setListener(new OnUMDItemClickListener() {
                @Override
                public void onClick(Object data, View view, int position) {
                    // OrderDetailActivity.startAction(getActivity());
                }
            });
            recyclerView.setAdapter(adapter);
        }

        protected List<Map<String, Object>> getDataList() {

            List<Map<String, Object>> list = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            List<Map<String, Object>> productList = new ArrayList<>();
            Map<String, Object> product = new HashMap<>();
            product.put("name", "麻辣烫");
            product.put("num", 2);
            productList.add(product);
            map.put("name", "麻辣烫");
            map.put("statusName", "订单已完成");
            map.put("status", 3);
            map.put("u_money", 20);
            map.put("product_list", productList);
            map.put("all_num", 2);
            map.put("money", 30);
            list.add(map);

            map = new HashMap<>();
            productList = new ArrayList<>();
            product = new HashMap<>();
            product.put("name", "麻辣烫");
            product.put("num", 2);
            productList.add(product);
            product.put("name", "麻辣烫");
            product.put("num", 2);
            productList.add(product);
            map.put("name", "朝鲜面");
            map.put("statusName", "订单已提交");
            map.put("status", 1);
            map.put("u_money", 20);
            map.put("product_list", productList);
            map.put("all_num", 4);
            map.put("money", 30);
            list.add(map);
            return list;
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

    public static class MyColFragmentPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList;
        private List<String> titleList;

        public MyColFragmentPagerAdapter(FragmentManager fm,
                                         List<Fragment> fragmentList, List<String> titleList) {
            super(fm);
            this.fragmentList = fragmentList;
            this.titleList = titleList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return titleList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }
}
