package com.ymd.client.component.activity.mine;

import android.os.Bundle;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.utils.StatusBarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyRatesActivity extends BaseActivity {
    @BindView(R.id.base_title)
    TextView mTxtTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rates);
        ButterKnife.bind(this);
        setStatusBar(R.color.white);
        mTxtTitle.setText(getResources().getString(R.string.fragment_my_rate));
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
}
