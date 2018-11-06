package com.ymd.client.component.adapter.mine;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ymd.client.R;
import com.ymd.client.component.activity.mine.ub.UbInFragment;
import com.ymd.client.component.adapter.CommonRecyclerAdapter;
import com.ymd.client.model.bean.user.GoodsForm;
import com.ymd.client.model.bean.user.UForm;
import com.ymd.client.utils.ToolUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

/**
 * 我的U币Adapter
 */
public class UbFragmentAdapter2 extends CommonRecyclerAdapter<UForm> {

    public UbFragmentAdapter2(List<UForm> list, FragmentActivity context) {
        super(context);
        this.datas = list;
    }

    @Override
    public UbFragmentAdapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_ub, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onUmdBindViewHolder((ViewHolder)holder, position);
    }

    public void onUmdBindViewHolder(UbFragmentAdapter2.ViewHolder holder, int position) {
        UForm data = datas.get(position);
        if (!TextUtils.isEmpty(data.getIcon())) {
            Glide.with(mContext).load(data.getIcon()).into(holder.iconIv);
        }
        holder.merchantNameTv.setText(ToolUtil.changeString(data.getMerchantName()));
        holder.orderDateTv.setText(ToolUtil.changeString(data.getTime()));
     //   holder.statusNameTv.setText(ToolUtil.changeString(data.get));
        holder.orderPriceTv.setText(ToolUtil.changeString(data.getPayAmt()));
        holder.useUTv.setText("新增U币："+ToolUtil.changeString(data.getNumber() + "U"));
        holder.uTv.setText(ToolUtil.changeString(data.getAvailable()) + "U");
        holder.goodsLt.removeAllViews();
        int i = 0;
        for (GoodsForm item : data.getGoods()) {
            View v = LayoutInflater.from(getContext()).inflate(R.layout.item_fragment_order_page_product, null);
            TextView nameView = v.findViewById(R.id.product_name_tv);
            TextView numView = v.findViewById(R.id.product_num_tv);
            nameView.setText(ToolUtil.changeString(item.getGoodsName()));
            numView.setText("x" + item.getGoodsNumber());
            if (i == 3) {
                nameView.setText("...");
                numView.setVisibility(View.GONE);
                holder.goodsLt.addView(v);
                break;
            }
            i ++;
            holder.goodsLt.addView(v);

        }
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