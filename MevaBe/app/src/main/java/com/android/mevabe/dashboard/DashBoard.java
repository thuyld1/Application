package com.android.mevabe.dashboard;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.mevabe.BaseFragment;
import com.android.mevabe.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thuyld on 12/14/16.
 */
public class DashBoard extends BaseFragment implements DBRecyclerViewAdapter.IDashBoardListHandler {
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<DBFeedItem> feedsList;
    private RecyclerView mRecyclerView;
    private DBRecyclerViewAdapter adapter;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(
                R.layout.dashboard, container, false);

        // Set up listener for swipe to refresh
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });

        mRecyclerView = (RecyclerView) view.findViewById(R.id.itemsRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        String url = "http://stacktips.com/?json=get_category_posts&slug=news&count=30";
        new DownloadTask().execute(url);

        return view;
    }

    @Override
    protected void onToolBarClicked(View v) {
        mRecyclerView.smoothScrollToPosition(0);
    }

    /**
     * Refresh item
     */
    private void refreshItems() {
        // Load items
        for (int i = 0; i < 10000; i++) {

        }
        // Load complete
        onItemsLoadComplete();
    }

    /**
     * Handle in case completed
     */
    private void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation
        swipeRefreshLayout.setRefreshing(false);
    }

    // ******** DBRecyclerViewAdapter.IDashBoardListHandler ******** //
    @Override
    public void onItemClick(DBFeedItem item) {
        Toast.makeText(getActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
    }


    // ******** DownloadTask ******** //
    public class DownloadTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            HttpURLConnection urlConnection;
            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                int statusCode = urlConnection.getResponseCode();

                // 200 represents HTTP OK
                if (statusCode == 200) {
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    parseResult(response.toString());
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed to fetch data!";
                }
            } catch (Exception e) {
                Log.d("", e.getLocalizedMessage());
            }
            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            progressBar.setVisibility(View.GONE);

            if (result == 1) {
                adapter = new DBRecyclerViewAdapter(getActivity(), feedsList);
                adapter.setHandler(DashBoard.this);
                mRecyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(getContext(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");
            feedsList = new ArrayList<>();

            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                DBFeedItem item = new DBFeedItem();
                item.setTitle(post.optString("title"));
                item.setThumbnail(post.optString("thumbnail"));
                feedsList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
