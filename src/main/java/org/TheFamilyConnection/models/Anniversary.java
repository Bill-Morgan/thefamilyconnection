package org.TheFamilyConnection.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.TheFamilyConnection.comparators.GenderComparator;
import org.springframework.format.annotation.DateTimeFormat;

public class Anniversary {

    String date;

    String names;

    private static GenderComparator comparatorGender = new GenderComparator();

    public Anniversary() {
    }

    public Anniversary(User user) {
        this.date = user.getAnniversaryDay();
        this.names = buildName(user);
    }

    private static String buildName(User user){
        String retValue;
        List<User> couple = new ArrayList<>();
        couple.add(user);
        couple.add(user.getSpouse());
        couple.sort(comparatorGender.reversed());
        if (couple.get(0).getLName().equals(couple.get(1).getLName())) {
            retValue = couple.get(0).getFName() + " & " + couple.get(1).getFName() + " " + couple.get(1).getLName();
        } else {
            retValue= couple.get(0).getFName() + " " + couple.get(0).getLName() + " & " + couple.get(1).getFName() + " " + couple.get(1).getLName();
        }
        return retValue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }
}
