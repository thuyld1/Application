package com.android.mevabe.view;

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
    private boolean isLoading = false;
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

    //set the child view of RefreshLayout,ListView
    public void setChildView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
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

                // Check load more condition
                if (canLoadMore()) {
                    loadData();
                }
                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    private boolean canLoadMore() {
        return isBottom() && !isLoading && isPullingUp();
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

    private void loadData() {
        if (mOnLoadMoreListener != null) {
            setLoading(true);
        }
    }

    public void setLoading(boolean loading) {
        if (recyclerView == null) return;
        isLoading = loading;
        if (loading) {
            // Cancel reresh
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
}