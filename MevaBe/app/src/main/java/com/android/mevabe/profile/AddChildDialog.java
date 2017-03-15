package com.android.mevabe.profile;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.android.mevabe.R;
import com.android.mevabe.common.Constants;
import com.android.mevabe.common.model.ProfileChildModel;

import java.util.Calendar;

/**
 * Created by thuyld on 1/18/17.
 */
public class AddChildDialog extends Dialog implements android.view.View.OnClickListener {
    /**
     * IAddChildDialogCallback interface for AddChildDialog call back
     */
    public interface IAddChildDialogCallback {
        void onAddChildFinish(ProfileChildModel child);
    }

    private IAddChildDialogCallback handler;

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
     * @param context Context
     * @param handler IEditChildDialogCallback
     */
    public AddChildDialog(Context context, IAddChildDialogCallback handler) {
        super(context);
        this.handler = handler;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init custom dialog view
        initView();

        // Show keyboard to input
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    /**
     * Init view for dialog
     */
    private void initView() {
        // Set title
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
                        calendar.set(year, month, day);
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
        addButton.getBackground().setAlpha(128);
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
                    addButton.setEnabled(false);
                    addButton.getBackground().setAlpha(128);
                } else {
                    addButton.setEnabled(true);
                    addButton.getBackground().setAlpha(255);
                }
            }
        });

    }

    /**
     * View selected date
     *
     * @param year  int
     * @param month int
     * @param day   int
     */
    private void showDate(int year, int month, int day) {
        childDateOfBirth.setText(String.format("%02d/%02d/%4d", day, month, year));
    }

    @Override
    public void onClick(View v) {
        if (v.equals(childDateOfBirth)) {
            // Show select date of birth dialog
            datePicker.show();

        } else if (v.equals(addButton)) {
            // Add child processing
            addChild();

            // Close dialog
            dismiss();
            addButton.getBackground().setAlpha(255);
        } else if (v.equals(cancelButton)) {
            // Close dialog
            dismiss();
            addButton.getBackground().setAlpha(255);
        }
    }

    /**
     * Add child processing
     */
    private void addChild() {
        if (handler != null) {
            // Get child information
            ProfileChildModel child = new ProfileChildModel();
            child.setName(childName.getText().toString().trim());
            child.setDateOfBirth(calendar.getTimeInMillis());
            if (childGender.isChecked()) {
                child.setGender(Constants.GENDER_MALE);
            } else {
                child.setGender(Constants.GENDER_FEMALE);
            }

            // Call back to process
            handler.onAddChildFinish(child);
        }

    }


}
