package com.ymd.client.component.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.common.base.OnUMDItemClickListener;
import com.ymd.client.component.adapter.MyRateAdapter;
import com.ymd.client.component.adapter.UbFragmentAdapter;
import com.ymd.client.utils.StatusBarUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的-我的评价
 */
public class MyRatesActivity extends BaseActivity {
    @BindView(R.id.base_title)
    TextView mTxtTitle;

    @BindView(R.id.my_rate_recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.my_rate_emptyLayout)
    View mEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rates);
        ButterKnife.bind(this);
        setStatusBar(R.color.white);
        mTxtTitle.setText(getResources().getString(R.string.fragment_my_rate));

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
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        MyRateAdapter adapter = new MyRateAdapter(getDataList(), this);
        adapter.setListener(new OnUMDItemClickListener() {
            @Override
            public void onClick(Object data, View view, int position) {
                // OrderDetailActivity.startAction(getActivity());
            }
        });
        recyclerView.setAdapter(adapter);
        mEmptyView.setVisibility(View.GONE);
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
}
