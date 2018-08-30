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

import com.ymd.client.R;
import com.ymd.client.component.activity.mine.evaluation.AddEvaluationActivity;
import com.ymd.client.utils.ScreenUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyRateAdapter extends CommonRecyclerAdapter<String> {

    private View.OnClickListener mButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            String bean = getContentList().get(position);

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

        String data = getContentList().get(position);

        int width = (ScreenUtil.getScreenWidthPix(mContext) - ScreenUtil.dip2px(mContext, 88)) / 3;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);
        holder.mImg3.setLayoutParams(params);

        params.rightMargin = ScreenUtil.dip2px(mContext, 9);
        holder.mImg1.setLayoutParams(params);
        holder.mImg2.setLayoutParams(params);

        //追加评价
        holder.mAddEva.setTag(position);
        holder.mAddEva.setOnClickListener(mButtonListener);
        //名称
        holder.mProjectName.setText(data);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.my_rate_img)
        GridLayout gridLayout;

        @BindView(R.id.item_my_rate_img1)
        ImageView mImg1;
        @BindView(R.id.item_my_rate_img2)
        ImageView mImg2;
        @BindView(R.id.item_my_rate_img3)
        ImageView mImg3;

        @BindView(R.id.item_my_rate_add_eva)
        View mAddEva;//追加评价

        @BindView(R.id.name_tv)
        TextView mProjectName;

        public ViewHolder(View rootView, CommonRecyclerAdapter.OnItemClickListener listener) {
            super(rootView);
            ButterKnife.bind(this, rootView);
        }
    }
}