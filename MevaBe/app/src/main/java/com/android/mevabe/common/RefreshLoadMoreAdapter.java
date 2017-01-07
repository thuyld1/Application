package com.android.mevabe.common;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.android.mevabe.model.BaseModel;
import com.android.mevabe.view.LoadMoreFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * RefreshLoadMoreAdapter controls view of list with load more option
 */
public class RefreshLoadMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_LOADMORE_FOOTER = -1;
    protected List listItems;
    protected Activity mContext;

    /**
     * Constructor
     *
     * @param context Context
     */
    public RefreshLoadMoreAdapter(Activity context) {
        this.listItems = new ArrayList<>();
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == VIEW_TYPE_LOADMORE_FOOTER) {
            LoadMoreFooter footer = new LoadMoreFooter(mContext);
            footer.setLayoutParams(
                    new FrameLayout.LayoutParams(
                            FrameLayout.LayoutParams.MATCH_PARENT,
                            FrameLayout.LayoutParams.WRAP_CONTENT
                    ));
            return new LoadMoreHolder(footer);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        BaseModel item = (BaseModel) listItems.get(position);
        if (item == null) {
            return VIEW_TYPE_LOADMORE_FOOTER;
        } else {
            return item.getViewType();
        }
    }

    @Override
    public int getItemCount() {
        return (null != listItems ? listItems.size() : 0);
    }

    // ************* Update data *********** //
    public void refreshItems(List result) {
        if (result != null) {
            listItems.clear();
            listItems.addAll(result);

            mContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    notifyDataSetChanged();
                }
            });
        }
    }

    public synchronized void startLoadMore() {
        // Add an empty item to show loading view at bottom
        listItems.add(null);
        mContext.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    /**
     * Update data when load more has finished
     */
    public synchronized void appendItems(List result) {
        // Add an empty item to show loading view at bottom
        listItems.remove(listItems.size() - 1);
        if (result != null) {
            listItems.addAll(result);
        }
        mContext.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }


    // ************* View Holder *********** //
    class LoadMoreHolder extends RecyclerView.ViewHolder {
        private LoadMoreFooter loadMoreFooter;

        public LoadMoreHolder(View itemView) {
            super(itemView);
            loadMoreFooter = (LoadMoreFooter) itemView;
        }
    }
}