package com.android.mevabe;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import com.android.mevabe.common.Constants;
import com.android.mevabe.model.WebViewModel;
import com.android.mevabe.view.SwipeWebView;

public class WebViewActivity extends AppCompatActivity {
    private SwipeWebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set UI layout
        setContentView(R.layout.activity_web_view);

        // Set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Implement back button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Get URL for webview
        webView = (SwipeWebView) findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        // Bind data to view
        WebViewModel item = (WebViewModel) getIntent().getSerializableExtra(Constants.INTENT_DATA);
        if (item != null && item.getUrl() != null) {
            actionBar.setTitle(item.getTitle());
            webView.loadUrl(item.getUrl());
        }

        webView.setGestureDetector(new GestureDetector(getApplicationContext(), new CustomeGestureDetector() {
            public void onSwipeRight() {
                finish();
            }
        }));
    }


    private class CustomeGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e1 == null || e2 == null || e1.getPointerCount() > 1 || e2.getPointerCount() > 1) {
                return false;
            } else {
                try {
                    if (e2.getX() - e1.getX() > 300 && Math.abs(velocityX) > 1000) {
                        onSwipeRight();
                        return true;
                    }
                } catch (Exception e) { // nothing
                }
                return false;
            }
        }

        public void onSwipeRight() {

        }
    }

}
