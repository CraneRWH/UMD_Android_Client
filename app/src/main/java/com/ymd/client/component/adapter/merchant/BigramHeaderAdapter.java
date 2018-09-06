package com.ymd.client.component.adapter.merchant;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eowise.recyclerview.stickyheaders.StickyHeadersAdapter;
import com.ymd.client.R;
import com.ymd.client.component.adapter.merchant.bean.GoodsListBean;

import java.util.List;

public class BigramHeaderAdapter implements StickyHeadersAdapter<BigramHeaderAdapter.ViewHolder> {

    private Context mContext;
    private List<GoodsListBean.DataEntity.GoodscatrgoryEntity.GoodsitemEntity> dataList;
    private List<GoodsListBean.DataEntity.GoodscatrgoryEntity> goodscatrgoryEntities;
    public BigramHeaderAdapter(Context context, List<GoodsListBean.DataEntity.GoodscatrgoryEntity.GoodsitemEntity> items
            , List<GoodsListBean.DataEntity.GoodscatrgoryEntity> goodscatrgoryEntities) {
        this.mContext = context;
        this.dataList = items;
        this.goodscatrgoryEntities = goodscatrgoryEntities;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_goods_list, parent, false);
        return new BigramHeaderAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BigramHeaderAdapter.ViewHolder headerViewHolder, int position) {
        headerViewHolder.tvGoodsItemTitle.setText(goodscatrgoryEntities.get(
                dataList.get(position).getId()).getName());
    }

    @Override
    public long getHeaderId(int position) {
        return dataList.get(position).getId();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvGoodsItemTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            tvGoodsItemTitle = (TextView) itemView.findViewById(R.id.tvGoodsItemTitle);
        }
    }
}
