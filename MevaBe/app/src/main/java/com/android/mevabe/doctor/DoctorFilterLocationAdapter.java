package com.android.mevabe.doctor;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mevabe.R;
import com.android.mevabe.common.model.VaccinationsPlanModel;

import java.util.ArrayList;
import java.util.List;

import static com.android.mevabe.R.id.moreInfo;
import static com.android.mevabe.R.id.vaccinDes;
import static com.android.mevabe.R.id.vaccinName;

/**
 * DoctorFilterLocationAdapter controls view of list setting location for filtering
 */
public class DoctorFilterLocationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /**
     * IDoctorFilterLocationAdapter interface for callback
     */
    public interface IDoctorFilterLocationAdapter {
        void showVaccineInfo(VaccinationsPlanModel item);

        void addVaccinePlan(VaccinationsPlanModel item);
    }

    private IDoctorFilterLocationAdapter handler;

    protected List<VaccinationsPlanModel> listItems;
    protected Activity context;


    /**
     * Constructor
     *
     * @param context Context
     */
    public DoctorFilterLocationAdapter(Activity context, IDoctorFilterLocationAdapter handler) {
        this.listItems = new ArrayList<>();
        this.context = context;
        this.handler = handler;
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
        private TextView title;
        private ImageView tick;

        public MyViewHolder(View view) {
            super(view);
            this.tick = (ImageView) view.findViewById(R.id.tick);
            this.title = (TextView) view.findViewById(R.id.child_info);
        }

        public void bindData(final VaccinationsPlanModel data) {
            //Setting text view title
            childInfo.setText(data.getChildInfo());
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