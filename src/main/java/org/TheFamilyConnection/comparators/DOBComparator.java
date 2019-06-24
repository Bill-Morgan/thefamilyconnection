package org.TheFamilyConnection.comparators;

import org.TheFamilyConnection.models.User;

import java.util.Comparator;

public class DOBComparator implements Comparator<User> {

    @Override
    public int compare(User o1, User o2) {
        return o1.getDob().compareTo(o2.getDob());
    }

}
