package com.ymd.client.component.activity.homePage.food.seller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ymd.client.R;
import com.ymd.client.component.adapter.food.EvaluateSellerAdapter;

import java.util.ArrayList;
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

    public EvaluateSellerFragment() {
    }

    public static EvaluateSellerFragment newInstance(/*String param1, String param2*/) {
        EvaluateSellerFragment fragment = new EvaluateSellerFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
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
        setEvaluteData();
    }

    private void setEvaluteData() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("date", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("point", 4);
        map.put("desc", "不能确保客户收到的货物与商城图片、产地、附件说明完全一致。只能确保为原厂正货！并且保证与当时市场上同样主流新品一致。若本商城没有及时更新，请大家谅解！");
        map.put("replay", "凭质保证书及京东商城发票，可享受全国联保服务（奢侈品、钟表除外；奢侈品、钟表由京东联系保修，享受法定三包售后服务），与您亲临商场");
        list.add(map);

        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("date", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("point", 4);
        map.put("desc", "不能确保客户收到的货物与商城图片、产地、附件说明完全一致。只能确保为原厂正货！并且保证与当时市场上同样主流新品一致。若本商城没有及时更新，请大家谅解！");
        map.put("replay", "凭质保证书及京东商城发票，可享受全国联保服务（奢侈品、钟表除外；奢侈品、钟表由京东联系保修，享受法定三包售后服务），与您亲临商场");
        list.add(map);

        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("date", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("point", 4);
        map.put("desc", "不能确保客户收到的货物与商城图片、产地、附件说明完全一致。只能确保为原厂正货！并且保证与当时市场上同样主流新品一致。若本商城没有及时更新，请大家谅解！");
        map.put("replay", "凭质保证书及京东商城发票，可享受全国联保服务（奢侈品、钟表除外；奢侈品、钟表由京东联系保修，享受法定三包售后服务），与您亲临商场");
        list.add(map);

        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("date", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("point", 4);
        map.put("desc", "不能确保客户收到的货物与商城图片、产地、附件说明完全一致。只能确保为原厂正货！并且保证与当时市场上同样主流新品一致。若本商城没有及时更新，请大家谅解！");
        map.put("replay", "凭质保证书及京东商城发票，可享受全国联保服务（奢侈品、钟表除外；奢侈品、钟表由京东联系保修，享受法定三包售后服务），与您亲临商场");
        list.add(map);

        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("date", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("point", 4);
        map.put("desc", "不能确保客户收到的货物与商城图片、产地、附件说明完全一致。只能确保为原厂正货！并且保证与当时市场上同样主流新品一致。若本商城没有及时更新，请大家谅解！");
        map.put("replay", "凭质保证书及京东商城发票，可享受全国联保服务（奢侈品、钟表除外；奢侈品、钟表由京东联系保修，享受法定三包售后服务），与您亲临商场");

        list.add(map);

        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("date", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("point", 4);
        map.put("desc", "不能确保客户收到的货物与商城图片、产地、附件说明完全一致。只能确保为原厂正货！并且保证与当时市场上同样主流新品一致。若本商城没有及时更新，请大家谅解！");
        map.put("replay", "凭质保证书及京东商城发票，可享受全国联保服务（奢侈品、钟表除外；奢侈品、钟表由京东联系保修，享受法定三包售后服务），与您亲临商场");
        list.add(map);

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
