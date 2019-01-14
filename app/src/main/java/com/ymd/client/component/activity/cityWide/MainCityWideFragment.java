package com.ymd.client.component.activity.cityWide;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.common.base.fragment.ViewPagerFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者:rongweihe
 * 日期:2019/1/13
 * 描述:    同城
 * 修改历史:
 */
public class MainCityWideFragment extends ViewPagerFragment {

    protected View rootView;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    Unbinder unbinder;

    public static MainCityWideFragment newInstance() {
        MainCityWideFragment fragment = new MainCityWideFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_main_city_wide, container, false);
            unbinder = ButterKnife.bind(this, rootView);
            initView(rootView);
        }
        return rootView;
    }

    private void initView(View rootView) {
        ivBack.setVisibility(View.GONE);
        txtTitle.setText("同城信息");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
