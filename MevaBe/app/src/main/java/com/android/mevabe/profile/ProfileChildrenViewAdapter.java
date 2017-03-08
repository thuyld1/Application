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
import com.android.mevabe.model.ProfileChildModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * ProfileChildrenViewAdapter controls view of list children in profile
 */
public class ProfileChildrenViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected List<ProfileChildModel> listItems;
    protected Activity context;

    /**
     * Constructor
     *
     * @param context Context
     */
    public ProfileChildrenViewAdapter(Activity context) {
        this.listItems = new ArrayList<>();
        this.context = context;
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
     * Update data when load more has finished
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


    // ************* View Holder *********** //
    class ChildViewHolder extends RecyclerView.ViewHolder {
        private ImageView childGender;
        private TextView title;
        private TextView description;

        public ChildViewHolder(View view) {
            super(view);
            this.childGender = (ImageView) view.findViewById(R.id.child_gender);
            this.title = (TextView) view.findViewById(R.id.title);
            this.description = (TextView) view.findViewById(R.id.description);
        }

        public void bindData(final ProfileChildModel data) {
            // Set child gender
            if (data.getGender() == Constants.GENDER_FEMALE) {
                childGender.setImageResource(R.drawable.profile_gender_female);
            } else {
                childGender.setImageResource(R.drawable.profile_gender_male);
            }

            //Setting text view title
            title.setText(data.getName());

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(data.getDateOfBirth());
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            description.setText(df.format(cal.getTime()));
        }
    }
}