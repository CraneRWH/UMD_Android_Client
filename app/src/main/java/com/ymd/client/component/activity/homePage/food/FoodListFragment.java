package com.ymd.client.component.activity.homePage.food;

import android.content.Context;
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
import com.ymd.client.common.base.OnUMDItemClickListener;
import com.ymd.client.component.activity.homePage.food.seller.MerchantDetailActivity;
import com.ymd.client.component.adapter.food.MerchantListAdapter;
import com.ymd.client.model.bean.homePage.MerchantInfoEntity;
import com.ymd.client.model.constant.URLConstant;
import com.ymd.client.model.info.LocationInfo;
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
 * 日期:2018/8/25
 * 描述:    各种美食的列表
 * 修改历史:
 */
public class FoodListFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;

    private String mParam1;
    private String mParam2;

    private int status;
    private long pid;


    public FoodListFragment() {
        // Required empty public constructor
    }

    public static FoodListFragment newInstance(int type,Long pid/*String param1, String param2*/) {
        FoodListFragment fragment = new FoodListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, type);
        args.putLong(ARG_PARAM2, pid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            status = getArguments().getInt(ARG_PARAM1);
            pid = getArguments().getLong(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
    /*    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        MerchantListAdapter adapter = new MerchantListAdapter(getData(), getContext());
        adapter.setListener(new OnUMDItemClickListener() {
            @Override
            public void onClick(Object data, View view, int position) {
                SellerDetailActivity.startAction(getActivity());
            }
        });
        recyclerView.setAdapter(adapter);*/
        requestMerchant(status);
    }

    public void refreshData(int status) {
        this.status = status;
        requestMerchant(status);
    }

    int page = 1;

    /**
     * 根据城市获取商户列表
     * @param type
     */
    private void requestMerchant(int type){
        Map<String,Object> params = new HashMap<>();
        params.put("county","130406");
        params.put("city", LocationInfo.getInstance().getChooseCity().getCityID());
        params.put("latitude",LocationInfo.getInstance().getLocationInfo().getLatitude());
        params.put("longitude",LocationInfo.getInstance().getLocationInfo().getLongitude());
        params.put("pageNum", page);
        params.put("twoClasses", pid);
        String method = URLConstant.COMPREHENSIVE_MERCHANT;
        switch (type){
            case 0:
                method = URLConstant.COMPREHENSIVE_MERCHANT;
                break;
            case 1:
                method = URLConstant.SALES_MERCHANT;
                break;
            case 2:
                method = URLConstant.PRAISE_MERCHANT;
                break;
            case 3:
                method = URLConstant.NEAR_MERCHANT;
                break;
        }
        WebUtil.getInstance().requestPOST(getActivity(), method, params,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject resultJson) {
                        resetMerchantData(resultJson.optString("list"));
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {

                    }
                });

    }

    private void resetMerchantData(String result) {
        List<MerchantInfoEntity> datas = new Gson().fromJson(result, new TypeToken<List<MerchantInfoEntity>>(){}.getType());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        MerchantListAdapter adapter = new MerchantListAdapter(datas, getContext());
        adapter.setListener(new OnUMDItemClickListener() {
            @Override
            public void onClick(Object data, View view, int position) {
                MerchantDetailActivity.startAction(getActivity(), (MerchantInfoEntity) data);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
