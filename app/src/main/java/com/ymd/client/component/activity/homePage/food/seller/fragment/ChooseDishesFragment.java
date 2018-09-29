package com.ymd.client.component.activity.homePage.food.seller.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eowise.recyclerview.stickyheaders.OnHeaderClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ymd.client.R;
import com.ymd.client.common.base.OnUMDItemClickListener;
import com.ymd.client.component.adapter.food.FoodSellerListAdapter;
import com.ymd.client.component.adapter.food.FoodTypeListAdapter;
import com.ymd.client.component.adapter.merchant.PersonAdapter;
import com.ymd.client.component.event.GoodsListEvent;
import com.ymd.client.model.bean.homePage.MerchantInfoEntity;
import com.ymd.client.model.bean.homePage.YmdGoodsEntity;
import com.ymd.client.model.bean.homePage.YmdRangeGoodsEntity;
import com.ymd.client.model.constant.URLConstant;
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
 * 作者:rongweihe
 * 日期:2018/8/24  时间:22:52
 * 描述:  点餐页面
 * 修改历史:
 */
public class ChooseDishesFragment extends BaseFragment implements PersonAdapter.OnShopCartGoodsChangeListener, OnHeaderClickListener {

    Unbinder unbinder;
    @BindView(R.id.recommendLayout)
    LinearLayout recommendLayout;
    @BindView(R.id.fcollapsing)
    CollapsingToolbarLayout fcollapsing;
    @BindView(R.id.type_rv)
    RecyclerView typeRv;
    @BindView(R.id.food_rv)
    RecyclerView foodRv;
    @BindView(R.id.fragment_main)
    CoordinatorLayout fragmentMain;

    //存储含有标题的第一个含有商品类别名称的条目的下表
    private List<Integer> titlePois = new ArrayList<>();
    //上一个标题的小标
    private int lastTitlePoi;
    private FoodTypeListAdapter typeAdapter;
    private FoodSellerListAdapter foodAdapter;

    private List<YmdRangeGoodsEntity> typeDatas = new ArrayList<>();
    private List<YmdGoodsEntity> foodDatas = new ArrayList<>();
    MerchantInfoEntity merchantInfo;
    public ChooseDishesFragment() {
        // Required empty public constructor
    }

    public static ChooseDishesFragment newInstance(MerchantInfoEntity merchantInfo/*String param1, String param2*/) {
        ChooseDishesFragment fragment = new ChooseDishesFragment();
        Bundle args = new Bundle();
        args.putSerializable("merchant", merchantInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null) {
            merchantInfo = (MerchantInfoEntity) getArguments().getSerializable("merchant");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_dishes_, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
     //   setRecommendLayoutData();
        requestRecommendData();
        requestFoodType();
        requestFoodList();
    //    setFoodTypeData();
    //    setFoodData();
    }

    private void requestRecommendData() {
        Map<String,Object> params = new HashMap<>();
        params.put("merchantId",merchantInfo.getId());
        WebUtil.getInstance().requestPOST(getActivity(), URLConstant.MERCHANT_RECOMMEND_GOODS, params,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject result) {
                        setRecommendLayoutData(result.optString("list"));
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {

                    }
                });
    }

