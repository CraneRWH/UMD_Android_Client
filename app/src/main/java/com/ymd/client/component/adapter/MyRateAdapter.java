package com.ymd.client.component.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ymd.client.R;
import com.ymd.client.common.base.OnUMDItemClickListener;
import com.ymd.client.utils.ScreenUtil;

import java.util.List;
import java.util.Map;

public class MyRateAdapter extends RecyclerView.Adapter<MyRateAdapter.ViewHolder> {

    private List<Map<String, Object>> datas;
    private Context mContext;

    private OnUMDItemClickListener listener;

    public MyRateAdapter(List<Map<String, Object>> datas, Context mContext) {
        this.datas = datas;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_rate, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Map<String, Object> data = datas.get(position);

        int width =(ScreenUtil.getScreenWidthPix(mContext) - ScreenUtil.dip2px(mContext, 88))/3;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);
        holder.mImg3.setLayoutParams(params);

        params.rightMargin = ScreenUtil.dip2px(mContext,9);
        holder.mImg1.setLayoutParams(params);
        holder.mImg2.setLayoutParams(params);

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

        GridLayout gridLayout;

        ImageView mImg1;
        ImageView mImg2;
        ImageView mImg3;

        public ViewHolder(View rootView) {
            super(rootView);

            gridLayout = rootView.findViewById(R.id.my_rate_img);

            mImg1 = rootView.findViewById(R.id.item_my_rate_img1);
            mImg2 = rootView.findViewById(R.id.item_my_rate_img2);
            mImg3 = rootView.findViewById(R.id.item_my_rate_img3);

        }
    }
}