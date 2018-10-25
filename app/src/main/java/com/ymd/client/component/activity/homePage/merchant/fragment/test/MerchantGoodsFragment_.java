package com.ymd.client.component.activity.homePage.merchant.fragment.test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ymd.client.R;
import com.ymd.client.component.adapter.goods.test.MerchantGoodsAdapter;
import com.ymd.client.model.bean.homePage.MerchantInfoEntity;
import com.ymd.client.model.bean.homePage.YmdGoodsEntity;
import com.ymd.client.model.bean.homePage.YmdRangeGoodsEntity;
import com.ymd.client.model.constant.URLConstant;
import com.ymd.client.web.WebUtil;

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
 * 日期:2018/8/28
 * 描述:    商家页面
 * 修改历史:
 */
public class MerchantGoodsFragment_ extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Unbinder unbinder;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private String mParam1;
    private String mParam2;
    MerchantInfoEntity merchantInfo;

    List<YmdGoodsEntity> recommendFoodDatas = new ArrayList<>();

    private List<YmdRangeGoodsEntity> typeDatas = new ArrayList<>();
    private List<YmdGoodsEntity> foodDatas = new ArrayList<>();

    public MerchantGoodsFragment_() {
        // Required empty public constructor
    }

    public static MerchantGoodsFragment_ newInstance(MerchantInfoEntity merchantInfo) {
        MerchantGoodsFragment_ fragment = new MerchantGoodsFragment_();
        Bundle args = new Bundle();
        args.putSerializable("merchant", merchantInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            merchantInfo = (MerchantInfoEntity) getArguments().getSerializable("merchant");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_merchant_goods_, container, false);
        unbinder = ButterKnife.bind(this, view);
      /*  setShopData();
        setManageData();*/

        requestRecommendData();
        return view;
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
                        requestFoodType();
                    }
                });
    }

    private void setRecommendLayoutData(String resultJson) {
        recommendFoodDatas = new Gson().fromJson(resultJson, new TypeToken<List<YmdGoodsEntity>>(){}.getType());
        requestFoodType();
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
    private void setFoodTypeData(String resultJson) {
        typeDatas = new Gson().fromJson(resultJson, new TypeToken<List<YmdRangeGoodsEntity>>() {
        }.getType());
        requestFoodList();
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
        foodDatas = new Gson().fromJson(resultJson, new TypeToken<List<YmdGoodsEntity>>() {
        }.getType());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        MerchantGoodsAdapter adapter = new MerchantGoodsAdapter(merchantInfo, recommendFoodDatas, typeDatas, foodDatas, getActivity());
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
