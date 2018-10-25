package com.ymd.client.component.adapter.goods.test;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ymd.client.R;
import com.ymd.client.common.base.OnUMDItemClickListener;
import com.ymd.client.component.adapter.goods.MerchantGoodTypeListAdapter;
import com.ymd.client.component.adapter.goods.MerchantGoodsListAdapter;
import com.ymd.client.model.bean.homePage.MerchantInfoEntity;
import com.ymd.client.model.bean.homePage.YmdGoodsEntity;
import com.ymd.client.model.bean.homePage.YmdRangeGoodsEntity;
import com.ymd.client.utils.ToolUtil;

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
public class MerchantGoodsListAdapter_ extends RecyclerView.Adapter<MerchantGoodsListAdapter_.ViewHolder> {


    MerchantInfoEntity merchantInfo;

    private Activity mContext;

    private OnUMDItemClickListener listener;

    private List<YmdRangeGoodsEntity> typeDatas = new ArrayList<>();
    private List<YmdGoodsEntity> foodDatas = new ArrayList<>();
    MerchantGoodsListAdapter foodAdapter;
    MerchantGoodTypeListAdapter typeAdapter;

    private OnRefreshListener refreshListener;

    public MerchantGoodsListAdapter_(MerchantInfoEntity datas, List<YmdRangeGoodsEntity> typeDatas, List<YmdGoodsEntity> foodDatas, Activity mContext) {
        this.merchantInfo = datas;
        this.typeDatas = typeDatas;
        this.foodDatas = foodDatas;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_merchant_goods_list_test, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position == 0) {
            setFoodTypeData(holder);
            setFoodData(holder);
        } else {
            holder.rootView.setVisibility(View.GONE);
        }

    }

    public void setFoodListCount(YmdGoodsEntity map) {
        for (YmdGoodsEntity item : foodDatas) {
            if (item.getId() == map.getId()) {
                item.setBuyCount(map.getBuyCount());
                break;
            }
        }
        foodAdapter.notifyDataSetChanged();
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
        typeAdapter = new MerchantGoodTypeListAdapter(typeDatas, mContext);
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
        foodAdapter = new MerchantGoodsListAdapter(foodDatas, mContext);
        foodAdapter.setBtnListener(new MerchantGoodsListAdapter.OnSubORAddListener() {
            @Override
            public void onSubClick(YmdGoodsEntity data, View view, int position) {
                if (refreshListener != null)
                    refreshListener.onRefresh(data);
            }

            @Override
            public void onAddClick(YmdGoodsEntity data, View view, int position) {
                if (refreshListener != null)
                    refreshListener.onRefresh(data);
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

    public OnRefreshListener getRefreshListener() {
        return refreshListener;
    }

    public void setRefreshListener(OnRefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View rootView;

        @BindView(R.id.type_rv)
        RecyclerView typeRv;
        @BindView(R.id.food_rv)
        RecyclerView foodRv;

        ViewHolder(View view) {
            super(view);
            rootView = view;
            ButterKnife.bind(this, view);
        }
    }

    public interface OnRefreshListener{
        public void onRefresh(YmdGoodsEntity data);
    }
}
