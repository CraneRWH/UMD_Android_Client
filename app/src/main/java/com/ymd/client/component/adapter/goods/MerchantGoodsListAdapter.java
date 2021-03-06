package com.ymd.client.component.adapter.goods;

import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ymd.client.R;
import com.ymd.client.common.base.OnUMDItemClickListener;
import com.ymd.client.model.bean.homePage.YmdGoodsEntity;
import com.ymd.client.utils.ToolUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者:rongweihe
 * 日期:2018/8/24  时间:22:52
 * 描述:  卖家美事列表的Adapter
 * 修改历史:
 */
public class MerchantGoodsListAdapter extends RecyclerView.Adapter<MerchantGoodsListAdapter.ViewHolder> {

    private List<YmdGoodsEntity> datas;
    private Context mContext;

    private OnUMDItemClickListener listener;
    private OnSubORAddListener btnListener;

    public MerchantGoodsListAdapter(List<YmdGoodsEntity> datas, Context mContext) {
        this.datas = datas;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_merchant_goods_list, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        YmdGoodsEntity data = datas.get(position);
        try {
            if (ToolUtil.changeString(data.getGoodsUrl().get(0)).length() > 0) {
                Glide.with(mContext).load(ToolUtil.changeString(data.getGoodsUrl().get(0))).into(holder.iconIv);
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        holder.nameTv.setText(ToolUtil.changeString(data.getGoodsName()));
        holder.descTv.setText(ToolUtil.changeString(data.getDescribe()));
        holder.saleNumTv.setText(ToolUtil.changeString(0));
        holder.nowPriceTv.setText(ToolUtil.changeString(data.getPreferentialPrice()));
        holder.oldPriceTv.setText("¥" + ToolUtil.changeString(data.getPrice()));
        if (ToolUtil.changeInteger(data.getBuyCount()) == 0) {
            holder.numTv.setVisibility(View.GONE);
            holder.subBtn.setVisibility(View.GONE);
        } else {
            holder.numTv.setVisibility(View.VISIBLE);
            holder.subBtn.setVisibility(View.VISIBLE);
        }
        holder.numTv.setText(ToolUtil.changeString(ToolUtil.changeInteger(data.getBuyCount())));
        holder.oldPriceTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(data, holder.rootView, position);
                }
            }
        });
        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.setBuyCount(ToolUtil.changeInteger(data.getBuyCount()) + 1);
                notifyDataSetChanged();
                EventBus.getDefault().post(data);
                if (btnListener != null) {
                    btnListener.onAddClick(data, v, position );
                }
            }
        });

        holder.subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getBuyCount() >0) {
                    data.setBuyCount(ToolUtil.changeInteger(data.getBuyCount()) - 1);
                    notifyDataSetChanged();
                    EventBus.getDefault().post(data);
                    if (btnListener != null) {
                        btnListener.onSubClick(data, v, position );
                    }
                }
            }
        });
    }

    public void refreshData(YmdGoodsEntity entity) {
        for (YmdGoodsEntity item : datas) {
            if (item.getId() == entity.getId()) {
                item.setBuyCount(ToolUtil.changeInteger(entity.getBuyCount()));
                break;
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public OnUMDItemClickListener getListener() {
        return listener;
    }

    public void setListener(OnUMDItemClickListener listener) {
        this.listener = listener;
    }

    public OnSubORAddListener getBtnListener() {
        return btnListener;
    }

    public void setBtnListener(OnSubORAddListener btnListener) {
        this.btnListener = btnListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View rootView;
        @BindView(R.id.icon_iv)
        ImageView iconIv;
        @BindView(R.id.name_tv)
        TextView nameTv;
        @BindView(R.id.desc_tv)
        TextView descTv;
        @BindView(R.id.sale_num_tv)
        TextView saleNumTv;
        @BindView(R.id.now_price_tv)
        TextView nowPriceTv;
        @BindView(R.id.old_price_tv)
        TextView oldPriceTv;
        @BindView(R.id.sub_btn)
        ImageView subBtn;
        @BindView(R.id.num_tv)
        TextView numTv;
        @BindView(R.id.add_btn)
        ImageView addBtn;

        ViewHolder(View view) {
            super(view);
            rootView = view;
            ButterKnife.bind(this, view);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(YmdGoodsEntity goodsEntity) {
        refreshData(goodsEntity);
    }

    public interface OnSubORAddListener {
        public void onSubClick(YmdGoodsEntity data, View view, int position);
        public void onAddClick(YmdGoodsEntity data, View view, int position);
    }
}
