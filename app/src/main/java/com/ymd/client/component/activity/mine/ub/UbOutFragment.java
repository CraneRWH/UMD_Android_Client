package com.ymd.client.component.activity.mine.ub;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ymd.client.R;
import com.ymd.client.common.base.OnUMDItemClickListener;
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
 * 首页-我的U币-支出
 */
public class UbOutFragment extends Fragment {

    View mView;
    @BindView(R.id.ubfragment_recycle_view)
    ZRecyclerView recyclerView;
    @BindView(R.id.ubfragment_recycler_empty)
    View mEmptyView;

    UbFragmentAdapter mAdapter;

    int page = 1;

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
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.default_recyclerview_divider));
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

    protected List<String> getDataList() {
        List<String> list = new ArrayList<>();
        list.add(String.valueOf(page));
        list.add(String.valueOf(page));
        list.add(String.valueOf(page));
        list.add(String.valueOf(page));
        list.add(String.valueOf(page));
        list.add(String.valueOf(page));
        list.add(String.valueOf(page));
        return list;
    }

    public void refreshList(List<String> beans) {
        Log.e("UbInFragment",String.valueOf(page));
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
}
