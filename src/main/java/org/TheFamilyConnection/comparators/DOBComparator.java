package org.TheFamilyConnection.comparators;

import org.TheFamilyConnection.models.User;

import java.util.Comparator;

public class DOBComparator implements Comparator<User> {

    @Override
    public int compare(User o1, User o2) {
        if (o2.getDob() == null) {
            if (o1.getDob() == null) {
                return 0;
            }
            return 1;
        }
        if (o1.getDob() == null) {
            return -1;
        }
        return o1.getDob().compareTo(o2.getDob());
    }

}
