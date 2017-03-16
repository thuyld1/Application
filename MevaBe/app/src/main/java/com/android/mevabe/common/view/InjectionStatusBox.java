package com.android.mevabe.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mevabe.R;
import com.android.mevabe.common.Constants;

import java.util.Calendar;

/**
 * Created by leducthuy on 3/16/17.
 */
public class InjectionStatusBox extends FrameLayout {
    private View layout;
    private ImageView statusThumb;
    private TextView inDayCountDown;
    private TextView inTitleCountDown;

    public InjectionStatusBox(Context context) {
        super(context);

        // Build GUI for view
        buildGUI();
    }

    public InjectionStatusBox(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Build GUI for view
        buildGUI();
    }

    public InjectionStatusBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // Build GUI for view
        buildGUI();
    }

    public InjectionStatusBox(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        // Build GUI for view
        buildGUI();
    }

    /**
     * Build GUI for view
     */
    private void buildGUI() {
        // Load layer for view
        LayoutInflater inflater = LayoutInflater.from(getContext());
        layout = inflater.inflate(R.layout.view_injection_status_box, this, false);
        addView(layout);

        // Bind view
        statusThumb = (ImageView) layout.findViewById(R.id.status_thumb);
        inDayCountDown = (TextView) layout.findViewById(R.id.in_day_countdown);
        inTitleCountDown = (TextView) layout.findViewById(R.id.in_title_countdown);

        // Default status is selected
        super.setSelected(true);
    }

    /**
     * Bind view for data
     *
     * @param status int
     * @param date   long
     */
    public void bindData(int status, long date) {
        // Case status is OK
        if (status == Constants.VACCINE_INJECTION_STATUS_OK) {
            statusThumb.setImageResource(R.drawable.vaccinations_history_ok);
            statusThumb.setVisibility(View.VISIBLE);
            inDayCountDown.setVisibility(View.GONE);
            inTitleCountDown.setText(R.string.vaccinations_injection_ok);
        } else if (date > 0) {
            // Base on injection date to show status
            Calendar current = Calendar.getInstance();
            Calendar inDate = Calendar.getInstance();
            inDate.setTimeInMillis(date);

            // Case current is over injection date
            if (current.after(inDate)) {
                statusThumb.setImageResource(R.drawable.vaccinations_history_over);
                statusThumb.setVisibility(View.VISIBLE);
                inDayCountDown.setVisibility(View.GONE);
                inTitleCountDown.setText(R.string.vaccinations_injection_over);
            } else {
                statusThumb.setVisibility(View.GONE);
                inDayCountDown.setVisibility(View.VISIBLE);
                inTitleCountDown.setText(R.string.vaccinations_injection_na);

                // Calculate day count down
                long countDown = inDate.getTimeInMillis() - current.getTimeInMillis();
                inDayCountDown.setText("" + (countDown / (24 * 60 * 60 * 1000)));
            }

        } else {
            statusThumb.setImageResource(R.drawable.vaccinations_history_na);
            statusThumb.setVisibility(View.VISIBLE);
            inDayCountDown.setVisibility(View.GONE);
            inTitleCountDown.setText(R.string.vaccinations_injection_na);
        }
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);

        // Change background color by selected flag
        if (selected) {
            layout.setBackgroundResource(R.drawable.bg_rounded_corners);
        } else {
            layout.setBackgroundResource(R.drawable.bg_rounded_corners_gray);
        }
    }
}
