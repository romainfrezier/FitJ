package com.fitj.classes;

import com.fitj.enums.Sexe;
import com.fitj.interfaces.DemandReceiver;
import com.fitj.interfaces.PaymentReceiver;

import java.util.*;

/**
 * Une classe qui représente un coach.
 * Un coach est un utilisateur qui peut proposer des produits.
 */
public class Coach extends Client implements PaymentReceiver, DemandReceiver {

    /**
     * Default constructor
     */
    public Coach(String email, String pseudo, double poids, String image, int taille, Sexe sexe, String password, int id) {
        super(email,pseudo,poids,image,taille,sexe,password,id);
    }


    /**
     * Reçoit une demande.
     *
     * @param content Demande, le contenu de la demande
     */
    @Override
    public void receive(Demande content) {

    }

    /**
     * Reçoit le paiement.
     *
     * @param content Paiement, le contenu du paiement
     */
    @Override
    public void receive(Paiement content) {

    }
}