package com.android.mevabe.bacsi;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;

import com.android.mevabe.R;
import com.android.mevabe.WebViewActivity;
import com.android.mevabe.common.AppConfig;
import com.android.mevabe.common.Constants;
import com.android.mevabe.model.DBFeedModel;
import com.android.mevabe.dashboard.DBRecyclerViewAdapter;
import com.android.mevabe.services.APIService;
import com.android.mevabe.view.FragmentBase;
import com.android.mevabe.view.LoadMoreRecyclerView;
import com.android.mevabe.view.RefreshLoadMoreLayout;

import java.util.List;

/**
 * Created by thuyld on 12/14/16.
 */
public class BacSiMain extends FragmentBase implements DBRecyclerViewAdapter.IDashBoardListHandler {
    private RefreshLoadMoreLayout swipeRefreshLayout;
    private LoadMoreRecyclerView mRecyclerView;
    private DBRecyclerViewAdapter adapter;

    @Override
    public int getLayoutContentViewXML() {
        return R.layout.bacsi;
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
        adapter.setHandler(BacSiMain.this);
        mRecyclerView.setAdapter(adapter);
        refreshItems();
    }


    @Override
    public void onToolBarClicked(View v) {
        Log.i(AppConfig.LOG_TAG, "onToolBarClicked");
        if (mRecyclerView != null) {
            mRecyclerView.smoothScrollToPosition(0);
        }
    }

    /**
     * Refresh item
     */
    private void refreshItems() {
        // Show refreshing UI
        swipeRefreshLayout.setRefreshing(true);

        String url = "http://stacktips.com/?json=get_category_posts&slug=news&count=5";
        APIService service = new APIService();
        service.callAPI(url, new APIService.IAPIServiceHandler<List<DBFeedModel>>() {
            @Override
            public void onSuccess(List<DBFeedModel> result) {
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
        service.callAPI(url, new APIService.IAPIServiceHandler<List<DBFeedModel>>() {
            @Override
            public void onSuccess(final List<DBFeedModel> result) {
                adapter.appendItems(result);

                // Stop loading
                swipeRefreshLayout.setLoadingMore(false);
            }
        });
    }

    // ******** DBRecyclerViewAdapter.IDashBoardListHandler ******** //
    @Override
    public void onItemClick(DBFeedModel item) {
        WebViewActivity act = new WebViewActivity();
        Intent intent = new Intent(getContext(), WebViewActivity.class);
        intent.putExtra(Constants.INTENT_DATA, item);
        startActivity(intent);
    }

}
