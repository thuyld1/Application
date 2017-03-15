package com.android.mevabe.common.services;

import com.android.mevabe.common.Constants;
import com.android.mevabe.common.model.DBFeedModel;

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
     * @return List<DBFeedModel>
     */
    private List<DBFeedModel> parseResult(ConnectionResult connectionResult) {
        try {
            JSONObject response = new JSONObject(connectionResult.getResult());
            JSONArray posts = response.optJSONArray("posts");
            List<DBFeedModel> feedsList = new ArrayList<>();

            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                DBFeedModel item = new DBFeedModel();
                item.setViewType(Constants.FEED_VIEW_TYPE_LEFT);
                item.setTitle(post.optString("title"));
                item.setThumb(post.optString("thumbnail"));
                item.setUrl("http://m.dantri.com.vn/xa-hoi/hai-phong-yeu-cau-thao-do-rong-vang-dau-pikachu-gay-tranh-cai-20170108172552427.htm");
                feedsList.add(item);
            }

            return feedsList;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
