package com.android.mevabe.common.services;

import android.os.AsyncTask;

import com.android.mevabe.common.utils.LogUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadTask extends AsyncTask<String, Void, ConnectionResult> {

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected ConnectionResult doInBackground(String... params) {
        ConnectionResult result = new ConnectionResult();
        HttpURLConnection urlConnection;
        try {
            URL url = new URL(params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            int statusCode = urlConnection.getResponseCode();
            result.setCode(statusCode);

            // 200 represents HTTP OK
            if (statusCode == ConnectionResult.SUCCESS_CODE) {
                BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    response.append(line);
                }
                // Case success full
                result.setResult(response.toString());
            }
        } catch (Exception e) {
            LogUtil.error(e);
            result.setCode(-1);
        }
        return result;
    }
}