package com.fitj.facades;

import com.fitj.classes.Materiel;
import com.fitj.dao.DAOMateriel;
import com.fitj.dao.factory.FactoryDAO;
import kotlin.Pair;

import java.util.ArrayList;
import java.util.List;

public class FacadeMateriel extends Facade {

    protected DAOMateriel daoMateriel;

    private static FacadeMateriel instance = null;
    protected FacadeMateriel(){
        this.daoMateriel = FactoryDAO.getInstance().getDAOMateriel();
    }

    public static FacadeMateriel getInstance(){
        if (instance == null){
            instance = new FacadeMateriel();
        }
        return instance;
    }

    public List<Materiel> getAllMateriels() throws Exception {
        return this.daoMateriel.getAllMateriel();
    }

    public void createMateriel(String materiel) throws Exception {
        this.daoMateriel.createMateriel(materiel);
    }

    public void deleteMateriel(int id) throws Exception {
        this.daoMateriel.supprimerMateriel(id);
    }

    public void updateMateriel(int id, String materiel) throws Exception {
        List<Pair<String, Object>> updateValue = new ArrayList<>();
        updateValue.add(new Pair<>("nom", materiel));
        this.daoMateriel.updateMateriel(updateValue, id);
    }

    public Materiel getMaterielById(int idMateriel) throws Exception {
        return this.daoMateriel.getMaterielById(idMateriel);
    }
}

