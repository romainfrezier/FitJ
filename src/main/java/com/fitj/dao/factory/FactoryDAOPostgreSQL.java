package com.fitj.dao.factory;

import com.fitj.dao.*;
import com.fitj.dao.postgresql.*;

/**
 * Classe de la factory de model PostgreSQL qui permettent de créer des model PostgreSQL
 *
 * Classe (singleton)
 *
 * @author Etienne Tillier
 */
public class FactoryDAOPostgreSQL extends FactoryDAO {

    public FactoryDAOPostgreSQL(){
        super();

    }


    /**
     * @return l'instance du modelClientPostgreSQL
     */
    public DAOClientPostgreSQL getModelClient(){
        return new DAOClientPostgreSQL();
    }

    /**
     * @return l'instance du modelSeancePostgreSQL
     */
    @Override
    public DAOSeance getModelSeance() {
        return new DAOSeancePostgreSQL();
    }

    /**
     * @return l'instance du modelPaiementPostgreSQL
     */
    @Override
    public DAOPaiement getModelPaiement() {
        return new DAOPaiementPostgreSQL();
    }

    /**
     * @return l'instance du modelExercicePostgreSQL
     */
    @Override
    public DAOExercice getModelExercice() {
        return new DAOExercicePostgreSQL();
    }

    /**
     * @return l'instance du modelAlimentPostgreSQL
     */
    @Override
    public DAOAliment getModelAliment() {
        return new DAOAlimentPostgreSQL();
    }

    /**
     * @return l'instance du modelRecettePostgreSQL
     */
    @Override
    public DAORecette getModelRecette() {
        return new DAORecettePostgreSQL();
    }

    /**
     * @return l'instance du modelAvisPostgreSQL
     */
    @Override
    public DAOAvis getModelAvis() {
        return new DAOAvisPostgreSQL();
    }

    /**
     * @return l'instance du modelCommandePostgreSQL
     */
    @Override
    public DAOCommande getModelCommande() {
        return new DAOCommandePostgreSQL();
    }

    /**
     * @return l'instance du modelProgrammeNutritionPostgreSQL
     */
    @Override
    public DAOProgrammeNutrition getModelProgrammeNutrition() {
        return new DAOProgrammeNutritionPostgreSQL();
    }

    /**
     * @return l'instance du modelProgrammeSportifPostgreSQL
     */
    @Override
    public DAOProgrammeSportif getModelProgrammeSportif() {
        return new DAOProgrammeSportifPostgreSQL();
    }

    /**
     * @return l'instance du modelProgrammePersonnalisePostgreSQL
     */
    @Override
    public DAOProgrammePersonnalise getModelProgrammePersonnalise() {
        return new DAOProgrammePersonnalisePostgreSQL();
    }

    /**
     * @return l'instance du modelPackPostgreSQL
     */
    @Override
    public DAOPack getModelPack() {
        return new DAOPackPostgreSQL();
    }


    /**
     * @return l'instance du modelMaterielPostgreSQL
     */
    @Override
    public DAOMateriel getModelMateriel() {
        return new DAOMaterielPostgreSQL();
    }

    /**
     * @return l'instance du modelSportPostgreSQL
     */
    @Override
    public DAOSport getModelSport() {
        return new DAOSportPostgreSQL();
    }


}
