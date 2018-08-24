package com.ymd.client.component.widget.pullRefreshView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ListView;

import com.ymd.client.R;


public class PullableListView extends ListView implements Pullable
{
	private boolean canPullUp = true;		//能否上拉加载
	private boolean canPullDown = true;		//能否下拉刷新
	public PullableListView(Context context)
	{
		super(context);
	}

	public PullableListView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initView(context,attrs);
	}

	public PullableListView(Context context, AttributeSet attrs, int defStyle)
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
		try {
			if (getCount() == 0)
			{
				// 没有item的时候也可以下拉刷新
				return canPullDown;
			} else if (getFirstVisiblePosition() == 0
					&& getChildAt(0).getTop() >= 0)
			{
				// 滑到ListView的顶部了
				return canPullDown;
			} else
				return false;
		} catch(NullPointerException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean canPullUp()
	{
		if (getCount() == 0)
		{
			// 没有item的时候也可以上拉加载
			return canPullUp;
		} else if (getLastVisiblePosition() == (getCount() - 1))
		{
			// 滑到底部了
			if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) != null
					&& getChildAt(
					getLastVisiblePosition()
							- getFirstVisiblePosition()).getBottom() <= getMeasuredHeight())
				return canPullUp;
		}
		return false;
	}
}
