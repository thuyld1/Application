package com.android.mevabe.vaccinations;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.mevabe.R;
import com.android.mevabe.common.Constants;
import com.android.mevabe.common.model.VaccinationsHistoryModel;
import com.android.mevabe.common.model.WebViewModel;
import com.android.mevabe.common.db.DBVacinations;
import com.android.mevabe.common.utils.DialogUtil;
import com.android.mevabe.common.view.BaseActivity;
import com.android.mevabe.common.view.InjectionStatusBox;
import com.android.mevabe.common.view.OnSwipeTouchListener;
import com.android.mevabe.common.view.WebViewActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by thuyld on 3/14/17.
 */
public class VaccinationsEditPlan extends BaseActivity implements View.OnClickListener {
    // Block vaccine information for child
    private TextView childInfo;
    private TextView vaccinName;
    private TextView moreInfo;
    private TextView vaccinPeriod;
    private TextView vaccinDes;

    // Block setting information
    private Button inDateBtn;
    private InjectionStatusBox inStatusOK;
    private InjectionStatusBox inStatusNA;
    private EditText inPlace;
    private EditText inNote;

    private DatePickerDialog datePicker;
    private Calendar calendar;
    private DateFormat dateFormat;

    // Block control buttons
    private Button btnCancel;
    private Button btnDelete;
    private Button btnAdd;

