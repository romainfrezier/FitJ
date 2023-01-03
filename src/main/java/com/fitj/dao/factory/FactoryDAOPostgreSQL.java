package com.fitj.dao.factory;

import com.fitj.dao.*;
import com.fitj.dao.postgresql.*;

/**
 * Classe de la factory de model PostgreSQL qui permettent de créer des model PostgreSQL
 * Classe (singleton)
 *
 * @author Etienne Tillier, Romain Frezier
 */
public class FactoryDAOPostgreSQL extends FactoryDAO {

    public FactoryDAOPostgreSQL(){
        super();

    }


    /**
     * @return l'instance du modelClientPostgreSQL
     */
    @Override
    public DAOClient getDAOClient() {
        return new DAOClientPostgreSQL();
    }

    /**
     * @return l'instance du modelSeancePostgreSQL
     */
    @Override
    public DAOSeance getDAOSeance() {
        return new DAOSeancePostgreSQL();
    }

    /**
     * @return l'instance du modelPaiementPostgreSQL
     */
    @Override
    public DAOPaiement getDAOPaiement() {
        return new DAOPaiementPostgreSQL();
    }

    /**
     * @return l'instance du modelExercicePostgreSQL
     */
    @Override
    public DAOExercice getDAOExercice() {
        return new DAOExercicePostgreSQL();
    }

    /**
     * @return l'instance du modelAlimentPostgreSQL
     */
    @Override
    public DAOAliment getDAOAliment() {
        return new DAOAlimentPostgreSQL();
    }

    /**
     * @return l'instance du modelRecettePostgreSQL
     */
    @Override
    public DAORecette getDAORecette() {
        return new DAORecettePostgreSQL();
    }

    /**
     * @return l'instance du modelAvisPostgreSQL
     */
    @Override
    public DAOAvis getDAOAvis() {
        return new DAOAvisPostgreSQL();
    }

    /**
     * @return l'instance du modelCommandePostgreSQL
     */
    @Override
    public DAOCommande getDAOCommande() {
        return new DAOCommandePostgreSQL();
    }

    /**
     * @return l'instance du modelProgrammeNutritionPostgreSQL
     */
    @Override
    public DAOProgrammeNutrition getDAOProgrammeNutrition() {
        return new DAOProgrammeNutritionPostgreSQL();
    }

    /**
     * @return l'instance du modelProgrammeSportifPostgreSQL
     */
    @Override
    public DAOProgrammeSportif getDAOProgrammeSportif() {
        return new DAOProgrammeSportifPostgreSQL();
    }

    /**
     * @return l'instance du modelProgrammePersonnalisePostgreSQL
     */
    @Override
    public DAOProgrammePersonnalise getDAOProgrammePersonnalise() {
        return new DAOProgrammePersonnalisePostgreSQL();
    }

    /**
     * @return l'instance du modelPackPostgreSQL
     */
    @Override
    public DAOPack getDAOPack() {
        return new DAOPackPostgreSQL();
    }


    /**
     * @return l'instance du modelMaterielPostgreSQL
     */
    @Override
    public DAOMateriel getDAOMateriel() {
        return new DAOMaterielPostgreSQL();
    }

    /**
     * @return l'instance du modelSportPostgreSQL
     */
    @Override
    public DAOSport getDAOSport() {
        return new DAOSportPostgreSQL();
    }

    @Override
    public DAODemande getDAODemande() {
        return new DAODemandePostgreSQL();
    }

    @Override
    public DAONotification getDAONotification() {
        return new DAONotificationPostgreSQL();
    }


}
