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
import com.ymd.client.utils.ToolUtil;

import java.util.List;
import java.util.Map;

import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

/**
 * 作者:rongweihe
 * 日期:2018/8/24  时间:22:52
 * 描述:  订单列表的Adapter
 * 修改历史:
 */
public class OrderPageAdapter extends RecyclerView.Adapter<OrderPageAdapter.ViewHolder> {

    private List<Map<String,Object>> datas;
    private Context mContext;

    private OnUMDItemClickListener listener;

    public OrderPageAdapter(List<Map<String, Object>> datas, Context mContext) {
        this.datas = datas;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_order_page, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Map<String,Object> data = datas.get(position);
        if (ToolUtil.changeInteger(data.get("status")) >2) {
            holder.statusNameTv.setTextColor(mContext.getColor(R.color.text_gray_dark));
            holder.btn1.setVisibility(View.VISIBLE);
        } else {
            holder.statusNameTv.setTextColor(mContext.getColor(R.color.bg_header));
            holder.btn1.setVisibility(View.GONE);
        }
        List<Map<String,Object>> products = (List<Map<String, Object>>) data.get("product_list");

        holder.productListLt.removeAllViews();
        for (Map<String,Object> item : products) {
            View v = LayoutInflater.from(getContext()).inflate(R.layout.item_fragment_order_page_product, null);
            TextView nameView = v.findViewById(R.id.product_name_tv);
            TextView numView = v.findViewById(R.id.product_num_tv);
            nameView.setText(ToolUtil.changeString(item.get("name")));
            numView.setText("x" + item.get("num"));
            holder.productListLt.addView(v);
        }

        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClick(data,holder.rootView, position);
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
}
