package com.fitj.facades;
import com.fitj.classes.Pack;
import com.fitj.dao.DAOPack;
import com.fitj.dao.factory.FactoryDAO;

import java.util.List;

/**
 * Facade utilisée pour les opérations sur les packs
 * @see Facade
 * @author Paul Merceur
 */


public class FacadePack extends Facade {

    /**
     * Instance de la facade, utilisée pour le pattern Singleton
     */
    protected DAOPack daoPack;

    private static FacadePack instance = null;

    /**
     * Constructeur de la FacadePack
     */
    protected FacadePack(){
        this.daoPack = FactoryDAO.getInstance().getDAOPack();
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

    public List<Pack> getAllPacks() throws Exception {
        return this.daoPack.getAllPack();
    }

    public List<Pack> getAllPacksByIdClient(int id) throws Exception {
        return this.daoPack.getAllPackByCoach(id);
    }

    public List<Pack> getAllPacksByIdCoach(int id) throws Exception {
        return this.daoPack.getAllPackByCoach(id);
    }
}
