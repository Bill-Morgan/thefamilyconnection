package org.TheFamilyConnection.comparators;

import org.TheFamilyConnection.models.Anniversary;
import org.TheFamilyConnection.models.User;

import java.util.Comparator;

public class AnniversaryComparator implements Comparator<Anniversary> {

    @Override
    public int compare(Anniversary o1, Anniversary o2) {
        return o1.getDate().compareTo(o2.getDate());
    }
}
