package com.fitj.classes;

import java.util.*;

/**
 * Classe qui représente un pack.
 * Un pack est un ensemble de produits.
 * @author Paco Munarriz, Etienne Tillier
 */
public class Pack extends Produit {

    private List<Produit> listeProduit;

    /**
     * Constructeur par défaut
     */
    public Pack(int id, String nom, String description, double prix, Coach coach){
        super(id, nom, description, prix, coach);
    }

    /**
     * Constructeur avec des produits
     */
    public Pack(int id, String nom, String description, double prix, Coach coach, List<Produit> listeProduit) {
        super(id, nom, description, prix, coach);
        this.listeProduit = listeProduit;
    }

    public Pack(int idpack) {
        super(idpack);
    }

    public Pack(int idpack, String nom) {
        super(idpack, nom);
    }

    /**
     * @return la liste des produits
     */
    public List<Produit> getListeProduit() {
        return listeProduit;
    }

    /**
     * Set la liste des produits
     * @param listeProduit la liste des produits
     */
    public void setListeProduit(List<Produit> listeProduit) {
        this.listeProduit = listeProduit;
    }

    /**
     * Ajoute un produit au pack
     * @param produit Produit, le produit à ajouter au pack
     */
    public void ajouterProduit(Produit produit){
        this.listeProduit.add(produit);
    }


    /**
     * Retire un produit du pack
     * @param produit Produit, le produit à retirer du pack
     */
    public void retirerProduit(Produit produit){
        this.listeProduit.remove(produit);
    }


}