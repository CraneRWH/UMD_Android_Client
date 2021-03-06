package com.ymd.client.component.activity.order;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ymd.client.R;
import com.ymd.client.common.base.OnUMDItemClickListener;
import com.ymd.client.common.base.fragment.ViewPagerFragment;
import com.ymd.client.component.activity.homePage.merchant.CommentSellerActivity;
import com.ymd.client.component.activity.login.LoginByPWActivity;
import com.ymd.client.component.activity.order.detail.OrderDetailActivity;
import com.ymd.client.component.activity.order.pay.OrderPayActivity;
import com.ymd.client.component.activity.order.pay.OrderPayResultActivity;
import com.ymd.client.component.adapter.order.OrderPageAdapter2;
import com.ymd.client.component.event.LoginEvent;
import com.ymd.client.component.event.OrderListRefreshEvent;
import com.ymd.client.component.widget.dialog.MyDialog;
import com.ymd.client.component.widget.zrecyclerview.ProgressStyle;
import com.ymd.client.component.widget.zrecyclerview.ZRecyclerView;
import com.ymd.client.model.bean.order.OrderResultForm;
import com.ymd.client.model.bean.order.YmdOrderGoods;
import com.ymd.client.model.constant.URLConstant;
import com.ymd.client.model.info.LoginInfo;
import com.ymd.client.utils.AlertUtil;
import com.ymd.client.utils.ToastUtil;
import com.ymd.client.utils.ToolUtil;
import com.ymd.client.web.WebUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 包名:com.ymd.client.component.activity.order
 * 类名:
 * 时间:2018/8/24 0024Time:11:18
 * 作者:荣维鹤
 * 功能简介:
 * 修改历史:
 */
public class OrderPageFragment extends ViewPagerFragment {

