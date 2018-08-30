/*
 *
 *  *  -------------------------------------------------------------------------------------
 *  *  Mi-Me Confidential
 *  *
 *  *  Copyright (C) 2017.  Shanghai Mi-Me Financial Information Service Co., Ltd.
 *  *  All rights reserved.
 *  *
 *  *  No part of this file may be reproduced or transmitted in any form or by any means,
 *  *  electronic, mechanical, photocopying, recording, or otherwise, without prior
 *  *  written permission of Shanghai Mi-Me Financial Information Service Co., Ltd.
 *  *  -------------------------------------------------------------------------------------
 *
 */

package com.ymd.client.component.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * <p> RecyclerViewHolder
 *
 * @author jianglei
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    public CommonRecyclerAdapter.OnItemClickListener mClickListener;

    public CommonRecyclerAdapter.OnItemLongClickListener mLongClickListener;

    public RecyclerViewHolder(View itemView, CommonRecyclerAdapter.OnItemClickListener listener) {
        super(itemView);
        this.mClickListener = listener;
        itemView.setOnClickListener(this);
    }

    public RecyclerViewHolder(View itemView,
            CommonRecyclerAdapter.OnItemClickListener clickListener,
            CommonRecyclerAdapter.OnItemLongClickListener longClickListener) {
        super(itemView);
        mClickListener = clickListener;
        mLongClickListener = longClickListener;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mClickListener != null && getAdapterPosition() >= 0) {
            mClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (mLongClickListener != null && getAdapterPosition() >= 0) {
            mLongClickListener.onItemLongClick(view, getAdapterPosition());
        }
        return true;
    }
}
