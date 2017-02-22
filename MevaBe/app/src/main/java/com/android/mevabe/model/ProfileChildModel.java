package com.android.mevabe.model;

import java.io.Serializable;

/**
 * Created by leducthuy on 1/18/17.
 */
public class ProfileChildModel implements Serializable {
    private int id;
    private String name;
    private long dateOfBirth;
    private int gender;

    public ProfileChildModel() {
        
    }

    public ProfileChildModel(int id, String name, long dateOfBirth, int gender) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
