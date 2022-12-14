package com.fitj.classes;

import java.util.*;

/**
 * Cette classe représente une Seance, qui compose un ProgrammeSportif et qui est un type de Produit.
 * @see ProgrammeNutrition
 * @see Produit
 * @author Paul Merceur
 */
public class Seance extends Produit {

    /**
     * Constructeur par défaut
     */
    public Seance() {
    }

    /**
     * Reçoit un commentaire pour la séance.
     *
     * @param content Avis, le contenu du commentaire
     */
    @Override
    public void receive(Avis content) {

    }

    /**
     * Envoie la séance à un destinataire.
     *
     * @param destinataire Client, le destinataire à qui envoyer le programme
     */
    @Override
    public void send(Client destinataire) {

    }
}