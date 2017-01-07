package com.android.mevabe.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

import com.android.mevabe.common.RefreshLoadMoreAdapter;

public class RefreshLoadMoreLayout extends SwipeRefreshLayout {

    /**
     * Interface handle load more processing
     */
    public interface ILoadMoreListener {
        public void onLoadMore();
    }

    private LoadMoreRecyclerView recyclerView;


    /**
     * Constructor
     *
     * @param context Context
     */
    public RefreshLoadMoreLayout(Context context) {
        this(context, null);
    }

    /**
     * Constructor
     *
     * @param context Context
     * @param attrs   AttributeSet
     */
    public RefreshLoadMoreLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Bind view for refresh and load more view
     *
     * @param recyclerView LoadMoreRecyclerView
     * @param loadListener ILoadMoreListener
     */
    public void bindView(LoadMoreRecyclerView recyclerView, ILoadMoreListener loadListener) {
        this.recyclerView = recyclerView;
        if (recyclerView != null) {
            recyclerView.setOnLoadMoreListener(loadListener);
        }
    }

    @Override
    public void setRefreshing(boolean isRefreshing) {
        // Stop load more if have
        if (isRefreshing) {
            setLoadingMore(false);
        }

        super.setRefreshing(isRefreshing);
    }

    /**
     * Start/stop loading more
     *
     * @param isLoading boolean
     */
    public void setLoadingMore(boolean isLoading) {
        if (isLoading) {
            // Stop refresh
            super.setRefreshing(false);

            // Start loading more
            RefreshLoadMoreAdapter adapter = (RefreshLoadMoreAdapter) recyclerView.getAdapter();
            adapter.startLoadMore();
        } else {
            recyclerView.setLoading(false);
        }
    }
}