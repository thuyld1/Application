package com.android.mevabe.doctor;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mevabe.R;
import com.android.mevabe.common.Constants;
import com.android.mevabe.common.db.DBDoctors;
import com.android.mevabe.common.model.DBFeedModel;
import com.android.mevabe.common.model.DoctorInfo;
import com.android.mevabe.common.model.WebViewModel;
import com.android.mevabe.common.services.APIService;
import com.android.mevabe.common.utils.LogUtil;
import com.android.mevabe.common.view.FragmentBase;
import com.android.mevabe.common.view.LoadMoreRecyclerView;
import com.android.mevabe.common.view.RecyclerViewSupportEmpty;
import com.android.mevabe.common.view.RefreshLoadMoreLayout;
import com.android.mevabe.common.view.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thuyld on 12/14/16.
 */
public class DoctorsMain extends FragmentBase implements View.OnClickListener, IDoctorsHandler {
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
    private List<DoctorInfo> listFavoriteDoctors;
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
        View emptyFavorite = layoutView.findViewById(R.id.empty_favorite);
        listFavorite.setLayoutManager(new LinearLayoutManager(getContext()));
        listFavorite.setEmptyView(emptyFavorite);
//        listFavorite.initEmptyMessage(contentFavorite);


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
        favoriteAdapter = new AdapterDoctorsFavorite(getActivity(), this);
        listFavorite.setAdapter(favoriteAdapter);
        dbDoctors = new DBDoctors();
        reloadListFavorite();

        adapter = new AdapterDoctorsSearch(getActivity(), this);
        mRecyclerView.setAdapter(adapter);
        refreshItems();


    }

    // ******** Action control ******** //
    @Override
    public void onToolBarClicked(View v) {
        LogUtil.debug("onToolBarClicked");
        // Case tab search is selected
        if (contentSearch.getVisibility() == View.VISIBLE) {
            mRecyclerView.smoothScrollToPosition(0);
        } else {
            listFavorite.smoothScrollToPosition(0);
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

        List<DoctorInfo> result = new ArrayList<>();
        for (int i = 1; i < 21; i++) {
            result.add(new DoctorInfo(i, "Dr.XXXX",
                    "https://scontent.fhan3-1.fna.fbcdn.net/v/t1.0-1/p160x160/13903281_10208607947094323_6663194054724689430_n.jpg?oh=c5c362ff79da307e58754afec6ba7c0e&oe=5998C030",
                    "01678882655", "Bác sĩ chuyên khoa"));
        }

        updateFavoriteStatus(result);
        adapter.refreshItems(result);

        // Stop refreshing UI
        swipeRefreshLayout.setRefreshing(false);

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
                adapter.appendItems(new ArrayList());

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

    // ******** View.OnClickListener ******** //
    @Override
    public void onClick(View v) {
        if (v.equals(btnTabSearch)) {
            contentSearch.setVisibility(View.VISIBLE);
            contentFavorite.setVisibility(View.GONE);
            btnTabSearch.setTextColor(getResources().getColor(R.color.colorPrimary));
            btnTabFavorite.setTextColor(getResources().getColor(R.color.textColor));

            updateFavoriteStatus(adapter.getListItems());
            adapter.notifyDataSetChanged();
        } else if (v.equals(btnTabFavorite)) {
            contentSearch.setVisibility(View.GONE);
            contentFavorite.setVisibility(View.VISIBLE);
            btnTabSearch.setTextColor(getResources().getColor(R.color.textColor));
            btnTabFavorite.setTextColor(getResources().getColor(R.color.colorPrimary));

            reloadListFavorite();
        } else if (v.equals(btnFilter)) {
            settingFilter();
        }
    }

    // ******** List favorite doctors control ******** //
    private void reloadListFavorite() {
        listFavoriteDoctors = dbDoctors.getFavoriteDoctors();
        favoriteAdapter.refreshItems(listFavoriteDoctors);
    }

    private void updateFavoriteStatus(List<DoctorInfo> listDoctors) {
        // Update favorite status for list doctors
        for (DoctorInfo info : listDoctors) {
            info.setFavorite(false);
            for (DoctorInfo favInfo : listFavoriteDoctors) {
                if (info.getCode() == favInfo.getCode()) {
                    info.setFavorite(favInfo.isFavorite());
                    break;
                }
            }
        }
    }


    // ******** IDoctorsHandler ******** //
    @Override
    public void onClickFavorite(DoctorInfo item) {
        // Update data into DB
        if (item.isFavorite()) {
            dbDoctors.addFavorite(item);
        } else {
            dbDoctors.deleteFavorite(item);
        }
    }

    @Override
    public void onClickCall(DoctorInfo item) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + item.getPhone()));
        startActivity(intent);
    }

    @Override
    public void onclickItem(DoctorInfo item) {
        WebViewActivity act = new WebViewActivity();
        Intent intent = new Intent(getContext(), WebViewActivity.class);
        WebViewModel info = new WebViewModel("", "");
        intent.putExtra(Constants.INTENT_DATA, info);
        startActivity(intent);
    }

}
