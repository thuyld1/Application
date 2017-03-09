package com.android.mevabe.model;

import com.android.mevabe.common.AppData;
import com.android.mevabe.common.utils.YearsOldUtil;

import java.io.Serializable;

/**
 * Created by leducthuy on 1/18/17.
 */
public class ProfileChildModel implements Serializable {
    private long id;
    private String name;
    private long dateOfBirth;
    private int gender;
    private String yearsOld;

    public ProfileChildModel() {

    }

    public ProfileChildModel(long id, String name, long dateOfBirth, int gender) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.yearsOld = YearsOldUtil.getYearsOld(AppData.context, dateOfBirth);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYearsOld() {
        return yearsOld;
    }

    public void setYearsOld(String yearsOld) {
        this.yearsOld = yearsOld;
    }
}
