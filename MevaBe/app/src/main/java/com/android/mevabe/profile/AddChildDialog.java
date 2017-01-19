package com.android.mevabe.profile;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.android.mevabe.R;

import java.util.Calendar;

/**
 * Created by thuyld on 1/18/17.
 */
public class AddChildDialog extends Dialog implements android.view.View.OnClickListener {
    private Button addButton;
    private Button cancelButton;

    private EditText childName;
    private Button childDateOfBirth;
    private DatePickerDialog datePicker;
    private Calendar calendar;

    private ToggleButton childGender;

    /**
     * Constructor
     *
     * @param context
     */
    public AddChildDialog(Context context) {
        super(context);

        // Init custom dialog view
        initView();
    }


    private void initView() {
        // Set title
        setTitle("Add child!");
        setCanceledOnTouchOutside(false);

        // Set layout
        setContentView(R.layout.profile_add_child_dialog);

        // Bind button controls
        addButton = (Button) findViewById(R.id.dialog_add);
        cancelButton = (Button) findViewById(R.id.dialog_cancel);
        addButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        // Bind input view
        childName = (EditText) findViewById(R.id.child_name);
        childDateOfBirth = (Button) findViewById(R.id.child_date_of_birth);

        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);
        DatePickerDialog.OnDateSetListener myDateListener = new
                DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker,
                                          int year, int month, int day) {
                        showDate(year, month + 1, day);
                    }
                };
        datePicker = new DatePickerDialog(getContext(), myDateListener, year, month, day);
        datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        childDateOfBirth.setOnClickListener(this);


        childGender = (ToggleButton) findViewById(R.id.child_gender);
        childGender.setTextOff(getContext().getText(R.string.child_gender_female));
        childGender.setTextOn(getContext().getText(R.string.child_gender_male));
        childGender.setChecked(false);

        // Add validate
        addButton.setEnabled(false);
        float alpha = 0.45f;
        AlphaAnimation alphaUp = new AlphaAnimation(alpha, alpha);
        alphaUp.setFillAfter(true);
        addButton.startAnimation(alphaUp);
        childName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // Disable add button if invalid
                if (s.toString().trim().length() == 0) {
                    float alpha = 0.45f;
                    AlphaAnimation alphaUp = new AlphaAnimation(alpha, alpha);
                    alphaUp.setFillAfter(true);
                    addButton.startAnimation(alphaUp);

                    addButton.setEnabled(false);
//                    addButton.getBackground().setAlpha(128);
                } else {
                    addButton.setEnabled(true);
//                    addButton.getBackground().setAlpha(255);
                    float alpha = 1f;
                    AlphaAnimation alphaUp = new AlphaAnimation(alpha, alpha);
                    alphaUp.setFillAfter(true);
                    addButton.startAnimation(alphaUp);
                }
            }
        });

    }

    /**
     * View selected date
     *
     * @param year
     * @param month
     * @param day
     */
    private void showDate(int year, int month, int day) {
        childDateOfBirth.setText(String.format("%02d/%02d/%4d", day, month, year));
    }

    @Override
    public void onClick(View v) {
        if (v.equals(childDateOfBirth)) {
            // Show select date of birth dialog
            datePicker.show();

        } else {
            dismiss();
        }
    }


}