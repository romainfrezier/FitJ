package com.fitj.comparators;

import com.fitj.classes.Notification;

/**
 * Comparateur pour comparer deux instances de {@link Notification}.
 *
 * <p>Compare deux objets {@code Notification} sur la base de leur date.
 */
public class NotificationComparator implements java.util.Comparator<Notification> {
    /**
     * Construit un nouveau {@code NotificationComparator}.
     */
    public NotificationComparator() {
        super();
    }

    /**
     * Compare l'orde de ses deux arguments.
     *
     * @param o1 la première {@code Notification} à comparer
     * @param o2 la seconde {@code Notification} à comparer
     * @return un nombre entier négatif, zéro, ou un nombre entier positif
     *         selon que la date du premier argument est inférieur, égal ou supérieur
     *         à la date du second.
     */
    @Override
    public int compare(Notification o1, Notification o2) {
        return o1.getDate().compareTo(o2.getDate());
    }
}

