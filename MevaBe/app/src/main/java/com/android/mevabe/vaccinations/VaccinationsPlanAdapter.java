package com.android.mevabe.vaccinations;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mevabe.R;
import com.android.mevabe.model.VaccinationsPlanModel;

import java.util.ArrayList;
import java.util.List;

/**
 * VaccinationsPlanAdapter controls view of list "Lịch tiêm"
 */
public class VaccinationsPlanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /**
     * IVaccinationsPlanHandler interface for callback
     */
    public interface IVaccinationsPlanHandler {
        void showVaccineInfo(VaccinationsPlanModel item);

        void addVaccinePlan(VaccinationsPlanModel item);
    }

    private IVaccinationsPlanHandler handler;

    protected List<VaccinationsPlanModel> listItems;
    protected Activity context;

    private String vaccinFormat;
    private String vaccinPeriodFormat;

    /**
     * Constructor
     *
     * @param context Context
     */
    public VaccinationsPlanAdapter(Activity context, IVaccinationsPlanHandler handler) {
        this.listItems = new ArrayList<>();
        this.context = context;
        this.handler = handler;

        vaccinFormat = context.getString(R.string.vaccinations_name);
        vaccinPeriodFormat = context.getString(R.string.vaccinations_period);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                .vaccinations_plan_item_view, viewGroup, false);
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
        private ImageView addVaccinePlan;
        private TextView childInfo;
        private TextView vaccinName;
        private TextView moreInfo;
        private TextView vaccinPeriod;
        private TextView vaccinDes;

        public MyViewHolder(View view) {
            super(view);
            this.addVaccinePlan = (ImageView) view.findViewById(R.id.add_vaccine_plan);
            this.childInfo = (TextView) view.findViewById(R.id.child_info);
            this.vaccinName = (TextView) view.findViewById(R.id.vaccinName);
            this.moreInfo = (TextView) view.findViewById(R.id.moreInfo);
            this.vaccinPeriod = (TextView) view.findViewById(R.id.vaccinPeriod);
            this.vaccinDes = (TextView) view.findViewById(R.id.vaccinDes);
        }

        public void bindData(final VaccinationsPlanModel data) {
            //Setting text view title
            childInfo.setText(data.getChildInfo());
            vaccinName.setText(String.format(vaccinFormat, data.getVaccinName()));
            vaccinPeriod.setText(String.format(vaccinPeriodFormat, data.getVaccinPeriod()));
            vaccinDes.setText(data.getVaccinDes());

            // Add listener
            if (handler != null) {
                // Set "Show vaccine information" listener
                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handler.showVaccineInfo(data);
                    }
                };
                vaccinName.setOnClickListener(listener);
                moreInfo.setOnClickListener(listener);

                // Set "Add add vaccine plan for child" listener
                View.OnClickListener addVaccineListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handler.addVaccinePlan(data);
                    }
                };
                addVaccinePlan.setOnClickListener(addVaccineListener);
            }

        }
    }
}