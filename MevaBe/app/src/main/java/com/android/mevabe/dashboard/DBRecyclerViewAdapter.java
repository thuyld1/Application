package com.android.mevabe.dashboard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mevabe.R;
import com.android.mevabe.view.LoadMoreFooter;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * DashBoardRecyclerAdapter controls view of list in DashBoard
 */
public class DBRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_NORMAL_NEWS = 0;
    public static final int TYPE_LOADMORE_FOOTER = 100;

    /**
     * IDashBoardListHandler interface for callback
     */
    public interface IDashBoardListHandler {
        void onItemClick(DBFeedItem item);
        void onClickToLoadMore();
    }

    private List<DBFeedItem> dbFeedItemList;
    private Context mContext;
    private IDashBoardListHandler handler;
    protected LoadMoreFooter loadMoreFooter;

    /**
     * Constructor
     *
     * @param context        Context
     * @param dbFeedItemList List<DBFeedItem>
     */
    public DBRecyclerViewAdapter(Context context, List<DBFeedItem> dbFeedItemList) {
        this.dbFeedItemList = dbFeedItemList;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case TYPE_NORMAL_NEWS:
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dashboard_item_update_info, null);
                CustomViewHolder viewItem = new CustomViewHolder(view);
                return viewItem;
            case TYPE_LOADMORE_FOOTER:
                LoadMoreFooter footer = new LoadMoreFooter(mContext);
                footer.setLayoutParams(
                        new FrameLayout.LayoutParams(
                                FrameLayout.LayoutParams.MATCH_PARENT,
                                FrameLayout.LayoutParams.WRAP_CONTENT
                        ));
                return new LoadMoreHolder(footer);
        }
        throw new IllegalArgumentException("onCreateViewHolder: Wrong type!");

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type) {
            case TYPE_NORMAL_NEWS:
                DBFeedItem feedItem = dbFeedItemList.get(position);
                ((CustomViewHolder) holder).bindData(feedItem);
                break;
            case TYPE_LOADMORE_FOOTER:
                loadMoreFooter = ((LoadMoreHolder) holder).loadMoreFooter;
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == dbFeedItemList.size()) {
            return TYPE_LOADMORE_FOOTER;
        } else {
            return TYPE_NORMAL_NEWS;
        }
    }

    @Override
    public int getItemCount() {
        return (null != dbFeedItemList ? dbFeedItemList.size() + 1 : 0);
    }

    // ***** View Holder ***** //
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

    class LoadMoreHolder extends RecyclerView.ViewHolder {
        private LoadMoreFooter loadMoreFooter;

        public LoadMoreHolder(View itemView) {
            super(itemView);
            loadMoreFooter = (LoadMoreFooter) itemView;
            loadMoreFooter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (handler != null) {
                        handler.onClickToLoadMore();
                    }
                }
            });
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