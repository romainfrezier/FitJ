package com.fitj.comparators;

import com.fitj.classes.Commande;

/**
 * Comparateur pour comparer deux instances de {@link Commande}.
 *
 * <p>Compare deux objets {@code Commande} sur la base de leur date.
 */
public class CommandeComparator implements java.util.Comparator<Commande> {
    /**
     * Construit un nouveau {@code CommandeComparator}.
     */
    public CommandeComparator() {
        super();
    }

    /**
     * Compare l'ordre des deux arguments.
     *
     * @param o1 la première {@code Commande} à comparer
     * @param o2 la seconde {@code Commande} à comparer
     * @return un nombre entier négatif, zéro, ou un nombre entier positif
     *         selon que la date de la première commande est inférieur, égal ou supérieur
     *        à la seconde commande.
     */
    @Override
    public int compare(Commande o1, Commande o2) {
        return o1.getDate().compareTo(o2.getDate());
    }
}


