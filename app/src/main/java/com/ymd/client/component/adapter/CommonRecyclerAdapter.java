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

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> 自定义通用RecyclerAdapter
 *
 * @author jianglei
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class CommonRecyclerAdapter<T> extends RecyclerView.Adapter {

    public Context mContext;

    public LayoutInflater mLayoutInflater;

    public OnItemClickListener mItemClickListener;

    public OnItemLongClickListener mItemLongClickListener;

    private List<T> mItems = new ArrayList<>();

    public CommonRecyclerAdapter(Context context) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    /**
     * 设置Item点击监听
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public void setItemLongClickListener(OnItemLongClickListener itemLongClickListener) {
        mItemLongClickListener = itemLongClickListener;
    }

    public void addItems(List<? extends T> list) {
        if (list == null) {
            return;
        }
        mItems.clear();
        mItems.addAll(list);
        notifyDataSetChanged();
    }

    public void appendItems(List<T> list) {
        int lastPosition = mItems.size();
        if (list == null) {
            return;
        }
        mItems.addAll(list);
        notifyItemRangeChanged(lastPosition, list.size());
        notifyDataSetChanged();
    }

    public void appendItemsNotify(List<T> list) {
        if (list == null) {
            return;
        }
        mItems.addAll(list);
        notifyDataSetChanged();
    }

    public void addItem(int index, T data) {
        if (data == null || index < 0 || index > mItems.size()) {
            return;
        }
        mItems.add(index, data);
    }

    public void remove(int index) {
        if (index > mItems.size() || index < 0) {
            return;
        }
        mItems.remove(index);
    }

    public void setContentList(List<T> list) {
        if (list == null) {
            return;
        }
        mItems = list;
    }

    public void clear() {
        mItems.clear();
    }

    public List<T> getContentList() {
        return mItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public LayoutInflater getLayoutInflater() {
        return mLayoutInflater;
    }

    public Context getContext() {
        return mContext;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }
}
