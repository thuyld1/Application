package com.android.mevabe.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class LoadMoreRecyclerView extends RecyclerView {
    private RefreshLoadMoreLayout.ILoadMoreListener mOnLoadMoreListener;

    private boolean isLoading;
    private int visibleThreshold = 3;
    private int lastVisibleItem;
    private int totalItemCount;

    /**
     * Constructor
     *
     * @param context Context
     */
    public LoadMoreRecyclerView(Context context) {
        this(context, null);
    }

    /**
     * Constructor
     *
     * @param context Context
     * @param attrs   AttributeSet
     */
    public LoadMoreRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Set layout manager
        setLayoutManager(new LinearLayoutManager(context));

        // Add scroll listener to detect load more
        addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) getLayoutManager();
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (mOnLoadMoreListener != null && !isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    mOnLoadMoreListener.onLoadMore();
                    isLoading = true;
                }
            }
        });
    }

    /**
     * Set load more listener
     *
     * @param loadListener ILoadMoreListener
     */
    public void setOnLoadMoreListener(RefreshLoadMoreLayout.ILoadMoreListener loadListener) {
        mOnLoadMoreListener = loadListener;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }
}