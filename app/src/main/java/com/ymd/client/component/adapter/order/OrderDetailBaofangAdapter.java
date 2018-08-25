package com.ymd.client.component.adapter.order;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
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
public class OrderDetailBaofangAdapter extends RecyclerView.Adapter<OrderDetailBaofangAdapter.ViewHolder> {

    private List<Map<String, Object>> datas;
    private Context mContext;

    private OnUMDItemClickListener listener;

    public OrderDetailBaofangAdapter(List<Map<String, Object>> datas, Context mContext) {
        this.datas = datas;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_order_eat_location, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Map<String, Object> data = datas.get(position);
        holder.name_tv.setText(ToolUtil.changeString(data.get("name")));
        holder.desc_tv.setText("可容纳"+ data.get("desc") + "人");

        if (ToolUtil.changeBoolean(data.get("isChoose"))) {
            holder.mainView.setBackgroundResource(R.mipmap.baojian_green_icon);
            holder.name_tv.setTextColor(mContext.getColor(R.color.white));
            holder.desc_tv.setTextColor(mContext.getColor(R.color.white));
        } else {
            holder.mainView.setBackgroundResource(R.mipmap.baojian_white_icon);
            holder.name_tv.setTextColor(mContext.getColor(R.color.common_text_color));
            holder.desc_tv.setTextColor(mContext.getColor(R.color.common_text_color));
        }

        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    selectChange(position);
                    listener.onClick(data, holder.rootView, position);
                }
            }
        });
    }

    private void selectChange(int position) {
        for (Map<String,Object> item : datas) {
            item.put("isChoose", false);
        }
        datas.get(position).put("isChoose", true);
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

    static class ViewHolder extends RecyclerView.ViewHolder {

        public View rootView;
        public TextView name_tv;
        public TextView desc_tv;
        public LinearLayout mainView;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            mainView = (LinearLayout) rootView.findViewById(R.id.mainView);
            this.name_tv = (TextView) rootView.findViewById(R.id.name_tv);
            this.desc_tv = (TextView) rootView.findViewById(R.id.desc_tv);
        }

    }

}
