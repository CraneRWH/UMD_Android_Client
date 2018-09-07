package com.ymd.client.component.adapter.food;

import android.annotation.SuppressLint;
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
import com.ymd.client.utils.ToolUtil;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者:rongweihe
 * 日期:2018/8/24  时间:22:52
 * 描述:  卖家美事种类列表的Adapter
 * 修改历史:
 */
public class FoodTypeListAdapter extends RecyclerView.Adapter<FoodTypeListAdapter.ViewHolder> {

    private List<Map<String, Object>> datas;
    private Context mContext;

    private OnUMDItemClickListener onItemClickListener;

    public FoodTypeListAdapter(List<Map<String, Object>> datas, Context mContext) {
        this.datas = datas;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seller_food_type, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Map<String, Object> data = datas.get(position);
        holder.itemTv.setText(ToolUtil.changeString(data.get("name")));
        if (ToolUtil.changeBoolean(data.get("isChoose"))) {
            holder.itemTv.setTextColor(R.color.common_text_color);
            holder.rootView.setBackgroundResource(R.color.text_gray);
        } else {
            holder.itemTv.setTextColor(R.color.text_gray2);
            holder.rootView.setBackgroundResource(R.color.white);
        }
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(data, holder.rootView, position);
                    changeChooseItem(position);
                }
            }
        });
    }

    public void changeChooseItem(int position) {
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

    public OnUMDItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnUMDItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View rootView;

        @BindView(R.id.item_tv)
        TextView itemTv;

        ViewHolder(View view) {
            super(view);
            rootView = view;
            ButterKnife.bind(this, view);
        }
    }
}
