package com.ymd.client.component.adapter.goods.test;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.idlestar.ratingstar.RatingStarView;
import com.ymd.client.R;
import com.ymd.client.common.base.OnUMDItemClickListener;
import com.ymd.client.model.bean.homePage.YmdEvaluationEntity;
import com.ymd.client.utils.ToolUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者:rongweihe
 * 日期:2018/8/24  时间:22:52
 * 描述:  卖家评价列表的Adapter
 * 修改历史:
 */
public class EvaluateSellerAdapter extends RecyclerView.Adapter<EvaluateSellerAdapter.ViewHolder> {


    private List<YmdEvaluationEntity> datas;
    private Context mContext;

    private OnUMDItemClickListener listener;

    public EvaluateSellerAdapter(List<YmdEvaluationEntity> datas, Context mContext) {
        this.datas = datas;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_evaluate_seller, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        YmdEvaluationEntity data = datas.get(position);
        if (ToolUtil.changeString(data.getUserUrl()).length() > 0) {
            Glide.with(mContext).load(ToolUtil.changeString(data.getUserUrl())).into(holder.iconIv);
        }
        holder.shopNameTv.setText(ToolUtil.changeString(data.getMerchantName()));
        holder.descTv.setText(ToolUtil.changeString(data.getContent()));
        holder.dateTv.setText(ToolUtil.changeString(data.getTime()));
        holder.scoreBarView.setRating(ToolUtil.changeFloat(data.getScore()));
        holder.sellerReplayTv.setText(ToolUtil.changeString(data.getReply()));
        if (ToolUtil.changeString(data.getReply()).length() == 0) {
            holder.replayLlt.setVisibility(View.GONE);
        } else {
            holder.replayLlt.setVisibility(View.VISIBLE);
        }
        if (data.getFileList().size() == 0) {
            holder.imageLlt.setVisibility(View.GONE);
        } else {
            holder.imageLlt.setVisibility(View.VISIBLE);
            try {
                Glide.with(mContext).load(ToolUtil.changeString(data.getUserUrl())).into(holder.iconIv);
                Glide.with(mContext).load(ToolUtil.changeString(data.getFileList().get(0))).into(holder.itemMyRateImg1);
                Glide.with(mContext).load(ToolUtil.changeString(data.getFileList().get(1))).into(holder.itemMyRateImg2);
                Glide.with(mContext).load(ToolUtil.changeString(data.getFileList().get(2))).into(holder.itemMyRateImg3);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
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
        @BindView(R.id.shop_name_tv)
        TextView shopNameTv;
        @BindView(R.id.date_tv)
        TextView dateTv;
        @BindView(R.id.scoreBarView)
        RatingStarView scoreBarView;
        @BindView(R.id.desc_tv)
        TextView descTv;
        @BindView(R.id.seller_replay_tv)
        TextView sellerReplayTv;
        @BindView(R.id.item_my_rate_img1)
        ImageView itemMyRateImg1;
        @BindView(R.id.item_my_rate_img2)
        ImageView itemMyRateImg2;
        @BindView(R.id.item_my_rate_img3)
        ImageView itemMyRateImg3;
        @BindView(R.id.image_llt)
        LinearLayout imageLlt;
        @BindView(R.id.replay_llt)
        LinearLayout replayLlt;
        ViewHolder(View view) {
            super(view);
            rootView = view;
            ButterKnife.bind(this, view);
        }
    }
}
