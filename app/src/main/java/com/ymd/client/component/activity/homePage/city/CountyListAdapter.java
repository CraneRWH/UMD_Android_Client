package com.ymd.client.component.activity.homePage.city;

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
import com.ymd.client.component.widget.recyclerView.MyGridView;
import com.ymd.client.model.bean.city.CityEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者:rongweihe
 * 日期:2018/8/24  时间:22:52
 * 描述:  县级城市列表的Adapter
 * 修改历史:
 */
public class CountyListAdapter extends RecyclerView.Adapter<CountyListAdapter.ViewHolder> {


    private List<CityEntity> datas;

    private OnUMDItemClickListener listener;

    public CountyListAdapter(List<CityEntity> datas) {
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_choose_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        CityEntity data = datas.get(position);
        holder.textItem.setText(data.getCityName());
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

    static class ViewHolder extends RecyclerView.ViewHolder {
        View rootView;
        @BindView(R.id.itemImage)
        ImageView itemImage;
        @BindView(R.id.textItem)
        TextView textItem;
        @BindView(R.id.gridView)
        MyGridView gridView;

        ViewHolder(View view) {
            super(view);
            rootView = view;
            ButterKnife.bind(this, view);
        }
    }
}
