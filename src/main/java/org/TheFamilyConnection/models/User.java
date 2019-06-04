package org.TheFamilyConnection.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    private String primaryEmail;

    private String password;

    @NotNull
    @Size(min=1, message = "Birth Last Name Required")
    private String bLName;

    @NotNull
    @Size(min=1, message = "Birth First Name Required")
    private String bFName;

    private String bMName;

    private String bSuffix;

    private String cLName;

    private String cFName;

    private String cMName;

    private String cSuffix;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    private String pob;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dod;

    private String address;

    private String city;

    private String state;

    private String zip;

    @Column(columnDefinition = "int default 0")
    private Integer admin;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date anniversary;

    @OneToOne
    private User father;

    @OneToOne
    private User mother;

    @OneToOne
    private User spouse;

    @OneToMany
    @JoinColumn(name = "userId")
    private List<EmailAddress> emails = new ArrayList<>();

    public User() {
    }

    public static boolean isNullOrEmpty(String str) {
        if(str != null && !str.isEmpty())
            return false;
        return true;
    }

    public Integer getId() {
        return id;
    }

    public Integer getAdmin() {
        return admin;
    }

    public void setAdmin(Integer admin) {
        this.admin = admin;
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

    public String getbLName() {
        return bLName;
    }

    public void setbLName(String bLName) {
        this.bLName = bLName;
    }

    public String getbFName() {
        return bFName;
    }

    public void setbFName(String bFName) {
        this.bFName = bFName;
    }

    public String getbMName() {
        return bMName;
    }

    public void setbMName(String bMName) {
        this.bMName = bMName;
    }

    public String getbSuffix() {
        return bSuffix;
    }

    public void setbSuffix(String bSuffix) {
        this.bSuffix = bSuffix;
    }

    public String getcFullName() {
        String retValue = "";
        if (!isNullOrEmpty(cFName)) {retValue = cFName;}
        if (!isNullOrEmpty(cMName)) {retValue += (" " + cMName);}
        if (!isNullOrEmpty(cLName)) {retValue += (" " + cLName);}
        if (!isNullOrEmpty(cSuffix)) {retValue += (" " + cSuffix);}
        return retValue;
    }

    public String getbFullName() {
        String retValue = "";
        if (!isNullOrEmpty(bFName)) {retValue = bFName;}
        if (!isNullOrEmpty(bMName)) {retValue += (" " + bMName);}
        if (!isNullOrEmpty(bLName)) {retValue += (" " + bLName);}
        if (!isNullOrEmpty(bSuffix)) {retValue += (" " + bSuffix);}
        return retValue;
    }

    public String getFullName() {
        String retValue = getcFullName();
        if (isNullOrEmpty(retValue)) {retValue = getbFullName();}
        return retValue;
    }

    public String getcLName() {
        return cLName;
    }

    public void setcLName(String cLName) {
        this.cLName = cLName;
    }

    public String getcFName() {
        return cFName;
    }

    public void setcFName(String cFName) {
        this.cFName = cFName;
    }

    public String getcMName() {
        return cMName;
    }

    public void setcMName(String cMName) {
        this.cMName = cMName;
    }

    public String getcSuffix() {
        return cSuffix;
    }

    public void setcSuffix(String cSuffix) {
        this.cSuffix = cSuffix;
    }

    public Date getAnniversary() {
        return anniversary;
    }

    public void setAnniversary(Date anniversary) {
        this.anniversary = anniversary;
    }

    public User getSpouse() {
        return spouse;
    }

    public void setSpouse(User spouse) {
        this.spouse = spouse;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getPob() {
        return pob;
    }

    public void setPob(String pob) {
        this.pob = pob;
    }

    public User getFather() {
        return father;
    }

    public void setFather(User father) {
        this.father = father;
    }

    public User getMother() {
        return mother;
    }

    public void setMother(User mother) {
        this.mother = mother;
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
