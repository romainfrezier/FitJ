package com.fitj.comparators;

import com.fitj.classes.Commande;

public class CommandeComparator implements java.util.Comparator<Commande> {
    public CommandeComparator() {
        super();
    }

    @Override
    public int compare(Commande o1, Commande o2) {
        return o1.getDate().compareTo(o2.getDate());
    }
}
