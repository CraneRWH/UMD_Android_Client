package com.ymd.client.component.activity.mine;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.component.activity.login.LoginByPWActivity;
import com.ymd.client.component.adapter.CommonRecyclerAdapter;
import com.ymd.client.component.adapter.MyRateAdapter;
import com.ymd.client.component.widget.zrecyclerview.ProgressStyle;
import com.ymd.client.component.widget.zrecyclerview.ZRecyclerView;
import com.ymd.client.model.bean.user.YmdEvaluation;
import com.ymd.client.model.constant.URLConstant;
import com.ymd.client.model.info.LoginInfo;
import com.ymd.client.utils.StatusBarUtils;
import com.ymd.client.utils.ToastUtil;
import com.ymd.client.web.WebUtil;

import org.json.JSONObject;

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
    ZRecyclerView recyclerView;
    @BindView(R.id.my_rate_emptyLayout)
    View mEmptyView;

    MyRateAdapter mAdapter;
    int page = 1;

    List<YmdEvaluation> datas;

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

        recyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.default_recyclerview_divider));
        recyclerView.addItemDecoration(divider);

        mAdapter = new MyRateAdapter(this);
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
                requestDataInfo();
            }

            @Override
            public void onLoadMore() {
                page++;
                requestDataInfo();
            }
        });

        requestDataInfo();
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


    private void requestDataInfo() {
        if (!LoginInfo.isLogin) {
            ToastUtil.ToastMessage(this, "请首先登录");
            refreshList("");
            LoginByPWActivity.startAction(this);
            return;
        }
        Map<String, Object> params = new HashMap<>();
    //    params.put("consumerId", LoginInfo.getInstance().getLoginInfo().getId());
        params.put("pageNum", page);
        WebUtil.getInstance().requestPOST(this, URLConstant.QUERY_COMMENT_LIST, params, true,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject result) {
                        refreshList(result.optString("list"));
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {
                        refreshList("");
                    }
                });
    }

    public void refreshList(String resultJson) {
        try {
            datas = new Gson().fromJson(resultJson, new TypeToken<List<YmdEvaluation>>() {
            }.getType());

            if (datas == null || datas.size() == 0) {
                recyclerView.loadMoreComplete();
                recyclerView.refreshComplete();
                if (page == 1) {
                    mEmptyView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    ToastUtil.ToastMessage(this, "没有更多的数据了");
                }
            } else {
                if (page == 1) {
                    mAdapter.addItems(datas);
                    recyclerView.refreshComplete();
                } else {
                    recyclerView.loadMoreComplete();
                    mAdapter.appendItems(datas);
                }
                mEmptyView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }

            recyclerView.refreshComplete();
            recyclerView.loadMoreComplete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showError(String msg) {
        recyclerView.loadMoreComplete();
        recyclerView.refreshComplete();
    }
}
