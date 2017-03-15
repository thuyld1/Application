package com.android.mevabe.profile;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mevabe.R;
import com.android.mevabe.common.Constants;
import com.android.mevabe.common.model.ProfileChildModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * ProfileChildrenViewAdapter controls view of list children in profile
 */
public class ProfileChildrenViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /**
     * IProfileChildrenViewHandler interface for item listener
     */
    public interface IProfileChildrenViewHandler {
        void onItemDelete(ProfileChildModel child);

        void onItemEdit(ProfileChildModel child);
    }

    protected List<ProfileChildModel> listItems;
    protected Activity context;
    private IProfileChildrenViewHandler handler;

    /**
     * Constructor
     *
     * @param context Context
     * @param handler IProfileChildrenViewHandler
     */
    public ProfileChildrenViewAdapter(Activity context, IProfileChildrenViewHandler handler) {
        this.listItems = new ArrayList<>();
        this.context = context;
        this.handler = handler;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                .profile_child_item_view, viewGroup, false);
        RecyclerView.ViewHolder view = new ChildViewHolder(layout);
        return view;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ProfileChildModel item = (ProfileChildModel) listItems.get(position);
        if (item != null) {
            ((ChildViewHolder) holder).bindData(item);
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
     * Update data when add more item
     */
    public synchronized void appendItem(ProfileChildModel result) {
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

    /**
     * Update data when remove an item
     */
    public synchronized void removeItem(ProfileChildModel result) {
        if (result != null) {
            listItems.remove(result);
        }
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }


    // ************* View Holder *********** //
    class ChildViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ProfileChildModel data;
        private ImageView childGender;
        private TextView childName;
        private TextView childDateOfBirth;
        private TextView childYearsOld;
        private ImageView btnDelete;
        private ImageView btnEdit;

        private SimpleDateFormat dateFormat;
        private Calendar cal = Calendar.getInstance();

        public ChildViewHolder(View view) {
            super(view);
            this.childGender = (ImageView) view.findViewById(R.id.child_gender);
            this.childName = (TextView) view.findViewById(R.id.child_name);
            this.childDateOfBirth = (TextView) view.findViewById(R.id.child_date_of_birth);
            this.childYearsOld = (TextView) view.findViewById(R.id.child_years_old);
            this.btnDelete = (ImageView) view.findViewById(R.id.child_delete);
            this.btnEdit = (ImageView) view.findViewById(R.id.child_edit);

            dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        }

        public void bindData(final ProfileChildModel data) {
            this.data = data;

            // Set child gender
            if (data.getGender() == Constants.GENDER_FEMALE) {
                childGender.setImageResource(R.drawable.profile_gender_female);
            } else {
                childGender.setImageResource(R.drawable.profile_gender_male);
            }

            //Setting text view title
            childName.setText(data.getName());
            cal.setTimeInMillis(data.getDateOfBirth());
            childDateOfBirth.setText(dateFormat.format(cal.getTime()));
            childYearsOld.setText(data.getYearsOld());

            // Add action listener
            btnDelete.setOnClickListener(this);
            btnEdit.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (handler != null) {
                if (v.equals(btnDelete)) {
                    handler.onItemDelete(data);
                } else if (v.equals(btnEdit)) {
                    handler.onItemEdit(data);
                }
            }

        }
    }
}