package com.android.mevabe.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.mevabe.R;
import com.android.mevabe.common.AppConfig;

/**
 * Created by anhdt on 1/11/16.
 */
public class LoadMoreFooter extends FrameLayout {

    private TextView tvMore;
    private ProgressBar progressBar;

    public LoadMoreFooter(Context context) {
        super(context);
        init();
    }

    public LoadMoreFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public LoadMoreFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoadMoreFooter(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();

    }

    protected void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_load_more_footer, this, true);
        tvMore = (TextView) view.findViewById(R.id.text_more);
        progressBar = (ProgressBar) view.findViewById(R.id.load_progress_bar);
    }

    public void setOnLoadMore(boolean isLoadMore) {
        Log.d(AppConfig.LOG_TAG, "setOnLoadMore() isLoadMore " + isLoadMore);
        if (isLoadMore) {
            tvMore.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            tvMore.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        }
    }
}
