package com.ymd.client.component.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ymd.client.R;
import com.ymd.client.component.adapter.food.FoodTypeListAdapter;
import com.ymd.client.model.bean.user.UForm;
import com.ymd.client.utils.ToolUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的U币Adapter
 */
public class UbFragmentAdapter extends RecyclerView.Adapter<UbFragmentAdapter.ViewHolder>  {


    private List<UForm> datas;
    private Context mContext;

    public UbFragmentAdapter(List<UForm> list) {
        this.datas = list;
    }

    @Override
    public UbFragmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_ub, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(UbFragmentAdapter.ViewHolder holder, int position) {
        UForm data = datas.get(position);
        holder.merchantNameTv.setText(ToolUtil.changeString(data.getMerchantName()));
        holder.orderDateTv.setText(ToolUtil.changeString(data.getTime()));
        Glide.with(mContext).load(data.getIcon()).into(holder.iconIv);
     //   holder.statusNameTv.setText(ToolUtil.changeString(data.get));
        holder.orderPriceTv.setText(ToolUtil.changeString(data.getPayAmt()));
        holder.useUTv.setText(ToolUtil.changeString(data.getAvailable() + "U"));
        holder.uTv.setText(ToolUtil.changeString(data.getNumber()) + "U");
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.icon_iv)
        ImageView iconIv;
        @BindView(R.id.merchant_name_tv)
        TextView merchantNameTv;
        @BindView(R.id.order_date_tv)
        TextView orderDateTv;
        @BindView(R.id.status_name_tv)
        TextView statusNameTv;
        @BindView(R.id.goods_lt)
        LinearLayout goodsLt;
        @BindView(R.id.order_price_tv)
        TextView orderPriceTv;
        @BindView(R.id.use_u_tv)
        TextView useUTv;
        @BindView(R.id.u_tv)
        TextView uTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}