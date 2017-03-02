package com.android.mevabe.vaccinations;

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
 * VaccinationsPlanAdapter controls view of list "Lịch tiêm"
 */
public class VaccinationsPlanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected List<VaccinationsPlanModel> listItems;
    protected Activity context;

    /**
     * Constructor
     *
     * @param context Context
     */
    public VaccinationsPlanAdapter(Activity context) {
        this.listItems = new ArrayList<>();
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                .vaccinations_plan_item_view, null);
        RecyclerView.ViewHolder view = new MyViewHolder(layout);
        return view;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VaccinationsPlanModel item = (VaccinationsPlanModel) listItems.get(position);
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
    public synchronized void appendItem(VaccinationsPlanModel result) {
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
        private TextView vaccinName;
        private TextView vaccinPeriod;
        private TextView vaccinDes;

        public MyViewHolder(View view) {
            super(view);
            this.childInfo = (TextView) view.findViewById(R.id.child_info);
            this.vaccinName = (TextView) view.findViewById(R.id.vaccinName);
            this.vaccinPeriod = (TextView) view.findViewById(R.id.vaccinPeriod);
            this.vaccinDes = (TextView) view.findViewById(R.id.vaccinDes);
        }

        public void bindData(final VaccinationsPlanModel data) {
            //Setting text view title
            childInfo.setText(data.getChildInfo());
            vaccinName.setText(data.getVaccinName());
            vaccinPeriod.setText(data.getVaccinPeriod());
            vaccinDes.setText(data.getVaccinDes());

        }
    }
}