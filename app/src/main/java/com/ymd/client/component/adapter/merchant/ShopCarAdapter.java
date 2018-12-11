package com.ymd.client.component.adapter.merchant;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.common.base.OnUMDItemClickListener;
import com.ymd.client.component.event.ShowShopCarEvent;
import com.ymd.client.model.bean.homePage.YmdGoodsEntity;
import com.ymd.client.utils.ToolUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;

/**
 * 作者:rongweihe
 * 日期:2018/8/24  时间:22:52
 * 描述:  购物车列表的Adapter
 * 修改历史:
 */
public class ShopCarAdapter extends RecyclerView.Adapter<ShopCarAdapter.ViewHolder> {

    private List<YmdGoodsEntity> datas;
    private Context mContext;

    private OnUMDItemClickListener listener;

    public ShopCarAdapter(List<YmdGoodsEntity> datas) {
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop_car, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        YmdGoodsEntity data = datas.get(position);
        if (data == null) {
            return;
        }
        holder.nameTv.setText(ToolUtil.changeString(data.getGoodsName()));
        holder.priceTv.setText(ToolUtil.changeString(data.getPrice()));
        holder.numTv.setText(ToolUtil.changeString(data.getBuyCount()));

        holder.addIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.setBuyCount(ToolUtil.changeInteger(data.getBuyCount()) + 1);
                notifyDataSetChanged();
                EventBus.getDefault().post(data);
                EventBus.getDefault().post(datas);
            }
        });

        holder.subIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.setBuyCount(ToolUtil.changeInteger(data.getBuyCount()) - 1);
                if (data.getBuyCount() == 0) {
                    datas.remove(position);
                }
                notifyDataSetChanged();
                EventBus.getDefault().post(data);
                EventBus.getDefault().post(datas);
                EventBus.getDefault().post(new ShowShopCarEvent(true));
            }
        });
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

    static class ViewHolder extends RecyclerView.ViewHolder {

        public View rootView;
        public TextView nameTv;
        public TextView priceTv;
        public ImageView subIv;
        public TextView numTv;
        public ImageView addIv;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.nameTv = (TextView) rootView.findViewById(R.id.name_tv);
            this.priceTv = (TextView) rootView.findViewById(R.id.price_tv);
            this.subIv = (ImageView) rootView.findViewById(R.id.sub_iv);
            this.numTv = (TextView) rootView.findViewById(R.id.buy_num_tv);
            this.addIv = (ImageView) rootView.findViewById(R.id.add_iv);
        }

    }
}