    @BindView(R.id.emptyView)
    TextView emptyView;
    @BindView(R.id.emptyLayout)
    LinearLayout emptyLayout;
    Unbinder unbinder;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private int type;      //订单的状态（0：全部，1：待支付，2：退款）
    public static OrderPageFragment newInstance(int type) {
        OrderPageFragment fragment = new OrderPageFragment();
        Bundle args = new Bundle();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt("type");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_order_page, container, false);
            unbinder = ButterKnife.bind(this, rootView);
            initView(rootView);
        }
        return rootView;
    }

    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                requestOrderInfo();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                requestOrderInfo();
            }
        });
        if (type == 0) {
            setUserVisibleHint(true);
        }
    }

    int page = 1;

    private void requestOrderInfo() {
        if (!LoginInfo.isLogin) {
            ToastUtil.ToastMessage(getActivity(), "请首先登录");
            resetOrderList("");
            if(!LoginByPWActivity.isFront){
                LoginByPWActivity.startAction(getActivity());
            }
            return;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", LoginInfo.getInstance().getLoginInfo().getId());
        params.put("type", type);
        params.put("pageNum", page);
        WebUtil.getInstance().requestPOST(getActivity(), URLConstant.ORDER_LIST, params, true,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject result) {
                        resetOrderList(result.optString("list"));
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadmore();
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {
                        resetOrderList("");
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadmore();
                    //    refreshLayout.finishLoadmoreWithNoMoreData();
                    }
                });
    }

    List<OrderResultForm> orderList = new ArrayList<>();
    OrderPageAdapter2 adapter;
    private void resetOrderList(String resultJson) {
        List<OrderResultForm> datas = new Gson().fromJson(resultJson, new TypeToken<List<OrderResultForm>>() {
        }.getType());
        if (page == 1) {
            orderList.clear();
            if(datas== null) {
                return;
            }
            orderList.addAll(datas);
            adapter = new OrderPageAdapter2(orderList,getActivity());
            adapter.setListener(new OnUMDItemClickListener() {
                @Override
                public void onClick(Object data, View view, int position) {
                    OrderResultForm item = (OrderResultForm) data;
                    if (item.getOrderStatus() == 1 || item.getOrderStatus() ==2) {
                        OrderPayResultActivity.startAction(getActivity(), item);
                    } else {
                        OrderDetailActivity.startAction(getActivity(), item.getId(), ToolUtil.changeInteger(item.getOrderType()));
                    }
                }
            });
            adapter.setBtnClickListener(new OrderPageAdapter2.OnBtnClickListener() {
                @Override
                public void onClick(Object data, int position, int id) {
                    OrderResultForm item = (OrderResultForm) data;
                    if (id == R.id.btn3) {
                        switch (item.getOrderStatus()) {
                            case 0:
                            //    CommentSellerActivity.startAction(getActivity(), item);
                                OrderPayActivity.startAction(getActivity(), item.getId());
                                break;
                            case 1:
                            case 2:
                                OrderPayResultActivity.startAction(getActivity(), item);
                                break;
                            case 4:
                                CommentSellerActivity.startAction(getActivity(), item);
                                break;
                        }
                    } else if (id == R.id.btn2) {
                        if (item.getOrderStatus() == 0) {
                            cancelOrder(item, position);

                        } else {
                            submitOrder(item);
                        }
                    }
                }
            });
            adapter.setLongClickListener(new OrderPageAdapter2.OnItemLongClickListener() {
                @Override
                public void onClick(OrderResultForm data, int position) {
                    showDeleteDialog(data,position);
                }
            });
            recyclerView.setAdapter(adapter);
        } else {
            if (datas != null) {
                adapter.appendItems(datas);
                orderList.addAll(datas);
            }
        }
        if (orderList.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
        }

    }

    private void showDeleteDialog(final OrderResultForm data,final int positon) {
        AlertUtil.AskDialog(getActivity(), "是否删除此订单？", new MyDialog.SureListener() {
            @Override
            public void onSureListener() {
                deleteOrder(data, positon);
            }
        });
    }

    private void deleteOrder(OrderResultForm data, final int position) {
        Map<String,Object> params = new HashMap<>();
        params.put("orderId", data.getId());
        WebUtil.getInstance().requestPOST(getContext(), URLConstant.DELETE_ORDER, params , true, true,
        new WebUtil.WebCallBack() {
            @Override
            public void onWebSuccess(JSONObject resultJson) {
                adapter.deleteItem(position);
            }

            @Override
            public void onWebFailed(String errorMsg) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void cancelOrder(OrderResultForm data , final int position) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", data.getId());
    //    params.put("payType", data.getP);
        WebUtil.getInstance().requestPOST(getActivity(), URLConstant.CANCLE_ORDER, params, true,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject result) {
                        adapter.deleteItem(position);
                        ToastUtil.ToastMessage(getActivity(), result.optString("msg"));
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {

                    }
                });
    }

    private void submitOrder(OrderResultForm data) {
        Map<String, Object> params = new HashMap<>();
        params.put("merchantId", data.getmId());
        params.put("payAmt", ToolUtil.changeString(data.getPayAmt()));
        params.put("totalAmt", ToolUtil.changeString(data.getTotalAmt()));
        params.put("uCurrency", "0");
        List<Map<String,Object>> list = new ArrayList<>();
        for (YmdOrderGoods item : data.getYmdOrderGoodsList()) {
            Map<String,Object> map = new HashMap<>();
            map.put("goodsId", item.getGoodsId());
            map.put("goodsNum", item.getGoodsNum());
            map.put("goodsType","0");
            list.add(map);
        }
        params.put("goodslist", list);
        WebUtil.getInstance().requestPOST(getActivity(), URLConstant.CREATE_ORDER, params, true,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject result) {
                        toOrderDetail(result.optString("id"), data);
                        EventBus.getDefault().post(new OrderListRefreshEvent(true));
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {

                    }
                });
    }

    private void toOrderDetail(String orderCode,OrderResultForm data) {
        OrderDetailActivity.startAction(getActivity(), ToolUtil.changeLong(orderCode), ToolUtil.changeInteger(data.getOrderType()));
    }


    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this))
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

        unbinder.unbind();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LoginEvent event) {
        if (event.isSuccess()) {
            page = 1;
            requestOrderInfo();
        } else {
            page = 1;
            orderList.clear();
            adapter.clear();
            adapter.notifyDataSetChanged();
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(OrderListRefreshEvent event) {
        if (event.isSuccess()) {
            page = 1;
            requestOrderInfo();
        } else {
            page = 1;
            resetOrderList("");
        }
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible) {

        } else {

        }
    }

    @Override
    protected void onFragmentFirstVisible() {
        requestOrderInfo();
    }
}
