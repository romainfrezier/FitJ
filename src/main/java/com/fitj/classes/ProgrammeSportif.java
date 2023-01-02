package com.fitj.classes;

import com.fitj.enums.ProgrammeType;

import java.util.*;

/**
 * Cette classe représente un ProgrammeSportif, qui est un type de Programme.
 * @see Programme
 * @author Paul Merceur, Etienne Tillier
 */
public class ProgrammeSportif extends Programme {

    private List<Seance> listeSeance;

    /**
     * Constructeur par défaut
     */
    public ProgrammeSportif(int id, String nom, String description, double prix, ProgrammeType type, int nbMois, Coach coach){
        super(id, nom, description, prix, type, nbMois, coach);
        this.listeSeance = new ArrayList<>();
    }

    /**
     * Constructeur avec les séances
     */
    public ProgrammeSportif(int id, String nom, String description, double prix, ProgrammeType type, int nbMois, Coach coach, List<Seance> listeSeance) {
        super(id, nom, description, prix, type, nbMois, coach);
        this.listeSeance = listeSeance;
    }

    public ProgrammeSportif(int idProduit) {
        super(idProduit);
    }

    public ProgrammeSportif(int idProduit, String nom, String description, double prix) {
        super(idProduit, nom, description, prix);
    }

    /**
     * @return la liste des séances du programme
     */
    public List<Seance> getListeSeance() {
        return listeSeance;
    }

    /**
     * Ajoute une séance au programme
     * @param seance Seance, la seance à ajouter au programme
     */
    public void ajouterSeance(Seance seance){
        this.listeSeance.add(seance);
    }

    /**
     * Retire une séance du programme
     * @param seance Seance, la seance à retirer du programme
     */
    public void retirerSeance(Seance seance){
        this.listeSeance.remove(seance);
    }

    /**
     * Reçoit un commentaire pour le programme sportif.
     *
     * @param content Avis, le contenu du commentaire
     */
    @Override
    public void receive(Avis content) {

    }

    /**
     * Envoie le programme sportif à un destinataire.
     *
     * @param destinataire Client, le destinataire à qui envoyer le programme
     */
    @Override
    public void send(Client destinataire) {

    }
}