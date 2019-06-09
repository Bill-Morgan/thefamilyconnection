package org.TheFamilyConnection.comparators;

import org.TheFamilyConnection.models.User;

import java.util.Comparator;

public class NameComparator implements Comparator<User> {

    @Override
    public int compare(User o1, User o2){
        int value1 = o1.getLName().compareTo(o2.getLName());
        if (value1 == 0) {
            int value2 = o1.getFName().compareTo(o2.getFName());
            if (value2 == 0) {
                int value3 = o1.getMName().compareTo(o2.getMName());
                if (value3 == 0) {
                    return(o1.getSuffix().compareTo(o2.getSuffix()));
                }
                return value3;
            }
            return value2;
        }
        return value1;
    }
}
