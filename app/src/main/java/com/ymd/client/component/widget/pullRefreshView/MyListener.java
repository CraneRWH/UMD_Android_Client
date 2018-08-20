package com.ymd.client.component.widget.pullRefreshView;

import android.os.Handler;
import android.os.Message;


public class MyListener implements PullToRefreshLayout.OnRefreshListener
{
	@Override
	public void onRefresh(final PullToRefreshLayout pullToRefreshLayout)
	{
		// 下拉刷新操作
		new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				// 千万别忘了告诉控件刷新完毕了哦！
				try {
					pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.sendEmptyMessageDelayed(0, 5000);
	}

	@Override
	public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout)
	{
		// 加载操作
		new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				// 千万别忘了告诉控件加载完毕了哦！
				try {
					pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.sendEmptyMessageDelayed(0, 5000);
	}
}
