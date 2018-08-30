package com.ymd.client.component.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.component.activity.mine.evaluation.AddEvaluationActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommonCollectionAdapter extends CommonRecyclerAdapter<String> {

    private View.OnClickListener mButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            String bean = getContentList().get(position);

            mContext.startActivity(new Intent(mContext, AddEvaluationActivity.class));
        }
    };

    public CommonCollectionAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_common_collection, parent,
                false);
        return new ViewHolder(view, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindCouponListHolder((ViewHolder) holder, position);
    }

    private void bindCouponListHolder(ViewHolder holder, int position) {

        String data = getContentList().get(position);

        holder.giftLayout.removeAllViewsInLayout();
      //  for (int x = 0; x < disStrs.size(); x++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_fragment_food_list_l, holder.giftLayout, false);
            TextView name_tv = (TextView) view.findViewById(R.id.item_tv);
            name_tv.setText(data);
            holder.giftLayout.addView(view);
       // }

        holder.name_tv.setText(data);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name_tv)
        TextView name_tv;
        @BindView(R.id.item_common_coll_layout)
        LinearLayout giftLayout;

        public ViewHolder(View rootView, CommonRecyclerAdapter.OnItemClickListener listener) {
            super(rootView);
            ButterKnife.bind(this, rootView);
        }
    }
}