package com.android.mevabe.doctor;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mevabe.R;
import com.android.mevabe.common.RefreshLoadMoreAdapter;
import com.android.mevabe.common.model.DoctorInfo;
import com.android.mevabe.common.utils.StringUtils;
import com.android.mevabe.common.view.RoundedTransformation;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.android.mevabe.R.id.doctor_des;

/**
 * AdapterDoctorsSearch controls view of list search doctors
 */
public class AdapterDoctorsSearch extends RefreshLoadMoreAdapter {
    private IDoctorsHandler handler;


    /**
     * Constructor
     *
     * @param context Context
     */
    public AdapterDoctorsSearch(Activity context, IDoctorsHandler handler) {
        super(context);
        this.handler = handler;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Check to load load more view or not
        RecyclerView.ViewHolder view = super.onCreateViewHolder(viewGroup, viewType);
        if (view != null) {
            return view;
        }

        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                .doctors_search_item_view, viewGroup, false);
        view = new MyViewHolder(layout);
        return view;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DoctorInfo item = (DoctorInfo) listItems.get(position);
        if (item != null) {
            ((MyViewHolder) holder).bindData(item);
        }

    }


    public List<DoctorInfo> getListItems() {
        return listItems;
    }

    // ************* Update data *********** //

    /**
     * Refresh data by new list data
     */
    public void refreshItems(List result) {
        if (result != null) {
            listItems.clear();
            listItems.addAll(result);

            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    notifyDataSetChanged();
                }
            });
        }
    }

    // ************* View Holder *********** //
    class MyViewHolder extends RecyclerView.ViewHolder {
        private View layout;
        private ImageView avatar;
        private TextView name;
        private TextView des;
        private TextView phone;
        private ImageView btnCall;
        private ImageView btnFavorite;

        private DoctorInfo data;

        public MyViewHolder(View view) {
            super(view);
            this.layout = view;
            this.avatar = (ImageView) view.findViewById(R.id.avatar);
            this.name = (TextView) view.findViewById(R.id.doctor_name);
            this.phone = (TextView) view.findViewById(R.id.doctor_phone);
            this.des = (TextView) view.findViewById(doctor_des);
            this.btnCall = (ImageView) view.findViewById(R.id.btn_call);
            this.btnFavorite = (ImageView) view.findViewById(R.id.btn_favorite);
        }

        public void bindData(final DoctorInfo data) {
            this.data = data;

            //Setting text view title
            Picasso.with(context).load(data.getAvatar())
                    .error(R.drawable.common_placeholder)
                    .placeholder(R.drawable.common_placeholder)
                    .transform(new RoundedTransformation(5, 0))
                    .into(avatar);

            name.setText(data.getName());
            des.setText(data.getDes());

            // Bind favorite view
            updateFavorive(data.isFavorite());

            // Bind call phone view
            if (StringUtils.isEmpty(data.getPhone())) {
                btnCall.setVisibility(View.GONE);
                phone.setText(data.getPhone());
            } else {
                btnCall.setVisibility(View.VISIBLE);
                phone.setText(data.getPhone());
            }

            // Set up action listener
            if (handler != null) {
                View.OnClickListener itemClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handler.onclickItem(data);
                    }
                };
                avatar.setOnClickListener(itemClickListener);

                View.OnClickListener callListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handler.onClickCall(data);
                    }
                };
                btnCall.setOnClickListener(callListener);
                phone.setOnClickListener(callListener);

                btnFavorite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateFavorive(!data.isFavorite());
                        handler.onClickFavorite(data);
                    }
                });
            }
        }

        /**
         * Update favorite buttons view
         *
         * @param isFavorite boolean
         */
        private void updateFavorive(boolean isFavorite) {
            data.setFavorite(isFavorite);
            if (data.isFavorite()) {
                btnFavorite.setImageResource(R.drawable.ic_favorite_on);
            } else {
                btnFavorite.setImageResource(R.drawable.ic_favorite_off);
            }
        }
    }
}