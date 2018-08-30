package com.ymd.client.component.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ymd.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的U币Adapter
 */
public class UbFragmentAdapter extends CommonRecyclerAdapter<String> {


    private View.OnClickListener mButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            String bean = getContentList().get(position);
        }
    };

    public UbFragmentAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_fragment_ub, parent,
                false);
        return new ViewHolder(view, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindCouponListHolder((ViewHolder) holder, position);
    }

    private void bindCouponListHolder(ViewHolder holder, int position) {

        String data = getContentList().get(position);

        holder.mProductName.setText(data);
    }

    static class ViewHolder extends RecyclerViewHolder {
        @BindView(R.id.name_tv)
        TextView mProductName;

        public ViewHolder(View itemView, CommonRecyclerAdapter.OnItemClickListener listener) {
            super(itemView, listener);
            ButterKnife.bind(this, itemView);
        }
    }
}