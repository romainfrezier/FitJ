package com.fitj.classes;

import java.util.*;

/**
 * Cette classe représente un ProgrammePersonnalise, qui est un type de Programme, personnalisé pour un client.
 * @see Programme
 * @author Paul Merceur, Etienne Tillier
 */
public class ProgrammePersonnalise extends Service {

    private Demande demande;

    /**
     * Constructeur par défaut
     */
    public ProgrammePersonnalise(int id, String nom, String description, double prix, Coach coach, Demande demande) {
        super(id, nom, description, prix, coach);
        this.demande = demande;
    }

    /**
     * Set la demande du programme
     * @param demande Demande, la demande du programme
     */
    public void setDemande(Demande demande){
        this.demande = demande;
    }

    /**
     * @return la demande du programme
     */
    public Demande getDemande(){
        return this.demande;
    }

    /**
     * Reçoit un commentaire pour le programme personnalisé.
     *
     * @param content Avis, le contenu du commentaire
     */
    @Override
    public void receive(Avis content) {

    }

    /**
     * Envoie le programme personnalisé à un destinataire.
     *
     * @param destinataire Client, le destinataire à qui envoyer le programme
     */
    @Override
    public void send(Client destinataire) {

    }
}