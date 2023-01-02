package com.fitj.classes;

import java.util.*;

/**
 * Cette classe représente un ProgrammePersonnalise, qui est un type de Programme, personnalisé pour un client.
 * @see Programme
 * @author Paul Merceur, Etienne Tillier
 */
public class ProgrammePersonnalise extends Service {

    /**
     * La liste des programmes
     */
    private List<Programme> listeProgrammes;

    /**
     * La demande du programme personnalisé
     */
    private Demande demande;



    /**
     * Constructeur par défaut
     */
    public ProgrammePersonnalise(int id, String nom, String description, double prix, Coach coach, Demande demande, ArrayList<Programme> listeProgrammes) {
        super(id, nom, description, prix, coach);
        this.demande = demande;
        this.listeProgrammes = listeProgrammes;
    }

    /**
     * Constructeur pour afficher le détail
     */
    public ProgrammePersonnalise(int id, String nom, String description, double prix, Coach coach, ArrayList<Programme> listeProgrammes) {
        super(id, nom, description, prix, coach);
        this.demande = null;
        this.listeProgrammes = listeProgrammes;
    }

    /**
     * Constructeur avec demande
     */
    public ProgrammePersonnalise(int id, String nom, String description, double prix, Coach coach, Demande demande) {
        super(id, nom, description, prix, coach);
        this.demande = demande;
        this.listeProgrammes = new ArrayList<>();
    }

    /**
     * Constructeur pour l'affichage sur le profil du coach
     */
    public ProgrammePersonnalise(int id, String nom, String description, double prix, Coach coach) {
        super(id, nom, description, prix, coach);
        this.demande = null;
        this.listeProgrammes = new ArrayList<>();
    }

    public ProgrammePersonnalise(int idProduit) {
        super(idProduit);
    }

    public ProgrammePersonnalise(int idProduit, String nom, String description, double prix, Demande demande) {
        super(idProduit, nom, description, prix);
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
     * @return la liste des programmes du programme personnalisé
     */
    public List<Programme> getListeProgrammes() {
        return listeProgrammes;
    }

    /**
     * Set la liste des programmes du programme personnalisé
     * @param listeProgrammes List<Programme>, la liste des programmes du programme personnalisé
     */
    public void setListeProgrammes(List<Programme> listeProgrammes) {
        this.listeProgrammes = listeProgrammes;
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