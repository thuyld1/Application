package com.android.mevabe.dashboard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mevabe.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * DashBoardRecyclerAdapter controls view of list in DashBoard
 */
public class DBRecyclerViewAdapter extends RecyclerView.Adapter<DBRecyclerViewAdapter.CustomViewHolder> {
    /**
     * IDashBoardListHandler interface for callback
     */
    public interface IDashBoardListHandler {
        void onItemClick(DBFeedItem item);
    }

    private List<DBFeedItem> DBFeedItemList;
    private Context mContext;
    private IDashBoardListHandler handler;

    /**
     * Constructor
     *
     * @param context        Context
     * @param DBFeedItemList List<DBFeedItem>
     */
    public DBRecyclerViewAdapter(Context context, List<DBFeedItem> DBFeedItemList) {
        this.DBFeedItemList = DBFeedItemList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dashboard_item, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        final DBFeedItem feedItem = DBFeedItemList.get(i);

        //Render image using Picasso library
        if (!TextUtils.isEmpty(feedItem.getThumbnail())) {
            Picasso.with(mContext).load(feedItem.getThumbnail())
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(customViewHolder.imageView);
        }

        //Setting text view title
        customViewHolder.textView.setText(Html.fromHtml(feedItem.getTitle()));

        // Add listener
        if (handler != null) {
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handler.onItemClick(feedItem);
                }
            };
            customViewHolder.imageView.setOnClickListener(listener);
            customViewHolder.textView.setOnClickListener(listener);
        }
    }

    @Override
    public int getItemCount() {
        return (null != DBFeedItemList ? DBFeedItemList.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView textView;

        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
            this.textView = (TextView) view.findViewById(R.id.title);
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