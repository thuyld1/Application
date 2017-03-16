package com.android.mevabe.common.model;

import java.io.Serializable;

/**
 * Created by leducthuy on 3/1/17.
 */
public class VaccinationsHistoryModel extends BaseModel implements Serializable {
    private ProfileChildModel child;

    private long historyID;
    private long inDate;
    private int inStatus;
    private String inPlace;
    private String inNote;
    private int status;

    private long vaccineID;
    private long vaccineCode;
    private String vaccineName;
    private String vaccinePeriod;
    private String vaccineShortDes;
    private String vaccineURL;


    /**
     * Constructor
     *
     * @param child ProfileChildModel
     */
    public VaccinationsHistoryModel(ProfileChildModel child) {
        this.child = child;
    }

    public String getChildInfo() {
        String childInfo = "";
        if (child != null) {
            childInfo = String.format("%s (%s)", child.getName(), child.getYearsOld());
        }

        return childInfo;
    }

    public ProfileChildModel getChild() {
        return child;
    }

    public void setChild(ProfileChildModel child) {
        this.child = child;
    }

    public long getHistoryID() {
        return historyID;
    }

    public void setHistoryID(long historyID) {
        this.historyID = historyID;
    }

    public long getInDate() {
        return inDate;
    }

    public void setInDate(long inDate) {
        this.inDate = inDate;
    }

    public String getInNote() {
        return inNote;
    }

    public void setInNote(String inNote) {
        this.inNote = inNote;
    }

    public String getInPlace() {
        return inPlace;
    }

    public void setInPlace(String inPlace) {
        this.inPlace = inPlace;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getVaccineCode() {
        return vaccineCode;
    }

    public void setVaccineCode(long vaccineCode) {
        this.vaccineCode = vaccineCode;
    }

    public long getVaccineID() {
        return vaccineID;
    }

    public void setVaccineID(long vaccineID) {
        this.vaccineID = vaccineID;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public String getVaccinePeriod() {
        return vaccinePeriod;
    }

    public void setVaccinePeriod(String vaccinePeriod) {
        this.vaccinePeriod = vaccinePeriod;
    }

    public String getVaccineShortDes() {
        return vaccineShortDes;
    }

    public void setVaccineShortDes(String vaccineShortDes) {
        this.vaccineShortDes = vaccineShortDes;
    }

    public String getVaccineURL() {
        return vaccineURL;
    }

    public void setVaccineURL(String vaccineURL) {
        this.vaccineURL = vaccineURL;
    }

    public int getInStatus() {
        return inStatus;
    }

    public void setInStatus(int inStatus) {
        this.inStatus = inStatus;
    }
}

