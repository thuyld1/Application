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


//    private void intView(Context context) {
//        // Build load more recycler view
//        recyclerView = new LoadMoreRecyclerView(context);
//        RecyclerView.LayoutParams layout = new RecyclerView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//        recyclerView.setVerticalScrollBarEnabled(true);
//        recyclerView.setLayoutParams(layout);
//        this.addView(recyclerView);
//    }

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

    /**
     * Start/stop loading more
     *
     * @param isLoading boolean
     */
    public void setLoadingMore(boolean isLoading) {
        RefreshLoadMoreAdapter adapter = (RefreshLoadMoreAdapter) recyclerView.getAdapter();
        if (isLoading) {
            adapter.startLoadMore();
        } else {
            recyclerView.setLoading(false);
        }
    }
}