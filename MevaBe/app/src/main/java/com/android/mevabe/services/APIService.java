package com.android.mevabe.services;

import com.android.mevabe.dashboard.DBFeedItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leducthuy on 1/4/17.
 */

public class APIService {
    /**
     * IAPIServiceHandler interface for API service callback
     *
     * @param <T>
     */
    public interface IAPIServiceHandler<T> {
        void onSuccess(T result);
    }

    /**
     * Call API to get result
     *
     * @param url
     * @param callback
     */
    public void callAPI(String url, final IAPIServiceHandler callback) {
        // Create task to download
        DownloadTask task = new DownloadTask() {
            @Override
            protected void onPostExecute(ConnectionResult connectionResult) {
                super.onPostExecute(connectionResult);

                // Parse result and callback
                if (callback != null) {
                    callback.onSuccess(parseResult(connectionResult));
                }
            }
        };
        task.execute(url);
    }


    /**
     * Parse JSON to class
     *
     * @param connectionResult
     * @return List<DBFeedItem>
     */
    private List<DBFeedItem> parseResult(ConnectionResult connectionResult) {
        try {
            JSONObject response = new JSONObject(connectionResult.getResult());
            JSONArray posts = response.optJSONArray("posts");
            List<DBFeedItem> feedsList = new ArrayList<>();

            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                DBFeedItem item = new DBFeedItem();
                item.setTitle(post.optString("title"));
                item.setThumb(post.optString("thumbnail"));
                feedsList.add(item);
            }

            return feedsList;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
