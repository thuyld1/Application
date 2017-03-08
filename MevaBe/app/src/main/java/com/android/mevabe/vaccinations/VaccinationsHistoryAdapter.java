package com.android.mevabe.vaccinations;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mevabe.R;
import com.android.mevabe.common.Constants;
import com.android.mevabe.model.VaccinationsHistoryModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * VaccinationsHistoryAdapter controls view of list "Chọn lịch tiêm" tab
 */
public class VaccinationsHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected List<VaccinationsHistoryModel> listItems;
    protected Activity context;

    private SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    private String vaccinInjectionDateFormat;

    /**
     * Constructor
     *
     * @param context Context
     */
    public VaccinationsHistoryAdapter(Activity context) {
        this.listItems = new ArrayList<>();
        this.context = context;

        vaccinInjectionDateFormat = context.getString(R.string.vaccinations_injection_date);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                .vaccinations_history_item_view, viewGroup, false);
        RecyclerView.ViewHolder view = new MyViewHolder(layout);
        return view;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VaccinationsHistoryModel item = (VaccinationsHistoryModel) listItems.get(position);
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
    public synchronized void appendItem(VaccinationsHistoryModel result) {
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
        private ImageView vaccinationsStatus;
        private TextView childInfo;
        private TextView vaccinName;
        private TextView injectionDate;
        private TextView vaccinDes;

        public MyViewHolder(View view) {
            super(view);
            this.vaccinationsStatus = (ImageView) view.findViewById(R.id.vaccinationsStatus);
            this.childInfo = (TextView) view.findViewById(R.id.child_info);
            this.vaccinName = (TextView) view.findViewById(R.id.vaccinName);
            this.injectionDate = (TextView) view.findViewById(R.id.injectionDate);
            this.vaccinDes = (TextView) view.findViewById(R.id.vaccinDes);
        }

        public void bindData(final VaccinationsHistoryModel data) {
            // Setting vaccinations status
            switch (data.getInjectionStatus()) {
                case Constants.VACCIN_INJECTION_DATE_INPROGRESS:
                    String formatNA = context.getString(R.string.vaccinations_injection_na);
                    vaccinName.setText(String.format(formatNA, data.getVaccinName()));
                    vaccinationsStatus.setImageResource(R.drawable.vaccinations_history_na);
                    break;
                case Constants.VACCIN_INJECTION_DATE_OK:
                    String formatOK = context.getString(R.string.vaccinations_injection_ok);
                    vaccinName.setText(String.format(formatOK, data.getVaccinName()));
                    vaccinationsStatus.setImageResource(R.drawable.vaccinations_history_ok);
                    break;
                case Constants.VACCIN_INJECTION_DATE_OVER:
                    String formatOver = context.getString(R.string.vaccinations_injection_over);
                    vaccinName.setText(String.format(formatOver, data.getVaccinName()));
                    vaccinationsStatus.setImageResource(R.drawable.vaccinations_history_over);
                    break;
                default:
                    String format = context.getString(R.string.vaccinations_injection_na);
                    vaccinName.setText(String.format(format, data.getVaccinName()));
                    vaccinationsStatus.setImageResource(R.drawable.vaccinations_history_na);
                    break;
            }

            //Setting text view title
            childInfo.setText(data.getChildInfo());
            vaccinDes.setText(data.getVaccinDes());

            if (data.getInjectionDate() > 0) {
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(data.getInjectionDate());
                injectionDate.setText(String.format(vaccinInjectionDateFormat, df.format(cal.getTime())));
            }
        }
    }
}