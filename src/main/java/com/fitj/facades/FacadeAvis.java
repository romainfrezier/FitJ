package com.fitj.facades;

/**
 * Facade utilisée pour les opérations sur les avis
 * @see Facade
 * @author Paul Merceur
 */
public class FacadeAvis extends Facade {

    /**
     * Instance de la facade, utilisée pour le pattern Singleton
     */
    private static FacadeAvis instance = null;

    /**
     * Constructeur de la FacadeAvis
     */
    protected FacadeAvis(){

    }

    /**
     * Méthode permettant de récupérer l'instance de la FacadeAvis
     * @return FacadeAvis, l'instance de la FacadeAvis
     */
    public static FacadeAvis getInstance(){
        if (instance == null){
            instance = new FacadeAvis();
        }
        return instance;
    }
}
