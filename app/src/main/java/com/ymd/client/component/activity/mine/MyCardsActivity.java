package com.ymd.client.component.activity.mine;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.common.base.fragment.PageFragment;
import com.ymd.client.component.activity.mine.ub.UbInFragment;
import com.ymd.client.component.widget.smarttablayout.SmartTabLayout;
import com.ymd.client.component.widget.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ymd.client.component.widget.smarttablayout.utils.v4.FragmentPagerItems;
import com.ymd.client.utils.StatusBarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的-我的券包
 */
public class MyCardsActivity extends BaseActivity {
    @BindView(R.id.base_title)
    TextView mTxtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cards);
        ButterKnife.bind(this);
        setStatusBar(R.color.white);
        mTxtTitle.setText(getResources().getString(R.string.fragment_my_cards));

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
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("全部", UbInFragment.class)
                .add("美食0", UbInFragment.class)
                .add("美食1", UbInFragment.class)
                .add("美食2", UbInFragment.class)
                .add("美食3", UbInFragment.class)
                .add("美食4", UbInFragment.class)
                .create());

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);
    }
}
