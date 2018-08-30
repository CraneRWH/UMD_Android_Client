package com.ymd.client.component.activity.mine.collection;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ymd.client.R;
import com.ymd.client.component.adapter.CommonRecyclerAdapter;
import com.ymd.client.component.adapter.MyRateAdapter;
import com.ymd.client.component.widget.zrecyclerview.ProgressStyle;
import com.ymd.client.component.widget.zrecyclerview.ZRecyclerView;
import com.ymd.client.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommonCollectionFragment extends Fragment {

    View mView;
    @BindView(R.id.ubfragment_recycle_view)
    ZRecyclerView recyclerView;
    @BindView(R.id.ubfragment_recycler_empty)
    ImageView mEmptyView;

    MyRateAdapter mAdapter;
    int page = 1;

    int currentPosition = 0;

    public CommonCollectionFragment() {
    }

    public static final Fragment newInstance(int position) {
        CommonCollectionFragment fragment = new CommonCollectionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        currentPosition = (int) getArguments().get("position");
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

        loadData();
        return mView;
    }

    private void initView() {
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        DividerItemDecoration divider = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.default_recyclerview_divider));
        recyclerView.addItemDecoration(divider);

        mAdapter = new MyRateAdapter(getContext());
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
    }


    private void loadData() {
        refreshList(getDataList());
    }

    protected List<String> getDataList() {
        List<String> list = new ArrayList<>();
        list.add(String.valueOf(currentPosition));
        list.add(String.valueOf(currentPosition));
        list.add(String.valueOf(currentPosition));
        list.add(String.valueOf(page));
        list.add(String.valueOf(page));
        list.add(String.valueOf(page));
        list.add(String.valueOf(page));
        return list;
    }

    public void refreshList(List<String> beans) {
        Log.d("loadData",String.valueOf(currentPosition));
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
