package org.TheFamilyConnection.models;

import java.util.Date;
import org.TheFamilyConnection.models.User;

public class Anniversary {

    Date date;

    String names;

    User user1;

    User user2;

    public Anniversary() {
    }

    public Anniversary(User user) {
        this.user1 = user;
        this.user2 = user.getSpouse();
        this.date = user.getAnniversary();
        this.names = buildName(user);
    }

    private static String buildName(User user){
        return "";
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public void setNames(String n1, String n2){
        this.names = n1 + " " + n2;
    }
}
