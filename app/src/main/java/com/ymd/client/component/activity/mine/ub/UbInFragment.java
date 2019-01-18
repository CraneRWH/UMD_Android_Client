package com.ymd.client.component.activity.mine.ub;

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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ymd.client.R;
import com.ymd.client.component.adapter.mine.UbFragmentAdapter;
import com.ymd.client.component.adapter.mine.UbFragmentAdapter2;
import com.ymd.client.component.widget.zrecyclerview.ProgressStyle;
import com.ymd.client.component.widget.zrecyclerview.ZRecyclerView;
import com.ymd.client.model.bean.user.UForm;
import com.ymd.client.model.constant.URLConstant;
import com.ymd.client.utils.ToastUtil;
import com.ymd.client.web.WebUtil;

import org.json.JSONObject;

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

    UbFragmentAdapter2 mAdapter;
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
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallPulse);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);

        recyclerView.setLoadingListener(new ZRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
            //    refreshList(getDataList());
                requestUlist();
            }

            @Override
            public void onLoadMore() {
                page++;
                requestUlist();
            }
        });

    //    refreshList(getDataList());
        requestUlist();
    }

    private void requestUlist() {
        Map<String, Object> params = new HashMap<>();
        params.put("type", 0);
        params.put("pageNum", page);
        WebUtil.getInstance().requestPOST(getActivity(), URLConstant.QUEYR_U_LIST, params, true,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject result) {
                        refreshList(result.optString("list"));

                        recyclerView.refreshComplete();
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {

                        refreshList("");
                        recyclerView.refreshComplete();
                    }
                });
    }

    List<UForm> datas = new ArrayList<>();
    public void refreshList(String resultJson) {
        Log.e("UbInFragment",String.valueOf(page));
        List<UForm> beans = new Gson().fromJson(resultJson, new TypeToken<List<UForm>>(){}.getType());
        if (page == 1) {
            datas.clear();
        }
        if (beans != null) {
            datas.addAll(beans);
        }
        mAdapter = new UbFragmentAdapter2(datas,getActivity());
        recyclerView.setAdapter(mAdapter);
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
                recyclerView.refreshComplete();
            } else {
                recyclerView.loadMoreComplete();
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
