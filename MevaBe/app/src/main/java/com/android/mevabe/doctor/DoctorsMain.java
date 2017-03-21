package com.android.mevabe.doctor;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.mevabe.R;
import com.android.mevabe.common.Constants;
import com.android.mevabe.common.model.DBFeedModel;
import com.android.mevabe.common.model.WebViewModel;
import com.android.mevabe.common.services.APIService;
import com.android.mevabe.common.utils.LogUtil;
import com.android.mevabe.common.view.FragmentBase;
import com.android.mevabe.common.view.LoadMoreRecyclerView;
import com.android.mevabe.common.view.RefreshLoadMoreLayout;
import com.android.mevabe.common.view.WebViewActivity;
import com.android.mevabe.dashboard.DBRecyclerViewAdapter;

import java.util.List;

/**
 * Created by thuyld on 12/14/16.
 */
public class DoctorsMain extends FragmentBase implements DBRecyclerViewAdapter.IDashBoardListHandler {
    public static final int DOCTORS_FILTER_CODE = 3017;

    // For view binder
    private EditText searchKey;
    private ImageView btnFilter;

    // For search result
    private RefreshLoadMoreLayout swipeRefreshLayout;
    private LoadMoreRecyclerView mRecyclerView;
    private DBRecyclerViewAdapter adapter;

    @Override
    public int getLayoutContentViewXML() {
        return R.layout.doctors;
    }

    @Override
    public void initView(View layoutView) {
        // Bind view
        searchKey = (EditText) layoutView.findViewById(R.id.search_key);
        btnFilter = (ImageView) layoutView.findViewById(R.id.btn_filter);

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingFilter();
            }
        });

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
        adapter.setHandler(DoctorsMain.this);
        mRecyclerView.setAdapter(adapter);
        refreshItems();
    }

    // ******** Action control ******** //
    @Override
    public void onToolBarClicked(View v) {
        LogUtil.debug("onToolBarClicked");
        if (mRecyclerView != null) {
            mRecyclerView.smoothScrollToPosition(0);
        }
    }

    /**
     * Show setting filter screen
     */
    private void settingFilter() {
        // Open setting filter view
        Intent intent = new Intent(getContext(), DoctorsFilterSetting.class);
        startActivityForResult(intent, DOCTORS_FILTER_CODE);
    }

    // ******** List results control ******** //
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
        WebViewModel info = new WebViewModel(item.getTitle(), item.getUrl());
        intent.putExtra(Constants.INTENT_DATA, info);
        startActivity(intent);
    }

}
