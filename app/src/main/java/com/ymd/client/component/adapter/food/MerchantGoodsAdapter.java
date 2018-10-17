package com.ymd.client.component.adapter.food;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
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
import com.ymd.client.R;
import com.ymd.client.common.base.OnUMDItemClickListener;
import com.ymd.client.model.bean.homePage.MerchantInfoEntity;
import com.ymd.client.model.bean.homePage.YmdGoodsEntity;
import com.ymd.client.model.bean.homePage.YmdRangeGoodsEntity;
import com.ymd.client.utils.ToolUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者:rongweihe
 * 日期:2018/8/24  时间:22:52
 * 描述:
 * 修改历史:
 */
public class MerchantGoodsAdapter extends RecyclerView.Adapter<MerchantGoodsAdapter.ViewHolder> {


    MerchantInfoEntity merchantInfo;

    private Activity mContext;

    private OnUMDItemClickListener listener;

    List<YmdGoodsEntity> recommendFoodDatas = new ArrayList<>();

    private List<YmdRangeGoodsEntity> typeDatas = new ArrayList<>();
    private List<YmdGoodsEntity> foodDatas = new ArrayList<>();
    FoodSellerListAdapter foodAdapter;
    FoodTypeListAdapter typeAdapter;

    public MerchantGoodsAdapter(MerchantInfoEntity datas, List<YmdGoodsEntity> recommendFoodDatas, List<YmdRangeGoodsEntity> typeDatas, List<YmdGoodsEntity> foodDatas, Activity mContext) {
        this.merchantInfo = datas;
        this.recommendFoodDatas = recommendFoodDatas;
        this.typeDatas = typeDatas;
        this.foodDatas = foodDatas;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_merchant_goods, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position == 0) {
            refreshRecommendDatas(holder);
            setFoodTypeData(holder);
            setFoodData(holder);
        } else {
            holder.rootView.setVisibility(View.GONE);
        }

    }

    private void refreshRecommendDatas(ViewHolder holder) {
        holder.recommendLayout.removeAllViews();
        //开始添加数据
        for (int x = 0; x < recommendFoodDatas.size(); x++) {
            //寻找行布局，第一个参数为行布局ID，第二个参数为这个行布局需要放到那个容器上
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_seller_food_recommend, holder.recommendLayout, false);
            YmdGoodsEntity data = recommendFoodDatas.get(x);
            ImageView icon_iv;
            TextView name_tv;
            TextView sale_num_tv;
            TextView now_price_tv;
            ImageView buy_btn;
            ImageView sub_btn;
            TextView num_tv;
            icon_iv = (ImageView) view.findViewById(R.id.icon_iv);
            name_tv = (TextView) view.findViewById(R.id.name_tv);
            sale_num_tv = (TextView) view.findViewById(R.id.sale_num_tv);
            now_price_tv = (TextView) view.findViewById(R.id.now_price_tv);
            buy_btn = (ImageView) view.findViewById(R.id.buy_btn);
            sub_btn = (ImageView) view.findViewById(R.id.sub_btn);
            num_tv = (TextView) view.findViewById(R.id.num_tv);
            try {
                //将int数组中的数据放到ImageView中
                if (ToolUtil.changeString(data.getGoodsUrl().get(0)).length() > 0) {
                    Glide.with(mContext).load(ToolUtil.changeString(data.getGoodsUrl().get(0))).into(icon_iv);
                }
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            name_tv.setText(data.getGoodsName());
            sale_num_tv.setText("月销 " + data.getSales());
            now_price_tv.setText(ToolUtil.changeString(data.getPrice()));
            if (ToolUtil.changeInteger(data.getBuyCount()) > 0) {
                num_tv.setText(ToolUtil.changeString(data.getBuyCount()));
                sub_btn.setVisibility(View.VISIBLE);
                num_tv.setVisibility(View.VISIBLE);
            } else {
                sub_btn.setVisibility(View.GONE);
                num_tv.setVisibility(View.GONE);
            }

            buy_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data.setBuyCount(ToolUtil.changeInteger(data.getBuyCount()) + 1);
                    if (data.getBuyCount() > 0) {
                        sub_btn.setVisibility(View.VISIBLE);
                        num_tv.setVisibility(View.VISIBLE);
                        num_tv.setText(ToolUtil.changeString(data.getBuyCount()));
                    }
                    EventBus.getDefault().post(data);
                    setFoodListCount(data);
                }
            });

            sub_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    data.setBuyCount(ToolUtil.changeInteger(data.getBuyCount()) - 1);
                    if (data.getBuyCount() == 0) {
                        sub_btn.setVisibility(View.GONE);
                        num_tv.setVisibility(View.GONE);
                        num_tv.setText(ToolUtil.changeString(data.getBuyCount()));
                    }
                    num_tv.setText(ToolUtil.changeString(data.getBuyCount()));
                    EventBus.getDefault().post(data);
                    setFoodListCount(data);
                }
            });

            //给TextView添加文字
            //    tv.setText("第"+(x+1)+"张");
            //把行布局放到linear里
            holder.recommendLayout.addView(view);
        }
    }

    private void setFoodListCount(YmdGoodsEntity map) {
        for (YmdGoodsEntity item : foodDatas) {
            if (item.getId() == map.getId()) {
                item.setBuyCount(map.getBuyCount());
                break;
            }
        }
        foodAdapter.notifyDataSetChanged();
    }

    private void setRecommendListCount(YmdGoodsEntity map) {
        for (YmdGoodsEntity item : recommendFoodDatas) {
            if (item.getId() == map.getId()) {
                item.setBuyCount(map.getBuyCount());
                break;
            }
        }
        notifyDataSetChanged();
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

    private void setFoodTypeData(ViewHolder holder) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        holder.typeRv.setLayoutManager(linearLayoutManager);
        typeAdapter = new FoodTypeListAdapter(typeDatas, mContext);
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
                                smoothMoveToPosition(holder.typeRv, i);
                            } else {
                                smoothMoveToPosition(holder.typeRv, i + 1);
                            }
                            break;
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        });
        holder.typeRv.setAdapter(typeAdapter);
        holder.typeRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mShouldScroll && RecyclerView.SCROLL_STATE_IDLE == newState) {
                    mShouldScroll = false;
                    smoothMoveToPosition(holder.typeRv, mToPosition);
                }
            }
        });
    }

    private void setFoodData(ViewHolder holder) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        holder.foodRv.setLayoutManager(linearLayoutManager);
        foodAdapter = new FoodSellerListAdapter(foodDatas, mContext);
        foodAdapter.setBtnListener(new FoodSellerListAdapter.OnSubORAddListener() {
            @Override
            public void onSubClick(YmdGoodsEntity data, View view, int position) {
                setRecommendListCount(data);
            }

            @Override
            public void onAddClick(YmdGoodsEntity data, View view, int position) {
                setRecommendListCount(data);
            }
        });
        holder.foodRv.setAdapter(foodAdapter);
        holder.foodRv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int position = linearLayoutManager.findFirstVisibleItemPosition();
                long typeId = ToolUtil.changeLong(foodDatas.get(position).getRangeGoods());
                for (int i = 0; i < typeDatas.size(); i++) {
                    if (ToolUtil.changeLong(typeDatas.get(i).getId()) == typeId) {
                        typeAdapter.changeChooseItem(i);
                    }
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public OnUMDItemClickListener getListener() {
        return listener;
    }

    public void setListener(OnUMDItemClickListener listener) {
        this.listener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View rootView;

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

        ViewHolder(View view) {
            super(view);
            rootView = view;
            ButterKnife.bind(this, view);
        }
    }
}
