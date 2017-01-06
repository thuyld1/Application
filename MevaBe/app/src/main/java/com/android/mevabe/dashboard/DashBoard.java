package com.android.mevabe.dashboard;

import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.mevabe.R;
import com.android.mevabe.common.AppConfig;
import com.android.mevabe.services.APIService;
import com.android.mevabe.view.FragmentBase;
import com.android.mevabe.view.LoadMoreRecyclerView;
import com.android.mevabe.view.RefreshLoadMoreLayout;

import java.util.List;

/**
 * Created by thuyld on 12/14/16.
 */
public class DashBoard extends FragmentBase implements DBRecyclerViewAdapter.IDashBoardListHandler {
    private RefreshLoadMoreLayout swipeRefreshLayout;
    private LoadMoreRecyclerView mRecyclerView;
    private DBRecyclerViewAdapter adapter;

    @Override
    public int getLayoutContentViewXML() {
        return R.layout.dashboard;
    }

    @Override
    public void initView(View layoutView) {
        // Set up listener for swipe to refresh
        mRecyclerView = (LoadMoreRecyclerView) layoutView.findViewById(R.id.itemsRecyclerView);

        swipeRefreshLayout = (RefreshLoadMoreLayout) layoutView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });
        swipeRefreshLayout.bindView(mRecyclerView, new RefreshLoadMoreLayout.ILoadMoreListener() {
            @Override
            public void onLoadMore() {
                // Load more data
                loadMore();
            }
        });

        // Prepare data
        adapter = new DBRecyclerViewAdapter(getActivity());
        adapter.setHandler(DashBoard.this);
        mRecyclerView.setAdapter(adapter);
        refreshItems();
    }


    @Override
    public void onToolBarClicked(View v) {
        Log.i(AppConfig.LOG_TAG, "onToolBarClicked");
        mRecyclerView.smoothScrollToPosition(0);
    }

    /**
     * Refresh item
     */
    private void refreshItems() {
        // Show refreshing UI
        swipeRefreshLayout.setRefreshing(true);

        String url = "http://stacktips.com/?json=get_category_posts&slug=news&count=2";
        APIService service = new APIService();
        service.callAPI(url, new APIService.IAPIServiceHandler<List<DBFeedItem>>() {
            @Override
            public void onSuccess(List<DBFeedItem> result) {
                adapter.refreshItems(result);

                // Stop refreshing UI
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    /**
     * Load more data
     */
    private void loadMore() {
        // Show loading more view
        swipeRefreshLayout.setLoadingMore(true);

        String url = "http://stacktips.com/?json=get_category_posts&slug=news&count=2";
        APIService service = new APIService();
        service.callAPI(url, new APIService.IAPIServiceHandler<List<DBFeedItem>>() {
            @Override
            public void onSuccess(final List<DBFeedItem> result) {
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.appendItems(result);

                        // Stop loading
                        swipeRefreshLayout.setLoadingMore(false);
                    }
                }, 5000);
            }
        });
    }

    // ******** DBRecyclerViewAdapter.IDashBoardListHandler ******** //
    @Override
    public void onItemClick(DBFeedItem item) {
        Toast.makeText(getActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
    }

}
