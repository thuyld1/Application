package com.android.mevabe.doctor;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mevabe.R;
import com.android.mevabe.common.model.Specialization;
import com.android.mevabe.common.utils.PrefUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * AdapterLocationProvince controls view of list setting location for filtering
 */
public class AdapterSpecialization extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Specialization> listItems;
    private Activity context;

    /**
     * Constructor
     *
     * @param context Context
     */
    public AdapterSpecialization(Activity context) {
        this.listItems = new ArrayList<>();
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                .doctors_filter_location_item, viewGroup, false);
        RecyclerView.ViewHolder view = new MyViewHolder(layout);
        return view;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Specialization item = (Specialization) listItems.get(position);
        if (item != null) {
            ((MyViewHolder) holder).bindData(item);
        }

    }

    @Override
    public int getItemCount() {
        return (null != listItems ? listItems.size() : 0);
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

    /**
     * Save selected items
     *
     * @return Set<String>
     */
    public void saveSelectedItems() {
        Set<String> selected = new HashSet<>();
        StringBuilder sb = new StringBuilder();
        for (Specialization item : listItems) {
            if (item.isSelected()) {
                selected.add(String.valueOf(item.getCode()));
                sb.append(item.getTitle()).append("\n");
            }
        }

        PrefUtil.writeList(DoctorsFilterSetting.FILTER_SPECIALIZATION_VALUE, selected);
        PrefUtil.writeString(DoctorsFilterSetting.FILTER_SPECIALIZATION_TITLE, sb.toString());
    }

    // ************* View Holder *********** //
    class MyViewHolder extends RecyclerView.ViewHolder {
        private View layout;
        private TextView title;
        private ImageView tick;

        public MyViewHolder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.title);
            this.tick = (ImageView) view.findViewById(R.id.tick);
            this.layout = view;

        }

        public void bindData(final Specialization data) {
            // Show title
            title.setText(data.getTitle());

            // ON/OFF tick
            if (data.isSelected()) {
                tick.setVisibility(View.VISIBLE);
                title.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            } else {
                tick.setVisibility(View.INVISIBLE);
                title.setTextColor(context.getResources().getColor(R.color.textColor));
            }

            // Add listener
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Update data and view
                    data.setSelected(!data.isSelected());
                    notifyDataSetChanged();
                }
            });


        }
    }
}