package com.android.mevabe.common.services;

import android.content.Context;
import android.widget.Toast;

import com.android.mevabe.R;
import com.android.mevabe.common.Constants;
import com.android.mevabe.common.model.DBFeedModel;
import com.android.mevabe.common.utils.AppUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leducthuy on 1/4/17.
 */

public class APIService {
    private Context context;

    /**
     * IAPIServiceHandler interface for API service callback
     *
     * @param <T>
     */
    public interface IAPIServiceHandler<T> {
        void beforeStarting();

        void onSuccess(T result);

        void onFail();
    }

    /**
     * Contructor
     *
     * @param context Context
     */
    public APIService(Context context) {
        this.context = context;
    }

    /**
     * Call API to get result
     *
     * @param url
     * @param callback
     */
    public void callAPI(String url, final IAPIServiceHandler callback) {
        // Check internet connection
        if (AppUtil.hasInternet(context)) {
            // Prepare before starting
            if (callback != null) {
                callback.beforeStarting();
            }

            // Create task to download
            DownloadTask task = new DownloadTask() {
                @Override
                protected void onPostExecute(ConnectionResult connectionResult) {
                    super.onPostExecute(connectionResult);

                    // Parse result and callback
                    if (callback != null) {
                        if (connectionResult.isSuccess()) {
                            callback.onSuccess(parseResult(connectionResult));
                        } else {
                            Toast.makeText(context, context.getText(R.string.connect_server_fail),
                                    Toast.LENGTH_SHORT).show();
                            callback.onFail();
                        }
                    }
                }
            };
            task.execute(url);
        } else {
            if (callback != null) {
                callback.onFail();
            }
            Toast.makeText(context, context.getText(R.string.connect_no_internet),
                    Toast.LENGTH_SHORT).show();
        }
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
