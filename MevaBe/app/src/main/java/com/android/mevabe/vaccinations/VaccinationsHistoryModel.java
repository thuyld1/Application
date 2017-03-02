package com.android.mevabe.vaccinations;

import com.android.mevabe.model.BaseModel;
import com.android.mevabe.model.ProfileChildModel;

import java.io.Serializable;

/**
 * Created by leducthuy on 3/1/17.
 */
public class VaccinationsHistoryModel extends BaseModel implements Serializable {
    private String vaccinName;
    private String vaccinDes;
    private long injectionDate;
    private int injectionStatus;

    private ProfileChildModel child;

    /**
     * Constructor
     *
     * @param child         ProfileChildModel
     * @param vaccinName    String
     * @param injectionDate long
     * @param vaccinDes     String
     */
    public VaccinationsHistoryModel(ProfileChildModel child, String vaccinName, long injectionDate, String vaccinDes) {
        this.child = child;
        this.vaccinName = vaccinName;
        this.injectionDate = injectionDate;
        this.vaccinDes = vaccinDes;
    }

    public String getChildInfo() {
        String childInfo = "";
        if (child != null) {
            childInfo = String.format("%s (%s)", child.getName(), "3T");
        } else {
            childInfo = String.format("%s (%s)", "Linh Ngọc", "3T");
        }

        return childInfo;
    }

    public String getVaccinName() {
        return vaccinName;
    }

    public void setVaccinName(String vaccinName) {
        this.vaccinName = vaccinName;
    }

    public String getVaccinDes() {
        return vaccinDes;
    }

    public void setVaccinDes(String vaccinDes) {
        this.vaccinDes = vaccinDes;
    }

    public long getInjectionDate() {
        return injectionDate;
    }

    public void setInjectionDate(long injectionDate) {
        this.injectionDate = injectionDate;
    }

    public ProfileChildModel getChild() {
        return child;
    }

    public void setChild(ProfileChildModel child) {
        this.child = child;
    }

    public int getInjectionStatus() {
        return injectionStatus;
    }

    public void setInjectionStatus(int injectionStatus) {
        this.injectionStatus = injectionStatus;
    }
}

