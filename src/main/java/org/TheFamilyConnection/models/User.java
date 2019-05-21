package org.TheFamilyConnection.models;

import java.util.Date;

public class User {

    private static Integer nextId = 1;

    private Integer id;

    private String primaryEmail;

    private String password;

    private String lName;

    private String fName;

    private String mName;

    private Date dob;

    private Date dod;

    private String address;

    private String city;

    private String state;

    private String zip;

    public User() {
        this.id = nextId;
        nextId++;
    }

    public User(String lName, String fName) {
        this.lName = lName;
        this.fName = fName;
        this.id = nextId;
        nextId++;
    }

    public Integer getId() {
        return id;
    }

    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Date getDod() {
        return dod;
    }

    public void setDod(Date dod) {
        this.dod = dod;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
