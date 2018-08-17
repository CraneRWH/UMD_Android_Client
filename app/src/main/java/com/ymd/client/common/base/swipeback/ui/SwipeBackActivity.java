
package com.ymd.client.common.base.swipeback.ui;

import android.os.Bundle;
import android.view.View;

import com.trello.rxlifecycle2.components.support.RxFragmentActivity;
import com.ymd.client.common.base.swipeback.SwipeBackLayout;
import com.ymd.client.common.base.swipeback.Utils;


public class SwipeBackActivity extends RxFragmentActivity implements SwipeBackActivityBase {
    private SwipeBackActivityHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    public void setEdgeTrackingEnabled(int edgeFlags) {
        mHelper.getSwipeBackLayout().setEdgeTrackingEnabled(edgeFlags);
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }
}
