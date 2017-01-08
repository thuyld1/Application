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
import com.android.mevabe.dashboard.DBFeedItem;
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
        DBFeedItem item = (DBFeedItem) getIntent().getSerializableExtra(Constants.INTENT_DATA);
        if (item != null && item.getUrl() != null) {
            actionBar.setTitle(item.getTitle());
            webView.loadUrl(item.getUrl());
        }

//        webView.setGestureDetector(new GestureDetector(new CustomeGestureDetector()));
    }


    private class CustomeGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e1 == null || e2 == null) return false;
            if (e1.getPointerCount() > 1 || e2.getPointerCount() > 1) return false;
            else {
                try { // right to left swipe .. go to next page
                    if (e1.getX() - e2.getX() > 100 && Math.abs(velocityX) > 800) {
                        //do your stuff
                        return true;
                    } //left to right swipe .. go to prev page
                    else if (e2.getX() - e1.getX() > 100 && Math.abs(velocityX) > 800) {
                        //do your stuff
                        return true;
                    } //bottom to top, go to next document
                    else if (e1.getY() - e2.getY() > 100 && Math.abs(velocityY) > 800
                            && webView.getScrollY() >= webView.getScale() * (webView.getContentHeight() - webView.getHeight())) {
                        //do your stuff
                        return true;
                    } //top to bottom, go to prev document
                    else if (e2.getY() - e1.getY() > 100 && Math.abs(velocityY) > 800) {
                        //do your stuff
                        return true;
                    }
                } catch (Exception e) { // nothing
                }
                return false;
            }
        }
    }

}
