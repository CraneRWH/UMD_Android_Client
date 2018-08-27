package com.ymd.client.component.activity.mine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.common.base.fragment.PageFragment;
import com.ymd.client.component.widget.other.MyChooseItemView;
import com.ymd.client.utils.StatusBarUtils;

import java.util.ArrayList;

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
    protected MyChooseItemView[] chooseItemViews;
    protected ArrayList<Fragment> pageFragments = new ArrayList<Fragment>();
    public int chooseStatus = 0;
    protected int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ub);
        ButterKnife.bind(this);
        setStatusBar(R.color.white);
        mTxtTitle.setText(getResources().getString(R.string.fragment_my_ub));

        init();
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

    private void init() {
        status = 2;
        chooseItemViews = new MyChooseItemView[status];
        for (int i = 0 ; i < status ; i ++ ) {
            chooseItemViews[i] = (MyChooseItemView)findViewById(
                    getResources().getIdentifier("my_ub_choose" + i, "id", "com.ymd.client")
            );
        }

        for (int i = 0 ; i < chooseItemViews.length ; i ++ ) {
            final int position = i;
            chooseItemViews[i].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    mViewPager.setCurrentItem(position);
                    chooseItem(position);
                }
            });
        }
        chooseItem(0);
    }

    protected void chooseItem(int position) {
        chooseStatus = position;
        try {
            for (int i = 0 ; i < chooseItemViews.length ; i ++ ) {
                if ( i == position) {
                    chooseItemViews[i].setChoose(true);
                }
                else {
                    chooseItemViews[i].setChoose(false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void viewPagerListener() {
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("chooseStatus", position);
                pageFragments.get(position).setArguments(bundle);
                return pageFragments.get(position);
            }

            @Override
            public int getCount() {
                return pageFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return "TAB" + position;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {

            }
        });
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                chooseItem(position);
                ((PageFragment)pageFragments.get(position)).queryDataStart();
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
    }
}
