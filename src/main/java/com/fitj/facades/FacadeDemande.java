package com.fitj.facades;

/**
 * Facade utilisée pour les opérations sur les packs
 * @see Facade
 * @author Paul Merceur
 */
public class FacadeDemande extends Facade {

    /**
     * Instance de la facade, utilisée pour le pattern Singleton
     */
    private static FacadeDemande instance = null;

    /**
     * Constructeur de la FacadeDemande
     */
    protected FacadeDemande(){

    }

    /**
     * Méthode permettant de récupérer l'instance de la FacadeDemande
     * @return FacadeDemande, l'instance de la FacadeDemande
     */
    public static FacadeDemande getInstance(){
        if (instance == null){
            instance = new FacadeDemande();
        }
        return instance;
    }
}
