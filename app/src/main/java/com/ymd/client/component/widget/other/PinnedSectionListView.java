package com.ymd.client.component.widget.other;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityEvent;
import android.widget.AbsListView;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SectionIndexer;

import com.ymd.client.BuildConfig;


/**
 * ListView的，它能够在它的顶部到指定的位置，而其余仍滚动。
 */
public class PinnedSectionListView extends ListView {

    //-- inner classes

    /** 用于与PinnedSectionListView适配器被用来实施列表适配器。 */
    public static interface PinnedSectionListAdapter extends ListAdapter {
        /** 如果次定类型的已被钉扎此方法应该返回“真”。 */
        boolean isItemViewTypePinned(int viewType);
    }

    /** 包装类固定截面图和列表中的位置。 */
    static class PinnedSection {
        public View view;
        public int position;
        public long id;
    }

    //-- class fields

    // 用于处理触摸事件领域
    private final Rect mTouchRect = new Rect();
    private final PointF mTouchPoint = new PointF();
    private int mTouchSlop;
    private View mTouchTarget;
    private MotionEvent mDownEvent;

    // 用于下固定部分绘制阴影场
    private GradientDrawable mShadowDrawable;
    private int mSectionsDistanceY;
    private int mShadowHeight;

    /** 委派监听器，可以为null。 */
    OnScrollListener mDelegateOnScrollListener;

    /** 阴影被回收，可以为null。 */
    PinnedSection mRecycleSection;

    /** 影实例与固定视图，可以为null。 */
    PinnedSection mPinnedSection;

    /** 固定的Y翻译。我们用它来粘钉扎以下一节。 */
    int mTranslateY;

    /** 滚动监听器做魔术 */
    private final OnScrollListener mOnScrollListener = new OnScrollListener() {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (mDelegateOnScrollListener != null) { // 代表
                mDelegateOnScrollListener.onScrollStateChanged(view, scrollState);
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            if (mDelegateOnScrollListener != null) { // 代表
                mDelegateOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }

            // 预计得到适配器或快速失败
            ListAdapter adapter = getAdapter();
            if (adapter == null || visibleItemCount == 0) {
                return; // nothing to do
            }

            final boolean isFirstVisibleItemSection =
                    isItemViewTypePinned(adapter, adapter.getItemViewType(firstVisibleItem));

            if (isFirstVisibleItemSection) {
                View sectionView = getChildAt(0);
                if (sectionView.getTop() == getPaddingTop()) { // 鉴于枝顶端，不需要固定的影子
                    destroyPinnedShadow();
                } else { // 部分不沾顶端，确保我们有一个固定的影子
                    ensureShadowForPosition(firstVisibleItem, firstVisibleItem, visibleItemCount);
                }

            } else { // 部分不在所述第一可见位置
                int sectionPosition = findCurrentSectionPosition(firstVisibleItem);
                if (sectionPosition > -1) { // 我们有部分位置
                    ensureShadowForPosition(sectionPosition, firstVisibleItem, visibleItemCount);
                } else { // 有第一个可见项目无节，破坏阴影
                    destroyPinnedShadow();
                }
            }
        };

    };

    /** 默认更改观察者。 */
    private final DataSetObserver mDataSetObserver = new DataSetObserver() {
        @Override public void onChanged() {
            recreatePinnedShadow();
        };
        @Override public void onInvalidated() {
            recreatePinnedShadow();
        }
    };

    //-- 建设者

