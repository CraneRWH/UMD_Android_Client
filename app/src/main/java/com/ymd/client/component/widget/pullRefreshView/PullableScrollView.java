package com.ymd.client.component.widget.pullRefreshView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.ymd.client.R;

public class PullableScrollView extends ScrollView implements Pullable
{

	private boolean canPullUp = true;		//能否上拉加载
	private boolean canPullDown = true;		//能否下拉刷新
	public PullableScrollView(Context context)
	{
		super(context);
	}

	public PullableScrollView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initView(context,attrs);
	}

	public PullableScrollView(Context context, AttributeSet attrs, int defStyle)
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
		if (getScrollY() == 0)
			return canPullDown;
		else
			return false;
	}

	@Override
	public boolean canPullUp()
	{
		if (getScrollY() >= (getChildAt(0).getHeight() - getMeasuredHeight()))
			return canPullUp;
		else
			return false;
	}

}
