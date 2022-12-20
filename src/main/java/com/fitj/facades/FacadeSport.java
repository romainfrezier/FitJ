package com.fitj.facades;

import com.fitj.classes.Sport;
import com.fitj.dao.DAOSport;
import com.fitj.dao.factory.FactoryDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

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
        return this.daoSport.getAllSports();
    }

    public void createSport(String sport) throws Exception {
        this.daoSport.createSport(sport);
    }
}
