package com.ymd.client.common.base.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.component.adapter.MySimpleAdapter;
import com.ymd.client.component.widget.pullRefreshView.PullToRefreshLayout;
import com.ymd.client.component.widget.pullRefreshView.PullableListView;
import com.ymd.client.utils.FastDoubleClickUtil;
import com.ymd.client.utils.ToolUtil;
import com.ymd.client.web.WebUtil;


public abstract class PageFragment extends Fragment {

	private View mainView;

	private PullToRefreshLayout bigLayout;
	private PullableListView listView;
	private LinearLayout emptyLayout;
	private TextView emptyView;

	private ArrayList<Map<String,Object>> queryDatas = new ArrayList<Map<String,Object>>();	//每种状态的订单数据
//	private int lp = 0;	//每个状态显示在最上面的订单所处的位置

	protected int chooseStatus = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		if (mainView == null)
			mainView = inflater.inflate(getFragmentInflate(), container, false);
		bindWidgetId();
		bindWidgetListener();
		return mainView;
	}

	private void bindWidgetId() {
		bigLayout=(PullToRefreshLayout) mainView.findViewById(R.id.bigLayout);
		listView = (PullableListView) mainView.findViewById(R.id.listView);
		emptyLayout = (LinearLayout) mainView.findViewById(R.id.emptyLayout);
		emptyView = (TextView) mainView.findViewById(R.id.emptyView);
	}

	protected int getFragmentInflate() {
		return R.layout.page_fragment;
	}

	private void bindWidgetListener() {
		bigLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
			//下拉刷新和上滑加载功能
			@Override
			public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
				refreshLayout = pullToRefreshLayout;
				refreshHandler.sendEmptyMessage(0);
			}

			@Override
			public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
		/*		loadLayout = pullToRefreshLayout;
				loadHandler.sendEmptyMessage(0);*/
			}
		});

	}

	@Override
	public void setArguments(Bundle bundle) {
		if (bundle != null)
			chooseStatus = bundle.getInt("chooseStatus");
		try {
			resetData();
		} catch (Exception e) {

		}
	}

	@Override
	public void onStart() {
		super.onStart();
		/*if (chooseStatus == (getActivity()).chooseStatus)
			queryDataStart();*/
	}


	/**
	 * 第一次加载数据
	 */
	public void queryDataStart() {
		clearData();
		chooseItem(chooseStatus);
	}

	private PullToRefreshLayout refreshLayout = null;
	//private PullToRefreshLayout loadLayout = null;

	private Handler refreshHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			clearData();
			successHandler.sendEmptyMessage(0);
		//	queryData();
		}
	};

	/*private Handler loadHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (listView.getAdapter() != null)
				getAdapter().notifyDataSetChanged();
			queryData();
		}
	};*/

	private void chooseItem(int position) {
		chooseStatus = position;
		if (!queryDatas.isEmpty()) {
			try {
				resetData();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
		//	queryData();
		}
	}

	/**
	 * 加载某种状态下的订单
	 */
	/*protected void queryData() {
		WebUtil.getInstance().requestGET(getActivity(), getMethod(), getParams(), getLoginFlag(),true,
				new WebUtil.WebCallBack<String>() {

					@Override
					public void onWebSuccess(String result) {
						Message msg = Message.obtain(successHandler);
						msg.obj = result;
						successHandler.sendMessage(msg);
					}

					@Override
					public void onWebFailed(String errorMsg) {

					}
				});
	}*/

	private Handler successHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			try {
				String resultStr = ToolUtil.changeString(msg.obj);
				List<Map<String,Object>> data = getDataList(); /*= (ArrayList<Map<String,Object>>)ToolUtil.analyseJsonArray(resultStr, getDataKey())*/;
				if (!data.isEmpty()) {
					queryDatas.addAll(data);
				}
				if (refreshLayout != null) {
					refreshLayout.setFinishListener(new PullToRefreshLayout.FinishListener() {

						@Override
						public void finish(boolean flag) {
							if (flag) {
								refreshLayout = null;
							}
						}
					});
					new Handler()
					{
						@Override
						public void handleMessage(Message msg)
						{
							if (refreshLayout != null) {
								try {
									refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							//		refreshLayout = null;
						}
					}.sendEmptyMessageDelayed(0, 1000);
				}
				/*if (loadLayout != null) {
					new Handler()
					{
						@Override
						public void handleMessage(Message msg)
						{
							// 千万别忘了告诉控件加载完毕了哦！
							if (isEnd)
								loadLayout.loadmoreFinish(PullToRefreshLayout.LOAD_END);
							else
								loadLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
							loadLayout = null;
						}
					}.sendEmptyMessageDelayed(0, 1000);
				}*/
				resetData();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	/**
	 * 显示某种状态下的列表信息
	 */
	private void resetData() throws Exception {
		if (queryDatas.isEmpty()) {
			listView.setVisibility(View.GONE);
			emptyLayout.setVisibility(View.VISIBLE);
			emptyView.setText(getEmptyAlarts()[chooseStatus]);
		}
		else {
			listView.setVisibility(View.VISIBLE);
			emptyLayout.setVisibility(View.GONE);
//			listView.setSelection(lp);
//			lp = queryDatas.size();
			MySimpleAdapter adapter = new MySimpleAdapter(getActivity(), queryDatas, getItemLayouts()[chooseStatus],
					getFrom(), getTo());
			listView.setAdapter(adapter);
			setFormat();
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
					if (isTouchEvent()) {
						onItemClickListener(view,queryDatas.get(position),position);
					}
				}
			});
			listView.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long arg3) {
					if (isTouchEvent())
						onItemLongClickListener(view,queryDatas.get(position),position);
					return true;
				}
			});
			nextData();
		}
	}

	protected void nextData(){

	}

	/*
	 * 为item中数据设置显示格式
	 */
	protected void setFormat() {

	}

	/*
	 * 判断是否需要登录才能查询接口，默认为true即需要登录才能查询接口
	 */
	protected boolean getLoginFlag() {
		return true;
	}

	protected MySimpleAdapter getAdapter() {
		return (MySimpleAdapter) listView.getAdapter();
	}

	//列表中单击item触发事件
	protected void onItemClickListener(View view,Map<String,Object> value,int position) {

	}

	//列表中长按item触发事件
	protected void onItemLongClickListener(View view,Map<String,Object> value,int position) {

	}

	protected ArrayList<Map<String,Object>> getData() {
		return this.queryDatas;
	}

	//清空数据
	protected void clearData() {
		getActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {
				queryDatas.clear();
				try {
					getAdapter().notifyDataSetChanged();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			try {
				getAdapter().notifyDataSetChanged();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	/**
	 * 这个方法很重要，主要线程安全的问题，若是下拉刷新的提示信息没有消失之前，就进行了跳转界面，就会出现线程错误问题
	 * @return
	 */
	protected boolean isTouchEvent() {
		if (!FastDoubleClickUtil.isFastDoubleClick() && refreshLayout == null) {
			return true;
		}
		return false;
	}

	//	protected abstract int getContentView();	//获取Fragment的布局
//	protected abstract String getHeadTitle();	//获取标题
	protected abstract String getMethod();		//获取数据的接口名称
	protected abstract Map<String,String> getParams();		//获取数据的参数；
	protected abstract int[] getItemLayouts();
	protected abstract String[] getEmptyAlarts();
	protected abstract String getDataKey();		//获取数据的总key
	protected abstract String[] getFrom();		//列表数据中单个数据的key值列表
	protected abstract int[] getTo();			//getFrom()的key值相对应item布局中的控件id，这里是一一对应的关系，getFrom()与getTo()是按排列顺序进行映射，故排列和数量必须相同

	protected abstract List<Map<String,Object>> getDataList();
}
