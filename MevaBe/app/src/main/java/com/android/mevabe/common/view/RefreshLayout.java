package com.android.mevabe.common.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

public class RefreshLayout extends SwipeRefreshLayout {
    private final int mTouchSlop;
    private RecyclerView recyclerView;
    private OnLoadMoreListener mOnLoadMoreListener;

    private float firstTouchY;
    private float lastTouchY;
    private boolean isLoadingMore;
    private float mStartX, mLastX;

    /**
     * Constructor
     *
     * @param context Context
     */
    public RefreshLayout(Context context) {
        this(context, null);
    }

    /**
     * Constructor
     *
     * @param context Context
     * @param attrs   AttributeSet
     */
    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                firstTouchY = event.getRawY();
                mStartX = event.getRawX();
                break;
            case MotionEvent.ACTION_UP:
                lastTouchY = event.getRawY();
                mLastX = event.getRawX();

                // Check load more condition to start load data
                if (mOnLoadMoreListener != null && canLoadMore()) {
                    setLoadingMore(true);
                }
                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    /**
     * Check can load more or not
     *
     * @return boolean
     */
    private boolean canLoadMore() {
        return !isLoadingMore && isBottom() && isPullingUp();
    }

    private boolean isBottom() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (layoutManager == null || adapter == null) return false;

        return layoutManager.findLastCompletelyVisibleItemPosition() == adapter.getItemCount() - 1;
    }

    private boolean isPullingUp() {
        float deltaX = mStartX - mLastX;
        float deltaY = firstTouchY - lastTouchY;
        if (Math.abs(deltaY) > Math.abs(deltaX) && deltaY >= mTouchSlop)
            return true;
        return false;
    }

    /**
     * Start load more or stop load
     *
     * @param loadingMore
     */
    public void setLoadingMore(boolean loadingMore) {
        if (recyclerView == null) return;
        isLoadingMore = loadingMore;
        if (loadingMore) {
            // Cancel refresh action
            if (isRefreshing()) {
                setRefreshing(false);
            }

            // Start load more data
            mOnLoadMoreListener.onLoadMore();
        } else {
            firstTouchY = 0;
            lastTouchY = 0;
            mStartX = 0;
            mLastX = 0;
        }
    }

    /**
     * Set load more listener
     *
     * @param loadListener OnLoadMoreListener
     */
    public void setOnLoadMoreListener(OnLoadMoreListener loadListener) {
        mOnLoadMoreListener = loadListener;
    }

    /**
     * Interface handle load more processing
     */
    public interface OnLoadMoreListener {
        public void onLoadMore();
    }

    /**
     * set the child view of RefreshLayout,ListView
     *
     * @param recyclerView RecyclerView
     */
    public void setChildView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }
}