package com.ymd.client.component.activity.homePage.food;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ymd.client.R;
import com.ymd.client.common.base.OnUMDItemClickListener;
import com.ymd.client.component.activity.homePage.food.seller.SellerDetailActivity;
import com.ymd.client.component.adapter.food.FoodListAdapter;

import java.util.ArrayList;
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


    public FoodListFragment() {
        // Required empty public constructor
    }

    public static FoodListFragment newInstance(/*String param1, String param2*/) {
        FoodListFragment fragment = new FoodListFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        FoodListAdapter adapter = new FoodListAdapter(getData(), getContext());
        adapter.setListener(new OnUMDItemClickListener() {
            @Override
            public void onClick(Object data, View view, int position) {
                SellerDetailActivity.startAction(getActivity());
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private List getData() {
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("name", "半天妖烤鱼");
        map.put("distance","253m");
        map.put("point",4);
        map.put("work_time", "9:00~21:30");
        map.put("dis_str","全场");
        map.put("dis_num", "8.6折");
        map.put("price", 86);
        map.put("unit","人");
        List<String> diss = new ArrayList<>();
        diss.add("全场8.9折优惠");
        diss.add("早餐免费领豆浆");
        diss.add("上午9:00至12:00有7折优惠");
        map.put("diss", diss);

        list.add(map);

        map = new HashMap<>();
        map.put("name", "驴肉火烧");
        map.put("distance","253m");
        map.put("point",4.3);
        map.put("work_time", "9:00~21:30");
        map.put("dis_str","全场");
        map.put("dis_num", "8.6折");
        map.put("price", 86);
        map.put("unit","人");
        diss = new ArrayList<>();
        diss.add("全场8.9折优惠");
        diss.add("早餐免费领豆浆");
        diss.add("上午9:00至12:00有7折优惠");
        map.put("diss", diss);

        list.add(map);

        map = new HashMap<>();
        map.put("name", "沙县小吃");
        map.put("distance","253m");
        map.put("point",4.3);
        map.put("work_time", "9:00~21:30");
        map.put("dis_str","全场");
        map.put("dis_num", "8.6折");
        map.put("price", 86);
        map.put("unit","人");
        diss = new ArrayList<>();
        diss.add("全场8.9折优惠");
        diss.add("早餐免费领豆浆");
        diss.add("上午9:00至12:00有7折优惠");
        map.put("diss", diss);

        list.add(map);

        return list;
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
