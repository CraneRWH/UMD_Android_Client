package com.ymd.client.component.activity.mine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.component.activity.mine.ub.UbInFragment;
import com.ymd.client.component.activity.mine.ub.UbOutFragment;
import com.ymd.client.component.adapter.AppFragmentPageAdapter;
import com.ymd.client.component.widget.other.MyChooseItemView;
import com.ymd.client.utils.StatusBarUtils;

import java.util.ArrayList;
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

    @BindView(R.id.my_ub_choose0)
    MyChooseItemView chooseItem0;
    @BindView(R.id.my_ub_choose1)
    MyChooseItemView chooseItem1;

    protected ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
    private List<MyChooseItemView> textViewList;
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

        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new UbInFragment());
        fragmentList.add(new UbOutFragment());

        textViewList = new ArrayList<MyChooseItemView>();
        textViewList.add(chooseItem0);
        textViewList.add(chooseItem1);
        viewPagerListener();
        chooseItem(0);

        viewPagerListener();
        chooseItem(0);
    }

    protected void chooseItem(int position) {
        chooseStatus = position;
        try {
            for (int i = 0; i < textViewList.size(); i++) {
                if (i == position) {
                    textViewList.get(i).setChoose(true);
                } else {
                    textViewList.get(i).setChoose(false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void viewPagerListener() {
            for (int i = 0; i < textViewList.size(); i++) {
                final int position = i;
                textViewList.get(i).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        mViewPager.setCurrentItem(position);
                        chooseItem(position);
                    }
                });
            }
            mViewPager.setAdapter(new AppFragmentPageAdapter(getSupportFragmentManager(), fragmentList));
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    //Do Nothing
                }

                @Override
                public void onPageSelected(int position) {
                    chooseItem(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    //Do Nothing
                }
            });
        }
    }
