package com.fitj.classes;


/**
 * Cette classe représente un Service, qui est un type de Produit.
 * @see Produit
 * @author Paul Merceur, Etienne Tillier
 */
public abstract class Service extends Produit {

    /**
     * Constructeur par défaut
     */
    public Service(int id, String nom, String description, double prix, Coach coach) {
        super(id, nom, description, prix, coach);
    }


    public Service(int idService) {
        super(idService);
    }

    public Service(int idProduit, String nom, String description, double prix) {
        super(idProduit, nom, description, prix);
    }
}