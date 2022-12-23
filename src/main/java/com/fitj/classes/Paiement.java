package com.fitj.classes;

import com.fitj.enums.PaiementType;
import com.fitj.interfaces.Sendable;


/**
 * Classe qui représente un paiement.
 * @author Paco Munarriz, Romain Frezier
 */
public class Paiement implements Sendable {

    /**
     * Constructeur par défaut
     */
    public Paiement() {
    }

    /**
     * L'id unique du paiement.
     */
    private int id;

    /**
     * Le montant du paiement.
     */
    private double montant;

    /**
     * Le type de paiement.
     */
    private PaiementType type;



    /**
     * Constructeur avec paramètres
     */
    public Paiement(int id, double montant, PaiementType type) {
        this.id = id;
        this.montant = montant;
        this.type = type;
    }

    /**
     * @param destinataire: Le coach qui reçoit le paiement.
     * Envoie le montant du paiement au destinataire.
     */
    public void send(Client destinataire) {
        // TODO implement here
    }

    public int getId() {
        return id;
    }

    public double getMontant() {
        return montant;
    }

    public PaiementType getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public void setType(PaiementType type) {
        this.type = type;
    }
}