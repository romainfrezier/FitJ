package com.fitj.facades;

import com.fitj.classes.Sport;
import com.fitj.dao.DAOSport;
import com.fitj.dao.factory.FactoryDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

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

    public ObservableList<Sport> getAllSports() throws Exception {
        return FXCollections.observableArrayList(this.daoSport.getAllSport(new ArrayList<>()));
    }
}
