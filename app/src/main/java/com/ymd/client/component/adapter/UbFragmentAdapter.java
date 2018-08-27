package com.ymd.client.component.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ymd.client.R;
import com.ymd.client.common.base.OnUMDItemClickListener;

import java.util.List;
import java.util.Map;

/**
 * 我的U币Adapter
 */
public class UbFragmentAdapter extends RecyclerView.Adapter<UbFragmentAdapter.ViewHolder> {

    private List<Map<String, Object>> datas;
    private Context mContext;

    private OnUMDItemClickListener listener;

    public UbFragmentAdapter(List<Map<String, Object>> datas, Context mContext) {
        this.datas = datas;
        this.mContext = mContext;
    }

    @Override
    public UbFragmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_ub, parent, false);
        UbFragmentAdapter.ViewHolder holder = new UbFragmentAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(UbFragmentAdapter.ViewHolder holder, int position) {
        Map<String, Object> data = datas.get(position);
    }

    private void selectChange(int position) {
        for (Map<String, Object> item : datas) {
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

        public ViewHolder(View rootView) {
            super(rootView);
        }

    }
}