package com.ymd.client.component.activity.mine.ub;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ymd.client.R;
import com.ymd.client.common.base.OnUMDItemClickListener;
import com.ymd.client.component.adapter.UbFragmentAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 首页-我的U币-收入
 */
public class UbInFragment extends Fragment {

    View mView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.fragment_emptyLayout)
    View mEmptyView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView != null) {
            ViewGroup parent = (ViewGroup) mView.getParent();
            if (parent != null) {
                parent.removeView(mView);
            }
            return mView;
        } else {
            mView = inflater.inflate(R.layout.fragment_ub_in, container, false);
        }
        ButterKnife.bind(this,mView);
        initView();
        return mView;
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        UbFragmentAdapter adapter = new UbFragmentAdapter(getDataList(), getContext());
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
