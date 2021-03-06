package com.android.mevabe.vaccinations;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import com.android.mevabe.common.model.VaccinationsPlanModel;
import com.android.mevabe.common.model.WebViewModel;
import com.android.mevabe.common.db.DBVacinations;
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
public class VaccinationsAddPlan extends BaseActivity implements View.OnClickListener {
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
    private Button btnAdd;

    // Data
    private VaccinationsPlanModel data;
    private DBVacinations dbVacinations;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vaccinations_add_plan);

        // Get data
        this.data = (VaccinationsPlanModel) getIntent().getSerializableExtra
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
        inStatusOK.setOnClickListener(this);
        inStatusNA.setOnClickListener(this);

        // Set up select injection data
        calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
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
        childInfo.setText(data.getChildInfo(calendar.getTimeInMillis()));
        vaccinName.setText(String.format(getString(R.string.vaccinations_name), data.getVaccinName()));
        vaccinPeriod.setText(String.format(getString(R.string.vaccinations_period), data.getVaccinPeriod()));
        vaccinDes.setText(data.getVaccinDes());

        // Set default status is not finish injection yet
        inStatusNA.bindData(Constants.VACCINE_INJECTION_STATUS_NA, calendar.getTimeInMillis());
        inStatusOK.bindData(Constants.VACCINE_INJECTION_STATUS_OK, 0);
        inStatusOK.setSelected(false);
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
            WebViewModel info = new WebViewModel(data.getVaccinName(), data.getVaccinURL());
            intent.putExtra(Constants.INTENT_DATA, info);
            startActivity(intent);

        } else if (v.equals(inDateBtn)) {
            // Show select date of birth dialog
            datePicker.show();
        } else if (v.equals(btnAdd)) {
            // Add data to DB
            addVaccinePlan();
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
     * Add vaccine plan for child
     */
    private void addVaccinePlan() {
        // Add vaccine plan for child
        String place = getTextString(inPlace);
        String note = getTextString(inNote);
        int inStatus = Constants.VACCINE_INJECTION_STATUS_OK;
        if (inStatusNA.isSelected()) {
            inStatus = Constants.VACCINE_INJECTION_STATUS_NA;
        }
        boolean result = dbVacinations.addVaccinePlan(data.getPlanID(), data.getChild().getId(), data.getVaccinID(), calendar
                .getTimeInMillis(), inStatus, place, note);
        if (result) {
            // Show success message
            Toast.makeText(this, getString(R.string.vaccinations_add_successful), Toast.LENGTH_SHORT).show();

            // Go back to list vaccine plan
            Intent returnIntent = new Intent();
            returnIntent.putExtra(Constants.INTENT_DATA, data.getPlanID());
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        } else {
            // Show error message
            Toast.makeText(this, getString(R.string.vaccinations_add_fail), Toast.LENGTH_SHORT).show();
        }
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
