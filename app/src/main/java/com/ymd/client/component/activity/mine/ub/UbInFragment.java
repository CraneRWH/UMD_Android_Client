package com.ymd.client.component.activity.mine.ub;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ymd.client.R;
import com.ymd.client.component.adapter.CommonRecyclerAdapter;
import com.ymd.client.component.adapter.UbFragmentAdapter;
import com.ymd.client.component.widget.zrecyclerview.ProgressStyle;
import com.ymd.client.component.widget.zrecyclerview.ZRecyclerView;
import com.ymd.client.utils.ToastUtil;

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
    @BindView(R.id.ubfragment_recycle_view)
    ZRecyclerView recyclerView;
    @BindView(R.id.ubfragment_recycler_empty)
    ImageView mEmptyView;

    UbFragmentAdapter mAdapter;
    int page = 1;

    public UbInFragment() {
    }

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
        ButterKnife.bind(this, mView);
        initView();
        return mView;
    }

    private void initView() {
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        DividerItemDecoration divider = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.default_recyclerview_divider));
        recyclerView.addItemDecoration(divider);

        mAdapter = new UbFragmentAdapter(getContext());
        mAdapter.setOnItemClickListener(new CommonRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }
        });
        recyclerView.setAdapter(mAdapter);
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallPulse);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);

        recyclerView.setLoadingListener(new ZRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                refreshList(getDataList());
            }

            @Override
            public void onLoadMore() {
                page++;
                refreshList(getDataList());
            }
        });

        refreshList(getDataList());
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

    public void refreshList(List<Map<String, Object>> beans) {
        if (beans == null || beans.size() == 0) {
            recyclerView.loadMoreComplete();
            recyclerView.refreshComplete();
            if (page == 1) {
                mEmptyView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                ToastUtil.ToastMessage(getContext(),"没有更多的数据了");
            }
        } else {
            if (page == 1) {
                mAdapter.addItems(beans);
                recyclerView.refreshComplete();
            } else {
                recyclerView.loadMoreComplete();
                mAdapter.appendItems(beans);
            }
            mEmptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    public void showError(String msg) {
        recyclerView.loadMoreComplete();
        recyclerView.refreshComplete();
    }
}
