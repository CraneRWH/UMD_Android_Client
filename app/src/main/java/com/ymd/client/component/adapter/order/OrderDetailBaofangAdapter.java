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
import com.ymd.client.model.bean.order.YmdMerchantRooms;
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

    private List<YmdMerchantRooms> datas;
    private Context mContext;

    private OnUMDItemClickListener listener;

    public OrderDetailBaofangAdapter(List<YmdMerchantRooms> datas, Context mContext) {
        this.datas = datas;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_order_eat_location, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        YmdMerchantRooms data = datas.get(position);
        holder.name_tv.setText(ToolUtil.changeString(data.getRoomsName()));
        holder.desc_tv.setText("可容纳"+ data.getDiningNumber() + "人");

        if (data.isChoose()) {
            holder.mainView.setBackgroundResource(R.mipmap.baojian_green_icon);
            holder.name_tv.setTextColor(R.color.white);
            holder.desc_tv.setTextColor(R.color.white);
        } else {
            holder.mainView.setBackgroundResource(R.mipmap.baojian_white_icon);
            holder.name_tv.setTextColor(R.color.common_text_color);
            holder.desc_tv.setTextColor(R.color.common_text_color);
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
        for (YmdMerchantRooms item : datas) {
            item.setChoose(false);
        }
        datas.get(position).setChoose(true);
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
