package com.android.mevabe.common.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.mevabe.R;

import static com.facebook.FacebookSdk.getApplicationContext;

public class RecyclerViewSupportEmpty extends RecyclerView {

    private View emptyView;

    final private AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            checkIfEmpty();
        }
    };

    public RecyclerViewSupportEmpty(Context context) {
        super(context);
    }

    public RecyclerViewSupportEmpty(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewSupportEmpty(Context context, AttributeSet attrs,
                                    int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        final Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(observer);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
        }

        checkIfEmpty();
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
        checkIfEmpty();
    }

    public void initEmptyMessage(ViewGroup parent, int resID) {
        // Inflate the layout for this fragment
        initEmptyMessage(parent);

        if (emptyView != null && emptyView instanceof TextView) {
            ((TextView) emptyView).setText(resID);
        }
    }

    public void initEmptyMessage(ViewGroup parent) {
        // Inflate the layout for this fragment
        LayoutInflater mInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        emptyView = mInflater.inflate(R.layout.common_empty_data_view, parent, true);
    }

    void checkIfEmpty() {
        if (emptyView != null && getAdapter() != null) {
            final boolean emptyViewVisible =
                    getAdapter().getItemCount() == 0;
            emptyView.setVisibility(emptyViewVisible ? VISIBLE : GONE);
            setVisibility(emptyViewVisible ? GONE : VISIBLE);
        }
    }
}