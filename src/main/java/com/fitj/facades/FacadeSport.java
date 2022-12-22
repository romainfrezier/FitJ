package com.fitj.facades;

import com.fitj.classes.Sport;
import com.fitj.dao.DAOSport;
import com.fitj.dao.factory.FactoryDAO;
import kotlin.Pair;
import java.util.*;

public class FacadeSport extends Facade {

    protected DAOSport daoSport;

    private static FacadeSport instance = null;
    protected FacadeSport(){
        this.daoSport = FactoryDAO.getInstance().getModelSport();
    }

    public static FacadeSport getInstance(){
        if (instance == null){
            instance = new FacadeSport();
        }
        return instance;
    }

    public List<Sport> getAllSports() throws Exception {
        return this.daoSport.getAllSport();
    }

    public void createSport(String sport) throws Exception {
        this.daoSport.createSport(sport);
    }

    public void deleteSport(int id) throws Exception {
        this.daoSport.supprimerSport(id);
    }

    public void updateSport(int id, String sport) throws Exception {
        List<Pair<String, Object>> updateValue = new ArrayList<>();
        updateValue.add(new Pair<>("nom", sport));
        this.daoSport.updateSport(updateValue, id);
    }

    public Sport getSportById(int idSport) throws Exception {
        return this.daoSport.getSportById(idSport);
    }
}
