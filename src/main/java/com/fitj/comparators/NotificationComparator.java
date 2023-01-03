package com.fitj.comparators;

import com.fitj.classes.Notification;

public class NotificationComparator implements java.util.Comparator<Notification> {
    public NotificationComparator() {
        super();
    }

    @Override
    public int compare(Notification o1, Notification o2) {
        return o1.getDate().compareTo(o2.getDate());
    }
}

