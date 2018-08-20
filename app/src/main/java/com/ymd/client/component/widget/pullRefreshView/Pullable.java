package com.ymd.client.component.widget.pullRefreshView;

public interface Pullable {


	/**
	 * 判断是否可以下拉，如果不需要下来功能可以直接return false
	 * @return 如果可以下拉返回true,否则返回false
	 */
	boolean canPullDown();

	/**
	 * 判断是否可以上拉，如果不需要上拉功能可以直接return false
	 * @return	如果可以上拉返回true,否则返回false
	 */
	boolean canPullUp();
}
