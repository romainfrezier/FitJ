package com.fitj.classes;

import com.fitj.enums.Sexe;

import java.util.*;

/**
 * Une classe qui représente un administrateur.
 * Un administrateur est Coach qui a des droits supplémentaires.
 * @author Paco Munarriz
 */
public class Admin extends Coach {

    /**
     * Constructeur par défaut
     */
    public Admin(String email, String pseudo, double poids, String image, int taille, Sexe sexe, String password, int id, boolean ban) {
        super(email,pseudo,poids,image,taille,sexe,password,id, ban);
    }

    public Admin(String email, String pseudo, double poids, String photo, int taille, Sexe sexe, String password, int id, ArrayList<Sport> listeSport, ArrayList<Commande> listeCommande, ArrayList<Materiel> listeMateriel, boolean ban) {
        super(email, pseudo, poids, photo, taille, sexe, password, id, listeSport, listeCommande, listeMateriel, ban);
    }

    /**
     * Ajouter un sport.
     */
    public void addSport() {
    }

    /**
     * Supprimer un sport.
     */
    public void removeSport() {
    }

    /**
     * Ajouter un materiel.
     */
    public void addMateriel() {
    }

    /**
     * Supprimer un materiel.
     */
    public void removeMateriel() {
    }

    /**
     * Supprimer un utilisateur.
     */
    public void removeUtilisateur() {
    }

    /**
     * Supprimer un avis.
     */
    public void removeAvis() {
    }

    /**
     * Supprimer un produit.
     */
    public void removeProduit() {
    }

    /**
     * Supprimer un paiement.
     */
    public void removePaiement() {
    }
}