package com.ymd.client.component.widget.pullRefreshView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.ymd.client.R;


public class PullableLayout extends LinearLayout implements Pullable {

	private boolean canPullUp = true;		//能否上拉加载
	private boolean canPullDown = true;		//能否下拉刷新
	public PullableLayout(Context context)
	{
		super(context);
	}

	public PullableLayout(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initView(context,attrs);
	}

	public PullableLayout(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		initView(context,attrs);
	}
	
	private void initView(Context context,AttributeSet attrs) {
		TypedArray type = context.obtainStyledAttributes(attrs, R.styleable.custom);
		canPullDown = type.getBoolean(R.styleable.custom_canPullDown, true);
		canPullUp = type.getBoolean(R.styleable.custom_canPullUp, true);
	}

	@Override
	public boolean canPullDown()
	{
		return canPullDown;
	}

	@Override
	public boolean canPullUp()
	{
		return canPullUp;
	}

}
