package com.fitj.facades;

import com.fitj.classes.Aliment;
import com.fitj.classes.Aliment;
import com.fitj.dao.DAOAliment;
import com.fitj.dao.DAOAliment;
import com.fitj.dao.factory.FactoryDAO;
import kotlin.Pair;

import java.util.ArrayList;
import java.util.List;

public class FacadeAliment extends Facade {

    protected DAOAliment daoAliment;

    private static FacadeAliment instance = null;
    protected FacadeAliment(){
        this.daoAliment = FactoryDAO.getInstance().getDAOAliment();
    }

    public static FacadeAliment getInstance(){
        if (instance == null){
            instance = new FacadeAliment();
        }
        return instance;
    }

    public List<Aliment> getAllAliments() throws Exception {
        return this.daoAliment.getAllAliments();
    }

    public void createAliment(String aliment) throws Exception {
        this.daoAliment.createAliment(aliment);
    }

    public void deleteAliment(int id) throws Exception {
        this.daoAliment.supprimerAliment(id);
    }

    public void updateAliment(int id, String aliment) throws Exception {
        List<Pair<String, Object>> updateValue = new ArrayList<>();
        updateValue.add(new Pair<>("nom", aliment));
        this.daoAliment.updateAliment(updateValue, id);
    }

    public Aliment getAlimentById(int idAliment) throws Exception {
        return this.daoAliment.getAlimentById(idAliment);
    }
}
