package com.ymd.client.component.activity.homePage.food.seller;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eowise.recyclerview.stickyheaders.OnHeaderClickListener;
import com.eowise.recyclerview.stickyheaders.StickyHeadersItemDecoration;
import com.ymd.client.R;
import com.ymd.client.component.activity.homePage.food.seller.fragment.BaseFragment;
import com.ymd.client.component.adapter.food.FoodSellerListAdapter;
import com.ymd.client.component.adapter.merchant.BigramHeaderAdapter;
import com.ymd.client.component.adapter.merchant.PersonAdapter;
import com.ymd.client.component.event.GoodsListEvent;
import com.ymd.client.utils.ToolUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    private RecyclerView recyclerView;
    private PersonAdapter personAdapter;
    private StickyHeadersItemDecoration top;
    private BigramHeaderAdapter headerAdapter;

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
        View view = inflater.inflate(R.layout.fragment_choose_dishes_, container, false);
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


    /**
     * 处理滑动 是两个ListView联动
     */
    private class MyOnGoodsScrollListener implements AbsListView.OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        /*    if (!(lastTitlePoi == goodsitemEntities
                    .get(firstVisibleItem)
                    .getId())) {
                lastTitlePoi = goodsitemEntities
                        .get(firstVisibleItem)
                        .getId();
                mGoodsCategoryListAdapter.setCheckPosition(goodsitemEntities
                        .get(firstVisibleItem)
                        .getId());
                mGoodsCategoryListAdapter.notifyDataSetChanged();
            }*/
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
       /* for (int i = 0; i < list.size(); i++) {
            //寻找行布局，第一个参数为行布局ID，第二个参数为这个行布局需要放到那个容器上
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_seller_food_type, foodTypeLt, false);

            TextView item_tv;
            item_tv = (TextView) view.findViewById(R.id.item_tv);
            item_tv.setText(ToolUtil.changeString(list.get(i).get("name")));
            if (i == 0) {
                item_tv.setTextColor(R.color.common_text_color);
                view.setBackgroundResource(R.color.bg_gray);
            } else {
                item_tv.setTextColor(R.color.text_gray_dark);
                view.setBackgroundResource(R.color.white);
            }
            //把行布局放到linear里
            foodTypeLt.addView(view);
        }*/
    }

    private ViewGroup anim_fragment_layout;//动画层

    /**
     * 设置动画（点击添加商品）
     *
     * @param v
     * @param startLocation
     */
    public void setAnim(final View v, int[] startLocation) {
        anim_fragment_layout = null;
        anim_fragment_layout = createAnimLayout();
        anim_fragment_layout.addView(v);//把动画小球添加到动画层
        final View view = addViewToAnimLayout(anim_fragment_layout, v, startLocation);
        int[] endLocation = new int[2];// 存储动画结束位置的X、Y坐标
        // 计算位移
        int endX = 0 - startLocation[0] + 40;// 动画位移的X坐标
        int endY = endLocation[1] - startLocation[1];// 动画位移的y坐标

        TranslateAnimation translateAnimationX = new TranslateAnimation(0, endX, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0, 0, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationY.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);
        set.setDuration(400);// 动画的执行时间
        view.startAnimation(set);
        // 动画监听事件
        set.setAnimationListener(new Animation.AnimationListener() {
            // 动画的开始
            @Override
            public void onAnimationStart(Animation animation) {
                v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            // 动画的结束
            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
            }
        });

    }

    /**
     * 初始化动画图层
     *
     * @return
     */
    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) getActivity().getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE - 1);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    /**
     * 将View添加到动画图层
     *
     * @param parent
     * @param view
     * @param location
     * @return
     */
    private View addViewToAnimLayout(final ViewGroup parent, final View view,
                                     int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
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
        map.put("type", 0);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 0);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 0);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 1);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 1);

        list.add(map);

        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 2);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 3);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 3);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 4);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 4);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 5);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 6);
        list.add(map);


        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 7);
        list.add(map);


        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 7);
        list.add(map);


        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 7);
        list.add(map);


        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 7);
        list.add(map);


        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 8);
        list.add(map);


        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 8);
        list.add(map);


        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 8);
        list.add(map);


        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 8);
        list.add(map);


        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 9);
        list.add(map);


        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 9);
        list.add(map);


        map = new HashMap<>();
        map.put("name", "天下第一凉粉");
        map.put("desc", "凉粉+酸梅汁+啤酒+可乐+筷子");
        map.put("sale_num", "200");
        map.put("price", 23);
        map.put("old_pride", 30);
        map.put("num", 0);
        map.put("type", 9);
        list.add(map);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        foodRv.setLayoutManager(linearLayoutManager);
        FoodSellerListAdapter adapter = new FoodSellerListAdapter(list, getActivity());
        foodRv.setAdapter(adapter);

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
