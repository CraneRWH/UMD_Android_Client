package com.ymd.client.component.activity.homePage.food.seller;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.component.adapter.food.FoodSellerListAdapter;
import com.ymd.client.utils.ToolUtil;

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
public class ChooseDishesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recommendLayout)
    LinearLayout recommendLayout;
    @BindView(R.id.food_type_lt)
    LinearLayout foodTypeLt;
    @BindView(R.id.food_rv)
    RecyclerView foodRv;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChooseDishesFragment() {
        // Required empty public constructor
    }

    public static ChooseDishesFragment newInstance(/*String param1, String param2*/) {
        ChooseDishesFragment fragment = new ChooseDishesFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_dishes, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        setRecommendLayoutData();
        setFoodTypeData();
        setFoodData();
    }

    private void setRecommendLayoutData() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("name", "food_item_icon");
        map.put("icon", R.mipmap.nice_good_icon1);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "hospital_item_icon");
        map.put("icon", R.mipmap.nice_good_icon1);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "car_item_icon");
        map.put("icon", R.mipmap.nice_good_icon1);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "meirong_item_icon");
        map.put("icon", R.mipmap.nice_good_icon1);
        list.add(map);

        //开始添加数据
        for (int x = 0; x < list.size(); x++) {
            //寻找行布局，第一个参数为行布局ID，第二个参数为这个行布局需要放到那个容器上
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_seller_food_recommend, recommendLayout, false);

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
            icon_iv.setImageResource(ToolUtil.changeInteger(list.get(x).get("icon")));
            //给TextView添加文字
            //    tv.setText("第"+(x+1)+"张");
            //把行布局放到linear里
            recommendLayout.addView(view);
        }
    }

    @SuppressLint("ResourceAsColor")
    private void setFoodTypeData() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("name", "凉菜");
        list.add(map);

        map = new HashMap<>();
        map.put("name", "酒水");
        list.add(map);

        map = new HashMap<>();
        map.put("name", "热菜");
        list.add(map);

        map = new HashMap<>();
        map.put("name", "热销");
        list.add(map);

        map = new HashMap<>();
        map.put("name", "凉菜");
        list.add(map);

        map = new HashMap<>();
        map.put("name", "酒水");
        list.add(map);

        map = new HashMap<>();
        map.put("name", "热菜");
        list.add(map);

        map = new HashMap<>();
        map.put("name", "热销");
        list.add(map);

        map = new HashMap<>();
        map.put("name", "凉菜");
        list.add(map);

        map = new HashMap<>();
        map.put("name", "酒水");
        list.add(map);

        map = new HashMap<>();
        map.put("name", "热菜");
        list.add(map);

        map = new HashMap<>();
        map.put("name", "热销");
        list.add(map);

        //开始添加数据
        for (int i = 0; i < list.size(); i++) {
            //寻找行布局，第一个参数为行布局ID，第二个参数为这个行布局需要放到那个容器上
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_seller_food_type, foodTypeLt, false);

            TextView item_tv;
            item_tv = (TextView) view.findViewById(R.id.item_tv);
            item_tv.setText(ToolUtil.changeString(list.get(i).get("name")));
            if (i== 0) {
                item_tv.setTextColor(R.color.common_text_color);
                view.setBackgroundResource(R.color.bg_gray);
            } else {
                item_tv.setTextColor(R.color.text_gray_dark);
                view.setBackgroundResource(R.color.white);
            }
            //把行布局放到linear里
            foodTypeLt.addView(view);
        }
    }

    private void setFoodData() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type",0);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type",0);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type",0);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type",1);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type",1);

        list.add(map);

        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type",2);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type",3);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type",3);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type",4);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type",4);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type",5);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type",6);
        list.add(map);


        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type",7);
        list.add(map);


        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type",7);
        list.add(map);


        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type",7);
        list.add(map);


        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type",7);
        list.add(map);


        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type",8);
        list.add(map);


        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type",8);
        list.add(map);


        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type",8);
        list.add(map);


        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type",8);
        list.add(map);


        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type",9);
        list.add(map);


        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type",9);
        list.add(map);


        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type",9);
        list.add(map);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        foodRv.setLayoutManager(linearLayoutManager);
        FoodSellerListAdapter adapter = new FoodSellerListAdapter(list, getActivity());
        foodRv.setAdapter(adapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
