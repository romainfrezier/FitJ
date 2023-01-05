package com.fitj.facades;

import com.fitj.classes.Pack;
import com.fitj.dao.DAOPack;
import com.fitj.dao.factory.FactoryDAO;

import java.util.List;

public class FacadePack extends Facade {

    protected DAOPack daoPack;

    private static FacadePack instance = null;
    protected FacadePack(){
        this.daoPack = FactoryDAO.getInstance().getDAOPack();
    }

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
