package com.ymd.client.model.helper;

import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.RelativeLayout;

public class MyBehavior extends CoordinatorLayout.Behavior <View>{

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof RelativeLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        int dependTop = dependency.getTop();

        child.setY(dependTop - 50);
        child.setX(dependency.getLeft());

        return true;
    }

}