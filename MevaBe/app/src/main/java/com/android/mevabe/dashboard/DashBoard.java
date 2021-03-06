package com.android.mevabe.dashboard;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.android.mevabe.R;
import com.android.mevabe.common.Constants;
import com.android.mevabe.common.model.DBFeedModel;
import com.android.mevabe.common.model.WebViewModel;
import com.android.mevabe.common.services.APIService;
import com.android.mevabe.common.utils.AppUtil;
import com.android.mevabe.common.utils.LogUtil;
import com.android.mevabe.common.view.FragmentBase;
import com.android.mevabe.common.view.LoadMoreRecyclerView;
import com.android.mevabe.common.view.RefreshLoadMoreLayout;
import com.android.mevabe.common.view.WebViewActivity;

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
        View emptyView = layoutView.findViewById(R.id.empty_data);
        mRecyclerView.setEmptyView(emptyView);

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
        LogUtil.debug("onToolBarClicked");
        if (mRecyclerView != null) {

            mRecyclerView.smoothScrollToPosition(0);
        }
    }

    /**
     * Refresh item
     */
    private void refreshItems() {
        String url = "http://stacktips.com/?json=get_category_posts&slug=news&count=5";
        APIService service = new APIService(getContext());
        service.callAPI(url, new APIService.IAPIServiceHandler<List<DBFeedModel>>() {
            @Override
            public void beforeStarting() {
                // Show refreshing UI
                swipeRefreshLayout.setRefreshing(true);
            }

            @Override
            public void onSuccess(List<DBFeedModel> result) {
                adapter.refreshItems(result);

                // Stop refreshing UI
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFail() {
                // Stop refreshing UI
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    /**
     * Load more data
     */
    private void loadMore() {
        String url = "http://stacktips.com/?json=get_category_posts&slug=news&count=2";
        APIService service = new APIService(getContext());
        service.callAPI(url, new APIService.IAPIServiceHandler<List<DBFeedModel>>() {
            @Override
            public void beforeStarting() {
                // Show loading more view
                swipeRefreshLayout.setLoadingMore(true);
            }

            @Override
            public void onSuccess(final List<DBFeedModel> result) {
                adapter.appendItems(result);

                // Stop loading
                swipeRefreshLayout.setLoadingMore(false);
            }

            @Override
            public void onFail() {
                // Stop loading
                swipeRefreshLayout.setLoadingMore(false);
            }
        });
    }

    // ******** DBRecyclerViewAdapter.IDashBoardListHandler ******** //
    @Override
    public void onItemClick(DBFeedModel item) {
        if (AppUtil.checkInternet(getContext())) {
            WebViewActivity act = new WebViewActivity();
            Intent intent = new Intent(getContext(), WebViewActivity.class);
            WebViewModel info = new WebViewModel(item.getTitle(), item.getUrl());
            intent.putExtra(Constants.INTENT_DATA, info);
            startActivity(intent);
        }
    }

}
