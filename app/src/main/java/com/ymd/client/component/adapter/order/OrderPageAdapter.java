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

import com.ymd.client.R;
import com.ymd.client.common.base.OnUMDItemClickListener;
import com.ymd.client.model.bean.order.OrderResultForm;
import com.ymd.client.model.bean.order.YmdOrderGoods;
import com.ymd.client.utils.ToolUtil;

import java.util.List;

import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

/**
 * 作者:rongweihe
 * 日期:2018/8/24  时间:22:52
 * 描述:  订单列表的Adapter
 * 修改历史:
 */
public class OrderPageAdapter extends RecyclerView.Adapter<OrderPageAdapter.ViewHolder> {

    private List<OrderResultForm> datas;
    private Context mContext;

    private OnUMDItemClickListener listener;
    private OnBtnClickListener btnClickListener;

    public OrderPageAdapter(List<OrderResultForm> datas, Context mContext) {
        this.datas = datas;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_order_page, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        OrderResultForm data = datas.get(position);
        if (ToolUtil.changeInteger(data.getOrderStatus()) > 2) {
            holder.statusNameTv.setTextColor(R.color.text_gray_dark);
            holder.btn1.setVisibility(View.VISIBLE);
        } else {
            holder.statusNameTv.setTextColor(R.color.bg_header);
            holder.btn1.setVisibility(View.GONE);
        }
        List<YmdOrderGoods> products = data.getYmdOrderGoodsList();

        holder.productListLt.removeAllViews();
        for (YmdOrderGoods item : products) {
            View v = LayoutInflater.from(getContext()).inflate(R.layout.item_fragment_order_page_product, null);
            TextView nameView = v.findViewById(R.id.product_name_tv);
            TextView numView = v.findViewById(R.id.product_num_tv);
            nameView.setText(ToolUtil.changeString(item.getGoodsName()));
            numView.setText("x" + item.getGoodsNum());
            holder.productListLt.addView(v);
        }

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

        switch (data.getOrderStatus()) {
            case 0:
                holder.btn3.setVisibility(View.VISIBLE);
                holder.btn3.setText("立即支付");
                break;
            case 1:
            case 2:
                holder.btn3.setVisibility(View.GONE);
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
                break;
            case 4:
                holder.btn3.setVisibility(View.VISIBLE);
                holder.btn3.setText("评价");
                break;
            case 5:
            case 6:
            case 7:
                holder.btn3.setVisibility(View.GONE);

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

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.iconIv = (ImageView) rootView.findViewById(R.id.icon_iv);
            this.nameTv = (TextView) rootView.findViewById(R.id.name_tv);
            this.statusNameTv = (TextView) rootView.findViewById(R.id.status_name_tv);
            this.productListLt = (LinearLayout) rootView.findViewById(R.id.product_list_lt);
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
