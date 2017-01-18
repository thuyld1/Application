package com.android.mevabe.model;

import java.io.Serializable;

/**
 * Created by leducthuy on 1/18/17.
 */
public class ProfileChildModel implements Serializable {
    private String name;
    private long dateOfBirth;
    private int gender;

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
}
