package com.fitj.classes;

import com.fitj.enums.Sexe;
import com.fitj.interfaces.DemandReceiver;
import com.fitj.interfaces.PaymentReceiver;

import java.util.*;

/**
 * Une classe qui représente un coach.
 * Un coach est un utilisateur qui peut proposer des produits.
 * @author Etienne Tillier, Romain Frezier
 */
public class Coach extends Client implements PaymentReceiver, DemandReceiver {

    /**
     * Le solde du coach
     */
    private double solde;

    /**
     * Default constructor
     */
    public Coach(String email, String pseudo, double poids, String image, int taille, Sexe sexe, String password, int id, boolean ban) {
        super(email,pseudo,poids,image,taille,sexe,password,id, ban);
    }

    public Coach(String email, String pseudo, double poids, String photo, int taille, Sexe sexe, String password, int id, ArrayList<Sport> listeSport, ArrayList<Commande> listeCommande, ArrayList<Materiel> listeMateriel, boolean ban) {
        super(email, pseudo, poids, photo, taille, sexe, password, id, listeSport, listeCommande, listeMateriel, ban);
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

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }
}