package com.fitj.facades;

/**
 * Facade utilisée pour les opérations sur les packs
 * @see Facade
 * @author Paul Merceur
 */
public class FacadePack extends Facade {

    /**
     * Instance de la facade, utilisée pour le pattern Singleton
     */
    private static FacadePack instance = null;

    /**
     * Constructeur de la FacadePack
     */
    protected FacadePack(){

    }

    /**
     * Méthode permettant de récupérer l'instance de la FacadePack
     * @return FacadePack, l'instance de la FacadePack
     */
    public static FacadePack getInstance(){
        if (instance == null){
            instance = new FacadePack();
        }
        return instance;
    }
}
