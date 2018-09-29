package com.ymd.client.component.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ymd.client.R;
import com.ymd.client.component.activity.mine.evaluation.AddEvaluationActivity;
import com.ymd.client.component.widget.RatingView;
import com.ymd.client.model.bean.user.YmdEvaluation;
import com.ymd.client.utils.ScreenUtil;
import com.ymd.client.utils.ToolUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的评价-adapter
 */
public class MyRateAdapter extends CommonRecyclerAdapter<YmdEvaluation> {


    private View.OnClickListener mButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            YmdEvaluation bean = getContentList().get(position);

            mContext.startActivity(new Intent(mContext, AddEvaluationActivity.class));
        }
    };

    public MyRateAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_my_rate, parent,
                false);
        return new ViewHolder(view, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindCouponListHolder((ViewHolder) holder, position);
    }

    private void bindCouponListHolder(ViewHolder holder, int position) {

        YmdEvaluation data = getContentList().get(position);

        int width = (ScreenUtil.getScreenWidthPix(mContext) - ScreenUtil.dip2px(mContext, 88)) / 3;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);
        holder.itemMyRateImg3.setLayoutParams(params);

        params.rightMargin = ScreenUtil.dip2px(mContext, 9);
        holder.itemMyRateImg1.setLayoutParams(params);
        holder.itemMyRateImg2.setLayoutParams(params);

        //追加评价
        holder.itemMyRateAddEva.setTag(position);
        holder.itemMyRateAddEva.setOnClickListener(mButtonListener);
        //名称
        holder.nameTv.setText(data.getUserName());
        holder.contentTv.setText(data.getContent());
        if (data.getFileList().size() == 0) {
            holder.imageLlt.setVisibility(View.GONE);
        } else {
            try {
                Glide.with(mContext).load(ToolUtil.changeString(data.getUserUrl())).into(holder.iconIv);
                Glide.with(mContext).load(ToolUtil.changeString(data.getFileList().get(0))).into(holder.itemMyRateImg1);
                Glide.with(mContext).load(ToolUtil.changeString(data.getFileList().get(1))).into(holder.itemMyRateImg2);
                Glide.with(mContext).load(ToolUtil.changeString(data.getFileList().get(2))).into(holder.itemMyRateImg3);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
        holder.statusNameTv.setText(data.getTime());
        holder.itemAgentManage.setRating(ToolUtil.changeFloat(data.getScore()));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.icon_iv)
        ImageView iconIv;
        @BindView(R.id.name_tv)
        TextView nameTv;
        @BindView(R.id.status_name_tv)
        TextView statusNameTv;
        @BindView(R.id.item_agent_manage)
        RatingView itemAgentManage;
        @BindView(R.id.content_tv)
        TextView contentTv;
        @BindView(R.id.my_rate_img)
        GridLayout myRateImg;
        @BindView(R.id.item_my_rate_img1)
        ImageView itemMyRateImg1;
        @BindView(R.id.item_my_rate_img2)
        ImageView itemMyRateImg2;
        @BindView(R.id.item_my_rate_img3)
        ImageView itemMyRateImg3;
        @BindView(R.id.image_llt)
        LinearLayout imageLlt;
        @BindView(R.id.replay_tv)
        TextView replayTv;
        @BindView(R.id.replay_llt)
        LinearLayout replayLlt;
        @BindView(R.id.item_my_rate_add_eva)
        LinearLayout itemMyRateAddEva;

        public ViewHolder(View rootView, OnItemClickListener listener) {
            super(rootView);
            ButterKnife.bind(this, rootView);
        }
    }
}