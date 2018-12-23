package com.ymd.client.component.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.model.bean.MemberCardBean;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OpenMemberAdapter extends CommonRecyclerAdapter<MemberCardBean> {

    MemberCardBean checked;

    public OpenMemberAdapter(Context mContext, MemberCardBean checked) {
        super(mContext);
        this.checked = checked;
    }

    public void setChecked(MemberCardBean checked) {
        this.checked = checked;
    }

    public void refresh(MemberCardBean checked) {
        this.checked = checked;
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_open_member, parent,
                false);
        return new ViewHolder(view, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindCouponListHolder((ViewHolder) holder, position);
    }

    private void bindCouponListHolder(ViewHolder holder, int position) {
        MemberCardBean memberCardBean = getContentList().get(position);
        if ("10A".equals(memberCardBean.getCardType())) {
            holder.mTxtName.setText("月卡");
        } else if ("10B".equals(memberCardBean.getCardType())) {
            holder.mTxtName.setText("季卡");
        } else if ("10C".equals(memberCardBean.getCardType())) {
            holder.mTxtName.setText("年卡");
        } else {
            holder.mTxtName.setText("未知");
        }
        //
        holder.mTxtOldPrice.setText("￥"+memberCardBean.getOriginalPrice());
        //折扣价
        holder.mTxtPrice.setText("￥"+memberCardBean.getDiscountPrice());

        if (checked == null) {
            if (position == 0) {
                holder.mViewBg.setBackgroundResource(R.drawable.open_member_checked_bg);
            } else {
                holder.mViewBg.setBackgroundResource(R.drawable.open_member_bg);
            }
        } else {
            if (checked.getId() == memberCardBean.getId()) {
                holder.mViewBg.setBackgroundResource(R.drawable.open_member_checked_bg);
            } else {
                holder.mViewBg.setBackgroundResource(R.drawable.open_member_bg);
            }
        }
    }

    static class ViewHolder extends RecyclerViewHolder {

        @BindView(R.id.item_open_member_name)
        TextView mTxtName;
        @BindView(R.id.item_open_member_old_price)
        TextView mTxtOldPrice;
        @BindView(R.id.item_open_member_price)
        TextView mTxtPrice;

        @BindView(R.id.open_member_bg)
        View mViewBg;

        public ViewHolder(View rootView, OnItemClickListener listener) {
            super(rootView, listener);
            ButterKnife.bind(this, rootView);
        }
    }
}