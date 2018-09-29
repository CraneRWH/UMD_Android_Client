package com.ymd.client.component.activity.homePage.food.seller.fragment;

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
import com.ymd.client.component.adapter.food.EvaluateSellerAdapter;
import com.ymd.client.model.bean.homePage.MerchantInfoEntity;
import com.ymd.client.model.bean.homePage.YmdEvaluationEntity;
import com.ymd.client.model.constant.URLConstant;
import com.ymd.client.web.WebUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者:rongweihe
 * 日期:2018/8/28 时间:22:52
 * 描述:  点评页面
 * 修改历史:
 */
public class EvaluateSellerFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.evaluate_rv)
    RecyclerView evaluateRv;
    Unbinder unbinder;

    private String mParam1;
    private String mParam2;
    MerchantInfoEntity merchantInfo;

    public EvaluateSellerFragment() {
    }

    public static EvaluateSellerFragment newInstance(MerchantInfoEntity merchantInfo) {
        EvaluateSellerFragment fragment = new EvaluateSellerFragment();
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
        View view = inflater.inflate(R.layout.fragment_evaluateseller, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        requestEvalustes();
    }

    int page = 1;
    private void requestEvalustes() {
        Map<String,Object> params = new HashMap<>();
        params.put("merchantId",merchantInfo.getId());
        params.put("pageNum", page);
        WebUtil.getInstance().requestPOST(getActivity(), URLConstant.MERCHANT_EVALUATION_LIST, params,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject result) {
                        setEvaluteData(result.optString("list"));
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {

                    }
                });
    }

    private void setEvaluteData(String resultJson) {
        List<YmdEvaluationEntity> list = new Gson().fromJson(resultJson, new TypeToken<List<YmdEvaluationEntity>>(){}.getType());
        for (YmdEvaluationEntity item : list) {
            item.setMerchantName(merchantInfo.getName());
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        evaluateRv.setLayoutManager(layoutManager);
        EvaluateSellerAdapter adapter = new EvaluateSellerAdapter(list, getActivity());
        evaluateRv.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
