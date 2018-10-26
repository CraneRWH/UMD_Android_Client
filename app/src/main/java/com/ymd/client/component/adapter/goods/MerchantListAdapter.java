package com.ymd.client.component.adapter.goods;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ymd.client.R;
import com.ymd.client.common.base.OnUMDItemClickListener;
import com.ymd.client.model.bean.homePage.MerchantInfoEntity;
import com.ymd.client.utils.ToolUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者:rongweihe
 * 日期:2018/8/24  时间:22:52
 * 描述:  订单列表的Adapter
 * 修改历史:
 */
public class MerchantListAdapter extends RecyclerView.Adapter<MerchantListAdapter.ViewHolder> {

    private List<MerchantInfoEntity> datas;
    private Context mContext;

    private OnUMDItemClickListener listener;

    public MerchantListAdapter(List<MerchantInfoEntity> datas, Context mContext) {
        this.datas = datas;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_food_list, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        MerchantInfoEntity data = datas.get(position);
        if (data.getFile() != null && !data.getFile().isEmpty()) {
            Glide.with(mContext).load(ToolUtil.changeString(data.getFile().get(0).getUrl())).into(holder.iconIv);
        }
        if (!TextUtils.isEmpty(data.getPhotoUrl())) {
            Glide.with(mContext).load(data.getPhotoUrl()).into(holder.iconIv);
        }
        holder.nameTv.setText(ToolUtil.changeString(data.getName()));
        if (data.getDistance() == null) {
            data.setDistance(ToolUtil.Distance(ToolUtil.changeDouble(data.getLatitude()), ToolUtil.changeDouble(data.getLongitude())));
        }
        if (ToolUtil.changeDouble(data.getDistance()) > 1000) {
            double distance = ToolUtil.changeDouble(data.getDistance())/1000;
            holder.distanceTv.setText(ToolUtil.double2Point(distance) + "km");
        } else {
            holder.distanceTv.setText(ToolUtil.double2Point(ToolUtil.changeDouble(data.getDistance())) + "m");
        }
        holder.scoreBarView.setRating(ToolUtil.changeFloat(data.getScore()));
        holder.workTimeTv.setText(ToolUtil.changeString(data.getStartBusinessTime()) + "-" + ToolUtil.changeString(data.getEndBusinessTime()));
    //    holder.disStrTv.setText(ToolUtil.changeString(data.get("dis_str")));
        holder.disNumTv.setText(ToolUtil.changeString(data.getDiscount()) +"折");
        holder.priceTv.setText("¥" + ToolUtil.changeString(data.getConsumption())/*+ data.get("price")*/);
        holder.unitTv.setText("/人"/*+ data.get("unit")*/);
     //   List<String> disStrs = (List<String>) data.get("diss");
        //开始添加数据
    /*    for (int x = 0; x < disStrs.size(); x++) {
            //寻找行布局，第一个参数为行布局ID，第二个参数为这个行布局需要放到那个容器上
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_fragment_food_list_l, holder.giftLayout, false);

            TextView name_tv = (TextView) view.findViewById(R.id.item_tv);
            name_tv.setText(disStrs.get(x));
            holder.giftLayout.addView(view);
        }*/
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
        @BindView(R.id.icon_iv)
        ImageView iconIv;
        @BindView(R.id.name_tv)
        TextView nameTv;
        @BindView(R.id.distance_tv)
        TextView distanceTv;
        @BindView(R.id.scoreBarView)
        RatingBar scoreBarView;
        @BindView(R.id.work_time_tv)
        TextView workTimeTv;
        @BindView(R.id.dis_str_tv)
        TextView disStrTv;
        @BindView(R.id.dis_num_tv)
        TextView disNumTv;
        @BindView(R.id.price_tv)
        TextView priceTv;
        @BindView(R.id.unit_tv)
        TextView unitTv;
        @BindView(R.id.giftLayout)
        LinearLayout giftLayout;

        ViewHolder(View view) {
            super(view);
            rootView = view;
            ButterKnife.bind(this, view);
        }
    }
}