    List<YmdGoodsEntity> recommendFoodDatas = new ArrayList<>();
    private void setRecommendLayoutData(String resultJson) {
        recommendFoodDatas = new Gson().fromJson(resultJson, new TypeToken<List<YmdGoodsEntity>>(){}.getType());

        //开始添加数据
        for (int x = 0; x < recommendFoodDatas.size(); x++) {
            //寻找行布局，第一个参数为行布局ID，第二个参数为这个行布局需要放到那个容器上
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_seller_food_recommend, recommendLayout, false);
            YmdGoodsEntity data = recommendFoodDatas.get(x);
            ImageView icon_iv;
            TextView name_tv;
            TextView sale_num_tv;
            TextView now_price_tv;
            ImageView buy_btn;
            icon_iv = (ImageView) view.findViewById(R.id.icon_iv);
            name_tv = (TextView) view.findViewById(R.id.name_tv);
            sale_num_tv = (TextView) view.findViewById(R.id.sale_num_tv);
            now_price_tv = (TextView) view.findViewById(R.id.now_price_tv);
            buy_btn = (ImageView) view.findViewById(R.id.buy_btn);
            //将int数组中的数据放到ImageView中
            Glide.with(this).load(data.getGoodsUrl()).into(icon_iv);
            name_tv.setText(data.getGoodsName());
            sale_num_tv.setText(data.getSales());
            now_price_tv.setText(ToolUtil.changeString(data.getPrice()));

            buy_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data.setBuyCount(ToolUtil.changeInteger(data.getBuyCount()) + 1);
                    EventBus.getDefault().post(data);
                }
            });
            //给TextView添加文字
            //    tv.setText("第"+(x+1)+"张");
            //把行布局放到linear里
            recommendLayout.addView(view);
        }
    }

    private void requestFoodType() {
        Map<String,Object> params = new HashMap<>();
        params.put("merchantId",merchantInfo.getId());
        WebUtil.getInstance().requestPOST(getActivity(), URLConstant.MERCHANT_GOOD_TYPE, params,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject result) {
                        setFoodTypeData(result.optString("list"));
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {

                    }
                });
    }

    @SuppressLint("ResourceAsColor")
    private void setFoodTypeData(String resultJson) {

        typeDatas = new Gson().fromJson(resultJson, new TypeToken<List<YmdRangeGoodsEntity>>(){}.getType());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        typeRv.setLayoutManager(linearLayoutManager);
        typeAdapter = new FoodTypeListAdapter(typeDatas, getActivity());
        typeAdapter.setOnItemClickListener(new OnUMDItemClickListener() {
            @Override
            public void onClick(Object data, View view, int position) {
                YmdRangeGoodsEntity map = (YmdRangeGoodsEntity) data;
                long pid = map.getId();
                try {
                    for (int i = 0; i < foodDatas.size(); i++) {
                        YmdGoodsEntity item = foodDatas.get(i);
                        if (item.getRangeGoods() == pid) {
                      /*  linearLayoutManager.scrollToPositionWithOffset(i, 0);
                        linearLayoutManager.setStackFromEnd(true);*/
                            if (i != -1) {
                                smoothMoveToPosition(typeRv, i);
                            } else {
                                smoothMoveToPosition(typeRv, i + 1);
                            }
                            break;
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        });
        typeRv.setAdapter(typeAdapter);
        typeRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mShouldScroll&& RecyclerView.SCROLL_STATE_IDLE == newState) {
                    mShouldScroll = false;
                    smoothMoveToPosition(typeRv, mToPosition);
                }
            }
        });
    }

    //目标项是否在最后一个可见项之后
    private boolean mShouldScroll;
    //记录目标项位置
    private int mToPosition;
    /**
     * 滑动到指定位置
     */
    private void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
        // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));
        if (position < firstItem) {
            // 第一种可能:跳转位置在第一个可见位置之前
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 第二种可能:跳转位置在第一个可见位置之后
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                mRecyclerView.smoothScrollBy(0, top);
            }
        } else {
            // 第三种可能:跳转位置在最后可见项之后
            mRecyclerView.smoothScrollToPosition(position);
            mToPosition = position;
            mShouldScroll = true;
        }
    }


    private void requestFoodList() {
        Map<String,Object> params = new HashMap<>();
        params.put("merchantId",merchantInfo.getId());
        WebUtil.getInstance().requestPOST(getActivity(), URLConstant.MERCHANT_GOOD_LIST, params,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject result) {
                        setFoodData(result.optString("list"));
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {

                    }
                });
    }

    private void setFoodData(String resultJson) {
        foodDatas = new Gson().fromJson(resultJson, new TypeToken<List<YmdGoodsEntity>>(){}.getType());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        foodRv.setLayoutManager(linearLayoutManager);
        foodAdapter = new FoodSellerListAdapter(foodDatas, getActivity());
        foodRv.setAdapter(foodAdapter);
        foodRv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int position = linearLayoutManager.findFirstVisibleItemPosition();
                long typeId = ToolUtil.changeLong(foodDatas.get(position).getRangeGoods());
                for (int i=0;i<typeDatas.size();i++){
                    if(ToolUtil.changeLong(typeDatas.get(i).getId()) == typeId ){
                        typeAdapter.changeChooseItem(i);
                    }
                }

            }
        });
    }

    /**
     * 添加 或者  删除  商品发送的消息处理
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(GoodsListEvent event) {
        if (event.buyNums.length > 0) {
           /* for (int i=0;i<event.buyNums.length;i++){
                goodscatrgoryEntities.get(i).setBugNum(event.buyNums[i]);
            }
            mGoodsCategoryListAdapter.changeData(goodscatrgoryEntities);*/
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onHeaderClick(View header, long headerId) {

    }

    @Override
    public void onNumChange() {

    }
}
