package com.android.mevabe.doctor;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mevabe.R;
import com.android.mevabe.common.Constants;
import com.android.mevabe.common.db.DBDoctors;
import com.android.mevabe.common.model.DBFeedModel;
import com.android.mevabe.common.model.WebViewModel;
import com.android.mevabe.common.services.APIService;
import com.android.mevabe.common.utils.LogUtil;
import com.android.mevabe.common.view.FragmentBase;
import com.android.mevabe.common.view.LoadMoreRecyclerView;
import com.android.mevabe.common.view.RecyclerViewSupportEmpty;
import com.android.mevabe.common.view.RefreshLoadMoreLayout;
import com.android.mevabe.common.view.WebViewActivity;
import com.android.mevabe.dashboard.DBRecyclerViewAdapter;

import java.util.List;

/**
 * Created by thuyld on 12/14/16.
 */
public class DoctorsMain extends FragmentBase implements View.OnClickListener, DBRecyclerViewAdapter.IDashBoardListHandler {
    public static final int DOCTORS_FILTER_CODE = 3017;

    // For sub header
    private TextView btnTabSearch;
    private TextView btnTabFavorite;
    private ViewGroup contentSearch;
    private ViewGroup contentFavorite;

    // For doctors search view content
    private EditText searchKey;
    private ImageView btnFilter;
    private RefreshLoadMoreLayout swipeRefreshLayout;
    private LoadMoreRecyclerView mRecyclerView;
    private AdapterDoctorsSearch adapter;

    // For doctors favorite
    private RecyclerViewSupportEmpty listFavorite;
    private AdapterDoctorsFavorite favoriteAdapter;
    private DBDoctors dbDoctors;

    @Override
    public int getLayoutContentViewXML() {
        return R.layout.doctors;
    }

    @Override
    public void initView(View layoutView) {
        // Bind view
        btnTabSearch = (TextView) layoutView.findViewById(R.id.btn_tab_search);
        btnTabFavorite = (TextView) layoutView.findViewById(R.id.btn_tab_favorite);
        contentSearch = (ViewGroup) layoutView.findViewById(R.id.content_search);
        contentFavorite = (ViewGroup) layoutView.findViewById(R.id.content_favorite);
        searchKey = (EditText) layoutView.findViewById(R.id.search_key);
        btnFilter = (ImageView) layoutView.findViewById(R.id.btn_filter);

        listFavorite = (RecyclerViewSupportEmpty) layoutView.findViewById(R.id.list_favorite);
        listFavorite.initEmptyMessage(contentFavorite);


        // Bind action listener
        btnTabSearch.setOnClickListener(this);
        btnTabFavorite.setOnClickListener(this);
        btnFilter.setOnClickListener(this);

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
        adapter = new AdapterDoctorsSearch(getActivity(), null);
        mRecyclerView.setAdapter(adapter);
        refreshItems();


        favoriteAdapter = new AdapterDoctorsFavorite(getActivity(), null);
        listFavorite.setAdapter(favoriteAdapter);
        dbDoctors = new DBDoctors();
        reloadListFavorite();

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

    // ******** View.OnClickListener ******** //
    @Override
    public void onClick(View v) {
        if (v.equals(btnTabSearch)) {
            contentSearch.setVisibility(View.VISIBLE);
            contentFavorite.setVisibility(View.GONE);
            btnTabSearch.setTextColor(getResources().getColor(R.color.colorPrimary));
            btnTabFavorite.setTextColor(getResources().getColor(R.color.textColor));
        } else if (v.equals(btnTabFavorite)) {
            contentSearch.setVisibility(View.GONE);
            contentFavorite.setVisibility(View.VISIBLE);
            btnTabSearch.setTextColor(getResources().getColor(R.color.textColor));
            btnTabFavorite.setTextColor(getResources().getColor(R.color.colorPrimary));
        } else if (v.equals(btnFilter)) {
            settingFilter();
        }
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

    // ******** List favorite doctors control ******** //
    private void reloadListFavorite() {
        List doctors = dbDoctors.getFavoriteDoctors();
        favoriteAdapter.refreshItems(doctors);
    }


}
