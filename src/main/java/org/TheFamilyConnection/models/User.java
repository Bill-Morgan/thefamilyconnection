package org.TheFamilyConnection.models;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class User {

    private static Integer nextId = 1;

    @NotNull
    private Integer id;

    private String primaryEmail;

    private String password;

    @NotNull
    private String mlName;

    @NotNull
    private String fName;

    @NotNull
    private String mName;

    private String lName;

    private Date dob;

    private String placeOfBirth;

    private Date dod;

    private String address;

    private String city;

    private String state;

    private String zip;

    public User() {
        this.id = nextId;
        nextId++;
    }

    private Integer newId(){
        Integer retval = nextId;
        nextId++;
        return retval;
    }

    public User(String fName, String mName) {
        this.id = newId();
        this.fName = fName;
        this.mName = mName;
    }

    public User(String primaryEmail, String password, String mlName, String fName, String mName, String lName, Date dob, String placeOfBirth, Date dod, String address, String city, String state, String zip) {
        this.id = newId();
        this.primaryEmail = primaryEmail;
        this.password = password;
        this.mlName = mlName;
        this.fName = fName;
        this.mName = mName;
        this.lName = lName;
        this.dob = dob;
        this.placeOfBirth = placeOfBirth;
        this.dod = dod;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
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
