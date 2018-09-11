package com.ymd.client.component.adapter.food;

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

import com.ymd.client.R;
import com.ymd.client.common.base.OnUMDItemClickListener;
import com.ymd.client.model.bean.homePage.YmdGoodsEntity;
import com.ymd.client.utils.ToolUtil;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者:rongweihe
 * 日期:2018/8/24  时间:22:52
 * 描述:  卖家美事列表的Adapter
 * 修改历史:
 */
public class FoodSellerListAdapter extends RecyclerView.Adapter<FoodSellerListAdapter.ViewHolder> {

    private List<YmdGoodsEntity> datas;
    private Context mContext;

    private OnUMDItemClickListener listener;

    public FoodSellerListAdapter(List<YmdGoodsEntity> datas, Context mContext) {
        this.datas = datas;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seller_food_list, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        YmdGoodsEntity data = datas.get(position);
        holder.nameTv.setText(ToolUtil.changeString(data.getGoodsName()));
        holder.descTv.setText(ToolUtil.changeString(data.getDescribe()));
        holder.saleNumTv.setText(ToolUtil.changeString(0));
        holder.nowPriceTv.setText(ToolUtil.changeString(data.getPrice()));
    //    holder.oldPriceTv.setText("¥" + ToolUtil.changeString(data.get("old_pride")));
        holder.numTv.setText(ToolUtil.changeString(0));
    //    holder.oldPriceTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(data, holder.rootView, position);
                }
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
}
