package com.android.mevabe.dashboard;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mevabe.R;
import com.android.mevabe.common.RefreshLoadMoreAdapter;
import com.squareup.picasso.Picasso;

/**
 * DashBoardRecyclerAdapter controls view of list in DashBoard
 */
public class DBRecyclerViewAdapter extends RefreshLoadMoreAdapter {
    /**
     * IDashBoardListHandler interface for callback
     */
    public interface IDashBoardListHandler {
        void onItemClick(DBFeedItem item);
    }

    private IDashBoardListHandler handler;

    /**
     * Constructor
     *
     * @param context Context
     */
    public DBRecyclerViewAdapter(Activity context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Check to load load more view or not
        RecyclerView.ViewHolder view = super.onCreateViewHolder(viewGroup, viewType);
        if (view == null) {
            View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dashboard_item_update_info, null);
            view = new CustomViewHolder(layout);
        }

        return view;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type) {
            case TYPE_NORMAL_NEWS:
                DBFeedItem feedItem = (DBFeedItem) listItems.get(position);
                ((CustomViewHolder) holder).bindData(feedItem);
                break;
            case TYPE_LOADMORE_FOOTER:
                break;
        }

    }


    // ************* View Holder *********** //
    class CustomViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;

        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
            this.textView = (TextView) view.findViewById(R.id.title);
        }

        public void bindData(final DBFeedItem data) {
            //Render image using Picasso library
            if (!TextUtils.isEmpty(data.getThumbnail())) {
                Picasso.with(mContext).load(data.getThumbnail())
                        .error(R.drawable.placeholder)
                        .placeholder(R.drawable.placeholder)
                        .into(imageView);
            }

            //Setting text view title
            textView.setText(Html.fromHtml(data.getTitle()));

            // Add listener
            if (handler != null) {
                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handler.onItemClick(data);
                    }
                };
                imageView.setOnClickListener(listener);
                textView.setOnClickListener(listener);
            }
        }
    }


    // ***** Getters and Setters ***** //
    public IDashBoardListHandler getHandler() {
        return handler;
    }

    public void setHandler(IDashBoardListHandler handler) {
        this.handler = handler;
    }
}