    // Data
    private VaccinationsHistoryModel data;
    private DBVacinations dbVacinations;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vaccinations_edit_plan);

        // Get data
        this.data = (VaccinationsHistoryModel) getIntent().getSerializableExtra
                (Constants.INTENT_DATA);

        // Finish screen if data invalid
        if (data == null) {
            finish();
            return;
        }

        // Build GUI for view
        buildGUI();

        // Bind data to view
        bindData();
    }

    /**
     * Build GUI for view
     */
    private void buildGUI() {
        // Set up toolbar
        setTitle(getString(R.string.vaccinations_add_screen_title));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Implement back button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        // Build swipe to close
        View conentView = findViewById(R.id.content_view);
        conentView.setOnTouchListener(new OnSwipeTouchListener(this));

        // Map view: vaccine information for child
        this.childInfo = (TextView) findViewById(R.id.child_info);
        this.vaccinName = (TextView) findViewById(R.id.vaccinName);
        this.moreInfo = (TextView) findViewById(R.id.moreInfo);
        this.vaccinPeriod = (TextView) findViewById(R.id.vaccinPeriod);
        this.vaccinDes = (TextView) findViewById(R.id.vaccinDes);

        // Map view: setting information
        this.inDateBtn = (Button) findViewById(R.id.in_date_btn);
        this.inStatusOK = (InjectionStatusBox) findViewById(R.id.in_status_ok);
        this.inStatusNA = (InjectionStatusBox) findViewById(R.id.in_status_na);
        this.inPlace = (EditText) findViewById(R.id.in_place);
        this.inNote = (EditText) findViewById(R.id.in_note);

        // Map view: control buttons
        this.btnCancel = (Button) findViewById(R.id.btn_cancel);
        this.btnDelete = (Button) findViewById(R.id.btn_delete);
        this.btnAdd = (Button) findViewById(R.id.btn_add);

        // Set up action control
        View.OnClickListener cancelListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
        toolbar.setNavigationOnClickListener(cancelListener);
        btnCancel.setOnClickListener(cancelListener);
        moreInfo.setOnClickListener(this);
        inDateBtn.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        inStatusOK.setOnClickListener(this);
        inStatusNA.setOnClickListener(this);

        // Set up select injection data
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(data.getInDate());
        dateFormat = new SimpleDateFormat(Constants.VACCINE_INJECTION_DATE_FORMAT);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        inDateBtn.setText(dateFormat.format(calendar.getTime()));
        DatePickerDialog.OnDateSetListener myDateListener = new
                DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker,
                                          int year, int month, int day) {
                        calendar.set(year, month, day);
                        onDateChange();
                    }
                };
        datePicker = new DatePickerDialog(this, myDateListener, year, month, day);
        dbVacinations = new DBVacinations();
    }

    /**
     * Bind data to view
     */
    private void bindData() {
        // Show  text vaccine information for child
        childInfo.setText(data.getChildInfo());
        vaccinName.setText(String.format(getString(R.string.vaccinations_name), data.getVaccineName()));
        vaccinPeriod.setText(String.format(getString(R.string.vaccinations_period), data.getVaccinePeriod()));
        vaccinDes.setText(data.getVaccineShortDes());

        // Set default status is not finish injection yet
        inStatusNA.bindData(Constants.VACCINE_INJECTION_STATUS_NA, data.getInDate());
        inStatusNA.setSelected(data.getInStatus() == Constants.VACCINE_INJECTION_STATUS_NA);
        inStatusOK.bindData(Constants.VACCINE_INJECTION_STATUS_OK, 0);
        inStatusOK.setSelected(data.getInStatus() == Constants.VACCINE_INJECTION_STATUS_OK);

        inPlace.setText(data.getInPlace());
        inNote.setText(data.getInNote());
    }

    /**
     * Update view when date change
     */
    private void onDateChange() {
        inDateBtn.setText(dateFormat.format(calendar.getTime()));
        inStatusNA.bindData(Constants.VACCINE_INJECTION_STATUS_NA, calendar.getTimeInMillis());
        childInfo.setText(data.getChildInfo(calendar.getTimeInMillis()));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    // ************ ACTION CONTROL ***************
    @Override
    public void onClick(View v) {
        // Case click to load more button
        if (v.equals(moreInfo)) {
            // Show details of vaccine in webview
            WebViewActivity act = new WebViewActivity();
            Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
            WebViewModel info = new WebViewModel(data.getVaccineName(), data.getVaccineURL());
            intent.putExtra(Constants.INTENT_DATA, info);
            startActivity(intent);

        } else if (v.equals(inDateBtn)) {
            // Show select date of birth dialog
            datePicker.show();
        } else if (v.equals(btnAdd)) {
            // Update data to DB
            updateVaccinePlan();
        } else if (v.equals(btnDelete)) {
            // Delete plan data
            deleteVaccinePlan();
        } else if (v.equals(inStatusNA)) {
            // Case user choose status not finish
            if (!inStatusNA.isSelected()) {
                inStatusNA.setSelected(true);
                inStatusOK.setSelected(false);
            }
        } else if (v.equals(inStatusOK)) {
            // Case user choose status not finish
            if (!inStatusOK.isSelected()) {
                inStatusNA.setSelected(false);
                inStatusOK.setSelected(true);
            }
        }
    }

    /**
     * Update vaccine plan for child
     */
    private void updateVaccinePlan() {
        // Update vaccine plan for child
        data.setInDate(calendar.getTimeInMillis());
        if (inStatusNA.isSelected()) {
            data.setInStatus(Constants.VACCINE_INJECTION_STATUS_NA);
        } else {
            data.setInStatus(Constants.VACCINE_INJECTION_STATUS_OK);
        }
        data.setInPlace(getTextString(inPlace));
        data.setInNote(getTextString(inNote));

        boolean result = dbVacinations.updateVaccinePlan(data);
        if (result) {
            // Show success message
            Toast.makeText(this, getString(R.string.vaccinations_edit_successful), Toast.LENGTH_SHORT).show();

            // Go back to list vaccine plan
            Intent returnIntent = new Intent();
            returnIntent.putExtra(Constants.INTENT_DATA, data);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        } else {
            // Show error message
            Toast.makeText(this, getString(R.string.vaccinations_edit_fail), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Delete vaccine plan for child
     */
    private void deleteVaccinePlan() {
        // Show confirm dialog
        String message = "Are you sure you want to delete this vaccine plan?";
        DialogUtil.showYesCancel(this, message, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Update vaccine plan for child
                boolean result = dbVacinations.deleteVaccinePlan(data);
                if (result) {
                    // Show success message
                    Toast.makeText(VaccinationsEditPlan.this, getString(R.string.vaccinations_edit_successful), Toast
                            .LENGTH_SHORT).show();

                    // Go back to list vaccine plan
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra(Constants.INTENT_DATA_DELETE, true);
                    returnIntent.putExtra(Constants.INTENT_DATA, data.getId());
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                } else {
                    // Show error message
                    Toast.makeText(VaccinationsEditPlan.this, getString(R.string.vaccinations_edit_fail), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    /**
     * Get text string from EditText
     *
     * @param editText EditText
     * @return String
     */
    private String getTextString(EditText editText) {
        String value = null;
        Editable editable = editText.getText();
        if (editable != null) {
            value = editable.toString().trim();
        }
        return value;
    }
}
