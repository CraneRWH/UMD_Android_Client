package com.ymd.client.component.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.utils.ToolUtil;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的U币Adapter
 */
public class UbFragmentAdapter extends CommonRecyclerAdapter<Map<String, Object>> {


    private View.OnClickListener mButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            Map<String, Object> bean = getContentList().get(position);
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

        Map<String, Object> data = getContentList().get(position);

        holder.mProductName.setText(ToolUtil.changeString(data.get("name")));
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