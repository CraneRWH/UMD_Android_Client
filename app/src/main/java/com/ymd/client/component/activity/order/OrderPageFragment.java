package com.ymd.client.component.activity.order;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ymd.client.R;
import com.ymd.client.common.base.OnUMDItemClickListener;
import com.ymd.client.component.activity.homePage.food.seller.CommentSellerActivity;
import com.ymd.client.component.activity.login.LoginByPWActivity;
import com.ymd.client.component.activity.order.detail.OrderDetailActivity;
import com.ymd.client.component.activity.order.pay.OrderPayActivity;
import com.ymd.client.component.adapter.order.OrderPageAdapter;
import com.ymd.client.component.adapter.order.OrderPageAdapter2;
import com.ymd.client.component.event.LoginEvent;
import com.ymd.client.component.event.OrderEvent;
import com.ymd.client.component.widget.zrecyclerview.ProgressStyle;
import com.ymd.client.component.widget.zrecyclerview.ZRecyclerView;
import com.ymd.client.model.bean.homePage.YmdGoodsEntity;
import com.ymd.client.model.bean.order.OrderResultForm;
import com.ymd.client.model.bean.order.YmdOrderGoods;
import com.ymd.client.model.constant.URLConstant;
import com.ymd.client.model.info.LoginInfo;
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
public class OrderPageFragment extends Fragment {

    @BindView(R.id.emptyView)
    TextView emptyView;
    @BindView(R.id.emptyLayout)
    LinearLayout emptyLayout;
    Unbinder unbinder;
    private ZRecyclerView recyclerView;

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
        View view = inflater.inflate(R.layout.fragment_order_page, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    private void initView(View view) {
        recyclerView = (ZRecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallPulse);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);

        recyclerView.setLoadingListener(new ZRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                requestOrderInfo();
            }

            @Override
            public void onLoadMore() {
                page++;
                requestOrderInfo();
            }
        });
        requestOrderInfo();
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
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {
                        resetOrderList("");
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
                    OrderDetailActivity.startAction(getActivity(), item.getId(), ToolUtil.changeInteger(item.getOrderType()));
                }
            });
            adapter.setBtnClickListener(new OrderPageAdapter2.OnBtnClickListener() {
                @Override
                public void onClick(Object data, int position, int id) {
                    OrderResultForm item = (OrderResultForm) data;
                    if (id == R.id.btn3) {
                        switch (item.getOrderStatus()) {
                            case 0:
                                OrderPayActivity.startAction(getActivity(), item.getId());
                                break;
                            case 4:
                                CommentSellerActivity.startAction(getActivity(), item);
                                break;
                        }
                    } else if (id == R.id.btn2) {
                        submitOrder(item);
                    }
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

        recyclerView.refreshComplete();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LoginEvent event) {
        if (event.isSuccess()) {
            page = 1;
            requestOrderInfo();
        } else {
            page = 1;
            resetOrderList("");
        }
    }
}