    public PinnedSectionListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PinnedSectionListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        setOnScrollListener(mOnScrollListener);
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        initShadow(true);
    }

    //-- public API methods

    public void setShadowVisible(boolean visible) {
        initShadow(visible);
        if (mPinnedSection != null) {
            View v = mPinnedSection.view;
            invalidate(v.getLeft(), v.getTop(), v.getRight(), v.getBottom() + mShadowHeight);
        }
    }

    //-- 固定部分绘制方法

    public void initShadow(boolean visible) {
        if (visible) {
            if (mShadowDrawable == null) {
                mShadowDrawable = new GradientDrawable(Orientation.TOP_BOTTOM,
                        new int[] { Color.parseColor("#ffa0a0a0"), Color.parseColor("#50a0a0a0"), Color.parseColor("#00a0a0a0")});
                mShadowHeight = (int) (20 * getResources().getDisplayMetrics().density);
            }
        } else {
            if (mShadowDrawable != null) {
                mShadowDrawable = null;
                mShadowHeight = 0;
            }
        }
    }

    /** 与视图在给定位置的固定视图创建阴影包装 */
    void createPinnedShadow(int position) {

        // 尝试回收阴影
        PinnedSection pinnedShadow = mRecycleSection;
        mRecycleSection = null;

        // 创造新的阴影，如果需要的话
        if (pinnedShadow == null) {
            pinnedShadow = new PinnedSection();
        }
        // 使用这种回收来看，请求新观点
        View pinnedView = getAdapter().getView(position, pinnedShadow.view, PinnedSectionListView.this);

        // 读布局参数
        LayoutParams layoutParams = (LayoutParams) pinnedView.getLayoutParams();
        if (layoutParams == null) {
            //layoutParams = (LayoutParams) generateDefaultLayoutParams();
            layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            pinnedView.setLayoutParams(layoutParams);
        }

        int heightMode = MeasureSpec.getMode(layoutParams.height);
        int heightSize = MeasureSpec.getSize(layoutParams.height);

        if (heightMode == MeasureSpec.UNSPECIFIED) {
            heightMode = MeasureSpec.EXACTLY;
        }

        int maxHeight = getHeight() - getListPaddingTop() - getListPaddingBottom();
        if (heightSize > maxHeight) {
            heightSize = maxHeight;
        }

        // measure & layout
        int ws = MeasureSpec.makeMeasureSpec(getWidth() - getListPaddingLeft() - getListPaddingRight(), MeasureSpec.EXACTLY);
        int hs = MeasureSpec.makeMeasureSpec(heightSize, heightMode);
        pinnedView.measure(ws, hs);
        pinnedView.layout(0, 0, pinnedView.getMeasuredWidth(), pinnedView.getMeasuredHeight());
        mTranslateY = 0;

        // 初始化固定阴影
        pinnedShadow.view = pinnedView;
        pinnedShadow.position = position;
        pinnedShadow.id = getAdapter().getItemId(position);

        // 店内固定的影子
        mPinnedSection = pinnedShadow;
    }

    /** 毁灭阴影包装器目前固定视图 */
    void destroyPinnedShadow() {
        if (mPinnedSection != null) {
            // 保持阴影后来被回收
            mRecycleSection = mPinnedSection;
            mPinnedSection = null;
        }
    }

    /** 确保我们有给定位置的实际固定的影子。 */
    void ensureShadowForPosition(int sectionPosition, int firstVisibleItem, int visibleItemCount) {
        if (visibleItemCount < 2) { // 没有必要在所有创造的影子，我们有一个可见项
            destroyPinnedShadow();
            return;
        }

        if (mPinnedSection != null
                && mPinnedSection.position != sectionPosition) { // 无效的影子，如果需要的话
            destroyPinnedShadow();
        }

        if (mPinnedSection == null) { // 创建阴影，如果空
            createPinnedShadow(sectionPosition);
        }

        // 根据下一节位置对齐的影子，如果需要的话
        int nextPosition = sectionPosition + 1;
        if (nextPosition < getCount()) {
            int nextSectionPosition = findFirstVisibleSectionPosition(nextPosition,
                    visibleItemCount - (nextPosition - firstVisibleItem));
            if (nextSectionPosition > -1) {
                View nextSectionView = getChildAt(nextSectionPosition - firstVisibleItem);
                final int bottom = mPinnedSection.view.getBottom() + getPaddingTop();
                mSectionsDistanceY = nextSectionView.getTop() - bottom;
                if (mSectionsDistanceY < 0) {
                    // 下一节重叠钉扎的影子，将其向上
                    mTranslateY = mSectionsDistanceY;
                } else {
                    // 下一节不重叠与寄托，坚持到顶部
                    mTranslateY = 0;
                }
            } else {
                // 没有其他的部分是可见的，坚持到顶部
                mTranslateY = 0;
                mSectionsDistanceY = Integer.MAX_VALUE;
            }
        }

    }

    int findFirstVisibleSectionPosition(int firstVisibleItem, int visibleItemCount) {
        ListAdapter adapter = getAdapter();

        if (firstVisibleItem + visibleItemCount >= adapter.getCount()) {
            return -1; // 数据发生了变化，没有候选人
        }

        for (int childIndex = 0; childIndex < visibleItemCount; childIndex++) {
            int position = firstVisibleItem + childIndex;
            int viewType = adapter.getItemViewType(position);
            if (isItemViewTypePinned(adapter, viewType)) {
                return position;
            }
        }
        return -1;
    }

    int findCurrentSectionPosition(int fromPosition) {
        ListAdapter adapter = getAdapter();

        if (fromPosition >= adapter.getCount()) {
            return -1; // 数据发生了变化，没有候选人
        }

        if (adapter instanceof SectionIndexer) {
            // 通过询问section indexer尽量快捷的方式
            SectionIndexer indexer = (SectionIndexer) adapter;
            int sectionPosition = indexer.getSectionForPosition(fromPosition);
            int itemPosition = indexer.getPositionForSection(sectionPosition);
            int typeView = adapter.getItemViewType(itemPosition);
            if (isItemViewTypePinned(adapter, typeView)) {
                return itemPosition;
            } // 否则，没有运气
        }

        // 通过上面看下一节项目尝试这样缓慢
        for (int position=fromPosition; position>=0; position--) {
            int viewType = adapter.getItemViewType(position);
            if (isItemViewTypePinned(adapter, viewType)) {
                return position;
            }
        }
        return -1; // 没有发现候选
    }

    void recreatePinnedShadow() {
        destroyPinnedShadow();
        ListAdapter adapter = getAdapter();
        if (adapter != null && adapter.getCount() > 0) {
            int firstVisiblePosition = getFirstVisiblePosition();
            int sectionPosition = findCurrentSectionPosition(firstVisiblePosition);
            if (sectionPosition == -1) return; // 没意见引脚，退出
            ensureShadowForPosition(sectionPosition,
                    firstVisiblePosition, getLastVisiblePosition() - firstVisiblePosition);
        }
    }

    @Override
    public void setOnScrollListener(OnScrollListener listener) {
        if (listener == mOnScrollListener) {
            super.setOnScrollListener(listener);
        } else {
            mDelegateOnScrollListener = listener;
        }
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
        post(new Runnable() {
            @Override public void run() { // 恢复配置更改后固定视图
                recreatePinnedShadow();
            }
        });
    }

    @Override
    public void setAdapter(ListAdapter adapter) {

        // 断言在调试模式适配器
        if (BuildConfig.DEBUG && adapter != null) {
            if (!(adapter instanceof PinnedSectionListAdapter)) {
                throw new IllegalArgumentException("Does your adapter implement PinnedSectionListAdapter?");
            }
			/*if (adapter.getViewTypeCount() < 3)
				throw new IllegalArgumentException("Does your adapter handle at least two types" +
						" of views in getViewTypeCount() method: items and sections?");*/
        }

        // 在旧的适配器注销观察员和新的注册
        ListAdapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterDataSetObserver(mDataSetObserver);
        }
        if (adapter != null) {
            adapter.registerDataSetObserver(mDataSetObserver);
        }

        // 摧毁钉扎的身影，新的适配器不一样的旧
        if (oldAdapter != adapter) {
            destroyPinnedShadow();
        }

        super.setAdapter(adapter);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (mPinnedSection != null) {
            int parentWidth = r - l - getPaddingLeft() - getPaddingRight();
            int shadowWidth = mPinnedSection.view.getWidth();
            if (parentWidth != shadowWidth) {
                recreatePinnedShadow();
            }
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        if (mPinnedSection != null) {

            // 准备变量
            int pLeft = getListPaddingLeft();
            int pTop = getListPaddingTop();
            View view = mPinnedSection.view;

            // 画儿
            canvas.save();

            int clipHeight = view.getHeight() +
                    (mShadowDrawable == null ? 0 : Math.min(mShadowHeight, mSectionsDistanceY));
            canvas.clipRect(pLeft, pTop, pLeft + view.getWidth(), pTop + clipHeight);

            canvas.translate(pLeft, pTop + mTranslateY);
            drawChild(canvas, mPinnedSection.view, getDrawingTime());

            if (mShadowDrawable != null && mSectionsDistanceY > 0) {
                mShadowDrawable.setBounds(mPinnedSection.view.getLeft(),
                        mPinnedSection.view.getBottom(),
                        mPinnedSection.view.getRight(),
                        mPinnedSection.view.getBottom() + mShadowHeight);
                mShadowDrawable.draw(canvas);
            }

            canvas.restore();
        }
    }

    //-- 触摸处理方法

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        final float x = ev.getX();
        final float y = ev.getY();
        final int action = ev.getAction();

        if (action == MotionEvent.ACTION_DOWN
                && mTouchTarget == null
                && mPinnedSection != null
                && isPinnedViewTouched(mPinnedSection.view, x, y)) { // 创建触摸目标

            // 用户感动固定视图
            mTouchTarget = mPinnedSection.view;
            mTouchPoint.x = x;
            mTouchPoint.y = y;

            // 抄下来事件最终在以后使用
            mDownEvent = MotionEvent.obtain(ev);
        }

        if (mTouchTarget != null) {
            if (isPinnedViewTouched(mTouchTarget, x, y)) { // 前锋事件寄托视图
                mTouchTarget.dispatchTouchEvent(ev);
            }

            if (action == MotionEvent.ACTION_UP) { // 在固定视图下进行的onClick
                super.dispatchTouchEvent(ev);
                performPinnedItemClick();
                clearTouchTarget();

            } else if (action == MotionEvent.ACTION_CANCEL) { // cancel
                clearTouchTarget();

            } else if (action == MotionEvent.ACTION_MOVE) {
                if (Math.abs(y - mTouchPoint.y) > mTouchSlop) {

                    // 取消触摸靶序列
                    MotionEvent event = MotionEvent.obtain(ev);
                    event.setAction(MotionEvent.ACTION_CANCEL);
                    mTouchTarget.dispatchTouchEvent(event);
                    event.recycle();

                    // 为进一步的处理提供正确的顺序超类
                    super.dispatchTouchEvent(mDownEvent);
                    super.dispatchTouchEvent(ev);
                    clearTouchTarget();

                }
            }

            return true;
        }

        // 调用super如果这不是我们的固定看法
        return super.dispatchTouchEvent(ev);
    }

    private boolean isPinnedViewTouched(View view, float x, float y) {
        view.getHitRect(mTouchRect);

        // 通过点击顶部或底部填充，列表上进行点击边框项目。
        // 我们这里不加顶边距保持行为一致。
        mTouchRect.top += mTranslateY;

        mTouchRect.bottom += mTranslateY + getPaddingTop();
        mTouchRect.left += getPaddingLeft();
        mTouchRect.right -= getPaddingRight();
        return mTouchRect.contains((int)x, (int)y);
    }

    private void clearTouchTarget() {
        mTouchTarget = null;
        if (mDownEvent != null) {
            mDownEvent.recycle();
            mDownEvent = null;
        }
    }

    private boolean performPinnedItemClick() {
        if (mPinnedSection == null) {
            return false;
        }

        OnItemClickListener listener = getOnItemClickListener();
        if (listener != null && getAdapter().isEnabled(mPinnedSection.position)) {
            View view =  mPinnedSection.view;
            playSoundEffect(SoundEffectConstants.CLICK);
            if (view != null) {
                view.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_CLICKED);
            }
            listener.onItemClick(this, view, mPinnedSection.position, mPinnedSection.id);
            return true;
        }
        return false;
    }

    public static boolean isItemViewTypePinned(ListAdapter adapter, int viewType) {
        if (adapter instanceof HeaderViewListAdapter) {
            adapter = ((HeaderViewListAdapter)adapter).getWrappedAdapter();
        }
        return ((PinnedSectionListAdapter) adapter).isItemViewTypePinned(viewType);
    }

}