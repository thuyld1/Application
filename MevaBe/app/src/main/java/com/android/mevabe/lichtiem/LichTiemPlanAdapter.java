package com.android.mevabe.lichtiem;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.mevabe.R;

import java.util.ArrayList;
import java.util.List;

/**
 * LichTiemPlanAdapter controls view of list "Lịch tiêm"
 */
public class LichTiemPlanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected List<LichTiemModel> listItems;
    protected Activity context;

    /**
     * Constructor
     *
     * @param context Context
     */
    public LichTiemPlanAdapter(Activity context) {
        this.listItems = new ArrayList<>();
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                .lich_tiem_plan_item_view, null);
        RecyclerView.ViewHolder view = new MyViewHolder(layout);
        return view;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        LichTiemModel item = (LichTiemModel) listItems.get(position);
        if (item != null) {
            ((MyViewHolder) holder).bindData(item);
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

            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    notifyDataSetChanged();
                }
            });
        }
    }

    /**
     * Update data when load more has finished
     */
    public synchronized void appendItem(LichTiemModel result) {
        if (result != null) {
            listItems.add(result);
        }
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }


    // ************* View Holder *********** //
    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView childInfo;
        private TextView vacxinName;
        private TextView vacxinPeriod;
        private TextView vacxinDes;

        public MyViewHolder(View view) {
            super(view);
            this.childInfo = (TextView) view.findViewById(R.id.child_info);
            this.vacxinName = (TextView) view.findViewById(R.id.vacxinName);
            this.vacxinPeriod = (TextView) view.findViewById(R.id.vacxinPeriod);
            this.vacxinDes = (TextView) view.findViewById(R.id.vacxinDes);
        }

        public void bindData(final LichTiemModel data) {
            //Setting text view title
            childInfo.setText(data.getChildInfo());
            vacxinName.setText(data.getVacxinName());
            vacxinPeriod.setText(data.getVacxinPeriod());
            vacxinDes.setText(data.getVacxinDes());

        }
    }
}