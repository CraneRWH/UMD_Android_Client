package com.ymd.client.component.activity.order.detail;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.common.base.OnUMDItemClickListener;
import com.ymd.client.component.adapter.order.OrderDetailBaofangAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderDetailFragment extends Fragment {


    @BindView(R.id.date_tv)
    TextView dateTv;
    @BindView(R.id.time_tv)
    TextView timeTv;
    @BindView(R.id.dating_tv)
    TextView datingTv;
    @BindView(R.id.baofang_tv)
    TextView baofangTv;
    @BindView(R.id.baofang_rv)
    RecyclerView baofangRv;
    @BindView(R.id.eat_location_lt)
    LinearLayout eatLocationLt;
    @BindView(R.id.sub_iv)
    ImageView subIv;
    @BindView(R.id.eat_person_num_tv)
    TextView eatPersonNumTv;
    @BindView(R.id.add_iv)
    ImageView addIv;
    @BindView(R.id.eat_person_num_rt)
    RelativeLayout eatPersonNumRt;
    @BindView(R.id.line2)
    View line2;
    @BindView(R.id.shop_name_tv)
    TextView shopNameTv;
    @BindView(R.id.food_list_lt)
    LinearLayout foodListLt;
    @BindView(R.id.more_pay_type_lt)
    LinearLayout morePayTypeLt;
    @BindView(R.id.order_price_tv)
    TextView orderPriceTv;
    @BindView(R.id.dis_price_tv)
    TextView disPriceTv;
    @BindView(R.id.u_dis_price_tv)
    TextView uDisPriceTv;
    @BindView(R.id.u_get_tv)
    TextView uGetTv;
    Unbinder unbinder;

    public OrderDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    private void initView(View view) {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        baofangRv.setLayoutManager(layoutManager);
        OrderDetailBaofangAdapter adapter = new OrderDetailBaofangAdapter(getBaofangList(), getContext());
        adapter.setListener(new OnUMDItemClickListener() {
            @Override
            public void onClick(Object data, View view, int position) {

            }
        });
        baofangRv.setAdapter(adapter);
    }

    private List getBaofangList() {
        List<Map<String ,Object>> list = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("name","牡丹厅");
        map.put("desc","8-10");
        map.put("isChoose", true);
        list.add(map);

        map = new HashMap<>();
        map.put("name","荷花厅");
        map.put("desc","8-10");
        map.put("isChoose", false);
        list.add(map);

        map = new HashMap<>();
        map.put("name","香草厅");
        map.put("desc","8-10");
        map.put("isChoose", false);
        list.add(map);

        map = new HashMap<>();
        map.put("name","百合厅");
        map.put("desc","8-10");
        map.put("isChoose", false);
        list.add(map);

        map = new HashMap<>();
        map.put("name","东坝厅");
        map.put("desc","8-10");
        map.put("isChoose", false);
        list.add(map);

        map = new HashMap<>();
        map.put("name","四海厅");
        map.put("desc","8-10");
        map.put("isChoose", false);
        list.add(map);

        map = new HashMap<>();
        map.put("name","五湖厅");
        map.put("desc","8-10");
        map.put("isChoose", false);
        list.add(map);

        map = new HashMap<>();
        map.put("name","江南厅");
        map.put("desc","8-10");
        map.put("isChoose", false);
        list.add(map);

        return list;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
