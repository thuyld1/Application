package com.android.mevabe.doctor;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mevabe.R;
import com.android.mevabe.common.model.DoctorInfo;
import com.android.mevabe.common.model.VaccinationsPlanModel;
import com.android.mevabe.common.view.RoundedTransformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.android.mevabe.R.id.doctor_des;

/**
 * AdapterDoctorsSearch controls view of list search doctors
 */
public class AdapterDoctorsSearch extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /**
     * IVaccinationsPlanHandler interface for callback
     */
    public interface IVaccinationsPlanHandler {
        void showVaccineInfo(VaccinationsPlanModel item);

        void addVaccinePlan(VaccinationsPlanModel item);
    }

    private IVaccinationsPlanHandler handler;

    protected List<DoctorInfo> listItems;
    protected Activity context;


    /**
     * Constructor
     *
     * @param context Context
     */
    public AdapterDoctorsSearch(Activity context, IVaccinationsPlanHandler handler) {
        this.listItems = new ArrayList<>();
        this.context = context;
        this.handler = handler;
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
        DoctorInfo item = (DoctorInfo) listItems.get(position);
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
     * Update data when load more has finished
     */
    public synchronized void appendItem(DoctorInfo result) {
        if (result != null) {
            listItems.add(result);
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    notifyDataSetChanged();
                }
            });
        }
    }

    /**
     * Remove one item of list
     *
     * @param planID long
     */
    public void removeItem(long planID) {
        if (planID > 0) {
            Iterator<DoctorInfo> iterator = listItems.iterator();
            DoctorInfo plan = null;
            while (iterator.hasNext()) {
                plan = iterator.next();
//                if (plan.getPlanID() == planID) {
//                    iterator.remove();
//                    break;
//                }
            }

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
        private ImageView avatar;
        private TextView name;
        private TextView des;
        private ImageView btnCall;
        private ImageView btnFavorite;

        public MyViewHolder(View view) {
            super(view);
            this.avatar = (ImageView) view.findViewById(R.id.avatar);
            this.name = (TextView) view.findViewById(R.id.doctor_name);
            this.des = (TextView) view.findViewById(doctor_des);
            this.btnCall = (ImageView) view.findViewById(R.id.btn_call);
            this.btnFavorite = (ImageView) view.findViewById(R.id.btn_favorite);
        }

        public void bindData(final DoctorInfo data) {
            //Setting text view title
            Picasso.with(context).load(data.getAvatar())
                    .error(R.drawable.common_placeholder)
                    .placeholder(R.drawable.common_placeholder)
                    .transform(new RoundedTransformation(5, 0))
                    .into(avatar);

            name.setText(data.getName());
            des.setText(data.getDes());


        }
    }
}