package com.ymd.client.component.adapter.order;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ymd.client.R;
import com.ymd.client.common.base.OnUMDItemClickListener;
import com.ymd.client.component.adapter.CommonRecyclerAdapter;
import com.ymd.client.model.bean.order.OrderResultForm;
import com.ymd.client.model.bean.order.YmdOrderGoods;
import com.ymd.client.model.constant.UmdDataConstants;
import com.ymd.client.utils.ToolUtil;

import java.util.List;

import butterknife.BindView;

/**
 * 作者:rongweihe
 * 日期:2018/8/24  时间:22:52
 * 描述:  订单列表的Adapter
 * 修改历史:
 */
public class OrderPageAdapter2 extends CommonRecyclerAdapter<OrderResultForm> {


    private OnUMDItemClickListener listener;
    private OnBtnClickListener btnClickListener;

    public OrderPageAdapter2(List<OrderResultForm> datas, Context mContext) {
        super(mContext);
        this.mContext = mContext;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_order_page, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onUmdBindViewHolder((ViewHolder) holder, position);
    }

    @SuppressLint("ResourceAsColor")
    public void onUmdBindViewHolder(ViewHolder holder, int position) {

        OrderResultForm data = datas.get(position);
        if (ToolUtil.changeInteger(data.getOrderStatus()) == 4) {
            holder.statusNameTv.setTextColor(R.color.text_gray_dark);
            holder.btn1.setVisibility(View.GONE);
        } else {
            holder.statusNameTv.setTextColor(R.color.bg_header);
            holder.btn1.setVisibility(View.GONE);
        }

        if (ToolUtil.changeString(data.getmIcon()).length() > 0) {
            Glide.with(mContext).load(ToolUtil.changeString(data.getmIcon())).into(holder.iconIv);
        }
        holder.nameTv.setText(ToolUtil.changeString(data.getmName()));
        List<YmdOrderGoods> products = data.getYmdOrderGoodsList();
        try {
            holder.payMoneyTv.setText("¥" + ToolUtil.changeDouble(data.getPayAmt()));
        } catch (Exception e) {
            e.printStackTrace();
            holder.payMoneyTv.setText("¥0.0");
        }
        try {
            holder.uMoneyTv.setText(ToolUtil.changeString(data.getuObtain()));
        } catch (Exception e) {
            e.printStackTrace();
            holder.uMoneyTv.setText("0");
        }
        holder.productListLt.removeAllViews();
        int allNum = 0;
        for (YmdOrderGoods item : products) {
            View v = LayoutInflater.from(getContext()).inflate(R.layout.item_fragment_order_page_product, null);
            TextView nameView = v.findViewById(R.id.product_name_tv);
            TextView numView = v.findViewById(R.id.product_num_tv);
            nameView.setText(ToolUtil.changeString(item.getGoodsName()));
            numView.setText("x" + item.getGoodsNum());
            allNum = allNum + item.getGoodsNum();
            holder.productListLt.addView(v);
        }

        holder.allProductNumTv.setText(ToolUtil.changeString(allNum));
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClick(data, holder.rootView, position);
                }
            }
        });

        holder.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnClickListener != null) {
                    btnClickListener.onClick(data, position, holder.btn2.getId());
                }
            }
        });

        try {
            holder.statusNameTv.setText(UmdDataConstants.orderStatusList[ToolUtil.changeInteger(data.getOrderStatus())]);
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        switch (data.getOrderStatus()) {
            case 0:
                holder.btn3.setVisibility(View.VISIBLE);
                holder.btn3.setText("立即支付");
                holder.statusNameTv.setVisibility(View.VISIBLE);
                holder.uLlt.setVisibility(View.GONE);
                holder.btn2.setText("取消订单");
                break;
            case 1:
                holder.btn3.setVisibility(View.GONE);
                holder.statusNameTv.setVisibility(View.VISIBLE);
                holder.uLlt.setVisibility(View.VISIBLE);
                holder.btn2.setText("再来一单");
                //    holder.statusNameTv.setText("待接单");
                break;
            case 2:
                holder.btn3.setVisibility(View.GONE);
                holder.statusNameTv.setVisibility(View.VISIBLE);
                holder.uLlt.setVisibility(View.VISIBLE);
                holder.btn2.setText("再来一单");
                //    holder.statusNameTv.setText("待确认");
                break;
            case 3:
                holder.btn3.setVisibility(View.VISIBLE);
                if (data.getPayStatus() == 5) {
                    holder.btn3.setText("退款中");
                } else if (data.getPayStatus() == 6) {
                    holder.btn3.setText("退款成功");
                } else if (data.getPayStatus() == 7) {
                    holder.btn3.setText("退款失败");
                }
                holder.statusNameTv.setVisibility(View.GONE);
                holder.uLlt.setVisibility(View.GONE);
                holder.btn2.setText("再来一单");
                //    holder.statusNameTv.setText("已拒单");
                break;
            case 4:
                holder.btn3.setVisibility(View.VISIBLE);
                holder.btn3.setText("评价");

                holder.statusNameTv.setVisibility(View.VISIBLE);
                holder.uLlt.setVisibility(View.VISIBLE);
                holder.btn2.setText("再来一单");
                //    holder.statusNameTv.setText("待评价");
                break;
            case 5:
            case 6:
            case 7:
                holder.btn3.setVisibility(View.GONE);
                holder.statusNameTv.setVisibility(View.VISIBLE);
                holder.uLlt.setVisibility(View.VISIBLE);
                holder.btn2.setText("再来一单");
                //    holder.statusNameTv.setText("");
        }
        holder.btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnClickListener != null) {
                    btnClickListener.onClick(data, position, holder.btn3.getId());
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

    public OnBtnClickListener getBtnClickListener() {
        return btnClickListener;
    }

    public void refreshItem(OrderResultForm data , int position) {
        datas.set(position, data);
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        datas.remove(position);
        notifyDataSetChanged();
    }

    public void setBtnClickListener(OnBtnClickListener btnClickListener) {
        this.btnClickListener = btnClickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public ImageView iconIv;
        public TextView nameTv;
        public TextView statusNameTv;
        public LinearLayout productListLt;
        public TextView uMoneyTv;
        public TextView allProductNumTv;
        public TextView payMoneyTv;
        public Button btn1;
        public Button btn2;
        public Button btn3;
        LinearLayout uLlt;
        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.iconIv = (ImageView) rootView.findViewById(R.id.icon_iv);
            this.nameTv = (TextView) rootView.findViewById(R.id.name_tv);
            this.statusNameTv = (TextView) rootView.findViewById(R.id.status_name_tv);
            this.productListLt = (LinearLayout) rootView.findViewById(R.id.product_list_lt);
            this.uLlt = (LinearLayout) rootView.findViewById(R.id.u_llt);
            this.uMoneyTv = (TextView) rootView.findViewById(R.id.u_money_tv);
            this.allProductNumTv = (TextView) rootView.findViewById(R.id.all_product_num_tv);
            this.payMoneyTv = (TextView) rootView.findViewById(R.id.pay_money_tv);
            this.btn1 = (Button) rootView.findViewById(R.id.btn1);
            this.btn2 = (Button) rootView.findViewById(R.id.btn2);
            this.btn3 = (Button) rootView.findViewById(R.id.btn3);
        }

    }

    public interface OnBtnClickListener {
        public void onClick(Object data, int position, int id);
    }
}
