package org.TheFamilyConnection.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    private String primaryEmail;

    private String password;

    @NotNull
    private String mlName;

    @NotNull
    private String fName;

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
    }

    public User(String mlName, String fName) {
        this.mlName = mlName;
        this.fName = fName;
    }


}
