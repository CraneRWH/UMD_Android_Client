package com.ymd.client.common.helper;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

/**
 * @ClassName
 * @PackageName com.ymd.client.common.helper
 * @CreateTime 2019/1/8 16:13
 * @Author rongweihe
 * @Description
 */
public class UmdClassicsHeader extends ClassicsHeader {
    private StartRefreshListener startRefreshListener;

    public UmdClassicsHeader(Context context) {
        super(context);
    }

    public UmdClassicsHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UmdClassicsHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public StartRefreshListener getStartRefreshListener() {
        return startRefreshListener;
    }

    public void setStartRefreshListener(StartRefreshListener startRefreshListener) {
        this.startRefreshListener = startRefreshListener;
    }

    @Override
    public void onPullingDown(float percent, int offset, int headerHeight, int extendHeight) {
        super.onPullingDown(percent, offset, headerHeight, extendHeight);
        if (percent > 0) {
            if (startRefreshListener != null) {
                startRefreshListener.onStart();
            }
        } else {
            if (startRefreshListener != null) {
                startRefreshListener.onEnd();
            }
        }
    }

    @Override
    public void onReleasing(float percent, int offset, int headHeight, int maxDragHeight) {
        super.onReleasing(percent, offset, headHeight, maxDragHeight);
        if (percent > 0) {
            if (startRefreshListener != null) {
                startRefreshListener.onStart();
            }
        } else {
            if (startRefreshListener != null) {
                startRefreshListener.onEnd();
            }
        }
    }
    @Override
    public void onRefreshReleased(RefreshLayout layout, int headerHeight, int maxDragHeight) {

    }

    public interface StartRefreshListener{
        void onStart();
        void onEnd();
    }
}
