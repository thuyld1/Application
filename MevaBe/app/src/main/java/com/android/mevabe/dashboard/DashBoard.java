package com.android.mevabe.dashboard;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.mevabe.R;
import com.android.mevabe.common.AppConfig;
import com.android.mevabe.common.Screen;
import com.android.mevabe.services.APIService;
import com.android.mevabe.view.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thuyld on 12/14/16.
 */
public class DashBoard extends Screen implements DBRecyclerViewAdapter.IDashBoardListHandler {
    private RefreshLayout swipeRefreshLayout;
    private List<DBFeedItem> feedsList;
    private RecyclerView mRecyclerView;
    private DBRecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(
                R.layout.dashboard, container, false);

        // Set up listener for swipe to refresh
        mRecyclerView = (RecyclerView) view.findViewById(R.id.itemsRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        swipeRefreshLayout = (RefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setChildView(mRecyclerView);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });
        swipeRefreshLayout.setOnLoadMoreListener(new RefreshLayout.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

            }
        });

        // Prepare data
        feedsList = new ArrayList<>();
        adapter = new DBRecyclerViewAdapter(getActivity(), feedsList);
        adapter.setHandler(DashBoard.this);
        mRecyclerView.setAdapter(adapter);


        String url = "http://stacktips.com/?json=get_category_posts&slug=news&count=15";
        APIService service = new APIService();
        service.callAPI(url, new APIService.IAPIServiceHandler<List<DBFeedItem>>() {
            @Override
            public void onSuccess(List<DBFeedItem> result) {
                if (result != null) {
                    feedsList.addAll(result);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
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
        String url = "http://stacktips.com/?json=get_category_posts&slug=news&count=15";
        APIService service = new APIService();
        service.callAPI(url, new APIService.IAPIServiceHandler<List<DBFeedItem>>() {
            @Override
            public void onSuccess(List<DBFeedItem> result) {
                if (result != null) {
                    feedsList.addAll(result);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
                }

                // Stop refresh animation
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    // ******** DBRecyclerViewAdapter.IDashBoardListHandler ******** //
    @Override
    public void onItemClick(DBFeedItem item) {
        Toast.makeText(getActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
    }

}
